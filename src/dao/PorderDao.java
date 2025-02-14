package dao;

import java.util.List;

import model.Porder;

public interface PorderDao {
	//create-->void
	void add(Porder porder);
	
	//read-->List
	List<Porder> selectAll();
	List<Porder> selectById(int id);
	
	//update-->void
	void update(Porder porder);
	
	//delete-->void
	void delete(int id);

}