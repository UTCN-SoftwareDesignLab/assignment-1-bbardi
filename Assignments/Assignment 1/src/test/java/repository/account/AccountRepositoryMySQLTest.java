package repository.account;

import database.Bootstrapper;
import database.DBConnectionFactory;
import model.Account;
import model.AccountType;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AccountRepositoryMySQLTest {
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;
    private static Client testClient;
    @BeforeAll
    public static void setup() throws SQLException {
        Bootstrapper bootstrapper = new Bootstrapper();
        bootstrapper.execute();
        DBConnectionFactory dbConnectionFactory = new DBConnectionFactory();
        clientRepository = new ClientRepositoryMySQL(dbConnectionFactory.getConnectionWrapper(true).getConnection());
        accountRepository = new AccountRepositoryMySQL(dbConnectionFactory.getConnectionWrapper(true).getConnection(),clientRepository);
        accountRepository.removeAll();
        clientRepository.removeAll();
        ClientBuilder builder = new ClientBuilder();
        testClient = builder.setName("Tester")
                            .setAddress("Null Street")
                            .setIdentityCard("12345")
                            .setPersonalNumericCode("1234567")
                            .build();
        clientRepository.save(testClient);
        testClient = clientRepository.findAll().get(0);
    }

    @Test
    public void addType(){
        Assertions.assertTrue(accountRepository.addType("TestAccount"));
    }

    @Test
    public void findTypeByName(){
        accountRepository.addType("TestFindByName");
        Assertions.assertNotNull(accountRepository.findTypeByName("TestFindByName"));
    }

    @Test
    public void findTypeByID(){
        AccountType type = accountRepository.findTypeByName("TestAccount");
        Assertions.assertNotNull(accountRepository.findTypeByID(type.getId()));
    }

    @Test
    public void save(){
        AccountBuilder accountBuilder = new AccountBuilder();
        Account testAccount = accountBuilder.setAccountNumber("12345")
                                            .setAmount(1234.0f)
                                            .setOwner(testClient)
                                            .setAccountType(accountRepository.findTypeByName("TestAccount"))
                                            .setCreationDate(LocalDate.now())
                                            .build();
        Assertions.assertTrue(accountRepository.save(testAccount));
    }
    @Test
    public void findAll(){
        AccountBuilder accountBuilder = new AccountBuilder();
        Account testAccount = accountBuilder.setAccountNumber("12345")
                .setAmount(1234.0f)
                .setOwner(testClient)
                .setAccountType(accountRepository.findTypeByName("TestAccount"))
                .setCreationDate(LocalDate.now())
                .build();
        Assertions.assertTrue(accountRepository.save(testAccount));
        Assertions.assertFalse(accountRepository.findAll().isEmpty());
    }

    @Test
    public void removeAll(){
        accountRepository.removeAll();
        Assertions.assertTrue(accountRepository.findAll().isEmpty());
    }

    @Test
    public void findByID(){
        AccountBuilder accountBuilder = new AccountBuilder();
        Account testAccount = accountBuilder.setAccountNumber("12345")
                .setAmount(1234.0f)
                .setOwner(testClient)
                .setAccountType(accountRepository.findTypeByName("TestAccount"))
                .setCreationDate(LocalDate.now())
                .build();
        accountRepository.save(testAccount);
        testAccount = accountRepository.findAll().get(0);
        Assertions.assertNotNull(accountRepository.findByID(testAccount.getId()));
    }
    @Test
    public void findByClient(){
        Assertions.assertNotNull(accountRepository.findByClient(testClient));
    }
}
