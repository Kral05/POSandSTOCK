package util;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class JTableCenter {

    // 將表格標題和內容都置中
    public static void setTableCenterAlignment(JTable table) {
        // 設置表格數據區域的渲染器為置中對齊
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);  // 設置為置中對齊

        // 設置每一列的內容都置中
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 設置表格標題的渲染器為置中對齊
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);  // 設置為置中對齊
    }
}
