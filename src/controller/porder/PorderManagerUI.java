package controller.porder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        // 查詢結果區域
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 120, 450, 300);  // 與 MemberManagerUI 相同的位置和大小
        contentPane.add(scrollPane);
        
        output = new JTextArea();
        output.setBackground(Color.WHITE);
        scrollPane.setViewportView(output);
        
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
                output.setText(porderserviceimpl.AllPorder());
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
                    int Id = Integer.parseInt(updateId.getText());
                    int BuckwheatTeaAmount = Integer.parseInt(BuckwheatTea.getText());
                    int BlackTeaAmount = Integer.parseInt(BlackTea.getText());
                    int OolongTeaAmount = Integer.parseInt(OolongTea.getText());
                    int GreenTeaAmount = Integer.parseInt(GreenTea.getText());
                    
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
                    JOptionPane.showMessageDialog(null, "請輸入有效的數字！");
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
                    int Id = Integer.parseInt(deleteId.getText());
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
                    JOptionPane.showMessageDialog(null, "請輸入有效的訂單編號！");
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
}