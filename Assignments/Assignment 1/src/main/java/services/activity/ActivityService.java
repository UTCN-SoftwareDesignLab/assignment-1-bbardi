package services.activity;

import model.Activity;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface ActivityService {

    void setUser(User user);

    void recordActivity(String description);

    Notification<List<Activity>> findActivityByUserAndPeriod(String user, String startDate, String endDate);
}
