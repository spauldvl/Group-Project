package com.bank;

import java.io.Serializable;

public class Checking extends Account {

    final int FEE = 5;


    public void compute(){

        for(int i = 0; i < getPeriods(); i++){
            setBalance((getInterest() * getBalance()) + getBalance() - FEE);
        }
    }
}
