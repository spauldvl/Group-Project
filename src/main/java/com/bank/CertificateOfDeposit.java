package com.bank;

public class CertificateOfDeposit extends Account {

    private static int term;


    public int getMaturity() {
        return term;
    }
    public void setMaturity(int inputTerm) {
        term = inputTerm;
    }

    @Override
    public String toString() {

        String message = "Account Number: " + getAccountNumber() + " Balance: " + getBalance() + " Interest: " + getInterest() + " Periods: " + getPeriods() + " Term: " + getMaturity();
        if (getName() != null){
            message = "Name: " + getName() + " " + message;
        }
        return message;

    }



}
