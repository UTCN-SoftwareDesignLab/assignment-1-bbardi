package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AccountValidator {
    private static final String ACCOUNT_NUMBER_VALIDATION_REGEX = "^[0-9]{10}$";
    private final Account account;
    private final List<String> errors;

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }
    public boolean validate(){
        validateNumber(account.getAccountNumber());
        if(account.getAccountNumber() == null)
            errors.add("Invalid initial amount");
        if(account.getOwner() == null)
            errors.add("Invalid client");
        return errors.isEmpty();
    }
    private void validateNumber(String accountNumber){
        if(!Pattern.compile(ACCOUNT_NUMBER_VALIDATION_REGEX).matcher(accountNumber).matches()){
            errors.add("Invalid Personal Numeric Code");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
