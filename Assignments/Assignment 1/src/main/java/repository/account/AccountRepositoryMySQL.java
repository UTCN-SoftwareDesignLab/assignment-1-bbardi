package repository.account;

import model.Account;
import model.AccountType;
import model.Client;
import model.builder.AccountBuilder;
import repository.client.ClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.ACCOUNT_TYPE;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;
    private final ClientRepository clientRepository;

    public AccountRepositoryMySQL(Connection connection, ClientRepository clientRepository) {
        this.connection = connection;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<AccountType> findAllTypes() {
        List<AccountType> accountTypes = new ArrayList<>();
        try{
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT * FROM `"+ ACCOUNT_TYPE + "`");
           while (resultSet.next()){
               accountTypes.add(getAccountTypeFromResultSet(resultSet));
           }
        } catch (SQLException e){
        }
       return accountTypes;
    }

    @Override
    public boolean addType(String type) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `" + ACCOUNT_TYPE + "` VALUES (null, ?)");
            statement.setString(1,type);
            statement.executeUpdate();
            return true;
        }catch (SQLException e){

        }
        return false;
    }

    @Override
    public AccountType findTypeByID(Long id) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + ACCOUNT_TYPE + "` WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return new AccountType(resultSet.getLong("id"),resultSet.getString("type"));
            }
        }catch (SQLException e){

        }
        return null;
    }

    @Override
    public AccountType findTypeByName(String name) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + ACCOUNT_TYPE + "` WHERE type = ?");
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return new AccountType(resultSet.getLong("id"),resultSet.getString("type"));
            }
        }catch (SQLException e){

        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + ACCOUNT +"`");
            while(resultSet.next()){
                accounts.add(getAccountFromResultSet(resultSet));
            }
        } catch (SQLException e){

        }
        return accounts;
    }

    @Override
    public Account findByID(Long id) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `"+ ACCOUNT + "` where id = ?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return getAccountFromResultSet(resultSet);
            }
        } catch (SQLException e){

        }
        return null;
    }

    @Override
    public List<Account> findByClient(Client client) {
        List<Account> accounts = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `"+ ACCOUNT + "` where clientid = ?");
            preparedStatement.setLong(1,client.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                accounts.add(getAccountFromResultSet(resultSet));
            }
        } catch (SQLException e){

        }
        return accounts;
    }

    @Override
    public boolean save(Account account) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `" + ACCOUNT + "` VALUES (null, ?, ?, ?, ?,?)");
            statement.setLong(1,account.getOwner().getId());
            statement.setString(2, account.getAccountNumber());
            statement.setFloat(3,account.getAmount());
            statement.setDate(4,Date.valueOf(account.getCreationDate()));
            statement.setLong(5,account.getType().getId());
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
            statement.executeUpdate("DELETE FROM `"+ ACCOUNT + "` WHERE id >= 0");
        } catch (SQLException e){

        }
    }

    private Account getAccountFromResultSet(ResultSet resultSet) throws SQLException{
        AccountBuilder builder = new AccountBuilder();
        return builder
                .setID(resultSet.getLong("id"))
                .setAccountNumber(resultSet.getString("accountNumber"))
                .setAmount(resultSet.getFloat("amount"))
                .setCreationDate(resultSet.getDate("creationdate").toLocalDate())
                .setOwner(clientRepository.findByID(resultSet.getLong("clientid")))
                .setAccountType(this.findTypeByID(resultSet.getLong("typeid")))
                .build();
    }

    private AccountType getAccountTypeFromResultSet(ResultSet resultSet) throws SQLException{
        return new AccountType(resultSet.getLong("id"),resultSet.getString("type"));
    }

    @Override
    public Account findByNumber(String accountNumber) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `"+ACCOUNT +"` WHERE accountNumber=?");
            statement.setString(1,accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return getAccountFromResultSet(resultSet);
            }
        }catch (SQLException e){

        }

        return null;
    }

    @Override
    public boolean removeAccount(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM `"+ACCOUNT+"` WHERE id = ?");
            statement.setLong(1,account.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e){

        }
        return false;
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE `"+ACCOUNT +"` SET clientid = ?,accountNumber = ?, amount = ?, creationdate = ?,typeid = ? WHERE id =?");
            statement.setLong(1,account.getOwner().getId());
            statement.setString(2,account.getAccountNumber());
            statement.setFloat(3,account.getAmount());
            statement.setDate(4,Date.valueOf(account.getCreationDate()));
            statement.setLong(5,account.getType().getId());
            statement.setLong(6,account.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e){

        }
        return false;
    }
}
