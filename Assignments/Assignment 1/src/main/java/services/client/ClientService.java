package services.client;

import model.Account;
import model.AccountType;
import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {

    Notification<Client> findByIDCard(String IDCard);

    Notification<Client> findByPersonalNumericCode(String personalNumericCode);

    Notification<Client> findByID(Long id);

    Notification<Boolean> save(String name, String personalNumericCode, String idCard, String address);

    Notification<Boolean> update(Long id, String name, String personalNumericCode, String idCard, String address);

    Notification<Boolean> delete(Long id);

    List<Account> findAccounts(Client client);

    Notification<Boolean> createAccount(Long id, String accountID, AccountType accountType, Float amount);
}
