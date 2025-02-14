package service;

import java.util.List;

import model.Porder;

public interface PorderService {
	//create
	void addPorder(Porder porder);
	
	//read-->列印報表
	String AllPorder();
	List<Porder> findAllPorder();	
	Porder findById(int id);
	
	//update
	void updatePorder(String name,int id);
	void updatePorder(int GreenTea, int BlackTea, int OolongTea, int BuckwheatTea,int id) ;
	void updtaePorder(Porder porder);
	
	//delete
	void deletePorderById(int id);

	void updatePorder(Porder porder);

}