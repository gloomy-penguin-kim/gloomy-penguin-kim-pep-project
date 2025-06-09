package Service;

import java.util.List; 

import Model.Message;
import DAO.MessageDAO; 

public class MessageService {
    private MessageDAO messsageDAO; 

    public MessageService(){
        messsageDAO = new MessageDAO();
    } 
    public MessageService(MessageDAO messsageDAO){
        this.messsageDAO = messsageDAO;
    }  
    
    // Create message by Message object 
    public Message createMessage(Message message) {
        // I would throw a custom Exception here for invalid message text
        // but I feel like that would be taking the project too far off course 
        // and the API tests say no error message with the 400 status code
        if (!isValidMessageText(message.getMessage_text())) {
            return null; 
        }
        return this.messsageDAO.createMessage(message);
    }

    // Delete message in database with message id 
    public Message deleteMessageByMessageId(int messageId) {
        return this.messsageDAO.deleteMessageByMessageById(messageId); 
    }

    // Delete message in database with Message object
    public Message deleteMessageByMessage(Message message) {
        return this.messsageDAO.deleteMessageByMessage(message); 
    }

    // Get all messages with the field "posted_by"
    public List<Message> getAllMessagesPostedBy(int postedBy) { 
        return this.messsageDAO.getAllMessagesPostedBy(postedBy); 
    }

    // Get all messages in the database 
    public List<Message> getAllMessagesInDB() { 
        return this.messsageDAO.getAllMessagesInDB(); 
    }

    // Get message from database by message id 
    public Message getMessageByMessageId(int message_id) { 
        return this.messsageDAO.getMessageByMessageId(message_id); 
    }

    // Update message text by message id
    public Message updateMessageTextByMessageId(int message_id, String message_text) {
        if (!isValidMessageText(message_text)) {
            return null; 
        }
        return this.messsageDAO.updateMessageTextByMessageId(message_id, message_text);
    } 

    // Update message text by Message object
    public Message updateMessageTextByMessage(Message message) {
        if (!isValidMessageText(message.getMessage_text())) {
            return null; 
        }
        return this.messsageDAO.updateMessageTextByMessage(message);
    } 


    // Business rules at Service Level as instructed and in one location 
    private boolean isValidMessageText(String text) {
        return text != null && !text.isEmpty() && text.length() <= 255; 
    } 
}
