package com.bank;

import javax.swing.*;
import java.util.ArrayList;
public class Banker {
     private static ArrayList<Account> allAccounts = new ArrayList<>();

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
            output += account.getName() + ": " + "$" + account.getBalance() + "\n";

        }
        JOptionPane.showMessageDialog(null, output);

    }
    public static void main (String[] args) {

        promptUser();
        displayOutput();
    }
}
