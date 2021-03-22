package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    User findByID(Long Id);

    User  findByUsername(String username);

    boolean update(User user);

    boolean removeByID(Long Id);

    void removeAll();
}
