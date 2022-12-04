package com.bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BankerForm {
    private JPanel panelMain;
    private JPanel jplButtonBar;
    private JLabel jplTopLabel;
    private JPanel jPanelMiddle;
    private JPanel jPanelInnerCenter;
    private JPanel jPanelInnerNorth;
    private JButton createButton;
    private JButton computeButton;
    private JList listAccounts;
    private JLabel accountTypeLbl;
    private JLabel balancelbl;
    private JLabel interestlbl;
    private JLabel periodslbl;
    private JTextField txtBalance;
    private JComboBox accountTypecmbx;
    private JTextField txtInterest;
    private JTextField txtPeriods;
    private JLabel namelbl;
    private JTextField txtName;
    private JTextField txtTerm;
    private JLabel termLable;
    private JButton openFileButton;
    private JLabel lblAccountNum;
    private JTextField txtAccountNum;
    private JLabel withdrawButton;
    private JTextField txtWithdraw;
    private JButton btnWithdraw;
    private JCheckBox termCheckBox;
    private HashMap<Integer, Account> allAccounts = new HashMap();
    private Account account;
    private static final Logger logger = LogManager.getLogger("accounts");
    private Collection<Account> values = allAccounts.values();
    private Vector<Account>  array = new Vector<>(values);
    private static Queue<Account> allAccountsQueue = new PriorityQueue<>();
    private Vector<Account> accountVector = new Vector<>(allAccounts.values());

    public BankerForm() {

        initializeAccountTypeComboBox();



        listAccounts.setListData(new Vector<>(allAccounts.values()));
        // jScrollPanel.setViewportView(listAccounts)
        AccountReader.readAccounts();

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strBalance = txtBalance.getText();
                double balance = Double.parseDouble(strBalance);

                String strInterest = txtInterest.getText();
                int interest = Integer.parseInt(strInterest);

                String strPeriods = txtPeriods.getText();
                int periods = Integer.parseInt(strPeriods);

                String strAccountNum = txtAccountNum.getText();
                int accountNum = Integer.parseInt(strAccountNum);

                String name = txtName.getText();

                String type = accountTypecmbx.getSelectedItem().toString();
                Account account = Banker.getInstance().createAccount(type);

                // line 48

                account.setBalance(balance);
                account.setInterest(interest);
                account.setPeriods(periods);
                account.setName(name);
                account.setAccountNumber(accountNum);

                if (accountTypecmbx.getSelectedItem().toString().equals(Banker.CERTIFICATE_OF_DEPOSIT)){
                    if (account instanceof CertificateOfDeposit){
                        CertificateOfDeposit certificateOfDeposit = (CertificateOfDeposit) account;
                        String strTerm = txtTerm.getText();
                        int term = Integer.parseInt(strTerm);
                        certificateOfDeposit.setMaturity(term);
                    }
                }


                allAccounts.put(accountNum, account);
                listAccounts.setListData(new Vector<>(allAccounts.values()));
                System.out.println("create " + allAccounts.values());

            }
        });
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                allAccounts.values().stream().forEach(account -> {
                    account.compute();
                });
                listAccounts.updateUI();

            }
        });
        accountTypecmbx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accountTypecmbx.getSelectedItem().toString().equals(Banker.CERTIFICATE_OF_DEPOSIT)){
                    txtTerm.setEnabled(true);
                } else {
                    txtTerm.setEnabled(false);
                }

            }
        });
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                    try  {
                        Reader reader = Files.newBufferedReader(Paths.get("accounts.json"));
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(Account.class, new AccountSerializer());
                        Gson gson = gsonBuilder.create();
                        Vector<Account> inAccounts = gson.fromJson(reader, new TypeToken<Vector<Account>>(){}.getType());
                        for (int i = 0 ; i < inAccounts.size() ; i++){
                            inAccounts.get(i).setAccountNumber(i + 1);
                            //allAccounts.put(i,inAccounts.get(i));
                            accountVector.add(inAccounts.get(i));

                        }
                        sort();
                        insertToQueue();

                        listAccounts.setListData(new Vector<>(allAccounts.values()));
                        reader.close();


                        //System.out.println(listAccounts.get);
                        //System.out.println(allAccounts);
                        System.out.println(new Vector<>(allAccounts.values()));
                    } catch (Exception ex) {
                        logger.error(ex);
                        JOptionPane.showMessageDialog(null, "Unable to open file");
                    }

                }

        });
        btnWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strWithdraw = txtWithdraw.getText();
                double withdraw = Double.parseDouble(strWithdraw);
                if (fetchNextQualifiedAccount() == null){
                    insertToQueue();
                }
                fetchNextQualifiedAccount().withdraw(withdraw);
                try {
                    removeAccount(fetchNextQualifiedAccount());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                listAccounts.setListData(new Vector<>(allAccounts.values()));


            }
        });
    }
    private void initializeAccountTypeComboBox(){
        DefaultComboBoxModel<String> accountTypesModel = new DefaultComboBoxModel<>();
        accountTypesModel.addElement(Banker.CHECKING);
        accountTypesModel.addElement(Banker.SAVINGS);
        accountTypesModel.addElement(Banker.CERTIFICATE_OF_DEPOSIT);
        accountTypecmbx.setModel(accountTypesModel);

    }
    private void insertToQueue(){
        int i = 1;
        for (Account account : accountVector){
            allAccountsQueue.offer(account);
            if (account.getAccountNumber() != -1){
                allAccounts.put(account.getAccountNumber(),account);
            }else {
                allAccounts.put(i, account);
            }
            i++;
        }
    }
    private void sort(){
        Collections.sort(accountVector);

        //accountVector.sort(Comparator.comparingDouble(Account::getInterest));
    }
    public static Account fetchNextQualifiedAccount() {
        return allAccountsQueue.peek();
    }

    public static void removeAccount(Account inAccount) throws Exception {
        Account nextAccount = allAccountsQueue.poll();
        if (!nextAccount.equals(inAccount)) {
            throw new Exception ("Account is not in queue");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankerForm");
        frame.setContentPane(new BankerForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
