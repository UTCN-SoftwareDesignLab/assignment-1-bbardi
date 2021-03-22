package services.account;

import model.Account;
import model.AccountType;
import model.Utility;
import model.validation.Notification;

public interface AccountService {

    Notification<Boolean> delete(String accountNumber);

    Notification<Account> deposit(String accountNumber, String amount);

    Notification<Account> withdraw(String accountNumber, String amount);

    Notification<Account> transfer(String accountNumber, String destinationNumber, String amount);

    Notification<Account> update(String accountNumber, AccountType newType);

    Notification<Account> payBill(String accountNumber, Utility utility, String amount, String identifier);

}
