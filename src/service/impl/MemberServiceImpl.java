package service.impl;

import java.util.List;
import dao.impl.MemberDaoImpl;
import model.Member;
import service.MemberService;

public class MemberServiceImpl implements MemberService {
    
    private MemberDaoImpl memberdaoimpl = new MemberDaoImpl();
    
    //create
    @Override
    public void addMember(Member member) {
        memberdaoimpl.add(member);
    }
    
    //read
    @Override
    public Member login(String username, String password) {
        Member member = null;
        List<Member> l = memberdaoimpl.selectUsernameAndPassword(username, password);
        if (l.size() != 0) {
            member = l.get(0);
        }
        return member;
    }
    
    @Override
    public boolean isUsernameBeenUse(String username) {
        return !memberdaoimpl.selectByUsername(username).isEmpty();
    }
    
    @Override
    public List<Member> getAllMembers() {
        return memberdaoimpl.selectAll();
    }
    
    @Override
    public Member getMember(String username) {
        List<Member> members = memberdaoimpl.selectByUsername(username);
        return members.isEmpty() ? null : members.get(0);
    }
    
    @Override
    public Member getMemberById(int id) {
        List<Member> members = memberdaoimpl.selectById(id);
        return members.isEmpty() ? null : members.get(0);
    }
    
    //update
    @Override
    public void updateMember(Member member) {
        memberdaoimpl.update(member);
    }
    
    //delete
    @Override
    public void deleteMember(String username) {
        memberdaoimpl.delete(username);
    }
    @Override
    public void deleteMemberById(int id) {
        memberdaoimpl.delete(id); 
    }
}