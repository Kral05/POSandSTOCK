package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.StockDao;
import model.Stock;
import util.DbConnection;

public class StockDaoImpl implements StockDao {

    @Override
    public void add(Stock stock) {
        String sql = "INSERT INTO tea_stock (tea_name, stock) VALUES (?, ?)";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 插入每一種茶葉的庫存
            pstmt.setString(1, "玄麥茉莉");
            pstmt.setInt(2, stock.getBuckwheatTeaStock());
            pstmt.addBatch();

            pstmt.setString(1, "赤羽紅茶");
            pstmt.setInt(2, stock.getBlackTeaStock());
            pstmt.addBatch();

            pstmt.setString(1, "初春青茶");
            pstmt.setInt(2, stock.getGreenTeaStock());
            pstmt.addBatch();

            pstmt.setString(1, "十薰茉莉");
            pstmt.setInt(2, stock.getOolongTeaStock());
            pstmt.addBatch();

            pstmt.executeBatch();  // 批量插入數據
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
    }

    @Override
    public List<Stock> selectAll() {
        List<Stock> stockList = new ArrayList<>();
        String sql = "SELECT tea_name, stock FROM tea_stock";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String teaName = rs.getString("tea_name");
                int stockValue = rs.getInt("stock");
                Stock stock = new Stock();  // 每次新建一個新的 Stock 對象

                switch (teaName) {
                    case "玄麥茉莉":
                        stock.setBuckwheatTeaStock(stockValue);
                        break;
                    case "赤羽紅茶":
                        stock.setBlackTeaStock(stockValue);
                        break;
                    case "初春青茶":
                        stock.setGreenTeaStock(stockValue);
                        break;
                    case "十薰茉莉":
                        stock.setOolongTeaStock(stockValue);
                        break;
                }
                stockList.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
        return stockList;
    }

    @Override
    public Stock selectByTeaName(String teaName) {
        Stock stock = null;
        String sql = "SELECT tea_name, stock FROM tea_stock WHERE tea_name = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, teaName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    stock = new Stock();  // 查到結果時才創建對象
                    int stockValue = rs.getInt("stock");

                    switch (teaName) {
                        case "玄麥茉莉":
                            stock.setBuckwheatTeaStock(stockValue);
                            break;
                        case "赤羽紅茶":
                            stock.setBlackTeaStock(stockValue);
                            break;
                        case "初春青茶":
                            stock.setGreenTeaStock(stockValue);
                            break;
                        case "十薰茉莉":
                            stock.setOolongTeaStock(stockValue);
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
        return stock;
    }

    @Override
    public void update(Stock stock) {
        String sql = "UPDATE tea_stock SET stock = ? WHERE tea_name = ?";
        try (Connection conn = DbConnection.getDb()) {
            conn.setAutoCommit(false);  // 開啟事務

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (stock.getBuckwheatTeaStock() != null) {
                    pstmt.setInt(1, stock.getBuckwheatTeaStock());
                    pstmt.setString(2, "玄麥茉莉");
                    pstmt.executeUpdate();
                }

                if (stock.getBlackTeaStock() != null) {
                    pstmt.setInt(1, stock.getBlackTeaStock());
                    pstmt.setString(2, "赤羽紅茶");
                    pstmt.executeUpdate();
                }

                if (stock.getGreenTeaStock() != null) {
                    pstmt.setInt(1, stock.getGreenTeaStock());
                    pstmt.setString(2, "初春青茶");
                    pstmt.executeUpdate();
                }

                if (stock.getOolongTeaStock() != null) {
                    pstmt.setInt(1, stock.getOolongTeaStock());
                    pstmt.setString(2, "十薰茉莉");
                    pstmt.executeUpdate();
                }

                conn.commit();  // 提交事務
            } catch (SQLException e) {
                conn.rollback();  // 發生錯誤時回滾
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);  // 重置自動提交
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
    }

    @Override
    public void delete(String teaName) {
        String sql = "DELETE FROM tea_stock WHERE tea_name = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, teaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
    }
}
