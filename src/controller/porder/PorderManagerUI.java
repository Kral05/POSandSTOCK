package controller.porder;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import service.impl.PorderServiceImpl;
import util.Tool;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PorderManagerUI frame = new PorderManagerUI();
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
	public PorderManagerUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 400); // 調整高度以顯示所有新標籤
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 220, 200);
		contentPane.add(scrollPane);
		
		JTextArea output = new JTextArea();
		scrollPane.setViewportView(output);
		
		// 修改標籤與變數名稱
		JLabel lblBuckwheatTea = new JLabel("玄麥茉莉：");
		lblBuckwheatTea.setBounds(238, 79, 100, 16);
		contentPane.add(lblBuckwheatTea);
		
		JLabel lblBlackTea = new JLabel("赤羽紅茶：");
		lblBlackTea.setBounds(238, 107, 100, 16);
		contentPane.add(lblBlackTea);
		
		JLabel lblOolongTea = new JLabel("初春青茶：");
		lblOolongTea.setBounds(238, 135, 100, 16);
		contentPane.add(lblOolongTea);
		
		// 新增 "十薰茉莉" 標籤
		JLabel lblGreenTea = new JLabel("十薰茉莉：");
		lblGreenTea.setBounds(238, 163, 100, 16);
		contentPane.add(lblGreenTea);

		// 對應的輸入框
		BuckwheatTea = new JTextField();
		BuckwheatTea.setBounds(340, 74, 61, 26);
		contentPane.add(BuckwheatTea);
		BuckwheatTea.setColumns(10);
		
		BlackTea = new JTextField();
		BlackTea.setColumns(10);
		BlackTea.setBounds(340, 102, 61, 26);
		contentPane.add(BlackTea);
		
		OolongTea = new JTextField();
		OolongTea.setColumns(10);
		OolongTea.setBounds(340, 130, 61, 26);
		contentPane.add(OolongTea);
		
		GreenTea = new JTextField();
		GreenTea.setColumns(10);
		GreenTea.setBounds(340, 158, 61, 26);
		contentPane.add(GreenTea);
		
		// ID 標籤和輸入框
		JLabel lblId = new JLabel("Id：");
		lblId.setBounds(417, 135, 61, 16);
		contentPane.add(lblId);
		
		deleteId = new JTextField();
		deleteId.setColumns(10);
		deleteId.setBounds(451, 130, 61, 26);
		contentPane.add(deleteId);
		
		JLabel lblId_1 = new JLabel("Id：");
		lblId_1.setBounds(238, 51, 61, 16);
		contentPane.add(lblId_1);
		
		updateId = new JTextField();
		updateId.setColumns(10);
		updateId.setBounds(340, 46, 61, 26);
		contentPane.add(updateId);
		
		// 查詢按鈕
		JButton btnNewButton = new JButton("查詢");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				output.setText(porderserviceimpl.AllPorder());
			}
		});
		btnNewButton.setBounds(61, 300, 117, 29);
		contentPane.add(btnNewButton);
		
		// 修改按鈕
		JButton btnNewButton_1 = new JButton("修改");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int Id = Integer.parseInt(updateId.getText());
				int BuckwheatTeaAmount = Integer.parseInt(BuckwheatTea.getText());
				int BlackTeaAmount = Integer.parseInt(BlackTea.getText());
				int OolongTeaAmount = Integer.parseInt(OolongTea.getText());
				int GreenTeaAmount = Integer.parseInt(GreenTea.getText());
				
				// 更新訂單
				porderserviceimpl.updatePorder(BuckwheatTeaAmount, BlackTeaAmount, OolongTeaAmount, GreenTeaAmount, Id);
			}
		});
		btnNewButton_1.setBounds(239, 300, 117, 29);
		contentPane.add(btnNewButton_1);
		
		// 刪除按鈕
		JButton btnNewButton_2 = new JButton("刪除");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int Id = Integer.parseInt(deleteId.getText());
				porderserviceimpl.deletePorderById(Id);
			}
		});
		btnNewButton_2.setBounds(417, 300, 117, 29);
		contentPane.add(btnNewButton_2);
		
		// 回主頁按鈕
		JButton btnNewButton_3 = new JButton("回主頁");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tool.gotoPorderMain();
				dispose();
			}
		});
		btnNewButton_3.setBounds(472, 3, 117, 29);
		contentPane.add(btnNewButton_3);
	}
}
