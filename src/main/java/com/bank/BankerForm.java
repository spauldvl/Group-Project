package com.bank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
    private JCheckBox termCheckBox;
    private Vector<Account> allAccounts = new Vector<>();

    public BankerForm() {

        initializeAccountTypeComboBox();

        listAccounts.setListData(allAccounts);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strBalance = txtBalance.getText();
                double balance = Double.parseDouble(strBalance);

                String strInterest = txtInterest.getText();
                int interest = Integer.parseInt(strInterest);

                String strPeriods = txtPeriods.getText();
                int periods = Integer.parseInt(strPeriods);

                String name = txtName.getText();



                String type = accountTypecmbx.getSelectedItem().toString();
                Account account = Banker.getInstance().createAccount(type);

                // line 48

                account.setBalance(balance);
                account.setInterest(interest);
                account.setPeriods(periods);
                account.setName(name);

                if (accountTypecmbx.getSelectedItem().toString().equals(Banker.CERTIFICATE_OF_DEPOSIT)){
                    if (account instanceof CertificateOfDeposit){
                        CertificateOfDeposit certificateOfDeposit = (CertificateOfDeposit) account;
                        String strTerm = txtTerm.getText();
                        int term = Integer.parseInt(strTerm);
                        certificateOfDeposit.setMaturity(term);
                    }
                }


                allAccounts.add(account);
                listAccounts.updateUI();

            }
        });
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                allAccounts.stream().forEach(account -> {
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
    }
    private void initializeAccountTypeComboBox(){
        DefaultComboBoxModel<String> accountTypesModel = new DefaultComboBoxModel<>();
        accountTypesModel.addElement(Banker.CHECKING);
        accountTypesModel.addElement(Banker.SAVINGS);
        accountTypesModel.addElement(Banker.CERTIFICATE_OF_DEPOSIT);
        accountTypecmbx.setModel(accountTypesModel);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankerForm");
        frame.setContentPane(new BankerForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
