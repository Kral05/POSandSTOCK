package controller.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Member;
import service.impl.MemberServiceImpl;
import controller.member.AddMemberUI;
import controller.porder.PorderMainUI;
import util.Tool;
import util.PasswordUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField username;
    private JTextField password;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private StringBuilder actualPassword = new StringBuilder(); // 改為 StringBuilder

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginUI frame = new LoginUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */

    public LoginUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(247, 245, 238));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // 加入圖片 JLabel
        ImageIcon icon = new ImageIcon(LoginUI.class.getResource("/三白茉莉.png"));
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
        
        JLabel lblNewLabel = new JLabel("帳號");
        lblNewLabel.setBounds(123, 109, 61, 16);
        contentPane.add(lblNewLabel);
        
        username = new JTextField();
        username.setBounds(182, 104, 187, 26);
        username.setBorder(null);
        contentPane.add(username);
        username.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("密碼");
        lblNewLabel_1.setBounds(123, 148, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        // 設置密碼輸入框
        password = new JTextField();
        password.setBounds(182, 143, 187, 26);
        password.setBorder(null);
        password.setLayout(new BorderLayout()); // 設置佈局管理器
        password.setColumns(10);
        
        // 添加眼睛按鈕
        JButton togglePasswordButton = new JButton();
        togglePasswordButton.setPreferredSize(new Dimension(30, 26));
        
        // 調整圖標大小
        ImageIcon originalIcon = new ImageIcon(LoginUI.class.getResource("/eye-closed.png"));
        Image image = originalIcon.getImage();
        Image newimg = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
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
        
        // 使用 PasswordUtil 處理密碼遮罩
        PasswordUtil.addPasswordMasking(password, actualPassword);
       
        
        btnNewButton = new JButton("登入");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String Username = username.getText();
                String Password = actualPassword.toString();  // 使用 StringBuilder 的 toString() 方法
                
                if (Username == null || Password == null || Username.trim().isEmpty() || Password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "帳號或密碼不得為空！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Member member = new MemberServiceImpl().login(Username, Password);
                
                if (member != null) {
                    Tool.save(member, "member.txt");  // 保存登入資訊
                    JOptionPane.showMessageDialog(null, "登入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    
                    // 跳轉到 PorderMainUI
                    PorderMainUI mainUI = new PorderMainUI();
                    mainUI.setVisible(true);
                    
                    dispose(); // 關閉 LoginUI
                } else {
                    JOptionPane.showMessageDialog(null, "登入失敗，請檢查帳號或密碼！", "錯誤", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton.setBounds(123, 194, 117, 29);
        contentPane.add(btnNewButton);
        
        btnNewButton_1 = new JButton("註冊");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddMemberUI addmember = new AddMemberUI();
                addmember.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setBounds(252, 194, 117, 29);
        contentPane.add(btnNewButton_1);
    }
}