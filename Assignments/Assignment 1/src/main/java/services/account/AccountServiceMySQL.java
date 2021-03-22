package services.account;

import model.Account;
import model.AccountType;
import model.Utility;
import model.validation.Notification;
import repository.account.AccountRepository;

import java.util.regex.Pattern;

public class AccountServiceMySQL implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> delete(String accountNumber) {
        Notification<Boolean> notification = new Notification<>();
        Account account = accountRepository.findByNumber(accountNumber);
        if(account == null){
            notification.setResult(Boolean.FALSE);
            notification.addError("Unable to find account");
        }
        else
            notification.setResult(accountRepository.removeAccount(account));
        return notification;
    }

    @Override
    public Notification<Account> deposit(String accountNumber, String amount) {
        Notification<Account> notification = new Notification<>();
        Account account = accountRepository.findByNumber(accountNumber);
        if(account == null){
            notification.addError("Unable to find account");
        }else{
            try{
                if(Float.parseFloat(amount) < 0)
                    throw new NumberFormatException();
                account.setAmount(account.getAmount() + Float.parseFloat(amount));
                if(accountRepository.update(account)){
                    notification.setResult(account);
                }else
                    notification.addError("Unable to save in Database");
            } catch (NumberFormatException e){
                notification.addError("Invalid amount");
            }
        }
        return notification;
    }

    @Override
    public Notification<Account> withdraw(String accountNumber, String amount) {
        Notification<Account> notification = new Notification<>();
        Account account = accountRepository.findByNumber(accountNumber);
        if(account == null){
            notification.addError("Unable to find account");
        }else{
            try{
                if(Float.parseFloat(amount) < 0)
                    throw new NumberFormatException();
                account.setAmount(account.getAmount() - Float.parseFloat(amount));
                if(account.getAmount() < 0){
                    notification.addError("Not enough balance");
                }else {
                    if (accountRepository.update(account)) {
                        notification.setResult(account);
                    }
                    else{
                        notification.addError("Unable to save in Database");
                    }
                }
            } catch (NumberFormatException e){
                notification.addError("Invalid amount");
            }
        }
        return notification;
    }

    @Override
    public Notification<Account> transfer(String accountNumber, String destinationNumber, String amount) {
        Notification<Account> notification = new Notification<>();
        Account sourceAccount = accountRepository.findByNumber(accountNumber);
        Account destinationAccount = accountRepository.findByNumber(destinationNumber);
        if(sourceAccount == null || destinationAccount == null){
            notification.setResult(null);
            notification.addError("Unable to find account");
        }else{
            try{
                if(Float.parseFloat(amount) < 0)
                    throw new NumberFormatException();
                sourceAccount.setAmount(sourceAccount.getAmount() - Float.parseFloat(amount));
                if(sourceAccount.getAmount() < 0){
                    notification.setResult(null);
                    notification.addError("Not enough balance");
                }else {
                    destinationAccount.setAmount(destinationAccount.getAmount() + Float.parseFloat(amount));
                    if(accountRepository.update(sourceAccount) && accountRepository.update(destinationAccount)){
                        notification.setResult(sourceAccount);
                    }
                    else
                        notification.addError("Unable to save in database");
                }
            } catch (NumberFormatException e){
                notification.setResult(null);
                notification.addError("Invalid amount");
            }
        }
        return notification;
    }

    @Override
    public Notification<Account> update(String accountNumber, AccountType newType) {
        Notification<Account> notification = new Notification<>();
        Account account = accountRepository.findByNumber(accountNumber);
        if(account == null){
            notification.addError("Unable to find account");
        }else{
            account.setType(newType);
            if(accountRepository.update(account)){
                notification.setResult(account);
            }
            else
                notification.addError("Unable to save in database");
        }
        return notification;
    }

    @Override
    public Notification<Account> payBill(String accountNumber, Utility utility, String amount, String identifier) {
        Notification<Account> notification = new Notification<>();
        Account account = accountRepository.findByNumber(accountNumber);
        if(account == null){
            notification.addError("Account not found");
        }else{
            try{
                if(Float.parseFloat(amount) < 0)
                    throw new NumberFormatException();
                account.setAmount(account.getAmount() - Float.parseFloat(amount));
                if(account.getAmount() < 0){
                    notification.addError("Not enough balance");
                }else {
                    if(!Pattern.compile(utility.getClientIdentifier_validation_regex()).matcher(identifier).matches()){
                        notification.addError("Invalid identifier");
                    }
                    else{
                        if (accountRepository.update(account)) {
                            notification.setResult(account);
                        }
                        else{
                            notification.addError("Unable to save in Database");
                        }
                    }
                }
            } catch (NumberFormatException e){
                notification.addError("Invalid amount");
            }
        }
        return notification;
    }
}
