package service.impl;

import java.util.List;

import dao.impl.PorderDaoImpl;
import model.Porder;
import service.PorderService;

public class PorderServiceImpl implements PorderService {

    public static void main(String[] args) {
        System.out.println(new PorderServiceImpl().findById(6));
    }

    private static PorderDaoImpl porderdaoimpl = new PorderDaoImpl();

    @Override
    public void addPorder(Porder porder) {
        if (porder.getGreenTea() >= 0 && porder.getBlackTea() >= 0 && porder.getOolongTea() >= 0 && porder.getBuckwheatTea() >= 0) {
            porderdaoimpl.add(porder);
        }
    }

    @Override
    public String AllPorder() {
        List<Porder> l = porderdaoimpl.selectAll();
        String show = "";

        for (Porder p : l) {
            int sum = p.getGreenTea() * 55 + p.getBlackTea() * 45 + p.getOolongTea() * 50 + p.getBuckwheatTea() * 45;

            show = show + "訂單編號:" + p.getId() +
                    "\t客戶名稱:" + p.getName() +
                    "\tGreenTea:" + p.getGreenTea() +
                    "\tBlackTea:" + p.getBlackTea() +
                    "\tOolongTea:" + p.getOolongTea() +
                    "\tBuckwheatTea:" + p.getBuckwheatTea() +
                    "\t金額:" + sum + "元\n";
        }

        return show;
    }

    /**
     * 新增方法，將所有訂單轉換為二維陣列，以便用於 JTable。
     */
    public String[][] AllPorderAsArray() {
        List<Porder> l = porderdaoimpl.selectAll();

        // 初始化二維陣列，行數為訂單數量，列數為 6（訂單編號、茶品數量、總金額）
        String[][] orderData = new String[l.size()][6];

        for (int i = 0; i < l.size(); i++) {
            Porder p = l.get(i);

            // 計算總金額
            int sum = p.getGreenTea() * 55 + p.getBlackTea() * 45 + p.getOolongTea() * 50 + p.getBuckwheatTea() * 45;

            // 填充每一行資料
            orderData[i][0] = String.valueOf(p.getId());                  // 訂單編號
            orderData[i][1] = String.valueOf(p.getGreenTea());            // 綠茶數量
            orderData[i][2] = String.valueOf(p.getBlackTea());            // 紅茶數量
            orderData[i][3] = String.valueOf(p.getOolongTea());           // 烏龍茶數量
            orderData[i][4] = String.valueOf(p.getBuckwheatTea());        // 玄麥茶數量
            orderData[i][5] = String.valueOf(sum);                        // 總金額
        }

        return orderData;
    }

    @Override
    public List<Porder> findAllPorder() {
        return porderdaoimpl.selectAll();
    }

    @Override
    public Porder findById(int id) {
        /*
         * 1. id >= 0
         * 2. Porder 無訂單 ---> null
         */
        Porder porder = null;
        if (id > 0) {
            List<Porder> l = porderdaoimpl.selectById(id);
            if (l.size() > 0) {
                porder = l.get(0);
            }
        }

        return porder;
    }

    @Override
    public void updatePorder(String name, int id) {
        Porder p = findById(id);
        p.setName(name);

        porderdaoimpl.update(p);
    }

    @Override
    public void updatePorder(int GreenTea, int BlackTea, int OolongTea, int BuckwheatTea, int id) {
        Porder p = findById(id);
        p.setGreenTea(GreenTea);
        p.setBlackTea(BlackTea);
        p.setOolongTea(OolongTea);
        p.setBuckwheatTea(BuckwheatTea);

        porderdaoimpl.update(p);
    }

    @Override
    public void updatePorder(Porder porder) {
        Porder p = findById(porder.getId());
        // p.set

        // porderdaoimpl.update(porder);
    }

    @Override
    public void deletePorderById(int id) {
        if (id > 0) {
            porderdaoimpl.delete(id);
        }
    }

    @Override
    public void updtaePorder(Porder porder) {
        // TODO Auto-generated method stub
    }
}
