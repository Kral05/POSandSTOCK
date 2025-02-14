package controller.member;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Member;
import service.impl.MemberServiceImpl;
import util.RegexUtil;
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
    private String actualPassword = "";

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

    // **設置標籤**
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

        JLabel lblNewLabel_6 = new JLabel("點餐與庫存系統");
        lblNewLabel_6.setForeground(new Color(121, 165, 40));
        lblNewLabel_6.setFont(new Font("MEllan HK", Font.PLAIN, 25));
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_6.setBounds(127, 6, 261, 50);
        contentPane.add(lblNewLabel_6);
    }

    // **設置輸入欄**
    private void setupInputFields() {
        name = new JTextField();
        name.setBounds(168, 59, 220, 26);
        contentPane.add(name);
        name.setColumns(10);

        username = new JTextField(usernameHint);
        setupPlaceholder(username, usernameHint);
        username.setBounds(168, 91, 220, 26);
        contentPane.add(username);

        password = new JTextField(passwordHint);
        setupPlaceholder(password, passwordHint);
        password.setBounds(168, 123, 220, 26);
        contentPane.add(password);

        // 密碼防窺功能
        addPasswordMasking(password);

        address = new JTextField(addressHint);
        setupPlaceholder(address, addressHint);
        address.setBounds(168, 155, 220, 26);
        contentPane.add(address);

        mobile = new JTextField(mobileHint);
        setupPlaceholder(mobile, mobileHint);
        mobile.setBounds(168, 186, 220, 26);
        contentPane.add(mobile);
    }

    // **設置按鈕**
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

    // **處理註冊按鈕點擊**
    private void handleRegisterClick() {
        String Name = name.getText().trim();
        String Username = username.getText().trim();
        String Password = actualPassword; // 使用真正的密碼
        String Address = address.getText().trim();
        String Mobile = mobile.getText().trim();

        // 防止使用者提交預設提示文字
        if (Username.equals(usernameHint) || Password.equals(passwordHint) || Address.equals(addressHint) || Mobile.equals(mobileHint)) {
            JOptionPane.showMessageDialog(null, "請輸入所有欄位，不可使用預設提示！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // **檢查欄位是否為空**
        if (Name.isEmpty() || Username.isEmpty() || Password.isEmpty() || Address.isEmpty() || Mobile.isEmpty()) {
            JOptionPane.showMessageDialog(null, "所有欄位皆為必填，請確認輸入！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // **檢查正規表示法**
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

    // **設定提示文字與監聽事件**
    private void setupPlaceholder(JTextField field, String hint) {
        field.setText(hint);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(hint)) {
                    field.setCaretPosition(0);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    resetField(field, hint);
                }
            }
        });

        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (field.getText().equals(hint)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
        });
    }

    // **重設輸入欄位回到提示狀態**
    private void resetField(JTextField field, String hint) {
        field.setText(hint);
        field.setForeground(Color.GRAY);
    }

    // **密碼遮罩功能**
    private void addPasswordMasking(JTextField passwordField) {
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLetterOrDigit(keyChar) || Character.isWhitespace(keyChar) || !Character.isISOControl(keyChar)) {
                    actualPassword += keyChar;
                    updatePasswordDisplay(passwordField);
                }
                e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && actualPassword.length() > 0) {
                    actualPassword = actualPassword.substring(0, actualPassword.length() - 1);
                    updatePasswordDisplay(passwordField);
                }
            }
        });
    }

    // **更新密碼顯示**
    private void updatePasswordDisplay(JTextField textField) {
        StringBuilder displayText = new StringBuilder();
        for (int i = 0; i < actualPassword.length(); i++) {
            if (i == actualPassword.length() - 1) {
                displayText.append(actualPassword.charAt(i));
            } else {
                displayText.append('*');
            }
        }
        textField.setText(displayText.toString());
    }
}
