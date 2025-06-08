package DAO;

import Model.Account; 
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO { 

    public Account loginWithAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername()); 
            ps.setString(2, account.getPassword()); 
            ResultSet rs = ps.executeQuery(); 
            if (rs.next()) {
                return new Account(rs.getInt("account_id"),
                                    rs.getString("username"),
                                    rs.getString("password")); 
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    } 

    public Account loginWithUsernameAndPassword(String username, String password) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username); 
            ps.setString(2, password); 
            ResultSet rs = ps.executeQuery(); 
            if (rs.next()) {
                return new Account(rs.getInt("account_id"),
                                    rs.getString("username"),
                                    rs.getString("password")); 
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account accountRegister(Account account) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "insert into account (username, password) values (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername()); 
            ps.setString(2, account.getPassword()); 
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generated_account_id = (int) rs.getLong("account_id"); 
                return new Account(generated_account_id,
                                    account.getUsername(), 
                                    account.getPassword());
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean verifyUsernameDoesNOTExist(String username) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "select account_id from account where username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);  
            ResultSet rs = ps.executeQuery(); 
            if (rs.next()) {
                return true; 
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
