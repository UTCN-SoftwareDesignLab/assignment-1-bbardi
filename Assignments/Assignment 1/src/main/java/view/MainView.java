package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private final JButton clientOperationsButton = new JButton("Client operations");
    private final JButton administratorOperationsButton = new JButton("Employee operations");
    private final JButton logOutButton = new JButton("Log out");

    public MainView() throws HeadlessException{
        this.setLayout(new FlowLayout());
        this.setTitle("Main Window");
        add(clientOperationsButton);
        add(administratorOperationsButton);
        add(logOutButton);
        this.pack();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void enableClientOperations(){
        clientOperationsButton.setEnabled(true);
    }
    public void enableEmployeeOperations(){
        administratorOperationsButton.setEnabled(true);
    }

    public void disableClientOperations(){
        clientOperationsButton.setEnabled(false);
    }
    public void disableEmployeeOperations(){
        administratorOperationsButton.setEnabled(false);
    }

    public void addClientOperationsActionListener(ActionListener clientOperationsActionListener){
        clientOperationsButton.addActionListener(clientOperationsActionListener);
    }

    public void addAdministratorOperationsActionListener(ActionListener administratorOperationsActionListener){
        administratorOperationsButton.addActionListener(administratorOperationsActionListener);
    }
    public void addLogOutActionListener(ActionListener logOutActionListener){
        logOutButton.addActionListener(logOutActionListener);
    }
}
