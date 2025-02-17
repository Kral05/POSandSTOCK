package controller.porder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import dao.PorderDao;
import dao.impl.PorderDaoImpl;
import model.Porder;
import model.Member;
import service.StockService;
import service.impl.StockServiceImpl;
import util.Tool;
import util.StockManager;

public class PaymentUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField paymentField;
    private JLabel changeDetails;
    private JButton successButton; // 新增「交易成功」按鈕
    private int totalAmount;
    private List<String[]> orderData; // 接收訂單資料
    private String orderTimestamp; // 接收訂單時間
    private Member member; // 新增會員資料
    private StockService stockService = new StockServiceImpl(); // 新增 StockService
    

    /**
     * Create the frame.
     */
    public PaymentUI(int totalAmount, List<String[]> orderData, String orderTimestamp) {
        this.totalAmount = totalAmount; // 接收總金額
        this.orderData = orderData; // 接收訂單資料
        this.orderTimestamp = orderTimestamp; // 接收訂單時間
        this.member = (Member) Tool.read("member.txt"); // 讀取會員資料

        setTitle("確認付款");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 300, 350); // 調整視窗大小
        setLocationRelativeTo(null); // 置中顯示
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(247, 245, 238)); // 設置背景顏色
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS)); // 使用垂直佈局

        // 建立總金額顯示的標籤
        JLabel totalAmountLabel = new JLabel("總金額: " + totalAmount + " 元", SwingConstants.CENTER);
        totalAmountLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        totalAmountLabel.setBackground(new Color(247, 245, 238)); // 設置背景顏色
        totalAmountLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 置中對齊
        contentPane.add(totalAmountLabel);

        // 添加一個小的垂直間距
        contentPane.add(Box.createVerticalStrut(10)); // 調整間距

        // 建立輸入框和按鈕的面板
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(247, 245, 238));
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // 調整間距

        JLabel paymentLabel = new JLabel("付款金額：");
        paymentField = new JTextField(8); // 調整輸入框寬度
        JButton confirmButton = new JButton("確認");
        inputPanel.add(paymentLabel);
        inputPanel.add(paymentField);
        inputPanel.add(confirmButton);

        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // 置中對齊
        contentPane.add(inputPanel);

        // 添加一個小的垂直間距
        contentPane.add(Box.createVerticalStrut(10)); // 調整間距

        // 建立顯示找零結果的標籤
        changeDetails = new JLabel();
        changeDetails.setVerticalAlignment(SwingConstants.TOP); // 置頂對齊
        changeDetails.setHorizontalAlignment(SwingConstants.CENTER); // 置中對齊
        changeDetails.setBackground(new Color(247, 245, 238)); // 設置背景顏色
        changeDetails.setAlignmentX(Component.CENTER_ALIGNMENT); // 置中對齊
        contentPane.add(changeDetails);

        // 新增「交易成功」按鈕
        successButton = new JButton("交易成功");
        successButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 置中對齊
        successButton.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        successButton.setEnabled(false); // 初始時禁用按鈕
        successButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!successButton.isEnabled()) {
                    JOptionPane.showMessageDialog(PaymentUI.this, "付款了嗎～", "提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    // 將訂單資料寫入資料庫
                    PorderDao porderDao = new PorderDaoImpl();

                    // 解析訂單資料
                    int greenTeaCount = 0;
                    int blackTeaCount = 0;
                    int oolongTeaCount = 0;
                    int buckwheatTeaCount = 0;

                    for (String[] row : orderData) {
                        String drinkName = row[0];
                        int quantity = Integer.parseInt(row[2]);

                        switch (drinkName) {
                            case "玄麥茉莉":
                                greenTeaCount = quantity;
                                stockService.deductStock("玄麥茉莉", quantity); // 扣除庫存
                                break;
                            case "赤羽紅茶":
                                blackTeaCount = quantity;
                                stockService.deductStock("赤羽紅茶", quantity); // 扣除庫存
                                break;
                            case "初春青茶":
                                oolongTeaCount = quantity;
                                stockService.deductStock("初春青茶", quantity); // 扣除庫存
                                break;
                            case "十薰茉莉":
                                buckwheatTeaCount = quantity;
                                stockService.deductStock("十薰茉莉", quantity); // 扣除庫存
                                break;
                        }
                    }

                    // 建立 Porder 物件
                    Porder porder = new Porder(
                        member.getName(), // 使用會員名稱
                        greenTeaCount,
                        blackTeaCount,
                        oolongTeaCount,
                        buckwheatTeaCount,
                        orderTimestamp // 使用從 FinishUI 傳遞的時間
                    );

                    // 先顯示交易完成訊息
                    JOptionPane.showMessageDialog(PaymentUI.this, 
                        "交易已完成，感謝您的購買！", 
                        "交易成功", 
                        JOptionPane.INFORMATION_MESSAGE);

                    // 關閉付款視窗
                    dispose();

                    // 然後檢查庫存
                    util.StockManager.loadStockFromDatabase();
                    util.StockManager.checkLowStock();
                    
                    PorderMainUI mainUI = new PorderMainUI();
		            mainUI.setVisible(true);
		            
                }
            }
        });

        // 添加一個小的垂直間距
        contentPane.add(Box.createVerticalStrut(10)); // 調整間距

        // 將「交易成功」按鈕加入視窗
        contentPane.add(successButton);

        // 確認按鈕的事件處理
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String paymentText = paymentField.getText().trim();

                try {
                    int payment = Integer.parseInt(paymentText);

                    if (payment < totalAmount) {
                        changeDetails.setText("<html><font color='red'>金額好像不太對唷～</font></html>");
                        paymentField.setText(""); // 清空輸入框
                        paymentField.selectAll(); // 選中輸入框內容
                        paymentField.requestFocus(); // 將焦點設置回輸入框
                        return;
                    }

                    int change = payment - totalAmount;
                    StringBuilder changeDetail = new StringBuilder("<html><div style='text-align: center;'>應找零：" + change + "元<br>==============<br>");

                    int[] denominations = {1000, 500, 100, 50, 10, 5, 1};
                    for (int denom : denominations) {
                        int count = change / denom;
                        if (count > 0) {
                            String unit = (denom == 50 || denom == 10 || denom == 5 || denom == 1) ? "個" : "張";
                            changeDetail.append(String.format("%d元：%d%s<br>", denom, count, unit));
                        }
                        change %= denom;
                    }

                    changeDetail.append("</div></html>"); // 結束置中標籤
                    changeDetails.setText(changeDetail.toString());

                    // 啟用「交易成功」按鈕
                    successButton.setEnabled(true);

                } catch (NumberFormatException ex) {
                    changeDetails.setText("<html><font color='red'>請輸入有效的支付金額！</font></html>");
                    paymentField.setText(""); // 清空輸入框
                    paymentField.selectAll(); // 選中輸入框內容
                    paymentField.requestFocus(); // 將焦點設置回輸入框
                }
            }
        });
    }
}