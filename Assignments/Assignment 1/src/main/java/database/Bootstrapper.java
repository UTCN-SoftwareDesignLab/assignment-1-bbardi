package database;

import model.Utility;
import model.builder.UserBuilder;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import repository.utilities.UtilityRepository;
import repository.utilities.UtilityRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static database.Constants.Roles.*;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.Tables.TABLES;
import static database.Constants.Rights.RIGHTS;
import static database.Constants.Tables.UTILITIES;
import static database.Constants.getRolesRights;
import static database.Constants.AccountTypes.ACCOUNTTYPES;
public class Bootstrapper {

    private RightsRolesRepository rightsRolesRepository;
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private UtilityRepository utilityRepository;
    public void execute() throws SQLException {
        dropAll();

        bootstrapTables();

        bootstrapUserData();

    }

    private void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables from schema:" + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `role_right`;",
                    "TRUNCATE `user_role`;",
                    "DROP TABLE IF EXISTS `role_right`;",
                    "DROP TABLE IF EXISTS `user_role`;",
                    "DROP TABLE IF EXISTS `role`,`right`,`user`,`client`,`account`,`account_type`,`activity`,`utilities`;"
            };
            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void bootstrapTables() throws SQLException{
        for (String schema : SCHEMAS) {
            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();
            Arrays.stream(TABLES).forEach(table->{
                try{
                    statement.execute(SQLTableCreator.getTableCreateSQL(table));
                } catch (SQLException e){
                    e.printStackTrace();
                }
            });
        }
    }
    private void bootstrapUserData() throws SQLException{
        for(String schema : SCHEMAS) {
            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
            accountRepository = new AccountRepositoryMySQL(connection, new ClientRepositoryMySQL(connection));
            userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
            utilityRepository = new UtilityRepositoryMySQL(connection);
            bootstrapRights();
            bootstrapRoles();
            bootstrapRoleRights();
            bootstrapUserRoles();
            bootstrapAccountTypes();
            bootstrapUtilities();
        }
    }

    private void bootstrapRights(){
        for(String right : RIGHTS){
            rightsRolesRepository.addRight(right);
        }
    }

    private void bootstrapRoles(){
        for(String role : ROLES){
            rightsRolesRepository.addRole(role);
        }
    }

    private void bootstrapRoleRights(){
        Map<String, List<String>> rolesRights = getRolesRights();

        for(String role : rolesRights.keySet()){
            Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

            for(String right : rolesRights.get(role)){
                Long rightId = rightsRolesRepository.findRightByTitle(right).getId();

                rightsRolesRepository.addRoleRight(roleId,rightId);
            }
        }
    }
    private void bootstrapAccountTypes(){
        for(String account : ACCOUNTTYPES){
            accountRepository.addType(account);
        }
    }
    private void bootstrapUserRoles() {
        UserBuilder adminBuilder = new UserBuilder();
        adminBuilder.setUsername("admin@admin.com")
                    .setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918")
                    .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(ADMINISTRATOR)));
        userRepository.save(adminBuilder.build());
        adminBuilder.setUsername("employee@admin.com")
                .setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE)));
        userRepository.save(adminBuilder.build());
    }
    private void bootstrapUtilities(){
        Utility utility = new Utility();
        utility.setUtilityName("Electric");
        utility.setClientIdentifier_validation_regex("^[0-9]{6}$");
        utilityRepository.save(utility);
        utility.setUtilityName("Gas");
        utility.setClientIdentifier_validation_regex("^[0-9]{6}$");
        utilityRepository.save(utility);
        utility.setUtilityName("Water");
        utility.setClientIdentifier_validation_regex("^[0-9]{6}$");
        utilityRepository.save(utility);
    }
}
