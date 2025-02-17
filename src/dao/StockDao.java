package dao;

import java.util.List;
import model.Stock;

public interface StockDao {
    // Create --> void
    void add(Stock stock);

    // Read --> List
    List<Stock> selectAll();
    Stock selectByTeaName(String teaName);

    // Update --> void
    void update(Stock stock);

    // Delete --> void
    void delete(String teaName);
}