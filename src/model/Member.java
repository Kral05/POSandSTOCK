package model;
import java.io.Serializable;

public class Member implements Serializable {
    private int id;
    private String name;
    private String username;
    private String password;
    private String address;
    private String mobile;
    
    private static final long serialVersionUID = 1L;

    // 構造函數
    public Member(String name, String username, String password, String address, String mobile) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.mobile = mobile;
    }
    
    // 添加包含 id 的構造函數
    public Member(int id, String name, String username, String password, String address, String mobile) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.mobile = mobile;
    }
    
    public Member() {
    }
    
    
    
    // Getter 和 Setter
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
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}