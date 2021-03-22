package view;

import model.AccountType;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;

public class CreateAccountDialog extends JDialog {

    private final JButton createAccount = new JButton("Create Account");
    private final JTextField accountNumber = new JTextField();
    private final JComboBox<AccountType> accountTypeJComboBox = new JComboBox<AccountType>();
    private final JTextField amount = new JTextField("0.0");

    public CreateAccountDialog(List<AccountType> accountTypeList) throws HeadlessException{
        setModalityType(ModalityType.APPLICATION_MODAL);
        this.setLayout(new GridBagLayout());
        accountTypeList.forEach(accountTypeJComboBox::addItem);
        placeLabels();
        placeFields();
        placeButton();
        this.pack();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void placeLabels(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Account Number:"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Account Type"),constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Amount of Money:"),constraints);
    }
    private void placeFields(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx=150;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(accountNumber,constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(accountTypeJComboBox,constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(amount,constraints);
    }
    private void placeButton(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth=2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(createAccount,constraints);
    }

    public void addCreateButtonActionListener(ActionListener createButtonActionListener){
        createAccount.addActionListener(createButtonActionListener);
    }

    public String getAccountNumber(){
        return accountNumber.getText();
    }
    public AccountType getAccountType(){
        return (AccountType) accountTypeJComboBox.getSelectedItem();
    }
    public Float getAmount(){
        try {
            return Float.valueOf(amount.getText());
        } catch (NumberFormatException e){
            return null;
        }
    }
}
