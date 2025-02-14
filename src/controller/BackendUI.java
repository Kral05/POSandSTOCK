package controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class BackendUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public BackendUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		setLocationRelativeTo(null); // 將視窗置中
		contentPane = new JPanel();
		contentPane.setBackground(new Color(247, 245, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		// 加載並顯示圖片
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/三白茉莉.png"));
		Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 調整圖片大小
		ImageIcon scaledIcon = new ImageIcon(image);
		contentPane.setLayout(null);
		
		// 創建一個 JLabel 顯示圖片
		JLabel imageLabel = new JLabel(scaledIcon);
		imageLabel.setBounds(6, 6, 100, 100); // 設定圖片位置與大小
		contentPane.add(imageLabel);

		// 標題文字
        JLabel lblNewLabel_6 = new JLabel("點餐與庫存系統");
        lblNewLabel_6.setForeground(new Color(121, 165, 40));
        lblNewLabel_6.setFont(new Font("MEllan HK", Font.PLAIN, 25));
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_6.setBounds(127, 6, 261, 50); // 調整標題的位置
        contentPane.add(lblNewLabel_6);
	}
}
