package bank;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);

    public static void admin() throws IOException, SQLException, ClassNotFoundException {
        String email, password;
        System.out.println("Enter the Email");
        email = scan.next();
        System.out.println("Enter the Password");
        password = scan.next();
        while (true) {
            if (email.equals("ro") && password.equals("21")) {
                inAdmin();
                break;
            } else {
                System.out.println("invalid email or password\n1 = Try again!\n2 = Home\n3 = Exit\nEnter Your Choice : ");
                int Flag = scan.nextInt();
                switch (Flag) {
                    case 1:
                        System.out.println("Enter the Email");
                        email = scan.next();
                        System.out.println("Enter the Password");
                        password = scan.next();
                        break;
                    case 2:
                        mainMenu();
                    case 3: 
                        System.exit(0);
                    default:
                        System.out.println("Invalid Choice ");
                        break;
                }
            }

        }
    }


public static void inAdmin() throws IOException, SQLException, ClassNotFoundException {
        Admin ad = new Admin();
        System.out.println("1 = Add New Account \n2 = Deactive Account \n3 = Transaction History\n"
                + "4 = Change Account Password \n5 = Logout \n6 = Exit\nEnter Your Choice : ");
        int inAdminChoice = scan.nextInt();

        switch (inAdminChoice) {
            case 1:
                ad.ActivationForUser();
                inAdmin();
                break;
            case 2:
                ad.deactivation();
                inAdmin();
                break;
            case 3:
                transactions(ad);
                break;
            case 4:
                System.out.println("Enter The Email that you want to change its password");
                String Email = scan.next();
                while (!ad.emailvalidator(Email)) {
                    System.out.println("Invalid ,try Again");
                    Email = scan.next();
                }
                ad.changePassword(Email);
                inAdmin();
                break;
            case 5:
                mainMenu();
                break;
            case 6:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Choice ");
                inAdmin();
        }
    }

    public static void transactions(Admin ad) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Search By\n1 = Email \n2 = Acc Number\n3 = Back \n4 = Logout\n5 = Exit \nEnter Your Choice : ");
        switch (scan.nextInt()) {
            case 1:
                System.out.println("Enter The Email : ");
                ad.transactionHistory(scan.next());
                transactions(ad);
                break;
            case 2:
                System.out.println("Enter The Acc Number : ");
                ad.transactionHistory(scan.nextInt());
                transactions(ad);
                break;
            case 3:
                inAdmin();
                break;
            case 4:
                mainMenu();
                break;
            case 5:
                System.exit(0);

            default:
                System.out.println("Invaled Choice");
                transactions(ad);
        }

    }

    public static void user() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Enter Your Email : ");
        String Email = scan.next();
        System.out.println("Enter Your Password : ");
        String Password = scan.next();
        Usr user = new Usr();
        while (!user.login(Email, Password)) {
            System.out.println("\n1 = Try again!\n2 = Home\n3 = Exit\nEnter Your Choice : ");
                int Flag = scan.nextInt();
                switch (Flag) {
                    case 1:
                        System.out.println("Enter Your Email");
                        Email = scan.next();
                        System.out.println("Enter Your Password");
                       Password = scan.next();
                        break;
                    case 2:
                        mainMenu();
                    case 3: 
                        System.exit(0);
                    default:
                        System.out.println("Invalid Choice ");
                        break;
                }
            
        }
        inUser(Email, Password, user);
    }

    public static void inUser(String email, String password, Usr user) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("1 = Deposite \n2 = Transfere Money \n3 = Withdrow \n4 = Account Info \n5 = Logout \n6 = Exit");
        switch (scan.nextInt()) {
            case 1:
                user.DepositsMoney(email);
                inUser(email, password, user);
                break;
            case 2:
                user.TransfarMoney(email);
                inUser(email, password, user);
                break;
            case 3:
                user.WithdrawMoney();
                inUser(email, password, user);
                break;
            case 4:
                user.PrintUserData(email);
                inUser(email, password, user);
                break;
            case 5:
                System.out.println("Logged Out");
                mainMenu();
                break;
            default:
                System.out.println("Invalid Choice");
                inUser(email, password, user);

        }
    }

    public static void ATM() throws IOException, SQLException, ClassNotFoundException {
        ATM a = new ATM();
        System.out.println("Enter Your Account number:");
        int ACC = scan.nextInt();
        System.out.println("Enter Your Pin number: ");
        int Pin = scan.nextInt();
        while (!a.AtmLogin(ACC, Pin)) {
            System.out.println("Invalid Account Number OR pin Code \n1 = Try Again\n2 = Home\n3 = Exit\nEnter Your Choice");
            int Flag = scan.nextInt();
            switch (Flag) {
                case 1:
                    System.out.println("Enter Your Account Number");
                    ACC = scan.nextInt();
                    System.out.println("Enter Your pin Code");
                    Pin = scan.nextInt();
                    break;
                case 2:
                    mainMenu();
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice ,try Again");
                    break;
            }
        }
        inATM(ACC, Pin);

    }

    public static void inATM(int ACC, int pin) throws IOException, SQLException, ClassNotFoundException {
        ATM atm = new ATM(ACC, pin);
        System.out.println("1 = Transfer Money\n2 = Deposite\n3 = Withdrow\n"
                + "4 = Account Info\n5 = Change Pin\n6 = Deactavite My Account\n7 = Logout\n8 = Exit\nEnter your Choice: ");
        switch (scan.nextInt()) {
            case 1:
                atm.TransferMoneyATM(ACC);
                inATM(ACC, pin);
                break;
            case 2:
                atm.DepositsMoney(ACC);
                inATM(ACC, pin);
                break;
            case 3:
                atm.WithdrawATM(ACC);
                inATM(ACC, pin);
                break;
            case 4:
                atm.AccInfo();
                inATM(ACC, pin);
                break;
            case 5:
                atm.changePin(ACC);
                inATM(ACC, pin);
                break;
            case 6:
                
                if(atm.deactivation(ACC)==0){
                    inATM(ACC, pin);
                }
                else{
                ATM();
                }
                break;
            case 7:
                mainMenu();
            case 8:
                System.exit(0);
            default:
                System.out.println("Invalid Choice ,Try Again");
                inATM(ACC, pin);

        }
    }

    public static void mainMenu() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Welcome to our bank");
        System.out.println("1 = Admin\n" + "2 = User\n" + "3 = ATM\n" + "4 = Exit \nEnter your choice:");
        int choice1 = scan.nextInt();
        switch (choice1) {
            case 1:
                admin();
                break;
            case 2:
                user();
                break;
            case 3:
                ATM();
                break;
            case 4:
                System.exit(0);
            default:
                System.out.println("Invalid choice!");
                mainMenu();
        }
    }

    public static void main(String[] args) throws IOException, Exception {
        mainMenu();

    }
}
