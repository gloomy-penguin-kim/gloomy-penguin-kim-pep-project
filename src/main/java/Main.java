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
        
        app
            .error(404, ctx -> ctx.result("not found"))
            .start(8080);
 
    }
   

   
}
