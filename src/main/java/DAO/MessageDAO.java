package DAO;

import Model.Message;

import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List; 

public class MessageDAO { 

    // Insert/create a new message into the database
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

    // Delete message in database by message id 
    public Message deleteMessageByMessageById(int message_id) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql1 = "select * from message where message_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, message_id);
            ResultSet rs = ps1.executeQuery(); 

            if (rs.next()) {
                String sql2 = "delete from message where message_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, message_id);  
                ps2.executeUpdate(); 

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

    // Delete message in database by Message object
    public Message deleteMessageByMessage(Message message) {
        return this.deleteMessageByMessageById(message.getMessage_id());
    }  

    // Get all messages by post_by  
    public List<Message> getAllMessagesPostedBy(int postedBy) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>(); 
        try { 
            String sql = "select * from message where posted_by = ?";
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

    // Get all messages in the database
    public List<Message> getAllMessagesInDB() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>(); 
        try { 
            String sql = "select * from message order by posted_by";
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
    // Update message in the database text by message id
    public Message updateMessageTextByMessageId(int message_id, String message_text) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql); 
            ps.setString(1, message_text);  
            ps.setInt(2, message_id);
            ps.executeUpdate(); 
            
            sql = "select * from message where message_id = ?";
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
 
    // Update message in the database text by Message object 
    public Message updateMessageTextByMessage(Message message) {
        return this.updateMessageTextByMessageId(message.getMessage_id(), message.getMessage_text());
    }

    // Get a message by message_id
    public Message getMessageByMessageId(int message_id) {
        Connection conn = ConnectionUtil.getConnection(); 
        try { 
            String sql = "select * from message where message_id = ?";
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

 