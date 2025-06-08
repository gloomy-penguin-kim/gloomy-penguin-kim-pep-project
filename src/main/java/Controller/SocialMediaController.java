package Controller;

import java.util.List;
import java.util.Objects;

import Model.Message;
import Model.Account; 

import Service.MessageService;
import Service.AccountService; 

import io.javalin.Javalin;
import io.javalin.http.Context;

 
public class SocialMediaController {
  
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("messages", this::createMessage); 
        app.get("messages", this::getAllMessagesInDB);
        app.get("accounts/{posted_by}/messages", this::getAllMessagesPostedBy);
        app.get("messages/{message_id}", this::getMessageByMessageId);
        app.delete("messages/{message_id}", this::deleteMessageByMessageId);
        app.patch("messages/{message_id}", this::updateMessage);

        app.post("login", this::accountLogin);
        app.post("register", this::accoountRegister);
        return app;
    }

    private void accountLogin(Context context) { 
        Account account = context.bodyAsClass(Account.class);
        AccountService as = new AccountService(); 
        Account accountResponse = as.loginWithAccount(account); 
        if (accountResponse != null) {
            context.json(accountResponse).status(200); 
        }
        else {
            context.status(401); 
        }
    }

    private void accoountRegister(Context context) { 
        Account account = context.bodyAsClass(Account.class);

        if (account.username.length() == 0 || account.password.length() < 4) {
            context.status(400); 
        }
        else {
            AccountService as = new AccountService(); 
            Account accountResponse = as.accountRegister(account); 
            if (accountResponse != null) {
                context.json(accountResponse).status(200); 
            }
            else {
                context.status(400); 
            }
        }
    }
 

    private void createMessage(Context context) { 
        Message message = context.bodyAsClass(Message.class);
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            context.status(400).result(); 
        }
        else { 
            try {
                MessageService ms = new MessageService();  
                Message messageResult = ms.createMessage(message); 
                context.json(messageResult).status(200); 
            }
            catch (Exception e) {
                context.status(400).result(); 
            }  
        }
    }

    private void getAllMessagesInDB(Context context) { 
        MessageService ms = new MessageService(); 
        List<Message> allMessages = ms.getAllMessagesInDB(); 
        context.json(allMessages).status(200);
    }

    private void getAllMessagesPostedBy(Context context) { 
        int posted_by = Integer.parseInt(Objects.requireNonNull(context.pathParam("posted_by")));
        MessageService ms = new MessageService();  
        List<Message> messages = ms.getAllMessagesPostedBy(posted_by); 
        context.json(messages).status(200); 
    }

    private void deleteMessageByMessageId(Context context) { 
        int message_id = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        MessageService ms = new MessageService();  
        Message message = ms.getMessageByMessageId(message_id);
        if (message != null) {
            ms.deleteMessageByMessageId(message_id); 
            context.json(message).status(200); 
        }
        else context.status(200); 
    }

    private void getMessageByMessageId(Context context) { 
        int message_id = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        MessageService ms = new MessageService();  
        Message messages = ms.getMessageByMessageId(message_id); 
        if (messages != null) context.json(messages).status(200); 
        else context.status(200); 
    }

    private void updateMessage(Context context) { 
        int message_id = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message message = context.bodyAsClass(Message.class); 
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            context.status(400); 
        } 
        else {
            MessageService ms = new MessageService();  
            Message messageResponse = ms.updateMessageTextByMessageId(message_id, message.getMessage_text()); 
            if (messageResponse != null) context.json(messageResponse).status(200); 
            else context.status(400); 
        }
    }




}