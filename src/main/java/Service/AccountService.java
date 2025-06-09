package Service;

import Model.Account;
import DAO.AccountDAO;  


public class AccountService {
    private AccountDAO accountDAO;  

    public AccountService(){
        accountDAO = new AccountDAO();
    } 
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }  
    
    // Log in with String username and String Password
    public Account loginWithUsernameAndPassword(String username, String password) {
        return this.accountDAO.loginWithUsernameAndPassword(username, password);
    }

    // Log in to account with Account object
    public Account loginWithAccount(Account account) {
        return this.accountDAO.loginWithAccount(account);
    }

    // Register account with Account Object
    public Account accountRegister(Account account) { 
        // I originally had this logic in the Controller but then I decided to ask during 
        // office hours where the business logic goes and he said definitely at Service level 
        if (!isValidUserName(account.getUsername()) || !isValidPassword(account.getPassword())) {
            return null; 
        }
        return this.accountDAO.accountRegister(account); 
    }

    // Verify username does not exist by String username
    public boolean verifyUsernameDoesNOTExist(String username) {
        return this.accountDAO.verifyUsernameDoesNOTExist(username); 
    }  
 
    private boolean isValidUserName(String username) {
        return username.length() > 0; 
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 4; 
    } 
}
