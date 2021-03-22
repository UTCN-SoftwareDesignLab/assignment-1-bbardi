package model.builder;

import model.Role;
import model.User;

import java.util.List;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setUsername(String userName){
        user.setUsername(userName);
        return this;
    }

    public UserBuilder setPassword(String password){
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRoles(List<Role> roles){
        user.setRoles(roles);
        return this;
    }

    public UserBuilder setId(Long id){
        user.setId(id);
        return this;
    }

    public User build(){
        return user;
    }
}
