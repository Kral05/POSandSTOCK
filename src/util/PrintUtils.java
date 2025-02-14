package util;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PrintUtils {

    public static void saveAsCSV(JTable table, String orderTimestamp, JFrame parentFrame) {
        try {
            // 構建 CSV 的內容，包括標題行
            StringBuilder content = new StringBuilder();
            content.append("品項, 價格, 數量, 訂購時間\n");

            // 獲取表格內容並生成對應的 CSV 內容
            for (int i = 0; i < table.getRowCount(); i++) {
                content.append(table.getValueAt(i, 0)).append(", ");  // 品項
                content.append(table.getValueAt(i, 1)).append(", ");  // 價格
                content.append(table.getValueAt(i, 2)).append(", ");  // 數量
                content.append(orderTimestamp).append("\n");          // 訂購時間
            }

            // 儲存為 CSV 文件
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("儲存 CSV");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getName().endsWith(".csv")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                    writer.write(content.toString());
                }
                JOptionPane.showMessageDialog(parentFrame, "CSV 已成功儲存！");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame, "CSV 儲存錯誤: " + e.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 列印表格內容
    public static void printOrder(JTable table, String orderTimestamp, JFrame parentFrame) {
        // 創建打印工作
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
                if (page > 0) {
                    return NO_SUCH_PAGE;  // 如果超過頁數，返回無效頁面
                }

                // 設定字體和格式
                g.setFont(new Font("Serif", Font.PLAIN, 12));
                g.drawString("訂單時間: " + orderTimestamp, 100, 100); // 顯示訂單時間

                // 設定表格內容
                int yPos = 120;
                for (int i = 0; i < table.getRowCount(); i++) {
                    String rowData = "";
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        rowData += table.getValueAt(i, j) + "\t"; // 表格每個單元格的數據
                    }
                    g.drawString(rowData, 100, yPos);
                    yPos += 20; // 每行之間的間距
                }

                return PAGE_EXISTS;  // 返回有效頁面
            }
        });

        // 顯示列印對話框
        if (job.printDialog()) {
            try {
                job.print();  // 開始列印
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentFrame, "列印錯誤: " + ex.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
