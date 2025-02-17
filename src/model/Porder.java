package model;

public class Porder {
	private int id;
	private String name;
	private int GreenTea;
	private int BlackTea;
	private int OolongTea;
	private int BuckwheatTea;
	private String Time;
	public Porder(String name, int greenTea, int blackTea, int oolongTea, int buckwheatTea, String time) {
		super();
		this.name = name;
		GreenTea = greenTea;
		BlackTea = blackTea;
		OolongTea = oolongTea;
		BuckwheatTea = buckwheatTea;
		Time = time;
	}
	public Porder() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGreenTea() {
		return GreenTea;
	}
	public void setGreenTea(int greenTea) {
		GreenTea = greenTea;
	}
	public int getBlackTea() {
		return BlackTea;
	}
	public void setBlackTea(int blackTea) {
		BlackTea = blackTea;
	}
	public int getOolongTea() {
		return OolongTea;
	}
	public void setOolongTea(int oolongTea) {
		OolongTea = oolongTea;
	}
	public int getBuckwheatTea() {
		return BuckwheatTea;
	}
	public void setBuckwheatTea(int buckwheatTea) {
		BuckwheatTea = buckwheatTea;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}

}
