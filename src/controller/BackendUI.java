package controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.member.MemberManagerUI;
import controller.porder.PorderManagerUI;

public class BackendUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BackendUI frame = new BackendUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BackendUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(247, 245, 238));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        
        // 加載並顯示圖片
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/三白茉莉.png"));
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        contentPane.setLayout(null);
        
        // 創建一個 JLabel 顯示圖片
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(6, 6, 100, 100);
        contentPane.add(imageLabel);

        // 標題文字
        JLabel lblNewLabel_6 = new JLabel("點餐與庫存系統");
        lblNewLabel_6.setForeground(new Color(121, 165, 40));
        lblNewLabel_6.setFont(new Font("MEllan HK", Font.PLAIN, 25));
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_6.setBounds(127, 6, 261, 50);
        contentPane.add(lblNewLabel_6);

        // 添加修改會員資料按鈕
        JButton memberButton = new JButton("修改會員資料");
        memberButton.setBounds(150, 120, 200, 40);
        memberButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MemberManagerUI memberManager = new MemberManagerUI();
                memberManager.setVisible(true);
                dispose();
            }
        });
        contentPane.add(memberButton);

        // 添加修改訂單資料按鈕
        JButton orderButton = new JButton("修改訂單資料");
        orderButton.setBounds(150, 180, 200, 40);
        orderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PorderManagerUI orderManager = new PorderManagerUI();
                orderManager.setVisible(true);
                dispose();
            }
        });
        contentPane.add(orderButton);
    }
}