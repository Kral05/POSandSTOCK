package controller.porder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.member.AddMemberUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import util.JTableCenter; // 引入 JTableCenter 工具類
import util.Tool;
import model.Member;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FinishUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JLabel lblTotalAmount;
    private JLabel lblOrderTimestamp;
    private static Member member = (Member) Tool.read("member.txt");
    private DefaultTableModel tableModel; // 新增 tableModel 作為成員變數

    public FinishUI(List<String[]> orderData, int totalAmount, int finalAmount) {
        setTitle("訂單完成");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 272, 456);
        setLocationRelativeTo(null); // 將視窗置中
        contentPane = new JPanel();
        contentPane.setBackground(new Color(247, 245, 238));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 加入圖片 JLabel
        ImageIcon icon = new ImageIcon(PorderMainUI.class.getResource("/三白茉莉.png"));
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // 調整大小
        JLabel lblNewLabel_5 = new JLabel(new ImageIcon(img));
        lblNewLabel_5.setBounds(6, 6, 80, 80);
        contentPane.add(lblNewLabel_5);

        // 建立表格
        String[] columnNames = {"飲料", "單價", "數量"};
        tableModel = new DefaultTableModel(columnNames, 0); // 初始化 tableModel
        for (String[] row : orderData) {
            tableModel.addRow(row);
        }

        table = new JTable(tableModel);
        JTableCenter.setTableCenterAlignment(table); // 讓表格內容與標題置中

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(6, 126, 260, 90);
        contentPane.add(scrollPane);

        // 顯示訂購時間
        String orderTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        lblOrderTimestamp = new JLabel("訂購時間: " + orderTimestamp);
        lblOrderTimestamp.setHorizontalAlignment(SwingConstants.LEFT);
        lblOrderTimestamp.setBounds(6, 98, 260, 16);
        contentPane.add(lblOrderTimestamp);

        // 顯示總金額
        lblTotalAmount = new JLabel("總金額: " + finalAmount + " 元");
        lblTotalAmount.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        lblTotalAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalAmount.setBounds(6, 228, 254, 16);
        contentPane.add(lblTotalAmount);

        // 使用動態會員名稱
        String memberName = member != null ? member.getName() : "會員"; // 確保會員名稱有效
        String labelText = "<html><div style='text-align: center;'>" + memberName + " 您好!<br>下列是您的訂購明細</div></html>";

        JLabel MemberatAdd = new JLabel(labelText);
        MemberatAdd.setFont(new Font("MEllan HK", Font.PLAIN, 14));
        MemberatAdd.setForeground(new Color(121, 165, 40));
        MemberatAdd.setHorizontalAlignment(SwingConstants.CENTER);
        MemberatAdd.setBounds(104, 20, 148, 50); // 調整高度以顯示換行
        contentPane.add(MemberatAdd);

        // 修改訂單按鈕
        JButton btnNewButton = new JButton("修改訂單");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 將當前訂單資料轉換為 List<String[]> 格式
                List<String[]> orderData = new ArrayList<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String drinkName = tableModel.getValueAt(i, 0).toString();
                    String price = tableModel.getValueAt(i, 1).toString();
                    String quantity = tableModel.getValueAt(i, 2).toString();
                    orderData.add(new String[]{drinkName, price, quantity});
                }

                // 開啟 PorderMainUI 並傳遞訂單資料
                PorderMainUI frame = new PorderMainUI(orderData);
                frame.setVisible(true);
                dispose(); // 關閉當前視窗
            }
        });
        btnNewButton.setBounds(74, 266, 117, 29);
        contentPane.add(btnNewButton);

        // 確認付款按鈕
        JButton btnNewButton_1 = new JButton("確認付款");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 獲取總金額
                int totalAmount = Integer.parseInt(lblTotalAmount.getText().replace("總金額: ", "").replace(" 元", ""));

                // 將當前訂單資料轉換為 List<String[]> 格式
                List<String[]> orderData = new ArrayList<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String drinkName = tableModel.getValueAt(i, 0).toString();
                    String price = tableModel.getValueAt(i, 1).toString();
                    String quantity = tableModel.getValueAt(i, 2).toString();
                    orderData.add(new String[]{drinkName, price, quantity});
                }

                // 獲取訂單時間
                String orderTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                // 開啟 PaymentUI 並傳遞總金額、訂單資料和時間
                PaymentUI paymentUI = new PaymentUI(totalAmount, orderData, orderTimestamp);
                paymentUI.setVisible(true);

                // 關閉 FinishUI
                dispose();
            }
        });
        btnNewButton_1.setBounds(74, 307, 117, 29);
        contentPane.add(btnNewButton_1);
    }
}