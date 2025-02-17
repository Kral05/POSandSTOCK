package controller.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Member;
import service.impl.MemberServiceImpl;
import util.RegexUtil;
import util.TextFieldUtil;
import util.PasswordUtil;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class AddMemberUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField name;
    private JTextField username;
    private JTextField password;
    private JTextField address;
    private JTextField mobile;
    private JButton btnNewButton;

    // 密碼儲存用變數
    private StringBuilder actualPassword = new StringBuilder();

    // 預設提示文字
    private final String usernameHint = " 4-12字母或數字";
    private final String passwordHint = " 至少8碼，含大小寫與數字";
    private final String addressHint = " 外送限台北地區，請輸入詳細地址";
    private final String mobileHint = " 09開頭的10位數號碼";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AddMemberUI frame = new AddMemberUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AddMemberUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        setLocationRelativeTo(null); // 將視窗置中
        contentPane = new JPanel();
        contentPane.setBackground(new Color(247, 245, 238));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 設置標籤
        setupLabels();

        // 設置輸入欄
        setupInputFields();

        // 設置按鈕
        setupButtons();
    }

    private void setupLabels() {
        JLabel lblNewLabel = new JLabel("姓名");
        lblNewLabel.setBounds(127, 68, 61, 16);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("帳號");
        lblNewLabel_1.setBounds(127, 99, 61, 16);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("密碼");
        lblNewLabel_2.setBounds(127, 130, 61, 16);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("地址");
        lblNewLabel_3.setBounds(127, 161, 61, 16);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("手機");
        lblNewLabel_4.setBounds(127, 192, 61, 16);
        contentPane.add(lblNewLabel_4);
        
        ImageIcon icon = new ImageIcon(AddMemberUI.class.getResource("/三白茉莉.png"));
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblNewLabel_5 = new JLabel(new ImageIcon(img));
        lblNewLabel_5.setBounds(6, 6, 100, 100);
        contentPane.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("點餐與庫存系統");
        lblNewLabel_6.setForeground(new Color(121, 165, 40));
        lblNewLabel_6.setFont(new Font("MEllan HK", Font.PLAIN, 25));
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_6.setBounds(127, 6, 261, 50);
        contentPane.add(lblNewLabel_6);
    }

    private void setupInputFields() {
        name = new JTextField();
        name.setBounds(168, 59, 220, 26);
        name.setBorder(null);
        contentPane.add(name);
        name.setColumns(10);

        username = new JTextField(usernameHint);
        TextFieldUtil.setupPlaceholder(username, usernameHint);
        username.setBounds(168, 91, 220, 26);
        username.setBorder(null);
        contentPane.add(username);

        // 設置密碼輸入框
        password = new JTextField(passwordHint);
        password.setBounds(168, 123, 220, 26);
        password.setBorder(null);
        password.setLayout(new BorderLayout()); // 設置佈局管理器
        TextFieldUtil.setupPlaceholder(password, passwordHint);

        // 添加眼睛按鈕
        JButton togglePasswordButton = new JButton();
        togglePasswordButton.setPreferredSize(new Dimension(30, 26));
        
        // 調整圖標大小
        ImageIcon originalIcon = new ImageIcon(AddMemberUI.class.getResource("/eye-closed.png"));
        Image image = originalIcon.getImage();
        Image newimg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(newimg);

        togglePasswordButton.setIcon(scaledIcon);
        togglePasswordButton.setBorderPainted(false);
        togglePasswordButton.setContentAreaFilled(false);
        togglePasswordButton.setFocusPainted(false);
        togglePasswordButton.addActionListener(e -> 
            PasswordUtil.togglePasswordVisibility(password, actualPassword, togglePasswordButton)
        );
        
        // 將按鈕添加到密碼輸入框的右側
        password.add(togglePasswordButton, BorderLayout.EAST);
        
        contentPane.add(password);

        // 密碼防窺功能
        PasswordUtil.addPasswordMasking(password, actualPassword);

        address = new JTextField(addressHint);
        TextFieldUtil.setupPlaceholder(address, addressHint);
        address.setBounds(168, 155, 220, 26);
        address.setBorder(null);
        contentPane.add(address);

        mobile = new JTextField(mobileHint);
        TextFieldUtil.setupPlaceholder(mobile, mobileHint);
        mobile.setBounds(168, 186, 220, 26);
        mobile.setBorder(null);
        contentPane.add(mobile);
    }

    private void setupButtons() {
        btnNewButton = new JButton("註冊");
        btnNewButton.setBounds(127, 240, 117, 29);
        contentPane.add(btnNewButton);

        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleRegisterClick();
            }
        });

        JButton btnNewButton_1 = new JButton("回上一頁");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginUI frame = new LoginUI();
                frame.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setBounds(271, 240, 117, 29);
        contentPane.add(btnNewButton_1);
    }

    private void handleRegisterClick() {
        String Name = name.getText().trim();
        String Username = username.getText().trim();
        String Password = actualPassword.toString();
        String Address = address.getText().trim();
        String Mobile = mobile.getText().trim();

        // 防止使用者提交預設提示文字
        if (Username.equals(usernameHint) || Password.equals(passwordHint) || Address.equals(addressHint) || Mobile.equals(mobileHint)) {
            JOptionPane.showMessageDialog(null, "請輸入所有欄位，不可使用預設提示！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 檢查欄位是否為空
        if (Name.isEmpty() || Username.isEmpty() || Password.isEmpty() || Address.isEmpty() || Mobile.isEmpty()) {
            JOptionPane.showMessageDialog(null, "所有欄位皆為必填，請確認輸入！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 檢查正規表示法
        if (!RegexUtil.isValid(Username, RegexUtil.USERNAME_REGEX)) {
            JOptionPane.showMessageDialog(null, "帳號格式錯誤！帳號只能包含 4-12 位字母或數字。", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!RegexUtil.isValid(Password, RegexUtil.PASSWORD_REGEX)) {
            JOptionPane.showMessageDialog(null, "密碼格式錯誤！密碼至少 8 碼，包含大小寫字母與數字。", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!RegexUtil.isValid(Mobile, RegexUtil.MOBILE_REGEX)) {
            JOptionPane.showMessageDialog(null, "手機號碼格式錯誤！請輸入 09 開頭的 10 碼台灣手機號碼。", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!RegexUtil.isValid(Address, RegexUtil.ADDRESS_REGEX)) {
            JOptionPane.showMessageDialog(null, "地址格式錯誤！須包含「區」和「號」", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (new MemberServiceImpl().isUsernameBeenUse(Username)) {
            JOptionPane.showMessageDialog(null, "帳號已被使用，請換一個帳號！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Member member = new Member(Name, Username, Password, Address, Mobile);
        new MemberServiceImpl().addMember(member);

        JOptionPane.showMessageDialog(null, "註冊成功！請重新登入", "提示", JOptionPane.INFORMATION_MESSAGE);
        LoginUI frame = new LoginUI();
        frame.setVisible(true);
        dispose();
    }
}