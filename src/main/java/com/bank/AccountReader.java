package com.bank;

import netscape.javascript.JSObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class AccountReader {

    private static Queue<Account> allAccountsQueue = new PriorityQueue<>();
    private static ArrayList<Account> allAccounts = new ArrayList<>();

    public static void readAccounts(){
        Path accountFilePath = Paths.get("accounts.json");
        try{
            List<String> accountLines = Files.readAllLines(accountFilePath);
            for (String accountDetail: accountLines)
            {
                System.out.println(accountDetail);
                String[] accountArray = accountDetail.split(", ");
                if (accountArray.length <= 5)
                {
                    System.out.println("here");
                    String accountType = accountArray[0];
                    System.out.println(accountType);
                    if (accountType.equals("CertificateOfDeposit"))
                    {
                        String strAccountTerm = accountArray[1];
                        int accountTerm = Integer.parseInt(strAccountTerm);

                        String strAccountRate = accountArray[2];
                        int accountRate = Integer.parseInt(strAccountRate);

                        String strAccountBalance = accountArray[3];
                        double accountBalance = Double.parseDouble(strAccountBalance);

                        String strAccountPeriods = accountArray[4];
                        int accountPeriods = Integer.parseInt(strAccountPeriods);

                        CertificateOfDeposit certificateOfDeposit = new CertificateOfDeposit();
                        certificateOfDeposit.setMaturity(accountTerm);
                        certificateOfDeposit.setBalance(accountBalance);
                        certificateOfDeposit.setInterest(accountRate);
                        certificateOfDeposit.setPeriods(accountPeriods);

                        allAccounts.add(certificateOfDeposit);
                    }
                    else if (accountType.equals("Savings"))
                    {

                        String strAccountRate = accountArray[2];
                        int accountRate = Integer.parseInt(strAccountRate);

                        String strAccountBalance = accountArray[3];
                        double accountBalance = Double.parseDouble(strAccountBalance);

                        String strAccountPeriods = accountArray[4];
                        int accountPeriods = Integer.parseInt(strAccountPeriods);

                        Savings savings = new Savings();
                        savings.setBalance(accountBalance);
                        savings.setInterest(accountRate);
                        savings.setPeriods(accountPeriods);

                        allAccounts.add(savings);

                    }else if (accountType.equals("Checking")){
                        String strAccountRate = accountArray[2];
                        int accountRate = Integer.parseInt(strAccountRate);

                        String strAccountBalance = accountArray[3];
                        double accountBalance = Double.parseDouble(strAccountBalance);

                        String strAccountPeriods = accountArray[4];
                        int accountPeriods = Integer.parseInt(strAccountPeriods);

                        Checking checking = new Checking();
                        checking.setBalance(accountBalance);
                        checking.setInterest(accountRate);
                        checking.setPeriods(accountPeriods);

                        allAccounts.add(checking);
                    }
                    sort();
                    insertToQueue();
                    for (Account account : allAccounts){
                        System.out.println(account);
                    }

                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }
    private static void insertToQueue(){
        for (Account account : allAccounts){
            allAccountsQueue.offer(account);
        }
    }
    private static void sort(){
        allAccounts.sort(Comparator.comparingDouble(Account::getInterest));
    }
    public static Account fetchNextQualifiedAccount() {
        return allAccountsQueue.peek();
    }

    public static void removeBuyer(Account inAccount) throws Exception {
        Account nextAccount = allAccountsQueue.poll();
        if (!nextAccount.equals(inAccount)) {
            throw new Exception ("Account is not in queue");
        }
    }
}