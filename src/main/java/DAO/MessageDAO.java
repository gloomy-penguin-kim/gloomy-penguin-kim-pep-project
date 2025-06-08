package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDAO { 

    public Message createMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by()); 
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys(); 

            if (rs.next()) {
                int generated_message_id = (int) rs.getLong(1); 
                return new Message(generated_message_id,
                                    message.getPosted_by(), 
                                    message.getMessage_text(),
                                    message.getTime_posted_epoch());
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // TODO: I dont know for the delete if it should take a messageId or message. I am also 
    //          unsure about the return type 
    public boolean deleteMessageByMessageId(int messageId) {
        Connection conn = ConnectionUtil.getConnection();  
        try { 
            String sql = "delete message where message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);  
            ps.executeUpdate();
            return true; 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }  
    public boolean deleteMessageByMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "delete message where message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message.getMessage_id());  
            ps.executeUpdate();
            return true; 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }  

    public List<Message> getAllMessagesPostedBy(int postedBy) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>(); 
        try { 
            String sql = "select message_id, posted_by, message_text, time_posted_epoch from message where posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postedBy); 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), 
                                              rs.getInt("posted_by"), 
                                              rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
                messages.add(message); 
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public List<Message> getAllMessagesInDB() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>(); 
        try { 
            String sql = "select message_id, posted_by, message_text, time_posted_epoch from message order by posted_by";
            PreparedStatement ps = conn.prepareStatement(sql); 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), 
                                              rs.getInt("posted_by"), 
                                              rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
                messages.add(message); 
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // TODO: should I be passing in a Message class object or is passing in the int message_id
    //       and String message_text okay?  
    public Message updateMessageTextByMessageId(int message_id, String message_text) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql); 
            ps.setString(1, message_text);  
            ps.setInt(2, message_id);
            ps.executeUpdate(); 
            sql = "select message_id, posted_by, message_text, time_posted_epoch  from message where message_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, message_id); 
            ResultSet rs = ps.executeQuery(); 
            if (rs.next()) {
                return new Message(rs.getInt("message_id"), 
                                    rs.getInt("posted_by"), 
                                    rs.getString("message_text"),
                                    rs.getLong("time_posted_epoch")); 
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessageByMessageId(int message_id) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "select message_id, posted_by, message_text, time_posted_epoch from message where message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message_id); 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Message(rs.getInt("message_id"), 
                                    rs.getInt("posted_by"), 
                                    rs.getString("message_text"),
                                    rs.getLong("time_posted_epoch")); 
            } 
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

 