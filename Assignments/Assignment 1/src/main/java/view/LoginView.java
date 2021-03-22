package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView() throws HeadlessException{
        initializeFields();
        setTitle("Please Login");
        setLayout(new GridBagLayout());
        placeComponents();
        this.pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void placeComponents(){
        GridBagConstraints constraints = new GridBagConstraints();
        //(0,0) - Username label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.ipadx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Username:"),constraints);
        //(0,1) - Username field
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.ipadx = 100;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField,constraints);
        //(1,0) - Password label
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.ipadx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Password:"),constraints);
        //(1,1) - Password field
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.ipadx = 100;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField,constraints);
        //(2,0) - Login Button(double width)
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        add(loginButton,constraints);
    }

    private void initializeFields(){
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
    }

    public String getUsername(){
        return usernameField.getText();
    }

    public String getPassword(){
        return new String(passwordField.getPassword());
    }

    public void addLoginButtonListener(ActionListener loginButtonListener){
        loginButton.addActionListener(loginButtonListener);
    }
}
