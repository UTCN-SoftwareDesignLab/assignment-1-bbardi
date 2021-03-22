package view;

import controller.ClientController;
import model.Account;
import model.AccountType;
import model.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class UpdateAccountDialog extends JDialog {

    private final JComboBox<AccountType> accountTypes = new JComboBox<>();
    private final JTextField accountNumber = new JTextField();
    private final JTextField available = new JTextField();
    private final JTextField destination = new JTextField();
    private final JTextField amount = new JTextField();
    private final JComboBox<Utility> utilityJComboBox = new JComboBox<>();
    private final JTextField identifierNumber = new JTextField();
    private final JTextField dateOfCreation = new JTextField();

    private final JButton payBillButton = new JButton("Pay bill");
    private final JButton depositButton = new JButton("Deposit Money");
    private final JButton withdrawButton = new JButton("Withdraw Money");
    private final JButton updateButton = new JButton("Update Account");
    private final JButton deleteButton = new JButton("Delete Account");
    private final JButton transferButton = new JButton("Transfer Money");

    public UpdateAccountDialog(List<AccountType> accountTypeList, List<Utility> utilities) throws HeadlessException{
        setModalityType(ModalityType.APPLICATION_MODAL);
        accountTypeList.forEach(accountTypes::addItem);
        utilities.forEach(utilityJComboBox::addItem);
        accountNumber.setEditable(false);
        available.setEditable(false);
        dateOfCreation.setEditable(false);
        this.setLayout(new GridBagLayout());
        placeLabels();
        placeFields();
        placeButtons();
        this.pack();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void placeLabels(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Account number:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Account Type:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Current Balance:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Destination account:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Utility company:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Utility identifier:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Amount:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Date of Creation:"),constraints);
    }

    private void placeFields(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(accountNumber,constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(accountTypes,constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(available,constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(destination,constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(utilityJComboBox,constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(identifierNumber,constraints);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(amount,constraints);
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(dateOfCreation,constraints);
    }

    private void placeButtons(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(updateButton,constraints);
        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(deleteButton,constraints);
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(withdrawButton,constraints);
        constraints.gridx = 1;
        constraints.gridy = 9;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(depositButton,constraints);
        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(payBillButton,constraints);
        constraints.gridx = 1;
        constraints.gridy = 10;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(transferButton,constraints);
    }

    public void addDeleteAccountButtonListener(ActionListener deleteAccountButtonListener){
        deleteButton.addActionListener(deleteAccountButtonListener);
    }

    public void addTransferMoneyButtonListener(ActionListener transferMoneyActionListener) {
        transferButton.addActionListener(transferMoneyActionListener);
    }

    public void addDepositMoneyButtonListener(ActionListener depositMoneyActionListener){
        depositButton.addActionListener(depositMoneyActionListener);
    }

    public void addWithdrawMoneyButtonListener(ActionListener withdrawMoneyActionListener){
        withdrawButton.addActionListener(withdrawMoneyActionListener);
    }

    public void addPayBillButtonListener(ActionListener payBillActionListener) {
        payBillButton.addActionListener(payBillActionListener);
    }

    public void addUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        updateButton.addActionListener(updateAccountButtonListener);
    }

    public void setAccount(Account selectedAccount){
        accountNumber.setText(selectedAccount.getAccountNumber());
        available.setText(selectedAccount.getAmount().toString());
        accountTypes.setSelectedItem(selectedAccount.getType());
        destination.setText("");
        amount.setText("");
        identifierNumber.setText("");
        dateOfCreation.setText(selectedAccount.getCreationDate().toString());
    }

    public String getAccountNumber(){
        return accountNumber.getText();
    }
    public String getDestinationNumber(){
        return destination.getText();
    }
    public String getAmount(){
        return amount.getText();
    }
    public String getIdentifier(){
        return identifierNumber.getText();
    }
    public AccountType getAccountType(){
        return (AccountType) accountTypes.getSelectedItem();
    }
    public Utility getUtility(){
        return (Utility) utilityJComboBox.getSelectedItem();
    }
}
