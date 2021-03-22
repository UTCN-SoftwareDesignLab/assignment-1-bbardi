package repository.activity;

import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import repository.user.UserRepository;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static database.Constants.Tables.ACTIVITY;
public class ActivityRepositoryMySQL implements ActivityRepository{

    private final Connection connection;
    private final UserRepository userRepository;

    public ActivityRepositoryMySQL(Connection connection, UserRepository userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(Activity activity) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `" + ACTIVITY + "` VALUES (null, ?,?,?)");
            statement.setLong(1,activity.getUser().getId());
            statement.setString(2,activity.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(activity.getDate()));
            statement.executeUpdate();
            return true;
        } catch (SQLException e){

        }
        return false;
    }

    @Override
    public Activity findByID(Long id) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + ACTIVITY + "` where id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return createActivityFromResultSet(resultSet);
        } catch (SQLException e){

        }
        return null;
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + ACTIVITY + "`");
            while(resultSet.next())
                activities.add(createActivityFromResultSet(resultSet));
        } catch (SQLException e){

        }
        return activities;
    }

    @Override
    public List<Activity> findByEmployeeAndPeriod(User user, LocalDateTime start, LocalDateTime end) {
        List<Activity> activities = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + ACTIVITY + "` WHERE user = ? AND eventTime >= ? AND eventTime <= ? ");
            statement.setLong(1,user.getId());
            statement.setTimestamp(2,Timestamp.valueOf(start));
            statement.setTimestamp(3,Timestamp.valueOf(end));
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
                activities.add(createActivityFromResultSet(resultSet));
        } catch (SQLException e){

        }
        return activities;
    }

    @Override
    public void removeAll() {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM `"+ACTIVITY +"` WHERE id >= 0");
        } catch (SQLException e){

        }
    }
    private Activity createActivityFromResultSet(ResultSet resultSet) throws SQLException{
        ActivityBuilder builder = new ActivityBuilder();
        return builder
                .setID(resultSet.getLong("id"))
                .setUser(userRepository.findByID(resultSet.getLong("user")))
                .setDescription(resultSet.getString("description"))
                .setDate(resultSet.getTimestamp("eventTime").toLocalDateTime())
                .build();
    }
}
