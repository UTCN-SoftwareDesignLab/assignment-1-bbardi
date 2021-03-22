package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.util.List;

public class ClientRepositoryMySQLTest {
    private static ClientRepository clientRepository;

    @BeforeAll
    public static void setup(){
        DBConnectionFactory dbConnectionFactory = new DBConnectionFactory();
        clientRepository = new ClientRepositoryMySQL(dbConnectionFactory.getConnectionWrapper(true).getConnection());
        clientRepository.removeAll();
    }

    @Test
    public void save(){
        ClientBuilder builder = new ClientBuilder();
        builder = builder.setName("Joe Generic").setAddress("Infinite Loop 1,CA").setIdentityCard("KT696969").setPersonalNumericCode("1234567890112");
        Assertions.assertTrue(clientRepository.save(builder.build()));
    }
    @Test
    public void findAll(){
        List<Client> clients;
        ClientBuilder builder = new ClientBuilder();
        clients = clientRepository.findAll();
        Assertions.assertTrue(clients.isEmpty());//check if its empty
        builder = builder.setName("Joe Generic").setAddress("Infinite Loop 1,CA").setIdentityCard("KT696969").setPersonalNumericCode("1234567890112");
        clientRepository.save(builder.build());
        clients = clientRepository.findAll();
        Assertions.assertFalse(clients.isEmpty());//check if it has an element
    }

    @Test
    public void removeAll(){
        clientRepository.removeAll();
        Assertions.assertTrue(clientRepository.findAll().isEmpty());
    }

    @Test
    public void findByID(){
        List<Client> clients;
        clientRepository.removeAll();
        ClientBuilder builder = new ClientBuilder();
        builder = builder.setName("Joe Generic").setAddress("Infinite Loop 1,CA").setIdentityCard("KT696969").setPersonalNumericCode("1234567890112");
        clientRepository.save(builder.build());
        Client client = clientRepository.findAll().get(0);
        Assertions.assertEquals(client.getPersonalNumericCode(),clientRepository.findByID(client.getId()).getPersonalNumericCode());
    }
}
