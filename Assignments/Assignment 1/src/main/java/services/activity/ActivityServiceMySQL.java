package services.activity;

import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import repository.activity.ActivityRepository;
import repository.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ActivityServiceMySQL implements ActivityService{

    private User currentUser;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void setUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void recordActivity(String description) {
        Activity activity = new ActivityBuilder()
                                .setUser(currentUser)
                                .setDate(LocalDateTime.now())
                                .setDescription(description)
                                .build();
        activityRepository.save(activity);
    }

    @Override
    public Notification<List<Activity>> findActivityByUserAndPeriod(String user, String startDate, String endDate) {
        Notification<List<Activity>> notification = new Notification<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        User selectedUser = userRepository.findByUsername(user);
        if(selectedUser == null)
            notification.addError("Unable to find user");
        else{try{
            LocalDateTime start = LocalDateTime.parse(startDate,formatter);
            LocalDateTime stop = LocalDateTime.parse(endDate,formatter);
            notification.setResult(activityRepository.findByEmployeeAndPeriod(selectedUser,start,stop));
        } catch (DateTimeParseException e){
            notification.addError("Dates must be in \"dd/MM/yyyy HH:mm\" format");
        }
        }
        return notification;
    }
}