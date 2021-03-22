package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import repository.security.RightsRolesRepository;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class ClientRepositoryMySQL implements  ClientRepository{

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM `" + CLIENT +"`";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                clients.add(getClientFromResultSet(resultSet));
            }

        } catch (SQLException throwables) {
        }
        return clients;
    }

    @Override
    public Client findByID(Long id) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + CLIENT + "` WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return getClientFromResultSet(resultSet);
            }
        } catch (SQLException e){

        }
        return null;
    }

    @Override
    public boolean save(Client client) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `" + CLIENT + "` VALUES (null, ?, ?, ?, ?)");
            statement.setString(1,client.getName());
            statement.setString(2,client.getIdentityCard());
            statement.setString(3,client.getPersonalNumericCode());
            statement.setString(4,client.getAddress());
            statement.executeUpdate();
            return true;
        } catch (SQLException e){

        }
        return false;
    }

    @Override
    public void removeAll() {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM `"+ CLIENT + "` WHERE id >= 0");
        } catch (SQLException e){

        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException{
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdentityCard(rs.getString("idcard"))
                .setPersonalNumericCode(rs.getString("cnp"))
                .setAddress(rs.getString("address"))
                .build();
    }

    @Override
    public boolean update(Client client) {
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE `" + CLIENT + "` SET name = ?, idcard = ?, cnp = ?, address = ? WHERE id = ?");
            statement.setString(1,client.getName());
            statement.setString(2,client.getIdentityCard());
            statement.setString(3,client.getPersonalNumericCode());
            statement.setString(4,client.getAddress());
            statement.setLong(5,client.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e){

        }
        return false;
    }

    @Override
    public boolean removeByID(Long id) {
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM `" + CLIENT + "` WHERE id = ?");
            statement.setLong(1,id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e){

        }
        return false;
    }
}
