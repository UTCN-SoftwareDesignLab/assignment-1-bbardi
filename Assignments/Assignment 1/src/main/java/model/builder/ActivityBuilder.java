package model.builder;

import model.Activity;
import model.User;

import java.time.LocalDateTime;

public class ActivityBuilder {
    private Activity activity;

    public ActivityBuilder(){
        activity = new Activity();
    }

    public ActivityBuilder setID(Long id){
        activity.setId(id);
        return this;
    }
    public ActivityBuilder setUser(User user){
        activity.setUser(user);
        return this;
    }
    public ActivityBuilder setDescription(String description){
        activity.setDescription(description);
        return this;
    }
    public ActivityBuilder setDate(LocalDateTime date){
        activity.setDate(date);
        return this;
    }
    public Activity build(){
        return activity;
    }
}