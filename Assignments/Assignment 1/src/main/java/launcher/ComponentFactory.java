package launcher;

import controller.AdministratorController;
import controller.ClientController;
import controller.LoginController;
import controller.MainController;
import database.Bootstrapper;
import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import repository.utilities.UtilityRepository;
import repository.utilities.UtilityRepositoryMySQL;
import services.account.AccountService;
import services.account.AccountServiceMySQL;
import services.activity.ActivityService;
import services.activity.ActivityServiceMySQL;
import services.client.ClientService;
import services.client.ClientServiceMySQL;
import services.user.AuthenticationService;
import services.user.AuthenticationServiceMySQL;
import services.user.UserManagementService;
import services.user.UserManagementServiceMySQL;
import view.*;


public class ComponentFactory {

    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final UtilityRepository utilityRepository;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    private final AdministratorView administratorView;
    private final ClientView clientView;
    private final MainView mainView;
    private final LoginView loginView;
    private final UpdateAccountDialog updateAccountDialog;
    private final CreateAccountDialog createAccountDialog;

    private final ClientService clientService;
    private final AuthenticationService authenticationService;
    private final ActivityService activityService;
    private final AccountService accountService;
    private final UserManagementService userManagementService;

    private final ClientController clientController;
    private final AdministratorController administratorController;
    private final MainController mainController;
    private final LoginController loginController;

    private static ComponentFactory instance;

    public static ComponentFactory getInstance(boolean testing){
        if(instance == null)
            instance = new ComponentFactory(testing);
        return instance;
    }

    private ComponentFactory(boolean testing) {
        JDBConnectionWrapper connectionWrapper = new DBConnectionFactory().getConnectionWrapper(testing);
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
        accountRepository = new AccountRepositoryMySQL(connectionWrapper.getConnection(), clientRepository);
        utilityRepository = new UtilityRepositoryMySQL(connectionWrapper.getConnection());
        userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(),rightsRolesRepository);
        activityRepository = new ActivityRepositoryMySQL(connectionWrapper.getConnection(), userRepository);

        clientService = new ClientServiceMySQL(clientRepository,accountRepository);
        authenticationService = new AuthenticationServiceMySQL(userRepository,rightsRolesRepository);
        activityService = new ActivityServiceMySQL(activityRepository,userRepository);
        accountService = new AccountServiceMySQL(accountRepository);
        userManagementService = new UserManagementServiceMySQL(userRepository,rightsRolesRepository);

        clientView = new ClientView();
        mainView = new MainView();
        loginView = new LoginView();
        administratorView = new AdministratorView(rightsRolesRepository.findAllRoles());
        updateAccountDialog = new UpdateAccountDialog(accountRepository.findAllTypes(),utilityRepository.findAll());
        createAccountDialog = new CreateAccountDialog(accountRepository.findAllTypes());

        clientController = new ClientController(mainView,clientView,clientService,accountService,activityService,createAccountDialog,updateAccountDialog);
        administratorController = new AdministratorController(mainView,activityService,userManagementService,administratorView);
        mainController = new MainController(loginView,mainView,administratorController,clientController,activityService);
        loginController = new LoginController(loginView,authenticationService,activityService,mainController);
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public UtilityRepository getUtilityRepository() {
        return utilityRepository;
    }

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AdministratorView getAdministratorView() {
        return administratorView;
    }

    public ClientView getClientView() {
        return clientView;
    }

    public MainView getMainView() {
        return mainView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public UpdateAccountDialog getUpdateAccountDialog() {
        return updateAccountDialog;
    }

    public CreateAccountDialog getCreateAccountDialog() {
        return createAccountDialog;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public UserManagementService getUserManagementService() {
        return userManagementService;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public AdministratorController getAdministratorController() {
        return administratorController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public LoginController getLoginController() {
        return loginController;
    }
}
