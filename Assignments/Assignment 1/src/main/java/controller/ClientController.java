package controller;

import model.Account;
import model.Client;
import model.User;
import model.validation.Notification;
import services.account.AccountService;
import services.activity.ActivityService;
import services.client.ClientService;
import view.ClientView;
import view.CreateAccountDialog;
import view.MainView;
import view.UpdateAccountDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientController {

    private final MainView mainView;
    private final ClientView clientView;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ActivityService activityService;

    private final CreateAccountDialog createAccountDialog;
    private final UpdateAccountDialog updateAccountDialog;

    public ClientController(MainView mainView, ClientView clientView, ClientService clientService,AccountService accountService,ActivityService activityService, CreateAccountDialog accountDialog, UpdateAccountDialog updateAccountDialog) {
        this.mainView = mainView;
        this.clientView = clientView;
        this.accountService = accountService;
        this.clientService = clientService;
        this.activityService = activityService;
        this.createAccountDialog = accountDialog;
        this.updateAccountDialog = updateAccountDialog;
        clientView.addBackButtonActionListener(new BackButtonActionListener());
        clientView.addSearchIDCardActionListener(new FindByIDCardActionListener());
        clientView.addSearchPersonalNumericCodeActionListener(new FindByPersonalNumericCodeActionListener());
        clientView.addAddClientActionListener(new AddClientActionListener());
        clientView.addAlterClientActionListener(new AlterClientActionListener());
        clientView.addDeleteButtonActionListener(new DeleteClientActionListener());
        clientView.addCreateAccountActionListener(new CreateAccountActionListener());
        clientView.addViewAccountActionListener(new ViewAccountActionListener());

        accountDialog.addCreateButtonActionListener(new CreateDialogButtonActionListener());

        updateAccountDialog.addDeleteAccountButtonListener(new DeleteAccountActionListener());
        updateAccountDialog.addTransferMoneyButtonListener(new TransferMoneyActionListener());
        updateAccountDialog.addWithdrawMoneyButtonListener(new WithdrawMoneyActionListener());
        updateAccountDialog.addDepositMoneyButtonListener(new DepositMoneyActionListener());
        updateAccountDialog.addPayBillButtonListener(new PayBillActionListener());
        updateAccountDialog.addUpdateAccountButtonListener(new UpdateAccountButtonListener());
    }

    public ClientView getClientView() {
        return clientView;
    }

    private class PayBillActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Account> notification = accountService.payBill(updateAccountDialog.getAccountNumber(), updateAccountDialog.getUtility(), updateAccountDialog.getAmount(), updateAccountDialog.getIdentifier());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),notification.getFormattedErrors());
            }
            else{
                    JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),"Bill processed");
                    activityService.recordActivity("Processed utility bill of company: "
                            + updateAccountDialog.getUtility()
                            + ", company identifier: "
                            + updateAccountDialog.getIdentifier()
                            + ", amount="
                            + updateAccountDialog.getAmount()
                            + ", from account: "
                            + updateAccountDialog.getAccountNumber());
                    updateAccountDialog.setAccount(notification.getResult());
            }
        }
    }

    private class UpdateAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Account> notification = accountService.update(updateAccountDialog.getAccountNumber(), updateAccountDialog.getAccountType());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),notification.getFormattedErrors());
            }
            else{
                    JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),"Account Modified");
                    activityService.recordActivity("Updated account information of "+ updateAccountDialog.getAccountNumber());
                    updateAccountDialog.setAccount(notification.getResult());
            }
        }
    }

    private class WithdrawMoneyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Account> notification = accountService.withdraw(updateAccountDialog.getAccountNumber(), updateAccountDialog.getAmount());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),notification.getFormattedErrors());
            }
            else{
                    JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),"Money withdrawn");
                    activityService.recordActivity("Withdrawn "+updateAccountDialog.getAmount() + " from account " + updateAccountDialog.getAccountNumber());
                    updateAccountDialog.setAccount(notification.getResult());
            }
        }
    }

    private class DepositMoneyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Account> notification = accountService.deposit(updateAccountDialog.getAccountNumber(), updateAccountDialog.getAmount());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),notification.getFormattedErrors());
            }
            else{
                JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),"Money deposited");
                activityService.recordActivity("Deposited " + updateAccountDialog.getAmount() + " into account " + updateAccountDialog.getAccountNumber());
                updateAccountDialog.setAccount(notification.getResult());
            }
        }
    }

    private class DeleteAccountActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = accountService.delete(updateAccountDialog.getAccountNumber());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),notification.getFormattedErrors());
            }
            else{
                if (notification.getResult()){
                    JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),"Account Deleted");
                    activityService.recordActivity("Deleted account "+updateAccountDialog.getAccountNumber());
                    updateAccountDialog.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),"Account not deleted due to a database error");
                }
            }
        }
    }

    private class TransferMoneyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Account> notification = accountService.transfer(updateAccountDialog.getAccountNumber(), updateAccountDialog.getDestinationNumber(), updateAccountDialog.getAmount());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),notification.getFormattedErrors());
            }
            else{
                JOptionPane.showMessageDialog(updateAccountDialog.getContentPane(),"Money transferred successfully");
                activityService.recordActivity("Transferred "+updateAccountDialog.getAmount()+" from "+updateAccountDialog.getAccountNumber() + " to " + updateAccountDialog.getDestinationNumber());
                updateAccountDialog.setAccount(notification.getResult());
            }
        }
    }

    private class ViewAccountActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateAccountDialog.setAccount(clientView.getSelectedAccount());
            updateAccountDialog.setVisible(true);
            //invalidate the accounts cached in the list
            clientView.fillAccounts(clientService.findAccounts(clientService.findByIDCard(clientView.getIDCard()).getResult()));
        }
    }

    private class CreateAccountActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            createAccountDialog.setVisible(true);
        }
    }

    private class CreateDialogButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = clientService.createAccount(clientView.getID(), createAccountDialog.getAccountNumber(), createAccountDialog.getAccountType(), createAccountDialog.getAmount());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(createAccountDialog.getContentPane(),notification.getFormattedErrors());
            }else{
                if(notification.getResult()){
                    JOptionPane.showMessageDialog(createAccountDialog.getContentPane(),"Account created");
                    clientView.fillAccounts(clientService.findAccounts(clientService.findByID(clientView.getID()).getResult()));
                    activityService.recordActivity("Created account " + createAccountDialog.getAccountNumber());
                    createAccountDialog.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(createAccountDialog.getContentPane(),"A database error occurred");
                }
            }
        }
    }

    private class DeleteClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = clientService.delete(clientView.getID());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(),notification.getFormattedErrors());
            }
            else
                if(notification.getResult()){
                    JOptionPane.showMessageDialog(clientView.getContentPane(),"Client data erased");
                    activityService.recordActivity("Deleted client " + clientView.getFullName());
                    clientView.setID("Client deleted");
                    clientView.setIDCard("");
                    clientView.setFullName("");
                    clientView.setPersonalNumericCode("");
                    clientView.setAddress("");
                    clientView.fillAccounts(new ArrayList<>());
                }
                else{
                    JOptionPane.showMessageDialog(clientView.getContentPane(),"Database error");
                }
        }
    }

    private class AlterClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = clientService.update(clientView.getID(), clientView.getFullName(), clientView.getPersonalNumericCode(), clientView.getIDCard(), clientView.getAddress());
            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(),notification.getFormattedErrors());
            }
            else{
                if(notification.getResult()){
                    JOptionPane.showMessageDialog(clientView.getContentPane(),"Client data modified");
                    activityService.recordActivity("Altered client data ID=" + clientView.getID());
                }
                else{
                    JOptionPane.showMessageDialog(clientView.getContentPane(),"Database error");
                }
            }
        }
    }

    private class AddClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> addNotification = clientService.save(clientView.getFullName(), clientView.getPersonalNumericCode(), clientView.getIDCard(), clientView.getAddress());
            if(addNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), addNotification.getFormattedErrors());
            }
            else{
                if(addNotification.getResult()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), "Client successfully added");
                    activityService.recordActivity("Added new client "+ clientView.getFullName());
                    Notification<Client> clientNotification = clientService.findByPersonalNumericCode(clientView.getPersonalNumericCode());
                    clientView.setID(clientNotification.getResult().getId().toString());
                }
                else{
                    JOptionPane.showMessageDialog(clientView.getContentPane(), "Database error");
                }
            }
        }
    }

    private class FindByPersonalNumericCodeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Client> clientNotification = clientService.findByPersonalNumericCode(clientView.getPersonalNumericCode());
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            }
            else{
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Client successfully found");
                clientView.setID(clientNotification.getResult().getId().toString());
                clientView.setAddress(clientNotification.getResult().getAddress());
                clientView.setFullName(clientNotification.getResult().getName());
                clientView.setIDCard(clientNotification.getResult().getIdentityCard());
                clientView.setPersonalNumericCode(clientNotification.getResult().getPersonalNumericCode());
                clientView.fillAccounts(clientService.findAccounts(clientNotification.getResult()));
            }
        }
    }

    private class FindByIDCardActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Client> clientNotification = clientService.findByIDCard(clientView.getIDCard());
            if(clientNotification.hasErrors()){
                JOptionPane.showMessageDialog(clientView.getContentPane(), clientNotification.getFormattedErrors());
            }
            else{
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Client successfully found");
                clientView.setID(clientNotification.getResult().getId().toString());
                clientView.setAddress(clientNotification.getResult().getAddress());
                clientView.setFullName(clientNotification.getResult().getName());
                clientView.setIDCard(clientNotification.getResult().getIdentityCard());
                clientView.setPersonalNumericCode(clientNotification.getResult().getPersonalNumericCode());
                clientView.fillAccounts(clientService.findAccounts(clientNotification.getResult()));
            }
        }
    }

    private class BackButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.setVisible(true);
            clientView.setVisible(false);
        }
    }
}
