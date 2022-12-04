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
        return " Name: " + getName() + " Balance: " + getBalance() + " Interest: " + getInterest() + " Periods: " + getPeriods() + " Term: " + getMaturity();
    }



}
