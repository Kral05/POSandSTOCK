package controller.porder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.BackendUI;
import controller.porder.FinishUI;
import util.JTableCenter;
import util.MarqueePanel;
import util.PrintUtils;
import util.StockManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.EventQueue;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PorderMainUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel leftone; 
    private JLabel clockLabel;
    private Timer clockTimer;
    private DefaultTableModel tableModel; // 表格的模型
    private JTable orderTable; // 訂單表格
    private JLabel totalCupsLabel; // 總杯數標籤
    private JLabel totalAmountLabel; // 總金額標籤
    private JLabel discountLabel; // 折扣後金額標籤
    private JCheckBox plasticBagCheckbox;
    private JTable stockTable;
    private int totalAmount = 0;  // 新增類別成員變數
    private int discountedTotal = 0;  // 新增類別成員變數

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PorderMainUI frame = new PorderMainUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PorderMainUI(List<String[]> orderData) {
        this(); // 呼叫原本的建構子
        // 將訂單資料填入 tableModel
        for (String[] row : orderData) {
            tableModel.addRow(new Object[]{row[0], Integer.parseInt(row[1]), Integer.parseInt(row[2])});
        }
        updateTotals(); // 更新總金額和總杯數
        updateStockTable(); // 更新庫存顯示
    }

    public PorderMainUI() {
        // 從資料庫讀取初始庫存
        StockManager.loadStockFromDatabase();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 760, 555);
        setLocationRelativeTo(null); // 將視窗置中
        contentPane = new JPanel();
        contentPane.setBackground(new Color(247, 245, 238));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel imagesPanel = new JPanel();
        imagesPanel.setBackground(new Color(247, 245, 238));
        imagesPanel.setBounds(6, 93, 430, 419);
        contentPane.add(imagesPanel);
        imagesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        String[][] drinks = {
            {"玄麥茉莉", "45", "玄麥茉莉.png"},
            {"赤羽紅茶", "45", "赤羽紅茶.png"},
            {"初春青茶", "50", "初春青茶.png"},
            {"十薰茉莉", "55", "十薰茉莉.png"}
        };

        int maxButtonWidth = 200;
        for (String[] drink : drinks) {
            JButton drinkButton = new JButton();
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/" + drink[2]));
                Image scaledImage = icon.getImage().getScaledInstance(maxButtonWidth, maxButtonWidth, Image.SCALE_SMOOTH);
                drinkButton.setIcon(new ImageIcon(scaledImage));
                drinkButton.setPreferredSize(new Dimension(maxButtonWidth, maxButtonWidth));
            } catch (Exception e) {
                System.err.println("圖片未找到: " + drink[2]);
                drinkButton.setText(drink[0]);
            }
            
            drinkButton.setBorder(BorderFactory.createEmptyBorder());
            drinkButton.setFocusPainted(false);
            imagesPanel.add(drinkButton);

            // 添加點擊事件
            drinkButton.addActionListener(e -> addOrderToTable(drink[0], Integer.parseInt(drink[1])));
        }

        // 創建茶湯庫存的JTable
        String[] stockColumns = {" ","玄麥茉莉", "赤羽紅茶", "初春青茶", "十薰茉莉"};
        Object[][] stockData = {
            {"剩餘杯數", 
                StockManager.getStock("玄麥茉莉"), 
                StockManager.getStock("赤羽紅茶"),
                StockManager.getStock("初春青茶"), 
                StockManager.getStock("十薰茉莉")}
        };

        // 設定表格的列名
        String[] columnNames = {"品項", "單價", "數量"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // 創建 JTable 並設置模型
        orderTable = new JTable(tableModel);
        JTableCenter.setTableCenterAlignment(orderTable);

        // 在 tableModel 的監聽器中更新
        tableModel.addTableModelListener(e -> {
            updateTotals();  // 更新總金額和總杯數
            StockManager.updateStockFromOrder(tableModel);  // 即時更新庫存顯示
            updateStockTable();  // 確保 UI 顯示更新
        });

        // 將表格添加到滾動面板中
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBounds(448, 103, 300, 130);
        contentPane.add(scrollPane);

        // 設置每一欄的寬度
        TableColumn column;
        column = orderTable.getColumnModel().getColumn(0); // "品項" 欄位
        column.setPreferredWidth(100); 
        column = orderTable.getColumnModel().getColumn(1); // "單價" 欄位
        column.setPreferredWidth(50); 
        column = orderTable.getColumnModel().getColumn(2); // "數量" 欄位
        column.setPreferredWidth(50); 

        // 設置 "數量" 欄位為可編輯的 JComboBox
        JComboBox<Integer> comboBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            comboBox.addItem(i);
        }
        TableColumn quantityColumn = orderTable.getColumnModel().getColumn(2);
        quantityColumn.setCellEditor(new DefaultCellEditor(comboBox));

        leftone = new JPanel();
        leftone.setBackground(new Color(247, 245, 238));
        leftone.setBounds(6, 2, 430, 92);
        contentPane.add(leftone);
        leftone.setLayout(null);

        ImageIcon originalIcon = new ImageIcon(PorderMainUI.class.getResource("/三白茉莉.png"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(82, 82, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imageLabel.setBounds(335, 10, 82, 82);
        leftone.add(imageLabel);

        setupMarquee();

        JButton settingsButton = new JButton(new ImageIcon(
            new ImageIcon(PorderMainUI.class.getResource("/Setting.png"))
                .getImage()
                .getScaledInstance(40, 40, Image.SCALE_SMOOTH)
        ));
        settingsButton.setBounds(6, 10, 40, 40);
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.addActionListener(e -> {
            new BackendUI().setVisible(true);
            dispose();
        });
        leftone.add(settingsButton);

        clockLabel = new JLabel("", SwingConstants.RIGHT);
        clockLabel.setBounds(448, 6, 300, 16);
        contentPane.add(clockLabel);
        clockLabel.setVerticalAlignment(SwingConstants.TOP);

        totalCupsLabel = new JLabel("總杯數：0 杯", SwingConstants.CENTER);
        totalCupsLabel.setBounds(448, 296, 300, 40);
        contentPane.add(totalCupsLabel);
        totalCupsLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

        totalAmountLabel = new JLabel("總金額：0 元", SwingConstants.CENTER);
        totalAmountLabel.setBounds(448, 348, 300, 40);
        contentPane.add(totalAmountLabel);
        totalAmountLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

        discountLabel = new JLabel("", SwingConstants.CENTER);
        discountLabel.setBounds(448, 400, 300, 40);
        contentPane.add(discountLabel);
        discountLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

        plasticBagCheckbox = new JCheckBox("購買塑膠袋 (+1元)");
        plasticBagCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
        plasticBagCheckbox.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        plasticBagCheckbox.setBounds(448, 245, 300, 40);
        contentPane.add(plasticBagCheckbox);
        plasticBagCheckbox.addActionListener(e -> updateTotals());

        clockTimer = new Timer(1000, e -> updateClock());
        clockTimer.start();
        
        JPanel fourbutton = new JPanel();
        fourbutton.setBackground(new Color(247, 245, 238));
        fourbutton.setBounds(448, 452, 300, 60);
        contentPane.add(fourbutton);

        fourbutton.setLayout(new GridLayout(1, 4, 10, 0));

        JButton checkoutButton = new JButton("結帳");
        checkoutButton.setFont(new Font("新細明體", Font.PLAIN, 16));
        checkoutButton.addActionListener(e -> proceedToCheckout());
        fourbutton.add(checkoutButton);

        JButton clearButton = new JButton("清除");
        clearButton.setFont(new Font("新細明體", Font.PLAIN, 16));
        clearButton.addActionListener(e -> clearOrder());
        fourbutton.add(clearButton);

        JButton deleteButton = new JButton("刪除");
        deleteButton.setFont(new Font("新細明體", Font.PLAIN, 16));
        deleteButton.addActionListener(e -> deleteOrder());
        fourbutton.add(deleteButton);

        JButton printButton = new JButton("列印");
        printButton.setFont(new Font("新細明體", Font.PLAIN, 16));
        printButton.addActionListener(e -> printOrder());
        fourbutton.add(printButton);

        DefaultTableModel stockTableModel = new DefaultTableModel(stockData, stockColumns);
        stockTable = new JTable(stockTableModel);
        JTableCenter.setTableCenterAlignment(stockTable);
        
        JScrollPane stockScrollPane = new JScrollPane(stockTable);
        stockScrollPane.setBounds(448, 34, 300, 47);
        contentPane.add(stockScrollPane);
        
        StockManager.setUpdateCallback(() -> {
            SwingUtilities.invokeLater(() -> {
                updateStockTable();
            });
        });
    }

    private void setupMarquee() {
        String text = "活動快訊：【買四杯送一杯】任選購買五杯飲品，折扣其中一杯價格最低者";
        MarqueePanel marqueePanel = new MarqueePanel(text, 285, 30);
        marqueePanel.setBounds(50, 35, 289, 30);  // 從x=50開始，寬度為285
        leftone.add(marqueePanel);
        marqueePanel.start();
    }
    
    private void updateStockTable() {
        DefaultTableModel stockTableModel = (DefaultTableModel) stockTable.getModel();
        stockTableModel.setValueAt(StockManager.getStock("玄麥茉莉"), 0, 1);
        stockTableModel.setValueAt(StockManager.getStock("赤羽紅茶"), 0, 2);
        stockTableModel.setValueAt(StockManager.getStock("初春青茶"), 0, 3);
        stockTableModel.setValueAt(StockManager.getStock("十薰茉莉"), 0, 4);
    }

    private void addOrderToTable(String drinkName, int price) {
        boolean found = false;
        int currentQuantity = 0;
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(drinkName)) {
                currentQuantity = (int) tableModel.getValueAt(i, 2);
                found = true;
                break;
            }
        }

        if (!StockManager.canAddOrder(drinkName, currentQuantity)) {
            return;
        }

        if (found) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(drinkName)) {
                    tableModel.setValueAt(currentQuantity + 1, i, 2);
                    break;
                }
            }
        } else {
            tableModel.addRow(new Object[]{drinkName, price, 1});
        }

        StockManager.updateStockFromOrder(tableModel);
        updateTotals();
        updateStockTable();
    }

    private void updateClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        clockLabel.setText(LocalDateTime.now().format(formatter));
    }

    private void updateTotals() {
        int totalCups = 0;
        totalAmount = 0;
        discountedTotal = 0;
        int discountAmount = 0;
        int plasticBagCost = plasticBagCheckbox.isSelected() ? 1 : 0;

        List<Integer> prices = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int price = (int) tableModel.getValueAt(i, 1);
            int quantity = (int) tableModel.getValueAt(i, 2);
            totalCups += quantity;
            totalAmount += price * quantity;
            for (int j = 0; j < quantity; j++) {
                prices.add(price);
            }
        }

        totalAmount += plasticBagCost;

        if (totalCups >= 5) {
            prices.sort(Integer::compareTo);
            int discountableCups = totalCups / 5;
            for (int i = 0; i < discountableCups; i++) {
                discountAmount += prices.get(i);
            }
        }

        totalCupsLabel.setText("總杯數：" + totalCups + " 杯");

        if (totalCups >= 5) {
            totalAmountLabel.setText("<html>總金額：<strike>" + (totalAmount - plasticBagCost) + "</strike> 元</html>");
        } else {
            totalAmountLabel.setText("總金額：" + totalAmount + " 元");
        }

        if (totalCups >= 5) {
            discountedTotal = totalAmount - discountAmount;
            discountLabel.setText("折扣後金額：" + discountedTotal + " 元");
        } else {
            discountedTotal = 0;
            discountLabel.setText("");
        }
    }

    private void proceedToCheckout() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "請先選擇飲料", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StockManager.updateStockFromOrder(tableModel);

        String orderTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        List<String[]> orderData = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String drinkName = tableModel.getValueAt(i, 0).toString();
            String price = tableModel.getValueAt(i, 1).toString();
            String quantity = tableModel.getValueAt(i, 2).toString();
            orderData.add(new String[]{drinkName, price, quantity, orderTimestamp});
        }

        int finalAmount = discountedTotal > 0 ? discountedTotal : totalAmount;
        FinishUI finishUI = new FinishUI(orderData, totalAmount, finalAmount);
        finishUI.setVisible(true);
        dispose();
    }
    
    private void clearOrder() {
        tableModel.setRowCount(0);
        plasticBagCheckbox.setSelected(false);
        StockManager.updateStockFromOrder(tableModel);
        updateStockTable();
        updateTotals();
    }
    
    private void deleteOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要刪除的訂單", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "確定要刪除此訂單嗎？", "確認刪除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            updateTotals();
            updateStockTable();
        }
    }

    private void printOrder() {
        String[] options = {"PDF", "CSV"};
        int choice = JOptionPane.showOptionDialog(this,
                "請選擇儲存格式",
                "儲存格式選擇",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        String orderTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        if (choice == 0) {
            PrintUtils.printOrder(orderTable, orderTimestamp, this);
        } else if (choice == 1) {
            PrintUtils.saveAsCSV(orderTable, orderTimestamp, this);
        } 
    }
}