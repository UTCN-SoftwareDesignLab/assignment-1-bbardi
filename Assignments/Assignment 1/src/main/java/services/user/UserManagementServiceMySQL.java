package services.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

public class UserManagementServiceMySQL implements UserManagementService{
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserManagementServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password, String confirmPassword, String role) {
        Notification<Boolean> notification = new Notification<>();
        if(!password.equals(confirmPassword)){
            notification.addError("Passwords must match");
            return notification;
        }
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(role)))
                .build();
        UserValidator validator = new UserValidator(user);
        if(!validator.validate()){
            validator.getErrors().forEach(notification::addError);
        }
        else{
            user.setPassword(encodePassword(user.getPassword()));
            notification.setResult(userRepository.save(user));
        }
        return notification;
    }

    @Override
    public Notification<Boolean> remove(String username) {
        Notification<Boolean> notification = new Notification<>();
        User user = userRepository.findByUsername(username);
        if(user == null){
            notification.addError("Unable to find user");
        }
        else{
            notification.setResult(userRepository.removeByID(user.getId()));
        }
        return notification;
    }

    @Override
    public Notification<Boolean> update(String username, String password, String confirmPassword, String role) {
        Notification<Boolean> notification = new Notification<>();
        User user = userRepository.findByUsername(username);
        if(user == null){
            notification.addError("Unable to find user");
        }
        else{
            if(!password.equals(confirmPassword)){
                notification.addError("Passwords do not match");
            }
            else
            {
                if(!password.equals(""))
                    user.setPassword(password);
                user.setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(role)));
                UserValidator validator = new UserValidator(user);
                if(!validator.validate()){
                    validator.getErrors().forEach(notification::addError);
                }else{
                    user.setPassword(encodePassword(password));
                    notification.setResult(userRepository.update(user));
                }
            }
        }
        return notification;
    }

    @Override
    public Notification<User> findByUsername(String username) {
        Notification<User> notification = new Notification<>();
        User user = userRepository.findByUsername(username);
        if(user == null)
            notification.addError("Unable to find user");
        notification.setResult(userRepository.findByUsername(username));
        return notification;
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
