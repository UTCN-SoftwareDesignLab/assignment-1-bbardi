package repository.client;

import model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    Client findByID(Long id);

    boolean save(Client client);

    boolean update(Client client);

    boolean removeByID(Long id);

    void removeAll();
}
