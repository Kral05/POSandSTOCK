package util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

public class StockManager {

    private static int BuckwheatTeaStock;
    private static int BlackTeaStock;
    private static int OolongTeaStock;
    private static int GreenTeaStock;
    private static StringBuilder teaToBrewList = new StringBuilder();
    private static Map<String, Boolean> teaRemindMap = new HashMap<>();
    private static Map<String, Boolean> brewingTeaMap = new HashMap<>();
    private static int currentDialogCount = 0; // 當前窗口數量
    private static final int OFFSET_STEP = 30; // 每個窗口偏移的像素量

    // 初始化 brewingTeaMap
    static {
        brewingTeaMap.put("玄麥茉莉", false);
        brewingTeaMap.put("赤羽紅茶", false);
        brewingTeaMap.put("初春青茶", false);
        brewingTeaMap.put("十薰茉莉", false);
        
        // 設置每天午夜自動重置庫存
        scheduleDailyStockReset();
    }

    private static Runnable updateCallback;

    public static void setUpdateCallback(Runnable callback) {
        updateCallback = callback;
    }

    public static void updateStockTable() {
        if (updateCallback != null) {
            updateCallback.run();
        }
    }

    public static Object[][] getStockData() {
        return new Object[][]{
            {"玄麥茉莉", BuckwheatTeaStock},
            {"赤羽紅茶", BlackTeaStock},
            {"初春青茶", OolongTeaStock},
            {"十薰茉莉", GreenTeaStock}
        };
    }

    public static void updateStockFromOrder(DefaultTableModel tableModel) {
        loadStockFromDatabase();
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String drinkName = tableModel.getValueAt(i, 0).toString();
            int quantity = (int) tableModel.getValueAt(i, 2);
            
            switch (drinkName) {
                case "玄麥茉莉":
                    if (BuckwheatTeaStock - quantity >= 0) {
                        BuckwheatTeaStock -= quantity;
                    } else {
                        JOptionPane.showMessageDialog(null, "玄麥茉莉庫存不足！", "警告", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "赤羽紅茶":
                    if (BlackTeaStock - quantity >= 0) {
                        BlackTeaStock -= quantity;
                    } else {
                        JOptionPane.showMessageDialog(null, "赤羽紅茶庫存不足！", "警告", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "初春青茶":
                    if (OolongTeaStock - quantity >= 0) {
                        OolongTeaStock -= quantity;
                    } else {
                        JOptionPane.showMessageDialog(null, "初春青茶庫存不足！", "警告", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "十薰茉莉":
                    if (GreenTeaStock - quantity >= 0) {
                        GreenTeaStock -= quantity;
                    } else {
                        JOptionPane.showMessageDialog(null, "十薰茉莉庫存不足！", "警告", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
            }
        }

        updateStockTable();
    }

    public static boolean canAddOrder(String drinkName, int currentQuantity) {
        int currentStock = getStock(drinkName);
        
        if (currentStock <= 0) {
            JOptionPane.showMessageDialog(null, 
                drinkName + " 庫存不足，請選擇其他飲品！", 
                "警告", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }

    public static int getStock(String drinkName) {
        switch (drinkName) {
            case "玄麥茉莉":
                return BuckwheatTeaStock;
            case "赤羽紅茶":
                return BlackTeaStock;
            case "初春青茶":
                return OolongTeaStock;
            case "十薰茉莉":
                return GreenTeaStock;
            default:
                return 0;
        }
    }

    public static void loadStockFromDatabase() {
        String sql = "SELECT tea_name, stock FROM tea_stock";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String teaName = rs.getString("tea_name");
                int stock = rs.getInt("stock");

                switch (teaName) {
                    case "玄麥茉莉":
                        BuckwheatTeaStock = stock;
                        break;
                    case "赤羽紅茶":
                        BlackTeaStock = stock;
                        break;
                    case "初春青茶":
                        OolongTeaStock = stock;
                        break;
                    case "十薰茉莉":
                        GreenTeaStock = stock;
                        break;
                }
            }

            updateStockTable();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "無法讀取庫存！", "錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 設置每天過12點自動重置庫存
    private static void scheduleDailyStockReset() {
        Timer timer = new Timer(true);
        TimerTask resetTask = new TimerTask() {
            @Override
            public void run() {
                resetStockToDefault();
            }
        };

        // 計算從當前時間到午夜12點的延遲時間
        Calendar midnight = Calendar.getInstance();
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);
        midnight.set(Calendar.MILLISECOND, 0);
        midnight.add(Calendar.DAY_OF_MONTH, 1); // 設為隔天的午夜

        long delay = midnight.getTimeInMillis() - System.currentTimeMillis();
        long period = 24 * 60 * 60 * 1000; // 24小時

        timer.scheduleAtFixedRate(resetTask, delay, period);
    }

    // 重置庫存為預設值
    private static void resetStockToDefault() {
        BuckwheatTeaStock = 16;
        BlackTeaStock = 20;
        OolongTeaStock = 20;
        GreenTeaStock = 20;
        
        updateStockTable();
        updateStockToDatabase();
        JOptionPane.showMessageDialog(null, "庫存已於午夜重置為預設值！", "提示", JOptionPane.INFORMATION_MESSAGE);
    }



    // 這裡新增的，僅在交易成功後更新庫存
    public static void updateStockToDatabase() {
        String sql = "SELECT tea_name, stock FROM tea_stock";
        String updateSql = "UPDATE tea_stock SET stock = ? WHERE tea_name = ?";

        try (Connection conn = DbConnection.getDb()) {
            // 先讀取當前資料庫的庫存
            Map<String, Integer> currentStock = new HashMap<>();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    currentStock.put(rs.getString("tea_name"), rs.getInt("stock"));
                }
            }

            // 更新庫存到資料庫
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                if (BuckwheatTeaStock != currentStock.getOrDefault("玄麥茉莉", 0)) {
                    updateStmt.setInt(1, BuckwheatTeaStock);
                    updateStmt.setString(2, "玄麥茉莉");
                    updateStmt.executeUpdate();
                }

                if (BlackTeaStock != currentStock.getOrDefault("赤羽紅茶", 0)) {
                    updateStmt.setInt(1, BlackTeaStock);
                    updateStmt.setString(2, "赤羽紅茶");
                    updateStmt.executeUpdate();
                }

                if (OolongTeaStock != currentStock.getOrDefault("初春青茶", 0)) {
                    updateStmt.setInt(1, OolongTeaStock);
                    updateStmt.setString(2, "初春青茶");
                    updateStmt.executeUpdate();
                }

                if (GreenTeaStock != currentStock.getOrDefault("十薰茉莉", 0)) {
                    updateStmt.setInt(1, GreenTeaStock);
                    updateStmt.setString(2, "十薰茉莉");
                    updateStmt.executeUpdate();
                }
            }

            // 資料庫更新完畢後，重新載入庫存數據
            loadStockFromDatabase();

            // 重新檢查低庫存
            checkLowStock();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "庫存更新失敗！", "錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }





    // 恢復初始庫存數量
    public static void restoreInitialStock() {
        loadStockFromDatabase();
    }
    
    public static void checkLowStock() {
        // 初始化提醒狀態
        teaRemindMap.putIfAbsent("玄麥茉莉", false);
        teaRemindMap.putIfAbsent("赤羽紅茶", false);
        teaRemindMap.putIfAbsent("初春青茶", false);
        teaRemindMap.putIfAbsent("十薰茉莉", false);

        // 檢查每種茶品庫存，並判斷是否需要提示
        checkAndPrompt("玄麥茉莉", BuckwheatTeaStock);
        checkAndPrompt("赤羽紅茶", BlackTeaStock);
        checkAndPrompt("初春青茶", OolongTeaStock);
        checkAndPrompt("十薰茉莉", GreenTeaStock);
    }

    // 抽取提示邏輯，減少重複代碼
    private static void checkAndPrompt(String teaName, int stock) {
        // 如果庫存不足且未進行提示，並且不在煮茶狀態，則提示
        if (stock <= 5 && !teaRemindMap.get(teaName) && !brewingTeaMap.get(teaName)) {
            promptBrewing(teaName);
        }
    }



    private static void promptBrewing(String teaName) {
        if (brewingTeaMap.get(teaName)) {
            return; // 已經在製作中，避免重複提示
        }

        int option = JOptionPane.showOptionDialog(null,
                teaName + "快沒了，該煮茶了",
                "庫存警告",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new Object[]{"現在就去煮", "等一下"},
                "現在就去煮");

        if (option == JOptionPane.YES_OPTION) {
            // 標記該茶品進入製作狀態
            teaRemindMap.put(teaName, true);
            brewingTeaMap.put(teaName, true);

            // 開始製作計時器
            startTeaBrewingTimer(teaName);
        } else {
            // 30秒後允許再次提醒
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    teaRemindMap.put(teaName, false);
                    checkLowStock(); // 重新檢查庫存
                }
            }, 30000);
        }
    }


    private static void startTeaBrewingTimer(String teaName) {
        final Timer brewingTimer = new Timer();
        final JDialog countdownDialog = new JDialog();
        final JLabel countdownLabel = new JLabel();

     // 設置倒計時對話框
        countdownDialog.setTitle("茶品製作中");
        countdownDialog.setSize(300, 150);
        countdownDialog.setLocationByPlatform(true);  // 自動決定不重疊的位置
        countdownDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        countdownDialog.setLayout(new FlowLayout());

        // 添加倒計時標籤
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 16));
        countdownDialog.add(countdownLabel);

        // 顯示需要煮的茶品
        JLabel teaLabel = new JLabel("<html>正在製作：" + teaName + "</html>");
        teaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        countdownDialog.add(teaLabel);

        countdownDialog.setVisible(true);

        final long startTime = System.currentTimeMillis();
        final long duration = 60 * 1000; // 1分鐘

        // 更新倒計時的計時器
        Timer updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;
                    long remainingTime = duration - elapsedTime;

                    if (remainingTime <= 0) {
                        countdownDialog.dispose();
                        updateTimer.cancel();
                    } else {
                        long minutes = remainingTime / 60000;
                        long seconds = (remainingTime % 60000) / 1000;
                        countdownLabel.setText(String.format("剩餘時間：%02d:%02d", minutes, seconds));
                    }
                });
            }
        }, 0, 1000);

        // 1分鐘後執行的任務
        brewingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    // 根據茶品名稱增加相應庫存
                    switch (teaName) {
                        case "玄麥茉莉":
                            BuckwheatTeaStock += 8;
                            break;
                        case "赤羽紅茶":
                            BlackTeaStock += 10;
                            break;
                        case "初春青茶":
                            OolongTeaStock += 10;
                            break;
                        case "十薰茉莉":
                            GreenTeaStock += 10;
                            break;
                    }

                    // 更新庫存顯示
                    updateStockTable();
                    updateStockToDatabase();

     
                    
                    // 創建非模式對話框來顯示提示
                    JDialog dialog = new JDialog();
                    dialog.setTitle("提示");
                    dialog.setSize(300, 150);
                    dialog.setLayout(new FlowLayout());
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // 點擊關閉按鈕即可關閉
                    dialog.setAlwaysOnTop(true); // 保持視窗在最上層

                    // 添加消息標籤
                    JLabel messageLabel = new JLabel(teaName + "已煮好，庫存已增加！");
                    messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    dialog.add(messageLabel);

                    // 添加一個按鈕來關閉窗口
                    JButton okButton = new JButton("OK");
                    okButton.addActionListener(e -> dialog.dispose());
                    dialog.add(okButton);

                    // 計算窗口位置，偏移每個窗口
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int x = (screenSize.width - dialog.getWidth()) / 2 + (OFFSET_STEP * currentDialogCount);
                    int y = (screenSize.height - dialog.getHeight()) / 2 + (OFFSET_STEP * currentDialogCount);

                    // 設置窗口位置，確保不超出螢幕邊界
                    if (x + dialog.getWidth() > screenSize.width) {
                        x = screenSize.width - dialog.getWidth();
                    }
                    if (y + dialog.getHeight() > screenSize.height) {
                        y = screenSize.height - dialog.getHeight();
                    }

                    dialog.setLocation(x, y);
                    dialog.setVisible(true);

                    // 增加窗口計數
                    currentDialogCount++;
                


                       



                    // 取消計時器
                    brewingTimer.cancel();

                    // 煮茶完成，移除 brewingTeaMap 的標記，允許再次提醒
                    brewingTeaMap.put(teaName, false);
                });
            }
        }, duration);
    }

}
