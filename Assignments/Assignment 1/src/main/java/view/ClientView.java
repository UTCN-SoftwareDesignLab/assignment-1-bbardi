package view;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientView extends JFrame {

    private final JButton searchIDCard = new JButton("Search by ID Card");
    private final JButton searchPersonalNumericCode = new JButton("Search by PNC");
    private final JButton deleteClient = new JButton("Delete Client");
    private final JButton backButton = new JButton("Back");
    private final JButton alterClient = new JButton("Modify Client");
    private final JButton addClient = new JButton("Add Client");
    private final JButton viewAccountButton = new JButton("V/U selected account");
    private final JButton createAccount = new JButton("Create new account");
    private final JTextField IDField = new JTextField();
    private final JTextField IDCardField = new JTextField();
    private final JTextField PNCField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField addressField = new JTextField();
    private final JComboBox<Account> accountJComboBox = new JComboBox<Account>();

    public ClientView() throws HeadlessException {
        setLayout(new GridBagLayout());
        this.setTitle("Client Operations");
        IDField.setEditable(false);
        IDField.setText("Client not Selected");
        placeLabels();
        placeFields();
        placeButtons();
        this.pack();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void placeButtons(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(searchIDCard,constraints);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(searchPersonalNumericCode,constraints);
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(alterClient,constraints);
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(addClient,constraints);
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(deleteClient,constraints);
        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(createAccount,constraints);
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(viewAccountButton,constraints);
        constraints.gridx = 1;
        constraints.gridy = 9;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(backButton,constraints);

    }

    private void placeFields(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(IDField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(nameField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(IDCardField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(PNCField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(addressField,constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(accountJComboBox,constraints);
        accountJComboBox.setEnabled(false);
    }

    private void placeLabels(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Database ID:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Name:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("ID Card:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("PNC:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Address:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Account:"),constraints);
    }

    public void addSearchIDCardActionListener(ActionListener searchIDCardActionListener){
        searchIDCard.addActionListener(searchIDCardActionListener);
    }

    public void addSearchPersonalNumericCodeActionListener(ActionListener searchPersonalNumericCodeActionListener){
        searchPersonalNumericCode.addActionListener(searchPersonalNumericCodeActionListener);
    }

    public void addAddClientActionListener(ActionListener addClientActionListener){
        addClient.addActionListener(addClientActionListener);
    }

    public void addAlterClientActionListener(ActionListener alterClientActionListener){
        alterClient.addActionListener(alterClientActionListener);
    }

    public void addCreateAccountActionListener(ActionListener createAccountActionListener){
        createAccount.addActionListener(createAccountActionListener);
    }

    public void addViewAccountActionListener(ActionListener viewAccountActionListener){
        viewAccountButton.addActionListener(viewAccountActionListener);
    }

    public void addBackButtonActionListener(ActionListener backButtonActionListener){
        backButton.addActionListener(backButtonActionListener);
    }
    public void addDeleteButtonActionListener(ActionListener deleteButtonActionListener){
        deleteClient.addActionListener(deleteButtonActionListener);
    }

    public String getPersonalNumericCode(){
        return PNCField.getText();
    }
    public String getIDCard(){
        return IDCardField.getText();
    }
    public String getAddress(){
        return addressField.getText();
    }
    public String getFullName(){
        return nameField.getText();
    }
    public Long getID(){
        try{
            return Long.valueOf(IDField.getText());
        } catch (NumberFormatException e){
            return -1L;
        }
    }

    public void setPersonalNumericCode(String personalNumericCode){
        PNCField.setText(personalNumericCode);
    }
    public void setIDCard(String idCard){
        IDCardField.setText(idCard);
    }
    public void setFullName(String fullName){
        nameField.setText(fullName);
    }
    public void setAddress(String address){
        addressField.setText(address);
    }
    public void fillAccounts(List<Account> accountList){
        if(!accountList.isEmpty()){
            accountJComboBox.removeAllItems();
            for(Account account : accountList){
                accountJComboBox.addItem(account);
            }
            accountJComboBox.setEnabled(true);
        }
        else{
            accountJComboBox.removeAllItems();
            accountJComboBox.setEnabled(false);
        }
    }
    public void setID(String ID){
        IDField.setText(ID);
    }
    public Account getSelectedAccount(){
        return (Account)accountJComboBox.getSelectedItem();
    }
}
