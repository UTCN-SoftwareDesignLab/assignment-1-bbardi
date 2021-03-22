package controller;

import model.Activity;
import model.Role;
import model.User;
import model.validation.Notification;
import services.activity.ActivityService;
import services.user.UserManagementService;
import view.AdministratorView;
import view.MainView;
import view.ReportDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdministratorController {
    private final MainView mainView;
    private final UserManagementService userManagementService;
    private final ActivityService activityService;
    private final AdministratorView administratorView;

    public AdministratorController(MainView mainView,ActivityService activityService, UserManagementService userManagementService, AdministratorView administratorView) {
        this.mainView = mainView;
        this.userManagementService = userManagementService;
        this.administratorView = administratorView;
        this.activityService = activityService;
        administratorView.addReturnButtonActionListener(new ReturnButtonActionListener());
        administratorView.addCreateUserButtonActionListener(new CreateUserButtonActionListener());
        administratorView.addDeleteUserButtonActionListener(new DeleteUserButtonActionListener());
        administratorView.addAlterUserButtonActionListener(new AlterUserButtonActionListener());
        administratorView.addViewUserButtonActionListener(new ViewUserButtonActionListener());
        administratorView.addGenerateReportButtonActionListener(new GenerateReportButtonActionListener());
    }

    public AdministratorView getView() {
        return administratorView;
    }

    private class CreateUserButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = userManagementService.register(administratorView.getUsername(), administratorView.getPassword(),administratorView.getConfirmPassword(), administratorView.getRole());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(administratorView.getContentPane(),notification.getFormattedErrors());
            }
            else{
                if (notification.getResult()){
                    JOptionPane.showMessageDialog(administratorView.getContentPane(),"User created");
                }
                else{
                    JOptionPane.showMessageDialog(administratorView.getContentPane(),"Database error occurred");
                }
            }
        }
    }

    private class DeleteUserButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = userManagementService.remove(administratorView.getUsername());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(administratorView.getContentPane(),notification.getFormattedErrors());
            }
            else{
                if (notification.getResult()){
                    JOptionPane.showMessageDialog(administratorView.getContentPane(),"User deleted");
                }
                else{
                    JOptionPane.showMessageDialog(administratorView.getContentPane(),"Database error occurred");
                }
            }
        }
    }

    private class AlterUserButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = userManagementService.update(administratorView.getUsername(), administratorView.getPassword(),administratorView.getConfirmPassword(), administratorView.getRole());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(administratorView.getContentPane(),notification.getFormattedErrors());
            }
            else{
                if (notification.getResult()){
                    JOptionPane.showMessageDialog(administratorView.getContentPane(),"User Updated");
                }
                else{
                    JOptionPane.showMessageDialog(administratorView.getContentPane(),"Database error occurred");
                }
            }
        }
    }

    private class ViewUserButtonActionListener implements ActionListener{
        private String formatRoles(List<Role> roleList){
            StringBuilder builder = new StringBuilder();
            roleList.forEach(l-> builder.append(" ").append(l.getRole()));
            return builder.toString();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<User> notification = userManagementService.findByUsername(administratorView.getUsername());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(administratorView.getContentPane(),notification.getFormattedErrors());
            }
            else{
                User foundUser = notification.getResult();
                JOptionPane.showMessageDialog(administratorView.getContentPane(),"Username: " + foundUser.getUsername() + "\nRole(s):" + formatRoles(foundUser.getRoles()));
            }
        }
    }

    private class GenerateReportButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<List<Activity>> notification = activityService.findActivityByUserAndPeriod(administratorView.getUsername(), administratorView.getStartDate(), administratorView.getEndDate());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(administratorView.getContentPane(),notification.getFormattedErrors());
            }else{
                ReportDialog reportDialog = new ReportDialog(notification.getResult());
                reportDialog.setVisible(true);
            }
        }
    }

    private class ReturnButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.setVisible(true);
            administratorView.setVisible(false);
        }
    }
}
