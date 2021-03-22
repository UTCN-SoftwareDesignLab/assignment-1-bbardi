package services.client;

import model.Account;
import model.AccountType;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ClientServiceMySQL implements ClientService{
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    public ClientServiceMySQL(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Client> findByID(Long id) {
        Notification<Client> clientNotification = new Notification<>();
        clientNotification.setResult(clientRepository.findByID(id));
        return clientNotification;
    }

    @Override
    public Notification<Boolean> createAccount(Long id, String accountID, AccountType accountType, Float amount) {
        Notification<Boolean> notification = new Notification<>();

        Client client = clientRepository.findByID(id);
        Account newAccount = new AccountBuilder()
                                .setAccountNumber(accountID)
                                .setAccountType(accountType)
                                .setAmount(amount)
                                .setOwner(client)
                                .setCreationDate(LocalDate.now())
                                .build();
        AccountValidator validator = new AccountValidator(newAccount);
        if(!validator.validate()){
            validator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        }
        else
            notification.setResult(accountRepository.save(newAccount));
        return notification;
    }

    @Override
    public Notification<Client> findByIDCard(String IDCard) {
        List<Client> clientList = clientRepository.findAll();
        Notification<Client> clientNotification = new Notification<>();
        Client foundClient = null;
        for(Client client : clientList){
            if(IDCard.equals(client.getIdentityCard())){
                foundClient = client;
            }
        }
        if(foundClient == null)
            clientNotification.addError("Unable to find client using this ID Card");
        else
            clientNotification.setResult(foundClient);
        return clientNotification;
    }

    @Override
    public Notification<Client> findByPersonalNumericCode(String personalNumericCode) {
        List<Client> clientList = clientRepository.findAll();
        Notification<Client> clientNotification = new Notification<>();
        Client foundClient = null;
        for(Client client : clientList){
            if(personalNumericCode.equals(client.getPersonalNumericCode())){
                foundClient = client;
            }
        }
        if(foundClient == null)
            clientNotification.addError("Unable to find client using this Personal Numeric Code");
        else
            clientNotification.setResult(foundClient);
        return clientNotification;
    }

    @Override
    public Notification<Boolean> save(String name, String personalNumericCode, String idCard, String address) {
        Client newClient = new ClientBuilder()
                .setName(name)
                .setIdentityCard(idCard)
                .setPersonalNumericCode(personalNumericCode)
                .setAddress(address)
                .build();
        ClientValidator validator = new ClientValidator(newClient);
        boolean clientValid = validator.validate();
        Notification<Boolean> saveNotification = new Notification<>();
        if(!clientValid){
            validator.getErrors().forEach(saveNotification::addError);
            saveNotification.setResult(Boolean.FALSE);
        } else{
            saveNotification.setResult(clientRepository.save(newClient));
        }
        return saveNotification;
    }

    @Override
    public Notification<Boolean> update(Long id,String name, String personalNumericCode, String idCard, String address) {
        Client updatedClient = new ClientBuilder()
                .setId(id)
                .setName(name)
                .setPersonalNumericCode(personalNumericCode)
                .setIdentityCard(idCard)
                .setAddress(address)
                .build();
        ClientValidator validator = new ClientValidator(updatedClient);
        Notification<Boolean> notification = new Notification<>();
        if(!validator.validate()){
            validator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        }
        else{
            if(id < 0)
            {
                notification.addError("Client not selected");
                notification.setResult(Boolean.FALSE);
            }
            else
                notification.setResult(clientRepository.update(updatedClient));
        }
        return notification;
    }

    @Override
    public List<Account> findAccounts(Client client) {
        return accountRepository.findByClient(client);
    }

    @Override
    public Notification<Boolean> delete(Long id) {
        Notification<Boolean> booleanNotification = new Notification<>();
        if(id == -1L){
            booleanNotification.addError("Client not selected");
            booleanNotification.setResult(Boolean.FALSE);
        }
        else
            booleanNotification.setResult(clientRepository.removeByID(id));
        return booleanNotification;
    }
}
