package model.builder;

import model.Account;
import model.AccountType;
import model.Client;

import java.time.LocalDate;

public class AccountBuilder {
    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setID(Long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setOwner(Client owner){
        account.setOwner(owner);
        return this;
    }

    public AccountBuilder setAccountNumber(String accountNumber){
        account.setAccountNumber(accountNumber);
        return this;
    }

    public AccountBuilder setAccountType(AccountType type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmount(Float amount){
        account.setAmount(amount);
        return this;
    }

    public AccountBuilder setCreationDate(LocalDate creationDate){
        account.setCreationDate(creationDate);
        return this;
    }

    public Account build(){
        return account;
    }
}
