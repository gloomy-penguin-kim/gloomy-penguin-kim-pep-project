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

    // TODO: should I also have logic in here for the business rules that a  message text
    //          cannot be blank and cnannot be longer than 255 characters?
    
    // Create message by Message object 
    public Message createMessage(Message message) {
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
        return this.messsageDAO.updateMessageTextByMessageId(message_id, message_text);
    } 

    // Update message text by Message object
    public Message updateMessageTextByMessage(Message message) {
        return this.messsageDAO.updateMessageTextByMessage(message);
    } 
}
