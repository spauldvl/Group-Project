package com.bank;

abstract class Account {

    private int balance;
    private int interest;
    private int periods;
    private String name;

    public int getBalance() {
        return balance;
    }
    public void setBalance(int inputBalance) {
        balance = inputBalance;
    }
    public int getInterest() {
        return interest;
    }
    public void setInterest(int inputInterest) {
        interest = inputInterest;
    }
    public int getPeriods() {
        return periods;
    }
    public void setPeriods(int inputPeriods) {
        periods = inputPeriods;
    }
    public void compute(){
        for(int i = 0; i < periods; i++){
            balance += interest * balance;
        }
    }
    public String getName(){
        return name;
    }
    public void setName(String inputName){
        name = inputName;
    }

}
