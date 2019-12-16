package bank;

import java.util.Scanner;
import java.io.*;
import java.sql.*;

public class People {

    Scanner input = new Scanner(System.in);

    People() throws IOException {

    }

    //DB Configuration
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost/users";
    String USER = "root";
    String PASSWORD = "";
    Connection Cursor = null;
    Statement State = null;

    public void Transaction(String filename, String str, float i) throws SQLException {
        try {
            int AccNum = getAccNum(filename);
            filename = String.valueOf(AccNum);
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write(str + " " + i + "\n");
            fr.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void Transaction(int AccNum, String str, float i) throws SQLException {
        try {

            String filename = String.valueOf(AccNum);
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write(str + " " + i + "\n");
            fr.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public void AccDeleted(int AccNum) throws SQLException {
        try {

            String filename = String.valueOf(AccNum);
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write(  "Account Deleted \n");
            fr.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
     public void AccDeleted(String Email) throws SQLException {
        try {
            String filename=String.valueOf(getAccNum(Email));
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write(  "Account Deleted \n");
            fr.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void UpdateBal(String email, float NewBalance) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
        State = Cursor.createStatement();
        String query = "UPDATE acc SET `Balance` = '" + NewBalance + "' WHERE email = '" + email + "'";
        State.executeUpdate(query);
        if (Cursor != null) {

        }

    }

    public void UpdateBal(int AccNum, float NewBalance) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
        State = Cursor.createStatement();
        String query = "UPDATE acc SET `Balance` = '" + NewBalance + "' WHERE AccNum = '" + AccNum + "'";
        State.executeUpdate(query);
        if (Cursor != null) {
        }
    }

    //DB Close connection
    public void closeconnection() throws SQLException, Exception {
        State.close();
        Cursor.close();
    }

    public int getAccNum(String Email) throws SQLException {
        ResultSet result = GetData(Email);
        int AccNum = 0;
        while (result.next()) {
            AccNum = result.getInt("AccNum");
        }
        return AccNum;
    }

    public ResultSet GetData(String email_sel) {
        ResultSet result = null;
        try {
            Class.forName(DRIVER);
            Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
            if (Cursor != null) {
            }
            State = Cursor.createStatement();
            String sader = "SELECT * FROM acc WHERE email= '" + email_sel + "'";
            result = State.executeQuery(sader);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null) {
            return result;
        } else {
            return null;
        }

    }

    public ResultSet GetData(int AccountNum) {
        ResultSet result = null;
        try {
            Class.forName(DRIVER);
            Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
            if (Cursor != null) {
            }
            State = Cursor.createStatement();
            String sader = "SELECT * FROM acc WHERE AccNum= '" + AccountNum + "'";
            result = State.executeQuery(sader);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null) {
            return result;
        } else {
            return null;
        }

    }

    public boolean login(String Email, String Password) throws SQLException {
        String DatabaseEmail = "";
        String DatabasePassword = "";
        String DatabaseFirstName = "";
        ResultSet result = GetData(Email);
        while (result.next()) {
            DatabaseEmail = result.getString("email");
            DatabasePassword = result.getString("PASSWORD");
            DatabaseFirstName = result.getString("FirstName");
        }

        if (Email.equals(DatabaseEmail) && Password.equals(DatabasePassword)) {
            System.out.println("Hello, " + DatabaseFirstName);
            return true;
        } else {
            System.out.println("Incorrect Email or Password");
            //IMPORTANT: This flag is meant for the main as to provide a flag to be given and thus help you make the user
            return false;                                                                               // enter the email and password till he  gets it right.
        }
    }

    public String EmailExist(String email_sel) throws SQLException {
        ResultSet result = GetData(email_sel);
        String Email = "Not Found";
        while (result.next()) {
            Email = result.getString("email");
        }
        return Email;
    }

    public String EmailExist(int AccNum) throws SQLException {
        ResultSet result = GetData(AccNum);
        String Email = "Not Found";
        while (result.next()) {
            Email = result.getString("AccNum");
        }
        return Email;
    }

    public int getBalance(String Email) throws SQLException {
        ResultSet result = GetData(Email);
        int Balance = 0;
        while (result.next()) {
            Balance = result.getInt("Balance");
        }
        return Balance;
    }

    public int getBalance(int AccNumber) throws SQLException {
        ResultSet result = GetData(AccNumber);
        int Balance = 0;
        while (result.next()) {
            Balance = result.getInt("Balance");
        }
        return Balance;
    }
}
