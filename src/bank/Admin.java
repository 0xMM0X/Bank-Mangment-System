package bank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.regex.Pattern;

public class Admin extends People {

    Admin() throws IOException {

    }

    //DB search
    public void search() throws SQLException {
        System.out.print("Enter the email:");
        String email_sel = input.next();
        ResultSet result = GetData(email_sel);
        while (result.next()) {
            System.out.println(" Name : " + result.getString("FirstName") + " "
                    + result.getString("lastName") + "\n"
                    + " Email : " + result.getString("email") + "\n"
                    + " Age : " + result.getInt("Age") + "\n"
                    + " Balance : " + result.getInt("balance"));
        }
    }

    public boolean emailvalidator(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public void ActivationForUser() throws ClassNotFoundException, SQLException {

        //Email input
        boolean Password_Checker_Flag = true;
        boolean Age_Checker_Flag = true;
        String Entered_Email = null;
        String First_Name;
        String Last_Name;
        String Password = null;
        float Balance;
        int Age = 0;
        System.out.println("Please enter your email");
        Entered_Email = input.next();
        while (true) {
            if (emailvalidator(Entered_Email)) {
                break;
            } else {
                System.out.println("Please make sure you entered a valid email , Try Again:");
            }
            Entered_Email = input.next();
        }

        // Name input
        System.out.println("Please enter your first name");
        First_Name = input.next();
        System.out.println("Please enter your second name");
        Last_Name = input.next();

        System.out.println("Please enter your age");
        Age = input.nextInt();
        while (Age_Checker_Flag) {

            if (Age < 21) {
                System.out.println("Please make sure you have entered the correct age ");

            } else if (Age >= 21) {

                Age_Checker_Flag = false;
                break;
            }
            Age = input.nextInt();

        }

        System.out.println("Please enter your Password");
        // password input
        Password = input.next();

        while (Password_Checker_Flag) {
            if (Password.length() < 8 || Password.length() > 32) {
                System.out.println("Please enter a password that is not less than 8 characters and not more than 32 characters");
                Password = input.next();
            } else {
                break;
            }
        }
        System.out.println("Please enter your Pin Code");
        int pinCode = input.nextInt();
        while (true) {
            if (String.valueOf(pinCode).length() == 6) {
                break;
            } else {
                System.out.println("Please enter a Pin Code that is 6 numbers");
                pinCode = input.nextInt();
            }
        }
        System.out.println("Please enter the balance");
        Balance = input.nextFloat();
        Class.forName(DRIVER);
        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);

        //This Alert To Verified If The conection Is succeed or Not
        State = Cursor.createStatement();
        String query = "INSERT INTO `acc` (FirstName,lastname,Age,Balance,email,PASSWORD,pinCode) VALUES ('" + First_Name + "','" + Last_Name + "','" + Age + "','" + Balance + "','" + Entered_Email + "','" + Password + "','" + pinCode + "') ";
        State.executeUpdate(query);
        if (Cursor != null) {
            System.out.println("Registerd Succefully!");
        }

    }

    public void deactivation() throws SQLException {
        String GivenEmail;
        System.out.println("Please enter your email for deactivation");
        GivenEmail = input.next();
        boolean CheckerForCorrectInfo;
        if (EmailExist(GivenEmail).equals("Not Found")) {
            CheckerForCorrectInfo = false;
        } else {
            CheckerForCorrectInfo = true;
        }
        while (!CheckerForCorrectInfo) {
            System.out.println("Please make sure that you re-enter the info correctly and enter email and password respectively");
            GivenEmail = input.next();
            if (EmailExist(GivenEmail).equals("Not Found")) {
                CheckerForCorrectInfo = false;
            } else {
                CheckerForCorrectInfo = true;
            }
        }

        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
        State = Cursor.createStatement();
        String query = "DELETE FROM acc WHERE email='" + GivenEmail + "'";
        State.executeUpdate(query);
        if (Cursor != null) {
            AccDeleted(GivenEmail);
            System.out.println("Your records have been deleted from our database");
        }

    }

    public void transactionHistory(String filename) throws SQLException {
        String line = null;
        try {
            int AccNum = getAccNum(filename);
            filename = String.valueOf(AccNum);
            FileReader file = new FileReader("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            BufferedReader br = new BufferedReader(file);
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Unable to open file " + filename);
        }

    }

    public void transactionHistory(int AccNum) throws SQLException {
        String line = null;
        try {

            String filename = String.valueOf(AccNum);
            FileReader file = new FileReader("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            BufferedReader br = new BufferedReader(file);
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Unable to find this : " + AccNum);
        }

    }
    public void changePassword(String Email) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
        State = Cursor.createStatement();
        System.out.println("Enter the new Password : ");
        String  Password = input.next();

        while (true) {
            if (Password.length() < 8 || Password.length() > 32) {
                System.out.println("Please enter a password that is not less than 8 characters and not more than 32 characters");
                Password = input.next();
            } else {
                break;
            }
        }
        String query = "UPDATE acc SET `Password` = '" +Password +"' WHERE email = '" + Email + "'";
        State.executeUpdate(query);
        if (Cursor != null) {
            System.out.println("Password Changed successfully!");
        }

    }

}
