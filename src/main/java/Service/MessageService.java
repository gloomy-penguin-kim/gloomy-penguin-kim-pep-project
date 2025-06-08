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

    public Message createMessage(Message message) {
        return this.messsageDAO.createMessage(message);
    }
    public boolean deleteMessageByMessageId(int messageId) {
        return this.messsageDAO.deleteMessageByMessageId(messageId); 
    }
    public boolean deleteMessageByMessage(Message message) {
        return this.messsageDAO.deleteMessageByMessage(message); 
    }
    public List<Message> getAllMessagesPostedBy(int postedBy) { 
        return this.messsageDAO.getAllMessagesPostedBy(postedBy); 
    }
    public List<Message> getAllMessagesInDB() { 
        return this.messsageDAO.getAllMessagesInDB(); 
    }
    public List<Message> getMessageByMessageId(int message_id) { 
        return this.messsageDAO.getMessageByMessageId(message_id); 
    }
}
