package model;

public class Stock {
	private Integer BuckwheatTeaStock;
	private Integer BlackTeaStock;
	private Integer OolongTeaStock;
	private Integer GreenTeaStock;
	public Stock(Integer buckwheatTeaStock, Integer blackTeaStock, Integer oolongTeaStock, Integer greenTeaStock) {
		super();
		BuckwheatTeaStock = buckwheatTeaStock;
		BlackTeaStock = blackTeaStock;
		OolongTeaStock = oolongTeaStock;
		GreenTeaStock = greenTeaStock;
	}
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getBuckwheatTeaStock() {
		return BuckwheatTeaStock;
	}
	public void setBuckwheatTeaStock(Integer buckwheatTeaStock) {
		BuckwheatTeaStock = buckwheatTeaStock;
	}
	public Integer getBlackTeaStock() {
		return BlackTeaStock;
	}
	public void setBlackTeaStock(Integer blackTeaStock) {
		BlackTeaStock = blackTeaStock;
	}
	public Integer getOolongTeaStock() {
		return OolongTeaStock;
	}
	public void setOolongTeaStock(Integer oolongTeaStock) {
		OolongTeaStock = oolongTeaStock;
	}
	public Integer getGreenTeaStock() {
		return GreenTeaStock;
	}
	public void setGreenTeaStock(Integer greenTeaStock) {
		GreenTeaStock = greenTeaStock;
	}
	
	
}