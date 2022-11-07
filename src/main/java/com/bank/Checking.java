package com.bank;

public class Checking extends Account {

    final int FEE = 5;
    private int balance;


    public void compute(){

        for(int i = 0; i < getPeriods(); i++){
            setBalance((getInterest() * getBalance()) + getBalance() - FEE);
        }
    }
}
