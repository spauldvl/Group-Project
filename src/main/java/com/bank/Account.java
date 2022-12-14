package com.bank;

import java.io.Serializable;

public abstract class Account implements Serializable, Comparable<Account> {

    private double balance;
    private double interest;
    private int periods;
    private String name;
    private int accountNumber;

    public int getAccountNumber(){
        return accountNumber;
    }
    public void setAccountNumber(int inputNumber){
        accountNumber = inputNumber;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double inputBalance) {
        balance = inputBalance;
    }
    public double getInterest() {
        return interest;
    }
    public void setInterest(double inputInterest) {

        interest = Double.parseDouble(String.valueOf(inputInterest)) / 100;
    }
    public int getPeriods() {
        return periods;
    }
    public void setPeriods(int inputPeriods) {
        periods = inputPeriods;
    }
    public void compute(){
        for(int i = 0; i < periods; i++){
            setBalance((getInterest() * getBalance()) + getBalance());
        }
    }
    public void withdraw(double amount){
        setBalance(getBalance() - amount);
    }

    public String getName(){
        return name;
    }
    public void setName(String inputName){
        name = inputName;
    }
    @Override
    public String toString() {
        String message = "Account Number: " + getAccountNumber() + " Balance: " + getBalance() + " Interest: " + getInterest() + " Periods: " + getPeriods();
        if (getName() != null){
            message = "Name: " + getName() + " " + message;
            System.out.println("to string" + getName());
        }
        return message;
    }
    public int compareTo(Account account){
        return Double.compare(getInterest(), account.getInterest());
    }

}
