package bank;

import java.io.IOException;
import java.sql.*;

public class ATM extends Usr {

    public ATM() throws IOException, SQLException {
        SetAtm(Email);
    }

    public ATM(int AccNumber, int AccPin) throws IOException, SQLException {
        this.AccNumber = AccNumber;
        this.AccPin = AccPin;
    }

    int AccNumber, AccPin;

    public final void SetAtm(String Email) throws SQLException {
        ResultSet result = GetData(Email);
        while (result.next()) {
            this.AccNumber = result.getInt("AccNum");
            this.AccPin = result.getInt("pinCode");
        }

    }

    public boolean AtmLogin(int Account, int Accpin) throws SQLException {
        ResultSet result = GetData(Account);
        int AccountNumber = 0;
        int AccountPin = 0;
        String Name = "";
        while (result.next()) {
            Name = result.getString("FirstName") + " " + result.getString("lastName");
            AccountNumber = result.getInt("AccNum");
            AccountPin = result.getInt("pinCode");
        }

        if (Account == AccountNumber && AccountPin == Accpin) {
            System.out.println("Welcome " + Name);
            return true;
        } else {
            return false;
        }

    }

    public void TransferMoneyATM(int AccNumber) throws SQLException, ClassNotFoundException {
        System.out.print("Dear  Customer, Enter the value this you wanna to Transfar : ");
        int value = input.nextInt();
        float Balance = getBalance(AccNumber);
        while (value > Balance) {
            System.out.println("Dear Customer , Please Enter A money You have you are poor : \"" + Balance + "\".");
            value = input.nextInt();
        }

        System.out.println("Enter the Account  of receiver : ");
        int acco_of_recei = input.nextInt();
        if (!EmailExist(acco_of_recei).equals("Not Found")) {
            float SenderBalance = Balance - value;
            Transaction(Email, "Transffered amount", SenderBalance);
            UpdateBal(AccNumber, SenderBalance);
            float ReseverBalance = getBalance(acco_of_recei) + value;
            UpdateBal(acco_of_recei, ReseverBalance);
        } else {
            System.out.println(EmailExist(acco_of_recei));
        }

    }

    public void AccInfo() throws SQLException {
        ResultSet result = GetData(AccNumber);
        String Name = "", email = "";
        int AccountNumber = AccNumber;
        float Balance1 = 0;
        if (!EmailExist(AccNumber).equals("Not Found")) {
            while (result.next()) {
                Name = result.getString("FirstName") + " " + result.getString("lastName");
                email = result.getString("email");
                AccountNumber = result.getInt("AccNum");
                Balance1 = result.getFloat("Balance");
            }

            System.out.println(" Welcome " + Name + "\n Your Account Number is : " + AccountNumber + "\n You Email is : "
                    + email + "\n Your Balance is : " + Balance1);
        }

    }

    public void changePin(int AccNum) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
        State = Cursor.createStatement();
        System.out.println("Enter the new Password : ");
        int pin = input.nextInt();

        while (true) {
            if (String.valueOf(pin).length() == 6) {
                break;
            } else {
                System.out.println("Please enter a password that is not less than 8 characters and not more than 32 characters");
                pin = input.nextInt();
            }
        }
        String query = "UPDATE acc SET `pinCode` = '" + pin + "' WHERE AccNum = '" + AccNum + "'";
        State.executeUpdate(query);
        if (Cursor != null) {
            System.out.println("Pin Code Changed successfully!");
        }

    }

    public void WithdrawATM(int AccNum) throws ClassNotFoundException, SQLException {
        Float value;
        System.out.print("Dear Customer, Enter the amount that you want to Withdraw: ");
        value = input.nextFloat();
        float balance = getBalance(AccNum);
        if (value <= balance) {
            balance = balance - value;
            UpdateBal(AccNum, balance);
            System.out.println("Withrew successfully!");
            Transaction(AccNum, "Withdrew amount", value);
        } else {
            System.out.println("You don't have that amount of money.\n Your Balance is : " + balance);
        }
    }

    public int deactivation(int Acc) throws SQLException {
        System.out.println("Are Sure You Want to delete your Account\n1 = yes\n2 = No");
        switch (input.nextInt()) {
            case 1:
                Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
                State = Cursor.createStatement();
                String query = "DELETE FROM acc WHERE AccNum='" + Acc + "'";
                State.executeUpdate(query);
                if (Cursor != null) {
                    AccDeleted(Acc);
                    System.out.println("Your records have been deleted from our database");
                }
                return 1;

            case 2:
                System.out.println("Great Choice");
                return 0;
            default:
                return 0;

        }
    }

}
