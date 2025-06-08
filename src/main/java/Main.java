import Controller.SocialMediaController;
import DAO.MessageDAO;
import Model.Account;
import io.javalin.Javalin;

import Model.Account;
import Model.Message; 

import Service.AccountService;
import Service.MessageService; 

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Objects;
import java.util.Optional; 
/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {

    AccountService accountService; 
    MessageService messageService; 

    public void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
 

        app.error(404, this::NotFoundHandler);
        app.exception(Exception.class, this::genericExceptionHandler);
    }
  


    public static Handler getAllMessagesPostedBy = ctx -> {
        int posted_by = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("posted_by")));
        MessageService ms = new MessageService();  
        List<Message> messages = ms.getAllMessagesPostedBy(posted_by); 
        ctx.json(messages); 

        // // Return the user object in the response
        // if (user.isPresent()) {
        //     ctx.json( user.get() );
        // } else {
        //     ctx.html("Not Found");
        // }

        /*
        Alternative to if-else statement above:
        user.ifPresent( u -> ctx.json(u) )

        if (!user.isPresent()) {
            ctx.html("Not Found");
        }
        */
    };

  
    private void NotFoundHandler(Context ctx) {
        ctx.status(404); 
        ctx.result("Resource not found.");
    }

    private void genericExceptionHandler(Exception e, Context ctx) {
        ctx.result("Oops, something went wrong: " + e.getClass() + "\n" + e.getMessage());
    }
}
