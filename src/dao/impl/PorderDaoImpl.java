package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.PorderDao;
import model.Porder;
import util.DbConnection;

public class PorderDaoImpl implements PorderDao {

    @Override
    public void add(Porder porder) {
        String sql = "INSERT INTO porder (name, GreenTea, BlackTea, OolongTea, BuckwheatTea, Time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, porder.getName());
            pstmt.setInt(2, porder.getGreenTea());
            pstmt.setInt(3, porder.getBlackTea());
            pstmt.setInt(4, porder.getOolongTea());
            pstmt.setInt(5, porder.getBuckwheatTea());
            pstmt.setString(6, porder.getTime());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
    }

    @Override
    public List<Porder> selectAll() {
        List<Porder> orders = new ArrayList<>();
        String sql = "SELECT * FROM porder";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Porder porder = new Porder(
                    rs.getString("name"),
                    rs.getInt("GreenTea"),
                    rs.getInt("BlackTea"),
                    rs.getInt("OolongTea"),
                    rs.getInt("BuckwheatTea"),
                    rs.getString("Time")
                );
                porder.setId(rs.getInt("id"));
                orders.add(porder);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
        return orders;
    }

    @Override
    public List<Porder> selectById(int id) {
        List<Porder> orders = new ArrayList<>();
        String sql = "SELECT * FROM porder WHERE id = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Porder porder = new Porder(
                        rs.getString("name"),
                        rs.getInt("GreenTea"),
                        rs.getInt("BlackTea"),
                        rs.getInt("OolongTea"),
                        rs.getInt("BuckwheatTea"),
                        rs.getString("Time")
                    );
                    porder.setId(rs.getInt("id"));
                    orders.add(porder);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
        return orders;
    }

    @Override
    public void update(Porder porder) {
        String sql = "UPDATE porder SET name=?, GreenTea=?, BlackTea=?, OolongTea=?, BuckwheatTea=?, Time=? WHERE id=?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, porder.getName());
            pstmt.setInt(2, porder.getGreenTea());
            pstmt.setInt(3, porder.getBlackTea());
            pstmt.setInt(4, porder.getOolongTea());
            pstmt.setInt(5, porder.getBuckwheatTea());
            pstmt.setString(6, porder.getTime());
            pstmt.setInt(7, porder.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM porder WHERE id=?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 可以改為記錄日誌
        }
    }
}