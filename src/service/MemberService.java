package service;

import model.Member;

public interface MemberService {
		//create
		void addMember(Member member);
		
		//read
		Member Login(String username,String password);
		boolean isUsernameBeenUse(String username);
		
		//update
		
		
		//delete
		

}
