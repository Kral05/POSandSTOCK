package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MemberDao;
import model.Member;
import util.DbConnection;

public class MemberDaoImpl implements MemberDao{

	public static void main(String[] args) {
		Member member = new Member("111","1111","Aa111111","中正路100號","0909090909");
		new MemberDaoImpl().add(member);
	}
	
	private static Connection conn=DbConnection.getDb();

	@Override
	public void add(Member member) {
		String SQ="insert into member(name,username,password,address,mobile) "
				+ "values(?,?,?,?,?)";
		try {
			PreparedStatement preparedstatement=conn.prepareStatement(SQ);
			preparedstatement.setString(1, member.getName());
			preparedstatement.setString(2, member.getUsername());
			preparedstatement.setString(3, member.getPassword());
			preparedstatement.setString(4, member.getAddress());
			preparedstatement.setString(5, member.getMobile());
			
			preparedstatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Member> selectAll() {
		String sql="select * from member";
		List<Member> l=new ArrayList();
		
		try {
			PreparedStatement preparedstatement=conn.prepareStatement(sql);
			ResultSet resultset=preparedstatement.executeQuery();
			
			
			
			while(resultset.next())
			{
				Member m=new Member();
				m.setId(resultset.getInt("id"));
				m.setName(resultset.getString("name"));
				m.setUsername(resultset.getString("username"));
				m.setPassword(resultset.getString("password"));
				m.setAddress(resultset.getString("address"));
				m.setMobile(resultset.getString("mobile"));
				
				l.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return l;
	}

	@Override
	public List<Member> selectUsernameAndPassword(String username, String password) {
		String sql="select * from member where username=? and password=?";
		List<Member> l=new ArrayList();
		try {
			PreparedStatement preparedstatement=conn.prepareStatement(sql);
			preparedstatement.setString(1, username);
			preparedstatement.setString(2, password);
			
			ResultSet resultset=preparedstatement.executeQuery();
			
			if(resultset.next())
			{
				Member m=new Member();
				m.setId(resultset.getInt("id"));
				m.setName(resultset.getString("name"));
				m.setUsername(resultset.getString("username"));
				m.setPassword(resultset.getString("password"));
				m.setAddress(resultset.getString("address"));
				m.setMobile(resultset.getString("mobile"));
				
				l.add(m);
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return l;
	}

	@Override
	public List<Member> selectById(int id) {
		String sql="select * from member where id=?";
		List<Member> l=new ArrayList();
		try {
			PreparedStatement preparedstatement=conn.prepareStatement(sql);
			preparedstatement.setInt(1, id);			
			
			ResultSet resultset=preparedstatement.executeQuery();
			
			if(resultset.next())
			{
				Member m=new Member();
				m.setId(resultset.getInt("id"));
				m.setName(resultset.getString("name"));
				m.setUsername(resultset.getString("username"));
				m.setPassword(resultset.getString("password"));
				m.setAddress(resultset.getString("address"));
				m.setMobile(resultset.getString("mobile"));
				
				l.add(m);
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return l;
	}

	@Override
	public List<Member> selectByUsername(String username) {
		String sql="select * from member where username=?";
		List<Member> l=new ArrayList();
		try {
			PreparedStatement preparedstatement=conn.prepareStatement(sql);
			preparedstatement.setString(1, username);			
			
			ResultSet resultset=preparedstatement.executeQuery();
			
			if(resultset.next())
			{
				Member m=new Member();
				m.setId(resultset.getInt("id"));
				m.setName(resultset.getString("name"));
				m.setUsername(resultset.getString("username"));
				m.setPassword(resultset.getString("password"));
				m.setAddress(resultset.getString("address"));
				m.setMobile(resultset.getString("mobile"));
				
				l.add(m);
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return l;
	}

	@Override
	public void update(Member member) {
		String sql="update member set name=?,password=?,address=?,mobile=? where id=?";
		
		try {
			PreparedStatement preparedstatement=conn.prepareStatement(sql);
			preparedstatement.setString(1,member.getName());
			preparedstatement.setString(2,member.getPassword());
			preparedstatement.setString(3,member.getAddress());
			preparedstatement.setString(4,member.getMobile());
			preparedstatement.setInt(5,member.getId());
			
			preparedstatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
    public void delete(int id)  {
        String Sql="delete from member where id=?";
        try {
            PreparedStatement preparedstatement=conn.prepareStatement(Sql);
            preparedstatement.setInt(1, id);
            preparedstatement.executeUpdate();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}