package com.bank;

public class Checking extends Account {

    final int FEE = 5;

    public void compute(){
        for(int i = 0; i < getPeriods(); i++){
            int balance = getBalance();
            balance += getInterest() * balance;
            setBalance(balance - FEE);
        }
    }
}
