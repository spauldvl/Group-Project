package com.bank;

import javax.swing.*;
import java.util.ArrayList;

import static com.bank.AccountReader.readAccounts;

public class Banker {
    public static final String CHECKING = "Checking";
    public static final String SAVINGS = "Savings";
    public static final String CERTIFICATE_OF_DEPOSIT = "Certificate of Deposit";

     private static ArrayList<Account> allAccounts = new ArrayList<>();
     private static Banker banker = null;
     private Banker(){

     }
    public static Banker getInstance() {
        if (banker == null) {
            banker = new Banker();
        }
        return banker;
    }

    public static void promptUser(){

        int goAgain = 0;
        do {
            String accountTypeInput = JOptionPane.showInputDialog("What type of account would you like to create?");

            if (accountTypeInput.equalsIgnoreCase("certificate of deposit")){
                CertificateOfDeposit account = new CertificateOfDeposit();
                String nameInput = JOptionPane.showInputDialog("What would you like to name this account?");
                account.setName(nameInput);

                String strTermInput = JOptionPane.showInputDialog("What is the term length?");
                int term = Integer.parseInt(strTermInput);
                account.setMaturity(term);

                String strBalance = JOptionPane.showInputDialog("Enter your account balance");
                double balance = Double.parseDouble(strBalance);
                account.setBalance(balance);

                String strInterest = JOptionPane.showInputDialog("Enter your interest rate");
                int interest = Integer.parseInt(strInterest);
                account.setInterest(interest);

                String strPeriods = JOptionPane.showInputDialog("Enter the number of Periods");
                int periods = Integer.parseInt(strPeriods);
                account.setPeriods(periods);

                allAccounts.add(account);
            }
            else if (accountTypeInput.equalsIgnoreCase("checking")){
                Checking account = new Checking();

                String nameInput = JOptionPane.showInputDialog("What would you like to name this account?");
                account.setName(nameInput);

                String strBalance = JOptionPane.showInputDialog("Enter your account balance");
                double balance = Double.parseDouble(strBalance);
                account.setBalance(balance);

                String strInterest = JOptionPane.showInputDialog("Enter your interest rate");
                int interest = Integer.parseInt(strInterest);
                account.setInterest(interest);

                String strPeriods = JOptionPane.showInputDialog("Enter the number of Periods");
                int periods = Integer.parseInt(strPeriods);
                account.setPeriods(periods);

                allAccounts.add(account);
            }
            else if (accountTypeInput.equalsIgnoreCase("savings")){
                Savings account = new Savings();

                String nameInput = JOptionPane.showInputDialog("What would you like to name this account?");
                account.setName(nameInput);

                String strBalance = JOptionPane.showInputDialog("Enter your account balance");
                double balance = Double.parseDouble(strBalance);
                account.setBalance(balance);

                String strInterest = JOptionPane.showInputDialog("Enter your interest rate");
                int interest = Integer.parseInt(strInterest);
                account.setInterest(interest);

                String strPeriods = JOptionPane.showInputDialog("Enter the number of Periods");
                int periods = Integer.parseInt(strPeriods);
                account.setPeriods(periods);

                allAccounts.add(account);
            }
            else{
                JOptionPane.showMessageDialog(null, "This account type is invalid");
            }

            goAgain = JOptionPane.showConfirmDialog(null, "Would you like to add another account?");

        }while(goAgain == JOptionPane.YES_OPTION);


    }
    private static void displayOutput() {
        String output = "";
        for (Account account : allAccounts) {
            account.compute();
            String formattedBalance = String.format("%.2f", account.getBalance());
            output += account.getName() + ": " + "$" + formattedBalance + "\n";

        }
        JOptionPane.showMessageDialog(null, output);

    }
    public static void main (String[] args) {
        Banker banker1 = getInstance();

        promptUser();
        displayOutput();
        readAccounts();
    }

    /**
     * Simple factory method to create and return a subclass of type Vehicle.
     *
     * @param selectedAccount A string representing the vehicle we want to create.
     * @return the created vehicle.
     */
    public Account createAccount (final Object selectedAccount) {
        Account account = null;
        if (selectedAccount.toString().equals(CHECKING)) {
            account = new Checking();
        } else if (selectedAccount.toString().equals(SAVINGS)) {
            Savings savings = new Savings();
            account = savings;
        } else if (selectedAccount.toString().equals(CERTIFICATE_OF_DEPOSIT)) {
            CertificateOfDeposit certificateOfDeposit = new CertificateOfDeposit();
            int term = certificateOfDeposit.getMaturity();
            account = certificateOfDeposit;
        }
        return account;
    }
}
