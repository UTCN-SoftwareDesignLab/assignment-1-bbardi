package controller;

import model.Role;
import model.User;
import services.activity.ActivityService;
import view.LoginView;
import view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class MainController {
    private final LoginView loginView;
    private final MainView mainView;
    private final ClientController clientController;
    private final AdministratorController administratorController;
    private final ActivityService activityService;

    public MainController(LoginView loginView, MainView mainView, AdministratorController administratorController, ClientController clientController, ActivityService activityService) {
        this.loginView = loginView;
        this.mainView = mainView;
        this.clientController = clientController;
        this.activityService = activityService;
        this.administratorController = administratorController;
        mainView.addLogOutActionListener(new LogOutButtonActionListener());
        mainView.addClientOperationsActionListener(new ClientOperationsActionListener());
        mainView.addAdministratorOperationsActionListener(new AdminOperationsActionListener());
    }

    public void enableFeaturesOfUser(User loggedUser){
        mainView.disableClientOperations();
        mainView.disableEmployeeOperations();
        for(Role role : loggedUser.getRoles()){
            if(role.getRole().equals(ADMINISTRATOR))
                mainView.enableEmployeeOperations();
            if(role.getRole().equals(EMPLOYEE)){
                mainView.enableClientOperations();
            }
        }
    }

    public MainView getMainView() {
        return mainView;
    }

    private class ClientOperationsActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.setVisible(false);
            clientController.getClientView().setVisible(true);
        }
    }

    private class LogOutButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.setVisible(false);
            activityService.recordActivity("Logged out");
            activityService.setUser(null);
            loginView.setVisible(true);
        }
    }
    private class AdminOperationsActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.setVisible(false);
            administratorController.getView().setVisible(true);
        }
    }
}
