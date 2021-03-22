package services.user;

import model.User;
import model.validation.Notification;

public interface UserManagementService {
    Notification<Boolean> register(String username, String password, String confirmPassword, String role);

    Notification<Boolean> remove(String username);

    Notification<Boolean> update(String username, String password, String confirmPassword, String role);

    Notification<User> findByUsername(String username);
}
