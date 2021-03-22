package controller;

import model.User;
import model.validation.Notification;
import services.activity.ActivityService;
import services.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final ActivityService activityService;
    private final MainController mainController;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, ActivityService activityService, MainController mainController) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.mainController = mainController;
        this.activityService = activityService;
        loginView.addLoginButtonListener(new LoginButtonListener());
    }


    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            Notification<User> loginNotification = authenticationService.login(username,password);

            if(loginNotification.hasErrors()){
                JOptionPane.showMessageDialog(loginView.getContentPane(),loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login Successful!");
                activityService.setUser(loginNotification.getResult());
                activityService.recordActivity("Logged in");
                loginView.setVisible(false);
                mainController.enableFeaturesOfUser(loginNotification.getResult());
                mainController.getMainView().setVisible(true);
            }
        }
    }
}
