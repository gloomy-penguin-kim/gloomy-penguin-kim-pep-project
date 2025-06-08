package Controller;

import java.util.List;
import java.util.Objects;

import Model.Message;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("messages", this::getAllMessagesInDB);

        app.get("accounts/{posted_by}/messages", this::getAllMessagesPostedBy);
  
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
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
    }



}