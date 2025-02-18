package service.impl;

import java.util.List;
import dao.impl.StockDaoImpl;
import model.Stock;
import service.StockService;

public class StockServiceImpl implements StockService {

    private static StockDaoImpl stockDaoImpl = new StockDaoImpl();

    @Override
    public void addStock(Stock stock) {
        // 檢查庫存是否為非負數
        if (stock.getBuckwheatTeaStock() >= 0 && stock.getBlackTeaStock() >= 0 &&
            stock.getOolongTeaStock() >= 0 && stock.getGreenTeaStock() >= 0) {
            stockDaoImpl.add(stock);
        } else {
            System.out.println("庫存數量不能為負數");
        }
    }


    @Override
    public String getAllStockReport() {
        List<Stock> stockList = stockDaoImpl.selectAll();
        StringBuilder report = new StringBuilder();
        report.append("茶葉庫存報表：\n");

        for (Stock stock : stockList) {
            report.append("玄麥茉莉: ").append(stock.getBuckwheatTeaStock()).append("\n")
                  .append("赤羽紅茶: ").append(stock.getBlackTeaStock()).append("\n")
                  .append("初春青茶: ").append(stock.getGreenTeaStock()).append("\n")
                  .append("十薰茉莉: ").append(stock.getOolongTeaStock()).append("\n");
        }

        return report.toString();
    }

    @Override
    public List<Stock> findAllStock() {
        return stockDaoImpl.selectAll();
    }

    @Override
    public Stock findByTeaName(String teaName) {
        Stock stock = null;
        if (teaName != null && !teaName.isEmpty()) {
            stock = stockDaoImpl.selectByTeaName(teaName);
        }
        return stock;
    }

    @Override
    public void updateStock(String teaName, int newStock) {
        if (newStock >= 0) {
            Stock stock = stockDaoImpl.selectByTeaName(teaName);
            if (stock != null) {
                boolean isUpdated = false;
                switch (teaName) {
                    case "玄麥茉莉":
                        if (stock.getBuckwheatTeaStock() != newStock) {
                            stock.setBuckwheatTeaStock(newStock);
                            isUpdated = true;
                        }
                        break;
                    case "赤羽紅茶":
                        if (stock.getBlackTeaStock() != newStock) {
                            stock.setBlackTeaStock(newStock);
                            isUpdated = true;
                        }
                        break;
                    case "初春青茶":
                        if (stock.getGreenTeaStock() != newStock) {
                            stock.setGreenTeaStock(newStock);
                            isUpdated = true;
                        }
                        break;
                    case "十薰茉莉":
                        if (stock.getOolongTeaStock() != newStock) {
                            stock.setOolongTeaStock(newStock);
                            isUpdated = true;
                        }
                        break;
                }

                if (isUpdated) {
                    stockDaoImpl.update(stock);
                }
            }
        }
    }


    @Override
    public void updateStock(Stock stock) {
        if (stock != null) {
            stockDaoImpl.update(stock);
        }
    }

    @Override
    public void deleteStockByTeaName(String teaName) {
        if (teaName != null && !teaName.isEmpty()) {
            stockDaoImpl.delete(teaName);
        }
    }

    @Override
    public void deductStock(String teaName, int quantity) {
        if (teaName != null && !teaName.isEmpty() && quantity > 0) {
            Stock stock = stockDaoImpl.selectByTeaName(teaName);
            if (stock != null) {
                int currentStock = 0;
                switch (teaName) {
                    case "玄麥茉莉":
                        currentStock = stock.getBuckwheatTeaStock();
                        break;
                    case "赤羽紅茶":
                        currentStock = stock.getBlackTeaStock();
                        break;
                    case "初春青茶":
                        currentStock = stock.getGreenTeaStock();
                        break;
                    case "十薰茉莉":
                        currentStock = stock.getOolongTeaStock();
                        break;
                }

                if (currentStock >= quantity) {
                    // 扣減庫存
                    switch (teaName) {
                        case "玄麥茉莉":
                            stock.setBuckwheatTeaStock(currentStock - quantity);
                            break;
                        case "赤羽紅茶":
                            stock.setBlackTeaStock(currentStock - quantity);
                            break;
                        case "初春青茶":
                            stock.setGreenTeaStock(currentStock - quantity);
                            break;
                        case "十薰茉莉":
                            stock.setOolongTeaStock(currentStock - quantity);
                            break;
                    }
                    stockDaoImpl.update(stock);
                } else {
                    // 不足庫存的情況，可以拋出異常或其他處理邏輯
                    System.out.println("庫存不足，無法扣減！");
                }
            }
        }
    }

}