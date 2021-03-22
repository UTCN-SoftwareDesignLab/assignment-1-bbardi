package repository.account;

import model.Account;
import model.AccountType;
import model.Client;

import java.util.List;

public interface AccountRepository {

    boolean addType(String type);

    AccountType findTypeByID(Long id);

    AccountType findTypeByName(String name);

    List<AccountType> findAllTypes();

    List<Account> findAll();

    Account findByID(Long id);

    Account findByNumber(String accountNumber);

    List<Account> findByClient(Client client);

    boolean save(Account account);

    boolean removeAccount(Account account);

    boolean update(Account account);

    void removeAll();
}
