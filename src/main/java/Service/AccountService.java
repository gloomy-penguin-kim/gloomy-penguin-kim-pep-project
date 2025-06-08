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

    // TODO:  is this data usually in a class object by now?  or is it okay they are 
    //          still floating around as username and password strings? 
    
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
        return this.accountDAO.accountRegister(account); 
    }

    // Verify username does not exist by String username
    public boolean verifyUsernameDoesNOTExist(String username) {
        return this.accountDAO.verifyUsernameDoesNOTExist(username); 
    }  
}
