package bank;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Usr extends People {

    Usr() throws IOException {

    }

    public Usr(String Email, String Password) throws IOException, SQLException {
        this.Email = Email;
        this.Password = Password;
        if (!EmailExist(Email).equals("Not Found")) {
            if (login(Email, Password)) {
                setter(Email);
            }
        }

    }

    protected String FirstName, LastName, Email, Password;
    protected int Age, Accountnum;
    protected float Balance;

    private void setter(String Email) throws SQLException {
        ResultSet result = GetData(Email);

        while (result.next()) {
            this.Accountnum = result.getInt("AccNum");
            this.FirstName = result.getString("FirstName");
            this.LastName = result.getString("lastName");
            this.Age = result.getInt("Age");
            this.Balance = result.getFloat("Balance");

        }
    }
    Scanner input = new Scanner(System.in);

    public void PrintUserData(String Email) throws SQLException {
        setter(Email);
        System.out.println("Dear Customer, Your Information : ");
        System.out.println("Name : " + FirstName + " " + LastName);
        System.out.println("Your Email : " + Email);
        System.out.println("Your Balance : " + Balance);

    }

    public void DepositsMoney(String Email) throws ClassNotFoundException, SQLException {
        float value;
        System.out.print("Dear Customer, Enter the amount that you want to deposit: ");
        value = input.nextFloat();
       float balance =getBalance(Email)+value;
        UpdateBal(Email, balance);
        System.out.println(" Now, Your Balance become: " + (balance));
        Transaction(Email, "Deposited amount", value);
    }
 public void DepositsMoney(int AccNum) throws ClassNotFoundException, SQLException {
        float value;
        System.out.print("Dear Customer, Enter the amount that you want to deposit: ");
        value = input.nextFloat();
       float balance =getBalance(AccNum)+value;
        UpdateBal(AccNum, balance);
        System.out.println(" Now, Your Balance become: " + (balance));
        Transaction(AccNum, "Deposited amount", value);
    }
    public void WithdrawMoney() throws ClassNotFoundException, SQLException {
        Float value;
        System.out.print("Dear Customer, Enter the amount that you want to Withdraw: ");
        value = input.nextFloat();
        if (value <= Balance) {
            this.Balance = Balance - value;
            UpdateBal(Email, Balance);
            System.out.println("Withrew successfully!");
            Transaction(Email, "Withdrew amount", value);
        } else {
            System.out.println("You don't have that amount of money.\n Your Balance is : " + Balance);
        }
    }

    public void TransfarMoney(String Email) throws SQLException, ClassNotFoundException {

        System.out.print("Dear  Customer, Enter the amount that you want to Transfar: ");
        int value = input.nextInt();
        this.Balance = getBalance(Email);
        while (value > Balance) {
            System.out.println("Dear Customer , the balance isn't enough: \"" + Balance + "\".");
            value = input.nextInt();
        }

        System.out.println("Enter the Account  of recipient : ");
        String acco_of_recei = input.next();
        if (!EmailExist(acco_of_recei).equals("Not Found")) {
            float SenderBalance = Balance - value;
            Transaction(Email, "Transferred amount", (float) value);
            UpdateBal(Email, SenderBalance);
            System.out.println("Your balance updated successfully!");
            float ReseverBalance = getBalance(acco_of_recei) + value;
            UpdateBal(acco_of_recei, ReseverBalance);
            System.out.println("Recipient balance updated successfully!");
            Transaction(acco_of_recei, "received amount", (float) value);
        } else {
            System.out.println(EmailExist(acco_of_recei));
        }

    }

}
