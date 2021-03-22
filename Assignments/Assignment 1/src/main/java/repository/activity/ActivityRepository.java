package repository.activity;

import model.Activity;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository {


    boolean save(Activity activity);

    Activity findByID(Long id);

    List<Activity> findAll();

    List<Activity> findByEmployeeAndPeriod(User user, LocalDateTime start, LocalDateTime end);

    void removeAll();
}
