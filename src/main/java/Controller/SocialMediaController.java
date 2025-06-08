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

    private final AccountService accountService = new AccountService();
    private final MessageService messageService = new MessageService();
  
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        // Account routes
        app.post("login", this::accountLogin);
        app.post("register", this::accoountRegister); 

        // Message routes
        app.post("messages", this::createMessage); 
        app.get("messages", this::getAllMessagesInDB);
        app.get("accounts/{posted_by}/messages", this::getAllMessagesPostedBy);
        app.get("messages/{message_id}", this::getMessageByMessageId);
        app.delete("messages/{message_id}", this::deleteMessageByMessageId);
        app.patch("messages/{message_id}", this::updateMessage);

        return app;
    }

    // Account login   
    private void accountLogin(Context ctx) { 
        Account account = ctx.bodyAsClass(Account.class); 
        Account loggedIn = accountService.loginWithAccount(account); 
        if (loggedIn != null) {
            ctx.status(200).json(loggedIn); 
        }
        else {
            ctx.status(401); 
        }
    }

    // TODO: would it be helpful to add something like logging to the project? 

    // Account register  
    private void accoountRegister(Context ctx) { 
        Account account = ctx.bodyAsClass(Account.class); 
        if (account.username.length() == 0 || account.password.length() < 4) {
            ctx.status(400); 
            return; 
        }  
        Account created = accountService.accountRegister(account); 
        if (created != null) {
            ctx.status(200).json(created); 
        }
        else {
            ctx.status(400); 
        } 
    }
  
    // Create Message   
    private void createMessage(Context ctx) { 
        Message message = ctx.bodyAsClass(Message.class); 
        if (!isValidMessageText(message.getMessage_text())) {
            ctx.status(400); 
            return;
        } 
        try {  
            Message created = messageService.createMessage(message); 
            if (created != null) {
                ctx.status(200).json(created); 
            }
            else {
                ctx.status(400); 
            }
        }
        catch (Exception e) {
            ctx.status(400); 
            return;
        }   
    }

    // List all the messages in the database 
    private void getAllMessagesInDB(Context ctx) {  
        List<Message> messages = messageService.getAllMessagesInDB(); 
        ctx.status(200).json(messages);
    }

    // List all the messages by post_by  
    private void getAllMessagesPostedBy(Context ctx) { 
        int posted_by = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("posted_by"))); 
        List<Message> messages = messageService.getAllMessagesPostedBy(posted_by); 
        ctx.status(200).json(messages); 
    }
 
    // Delete messages by message_id 
    private void deleteMessageByMessageId(Context ctx) { 
        int message_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id"))); 
        Message deleted = messageService.deleteMessageByMessageId(message_id); 
        if (deleted != null) {
            ctx.status(200).json(deleted); 
        }
        else {
            ctx.status(200); 
        }
    }

    // Get message by message_id  
    private void getMessageByMessageId(Context ctx) { 
        int message_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id"))); 
        Message message = messageService.getMessageByMessageId(message_id); 
        if (message != null) {
            ctx.status(200).json(message); 
        }
        else {
            ctx.status(200); 
        }
    }

    // Update message text with message_id 
    private void updateMessage(Context ctx) { 
        int message_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message message = ctx.bodyAsClass(Message.class); 
        if (!isValidMessageText(message.getMessage_text())) {
            ctx.status(400); 
            return;
        }   
        Message updated = messageService.updateMessageTextByMessageId(message_id, message.getMessage_text()); 
        if (updated != null) {
            ctx.json(updated).status(200); 
        }
        else {
            ctx.status(400); 
        }
    }

    // Utility function for valid message text
    private boolean isValidMessageText(String text) { 
        return text != null && !text.isEmpty() && text.length() <= 255; 
    }

}