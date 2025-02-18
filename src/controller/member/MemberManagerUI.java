package controller.member;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Member;
import service.impl.MemberServiceImpl;
import util.Tool;
import controller.BackendUI;

public class MemberManagerUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable memberTable;
    private JTextField nameField;
    private JTextField passwordField;
    private JTextField addressField;
    private JTextField mobileField;
    private JTextField deleteUsername;
    private JTextField updateIdField;  // 改為 updateIdField
    private static MemberServiceImpl memberService = new MemberServiceImpl();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MemberManagerUI frame = new MemberManagerUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MemberManagerUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 840, 550);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(247, 245, 238));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setupHeader();
        setupTable();
        setupUpdateArea();
        setupDeleteArea();
        setupButtons();
    }

    private void setupHeader() {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/三白茉莉.png"));
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBounds(6, 6, 100, 100);
        contentPane.add(imageLabel);

        JLabel titleLabel = new JLabel("會員資料管理");
        titleLabel.setForeground(new Color(121, 165, 40));
        titleLabel.setFont(new Font("MEllan HK", Font.PLAIN, 25));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(294, 27, 261, 50);
        contentPane.add(titleLabel);
    }

    private void setupTable() {
        String[] columnNames = {"會員編號", "姓名", "帳號", "密碼", "地址", "手機"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        memberTable = new JTable(model);
        memberTable.setBackground(Color.WHITE);
        
        memberTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        memberTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        memberTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        memberTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        memberTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        memberTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        // ** 設置表格內容置中 **
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < memberTable.getColumnCount(); i++) {
            memberTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // ** 設置表頭文字置中 **
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        memberTable.getTableHeader().setDefaultRenderer(headerRenderer);

        JScrollPane scrollPane = new JScrollPane(memberTable);
        scrollPane.setBounds(20, 120, 450, 300);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        memberTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        contentPane.add(scrollPane);
        
        // 修改區域標題
        JLabel updateTitle = new JLabel("修改資料");
        updateTitle.setForeground(new Color(121, 165, 40));
        updateTitle.setFont(new Font("MEllan HK", Font.PLAIN, 20));
        updateTitle.setBounds(482, 120, 200, 30);
        contentPane.add(updateTitle);
        
        // 刪除區域標題
        JLabel deleteTitle = new JLabel("刪除資料");
        deleteTitle.setForeground(new Color(121, 165, 40));
        deleteTitle.setFont(new Font("MEllan HK", Font.PLAIN, 20));
        deleteTitle.setBounds(482, 350, 200, 30);
        contentPane.add(deleteTitle);
    }


    private void setupUpdateArea() {
        JLabel lblUpdateId = new JLabel("會員編號：");  // 改為會員編號
        lblUpdateId.setBounds(482, 160, 80, 25);
        contentPane.add(lblUpdateId);
        
        updateIdField = new JTextField();  // 改為 updateIdField
        updateIdField.setBounds(562, 160, 150, 25);
        contentPane.add(updateIdField);
        
        JLabel lblName = new JLabel("姓名：");
        lblName.setBounds(482, 195, 80, 25);
        contentPane.add(lblName);
        
        nameField = new JTextField();
        nameField.setBounds(562, 195, 150, 25);
        contentPane.add(nameField);
        
        JLabel lblPassword = new JLabel("密碼：");
        lblPassword.setBounds(482, 230, 80, 25);
        contentPane.add(lblPassword);
        
        passwordField = new JTextField();
        passwordField.setBounds(562, 230, 150, 25);
        contentPane.add(passwordField);
        
        JLabel lblAddress = new JLabel("地址：");
        lblAddress.setBounds(482, 265, 80, 25);
        contentPane.add(lblAddress);
        
        addressField = new JTextField();
        addressField.setBounds(562, 265, 150, 25);
        contentPane.add(addressField);
        
        JLabel lblMobile = new JLabel("手機：");
        lblMobile.setBounds(482, 300, 80, 25);
        contentPane.add(lblMobile);
        
        mobileField = new JTextField();
        mobileField.setBounds(562, 300, 150, 25);
        contentPane.add(mobileField);
    }

    private void setupDeleteArea() {
        JLabel lblDeleteId = new JLabel("會員編號：");
        lblDeleteId.setBounds(482, 390, 100, 25);
        contentPane.add(lblDeleteId);
        
        deleteUsername = new JTextField();  // 變數名稱保持不變
        deleteUsername.setBounds(562, 389, 150, 25);
        contentPane.add(deleteUsername);
    }

    private void setupButtons() {
        // 查詢按鈕
        JButton queryButton = new JButton("查詢");
        queryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateTable();
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
                    int id = Integer.parseInt(updateIdField.getText());
                    Member existingMember = memberService.getMemberById(id);
                    
                    if (existingMember != null) {
                        // 只更新有填入值的欄位
                        String name = nameField.getText().trim();
                        String password = passwordField.getText().trim();
                        String address = addressField.getText().trim();
                        String mobile = mobileField.getText().trim();
                        
                        // 如果欄位有填值，才更新
                        if (!name.isEmpty()) {
                            existingMember.setName(name);
                        }
                        if (!password.isEmpty()) {
                            existingMember.setPassword(password);
                        }
                        if (!address.isEmpty()) {
                            existingMember.setAddress(address);
                        }
                        if (!mobile.isEmpty()) {
                            existingMember.setMobile(mobile);
                        }
                        
                        memberService.updateMember(existingMember);
                        JOptionPane.showMessageDialog(null, "修改成功！");
                        updateTable();
                        
                        // 清空輸入欄位
                        updateIdField.setText("");
                        nameField.setText("");
                        passwordField.setText("");
                        addressField.setText("");
                        mobileField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "找不到此會員！");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "請輸入有效的會員編號！");
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
                    int id = Integer.parseInt(deleteUsername.getText());
                    Member member = memberService.getMemberById(id);
                    
                    if (member != null) {
                        // 顯示確認對話框
                        int choice = JOptionPane.showConfirmDialog(
                            null,
                            "確定要刪除會員編號 " + id + " 的資料嗎？",
                            "確認刪除",
                            JOptionPane.YES_NO_OPTION
                        );
                        
                        if (choice == JOptionPane.YES_OPTION) {
                            memberService.deleteMemberById(id);
                            JOptionPane.showMessageDialog(null, "刪除成功！");
                            updateTable();
                            deleteUsername.setText("");  // 清空輸入欄位
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "找不到此會員！");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "請輸入有效的會員編號！");
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
    

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) memberTable.getModel();
        model.setRowCount(0);
        
        List<Member> members = memberService.getAllMembers();
        for (Member member : members) {
            model.addRow(new Object[]{
                member.getId(),
                member.getName(),
                member.getUsername(),
                member.getPassword(),
                member.getAddress(),
                member.getMobile()
            });
        }
    }
}