package service;

import java.util.List;
import model.Stock;

public interface StockService {
    // Create
    void addStock(Stock stock);

    // Read --> 列印報表
    String getAllStockReport();
    List<Stock> findAllStock();
    Stock findByTeaName(String teaName);

    // Update
    void updateStock(String teaName, int newStock);
    void updateStock(Stock stock);

    // Delete
    void deleteStockByTeaName(String teaName);

    // 新增：扣除庫存
    void deductStock(String teaName, int quantity);
}