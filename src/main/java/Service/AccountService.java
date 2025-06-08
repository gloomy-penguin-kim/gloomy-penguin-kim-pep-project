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
    public Account loginWithUsernameAndPassword(String username, String password) {
        return this.accountDAO.loginWithUsernameAndPassword(username, password);
    }
    public Account loginWithAccount(Account account) {
        return this.accountDAO.loginWithAccount(account);
    }
    public Account createAccount(Account account) { 
        return this.accountDAO.createAccount(account); 
    }
    public boolean verifyUsernameDoesNOTExist(String username) {
        return this.accountDAO.verifyUsernameDoesNOTExist(username); 
    }  
}
