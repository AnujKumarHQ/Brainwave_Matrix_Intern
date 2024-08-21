import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.ArrayList;

class Account {
    private String accountHolder;
    private String accountNumber;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(String accountHolder, String accountNumber, String pin, double initialBalance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Initial balance: $" + initialBalance);
    }

    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            return true;
        } else {
            return false;
        }
    }

    public String getTransactionHistory() {
        StringBuilder history = new StringBuilder();
        for (String transaction : transactionHistory) {
            history.append(transaction).append("\n");
        }
        return history.toString();
    }

    public String getAccountInfo() {
        return "Account Holder: " + accountHolder + "\nAccount Number: " + accountNumber + "\nCurrent Balance: $" + balance;
    }
}

public class AdvancedATM_GUI {
    private JFrame frame;
    private JPanel panelLogin, panelMain;
    private CardLayout cardLayout;
    private HashMap<String, Account> accounts;
    private Account currentAccount;

    public AdvancedATM_GUI() {
        accounts = new HashMap<>();
        accounts.put("123456789", new Account("Anuj Kumar", "123456789", "1234", 100000.00));
        accounts.put("987654321", new Account("Tommy", "987654321", "4321", 2000.00));
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Advanced ATM Interface");
        frame.setBounds(100, 100, 400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        frame.getContentPane().setLayout(cardLayout);

        // Login Panel
        panelLogin = new JPanel();
        frame.getContentPane().add(panelLogin, "Login");
        panelLogin.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel lblAccountNumber = new JLabel("Account Number:");
        JTextField txtAccountNumber = new JTextField();
        JLabel lblPin = new JLabel("PIN:");
        JPasswordField txtPin = new JPasswordField();
        JButton btnLogin = new JButton("Login");
        JLabel lblLoginMessage = new JLabel("", JLabel.CENTER);

        panelLogin.add(lblAccountNumber);
        panelLogin.add(txtAccountNumber);
        panelLogin.add(lblPin);
        panelLogin.add(txtPin);
        panelLogin.add(btnLogin);
        panelLogin.add(lblLoginMessage);

        // Main Panel
        panelMain = new JPanel();
        frame.getContentPane().add(panelMain, "Main");
        panelMain.setLayout(new GridLayout(0, 1, 10, 10));

        JButton btnBalance = new JButton("View Balance");
        JButton btnDeposit = new JButton("Deposit Money");
        JButton btnWithdraw = new JButton("Withdraw Money");
        JButton btnTransactionHistory = new JButton("View Transaction History");
        JButton btnAccountInfo = new JButton("View Account Info");
        JButton btnLogout = new JButton("Logout");

        panelMain.add(btnBalance);
        panelMain.add(btnDeposit);
        panelMain.add(btnWithdraw);
        panelMain.add(btnTransactionHistory);
        panelMain.add(btnAccountInfo);
        panelMain.add(btnLogout);

        // Action Listeners
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumber = txtAccountNumber.getText();
                String pin = new String(txtPin.getPassword());

                if (accounts.containsKey(accountNumber) && accounts.get(accountNumber).verifyPin(pin)) {
                    currentAccount = accounts.get(accountNumber);
                    lblLoginMessage.setText("");
                    cardLayout.show(frame.getContentPane(), "Main");
                } else {
                    lblLoginMessage.setText("Invalid account number or PIN.");
                }
            }
        });

        btnBalance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Current Balance: $" + currentAccount.getBalance());
            }
        });

        btnDeposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter deposit amount:");
                try {
                    double amount = Double.parseDouble(input);
                    currentAccount.deposit(amount);
                    JOptionPane.showMessageDialog(frame, "Deposited: $" + amount);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount entered.");
                }
            }
        });

        btnWithdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter withdrawal amount:");
                try {
                    double amount = Double.parseDouble(input);
                    if (currentAccount.withdraw(amount)) {
                        JOptionPane.showMessageDialog(frame, "Withdrew: $" + amount);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Insufficient balance or invalid amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount entered.");
                }
            }
        });

        btnTransactionHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, currentAccount.getTransactionHistory());
            }
        });

        btnAccountInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, currentAccount.getAccountInfo());
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentAccount = null;
                txtAccountNumber.setText("");
                txtPin.setText("");
                cardLayout.show(frame.getContentPane(), "Login");
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdvancedATM_GUI window = new AdvancedATM_GUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
