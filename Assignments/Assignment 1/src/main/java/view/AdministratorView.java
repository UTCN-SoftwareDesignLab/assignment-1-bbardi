package view;

import model.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class AdministratorView extends JFrame {
    private final JButton viewUser = new JButton("View user");
    private final JButton createUser = new JButton("Create user");
    private final JButton deleteUser = new JButton("Delete user");
    private final JButton updateUser = new JButton("Update user");
    private final JButton generateReport = new JButton("Generate report");
    private final JButton exitModeButton = new JButton("Return to main");

    private final JComboBox<Role> roleJComboBox = new JComboBox<>();
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField confirmPasswordField = new JPasswordField();
    private final JTextField dateStartField = new JTextField();
    private final JTextField dateEndField = new JTextField();

    public AdministratorView(List<Role> roleList) throws HeadlessException{
        this.setLayout(new GridBagLayout());
        this.setTitle("Admin Window");
        placeLabels();
        placeFields();
        placeButtons();
        roleList.forEach(roleJComboBox::addItem);
        this.pack();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void placeLabels(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Username:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Password:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Confirm Password:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("New Role:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Begin date:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("End date:"),constraints);
    }

    private void placeFields(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(confirmPasswordField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(roleJComboBox,constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(dateStartField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(dateEndField,constraints);
    }
    private void placeButtons(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(createUser,constraints);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(viewUser,constraints);
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(updateUser,constraints);
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(deleteUser,constraints);
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(generateReport,constraints);
        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(exitModeButton,constraints);
    }

    public void addReturnButtonActionListener(ActionListener returnButtonActionListener){
        exitModeButton.addActionListener(returnButtonActionListener);
    }

    public void addCreateUserButtonActionListener(ActionListener createUserButtonActionListener){
        createUser.addActionListener(createUserButtonActionListener);
    }

    public void addDeleteUserButtonActionListener(ActionListener deleteUserButtonActionListener){
        deleteUser.addActionListener(deleteUserButtonActionListener);
    }

    public void addAlterUserButtonActionListener(ActionListener alterUserButtonActionListener){
        updateUser.addActionListener(alterUserButtonActionListener);
    }

    public void addViewUserButtonActionListener(ActionListener viewUserButtonActionListener){
        viewUser.addActionListener(viewUserButtonActionListener);
    }

    public void addGenerateReportButtonActionListener(ActionListener generateReportButtonActionListener){
        generateReport.addActionListener(generateReportButtonActionListener);
    }

    public String getUsername(){
        return usernameField.getText();
    }
    public String getPassword(){
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword(){
        return new String(confirmPasswordField.getPassword());
    }

    public String getRole(){
        return ((Role) roleJComboBox.getSelectedItem()).getRole();
    }
    public String getStartDate(){
        return dateStartField.getText();
    }
    public String getEndDate(){
        return dateEndField.getText();
    }
}
