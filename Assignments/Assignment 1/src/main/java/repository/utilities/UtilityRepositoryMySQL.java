package repository.utilities;

import model.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.UTILITIES;

public class UtilityRepositoryMySQL implements UtilityRepository{
    private final Connection connection;

    public UtilityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Utility utility) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `"+UTILITIES + "` VALUES (null, ?, ?)");
            statement.setString(1,utility.getUtilityName());
            statement.setString(2,utility.getClientIdentifier_validation_regex());
            statement.executeUpdate();
            return true;
        }catch (SQLException e){

        }
        return false;
    }

    @Override
    public List<Utility> findAll() {
        List<Utility> utilities = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT * FROM `"+UTILITIES+"`");
            while(resultSet.next()){
                Utility utility = new Utility();
                utility.setUtilityName(resultSet.getString("name"));
                utility.setClientIdentifier_validation_regex(resultSet.getString("validationRegex"));
                utilities.add(utility);
            }
        } catch (SQLException e){

        }
        return utilities;
    }

    @Override
    public void removeAll() {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM `"+UTILITIES+"` where id >= 0");
        } catch (SQLException e){

        }
    }
}
