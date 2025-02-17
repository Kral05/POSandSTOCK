package service;

import java.util.List;
import model.Member;

public interface MemberService {
    //create
    void addMember(Member member);
    
    //read
    Member login(String username, String password);
    boolean isUsernameBeenUse(String username);
    List<Member> getAllMembers();
    Member getMember(String username);
    Member getMemberById(int id);  
    
    //update
    void updateMember(Member member);
    
    //delete
    void deleteMember(String username);
    void deleteMemberById(int id);  
}