package controller.porder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import service.impl.PorderServiceImpl;
import util.Tool;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PorderManagerUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField OolongTea;
    private JTextField BlackTea;
    private JTextField BuckwheatTea;
    private JTextField GreenTea;
    private JTextField deleteId;
    private static PorderServiceImpl porderserviceimpl = new PorderServiceImpl();
    private JTextField updateId;
    private JTextArea output;
    private JTable outputTable;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PorderManagerUI frame = new PorderManagerUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PorderManagerUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 840, 550);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(247, 245, 238));  // 與 MemberManagerUI 相同的背景色
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setupHeader();
        setupOrderArea();
        setupButtons();
    }

    private void setupHeader() {
        // Logo
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/三白茉莉.png"));
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBounds(6, 6, 100, 100);
        contentPane.add(imageLabel);

        // 標題
        JLabel titleLabel = new JLabel("訂單資料管理");
        titleLabel.setForeground(new Color(121, 165, 40));  // 與 MemberManagerUI 相同的標題顏色
        titleLabel.setFont(new Font("MEllan HK", Font.PLAIN, 25));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(294, 27, 261, 50);
        contentPane.add(titleLabel);
    }

    private void setupOrderArea() {
        // 表頭設定
        String[] columnNames = {"會員編號", "玄麥茉莉", "赤羽紅茶", "初春青茶", "十薰茉莉", "總金額"};
        tableModel = new DefaultTableModel(null, columnNames);
        
        // 創建 JTable
        outputTable = new JTable(tableModel);
        outputTable.setBackground(Color.WHITE);
        outputTable.setFillsViewportHeight(true);  // 設置填滿視口

        // ** 設置表格內容置中 **
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);  // 置中
        for (int i = 0; i < outputTable.getColumnCount(); i++) {
            outputTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // ** 設置表頭文字置中 **
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);  // 表頭置中
        outputTable.getTableHeader().setDefaultRenderer(headerRenderer);

        // 設置 JScrollPane 並添加表格
        JScrollPane scrollPane = new JScrollPane(outputTable);
        scrollPane.setBounds(20, 120, 450, 300);  // 與原來的位置和大小相同
        contentPane.add(scrollPane);
        
        // 修改區域標題
        JLabel updateTitle = new JLabel("修改訂單");
        updateTitle.setForeground(new Color(121, 165, 40));
        updateTitle.setFont(new Font("MEllan HK", Font.PLAIN, 20));
        updateTitle.setBounds(482, 120, 200, 30);
        contentPane.add(updateTitle);
        
        // 修改區域
        JLabel lblUpdateId = new JLabel("訂單編號：");
        lblUpdateId.setBounds(482, 160, 80, 25);
        contentPane.add(lblUpdateId);
        
        updateId = new JTextField();
        updateId.setBounds(562, 160, 150, 25);
        contentPane.add(updateId);
        
        // 茶品數量輸入區
        setupTeaInputs();
        
        // 刪除區域標題
        JLabel deleteTitle = new JLabel("刪除訂單");
        deleteTitle.setForeground(new Color(121, 165, 40));
        deleteTitle.setFont(new Font("MEllan HK", Font.PLAIN, 20));
        deleteTitle.setBounds(482, 350, 200, 30);
        contentPane.add(deleteTitle);
        
        // 刪除區域
        JLabel lblDeleteId = new JLabel("訂單編號：");
        lblDeleteId.setBounds(482, 390, 80, 25);
        contentPane.add(lblDeleteId);
        
        deleteId = new JTextField();
        deleteId.setBounds(562, 390, 150, 25);
        contentPane.add(deleteId);
    }

    private void setupTeaInputs() {
        // 茶品數量輸入區
        JLabel lblBuckwheatTea = new JLabel("玄麥茉莉：");
        lblBuckwheatTea.setBounds(482, 195, 80, 25);
        contentPane.add(lblBuckwheatTea);
        
        BuckwheatTea = new JTextField();
        BuckwheatTea.setBounds(562, 195, 150, 25);
        contentPane.add(BuckwheatTea);
        
        JLabel lblBlackTea = new JLabel("赤羽紅茶：");
        lblBlackTea.setBounds(482, 230, 80, 25);
        contentPane.add(lblBlackTea);
        
        BlackTea = new JTextField();
        BlackTea.setBounds(562, 230, 150, 25);
        contentPane.add(BlackTea);
        
        JLabel lblOolongTea = new JLabel("初春青茶：");
        lblOolongTea.setBounds(482, 265, 80, 25);
        contentPane.add(lblOolongTea);
        
        OolongTea = new JTextField();
        OolongTea.setBounds(562, 265, 150, 25);
        contentPane.add(OolongTea);
        
        JLabel lblGreenTea = new JLabel("十薰茉莉：");
        lblGreenTea.setBounds(482, 300, 80, 25);
        contentPane.add(lblGreenTea);
        
        GreenTea = new JTextField();
        GreenTea.setBounds(562, 300, 150, 25);
        contentPane.add(GreenTea);
    }

    private void setupButtons() {
        // 查詢按鈕
        JButton queryButton = new JButton("查詢");
        queryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[][] orderData = porderserviceimpl.AllPorderAsArray();  // 確保有這個方法返回表格數據
                updateTable(orderData);  // 更新表格內容
            }
        });
        queryButton.setBounds(185, 450, 100, 30);
        contentPane.add(queryButton);
        
        // 修改按鈕
        JButton updateButton = new JButton("修改");
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // 先檢查是否有空欄位
                    if (updateId.getText().trim().isEmpty() ||
                        BuckwheatTea.getText().trim().isEmpty() ||
                        BlackTea.getText().trim().isEmpty() ||
                        OolongTea.getText().trim().isEmpty() ||
                        GreenTea.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "所有欄位都必須填寫！");
                        return;
                    }

                    // 移除可能的空白字符並轉換為整數
                    int Id = Integer.parseInt(updateId.getText().trim());
                    int BuckwheatTeaAmount = Integer.parseInt(BuckwheatTea.getText().trim());
                    int BlackTeaAmount = Integer.parseInt(BlackTea.getText().trim());
                    int OolongTeaAmount = Integer.parseInt(OolongTea.getText().trim());
                    int GreenTeaAmount = Integer.parseInt(GreenTea.getText().trim());

                    // 檢查數量是否為負數
                    if (BuckwheatTeaAmount < 0 || BlackTeaAmount < 0 || 
                        OolongTeaAmount < 0 || GreenTeaAmount < 0) {
                        JOptionPane.showMessageDialog(null, "數量不能為負數！");
                        return;
                    }
                    
                    porderserviceimpl.updatePorder(BuckwheatTeaAmount, BlackTeaAmount, 
                                                 OolongTeaAmount, GreenTeaAmount, Id);
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    output.setText(porderserviceimpl.AllPorder());
                    
                    // 清空輸入欄位
                    updateId.setText("");
                    BuckwheatTea.setText("");
                    BlackTea.setText("");
                    OolongTea.setText("");
                    GreenTea.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, 
                        "請確認所有欄位都輸入有效的整數！\n" +
                        "錯誤訊息：" + ex.getMessage());
                }
            }
        });
        updateButton.setBounds(724, 155, 100, 30);
        contentPane.add(updateButton);
        
     // 刪除按鈕
        JButton deleteButton = new JButton("刪除");
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String idText = deleteId.getText().trim();
                    if (idText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "請輸入訂單編號！");
                        return;
                    }

                    int Id = Integer.parseInt(idText);
                    if (Id <= 0) {
                        JOptionPane.showMessageDialog(null, "訂單編號必須大於0！");
                        return;
                    }

                    int choice = JOptionPane.showConfirmDialog(
                        null,
                        "確定要刪除訂單編號 " + Id + " 的資料嗎？",
                        "確認刪除",
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (choice == JOptionPane.YES_OPTION) {
                        porderserviceimpl.deletePorderById(Id);
                        JOptionPane.showMessageDialog(null, "刪除成功！");
                        output.setText(porderserviceimpl.AllPorder());
                        deleteId.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, 
                        "請輸入有效的訂單編號！\n" +
                        "錯誤訊息：" + ex.getMessage());
                }
            }
        });
        deleteButton.setBounds(724, 388, 100, 30);
        contentPane.add(deleteButton);
        
        // 返回按鈕
        JButton backButton = new JButton("返回");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tool.gotoPorderMain();
                dispose();
            }
        });
        backButton.setBounds(724, 6, 100, 30);
        contentPane.add(backButton);
    }
    
    private void updateTable(String[][] data) {
        // 清空原有資料
        tableModel.setRowCount(0);
        
        // 新增新的資料，格式化顯示
        for (String[] row : data) {
            row[1] = row[1] + " 杯";
            row[2] = row[2] + " 杯";
            row[3] = row[3] + " 杯";
            row[4] = row[4] + " 杯";
            row[5] = row[5] + " 元";
            tableModel.addRow(row);
        }
    }
}
