package service.impl;

import java.util.List;

import dao.impl.PorderDaoImpl;
import model.Porder;
import service.PorderService;

public class PorderServiceImpl implements PorderService{

	public static void main(String[] args) {
		System.out.println(new PorderServiceImpl().findById(6));

	}
	
	private static PorderDaoImpl porderdaoimpl=new PorderDaoImpl();
	
	
	@Override
	public void addPorder(Porder porder) {
		if(porder.getGreenTea()>=0 && porder.getBlackTea()>=0 && porder.getOolongTea()>=0 && porder.getBuckwheatTea()>=0)
		{
			porderdaoimpl.add(porder);
		}
		
	}

	@Override
	public String AllPorder() {
		List<Porder> l=porderdaoimpl.selectAll();
		String show="";
		
		for(Porder p:l)
		{
			int sum=p.getGreenTea()*55+p.getBlackTea()*45+p.getOolongTea()*50+p.getBuckwheatTea()*45;
			
			show=show+"訂單編號:"+p.getId()+
					"\t客戶名稱:"+p.getName()+
					"\tGreenTea:"+p.getGreenTea()+
					"\tBlackTea:"+p.getBlackTea()+
					"\tOolongTea:"+p.getOolongTea()+
					"\tBuckwheatTea:"+p.getBuckwheatTea()+
					"\t金額:"+sum+"元\n";
		}
		
		
		return show;
	}

	@Override
	public List<Porder> findAllPorder() {
		
		return porderdaoimpl.selectAll();
	}

	@Override
	public Porder findById(int id) {
		/*
		 * 1.id>=0
		 * 2.Porder無訂單--->null
		 */
		Porder porder=null;
		if(id>0)
		{
			List<Porder> l=porderdaoimpl.selectById(id);
			if(l.size()>0)
			{
				porder=l.get(0);
			}
			
		}	
		
		
		
		return porder;
	}

	@Override
	public void updatePorder(String name, int id) {
		Porder p=findById(id);
		p.setName(name);
		
		porderdaoimpl.update(p);
		
	}

	@Override
	public void updatePorder(int GreenTea, int BlackTea, int OolongTea, int BuckwheatTea,int id) {
		Porder p=findById(id);
		p.setGreenTea(GreenTea);
		p.setBlackTea(BlackTea);
		p.setOolongTea(OolongTea);
		p.setBuckwheatTea(BuckwheatTea);
		
		porderdaoimpl.update(p);
		
	}

	@Override
	public void updatePorder(Porder porder) {
		Porder p=findById(porder.getId());
		//p.set
		
		//porderdaoimpl.update(porder);
	}

	@Override
	public void deletePorderById(int id) {
		if(id>0)
		{
			porderdaoimpl.delete(id);
		}
		
	}

	@Override
	public void updtaePorder(Porder porder) {
		// TODO Auto-generated method stub
		
	}

}