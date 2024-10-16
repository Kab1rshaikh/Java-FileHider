package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    private static final int LOGIN_OPTION = 1;
    private static final int SIGNUP_OPTION = 2;
    private static final int EXIT_OPTION = 3;

    public void welcomeScreen() throws SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Welcome!");
            System.out.println("Press 1 to login");
            System.out.println("Press 2 to signup");
            System.out.println("Press 3 to exit");

            int choice = 0;
            try {
                choice = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                System.out.println("Error reading input, please try again.");
                continue; // Go back to the start of the loop
            }

            switch (choice) {
                case LOGIN_OPTION:
                    login();
                    break; // Added break
                case SIGNUP_OPTION:
                    signUp();
                    break; // Added break
                case EXIT_OPTION:
                    System.out.println("Exiting System");
                    isRunning = false;
                    break; // Added break
                default:
                    System.out.println("Invalid choice, please select again."); // Handle invalid choices
                    break;
            }
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email");
        String email = sc.next();

        try {
            if (UserDAO.isExists(email)) {
                String generatedOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, generatedOTP);
                System.out.println("Enter the OTP");
                String otp = sc.next(); // Changed to next() for proper input

                if (otp.equals(generatedOTP)) {
                    new UserView(email).home();

                } else {
                    System.out.println("Wrong OTP");
                }
            } else {
                System.out.println("User does not exist. Please sign up.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during login. Please try again.");
            e.printStackTrace(); // Optional: You may log this instead
        }
    }

    private void signUp() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        String name = sc.nextLine();
        System.out.println("Enter email");
        String email = sc.nextLine();

        // Add email format validation here if necessary

        String generatedOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, generatedOTP);
        System.out.println("Enter the OTP");
        String otp = sc.nextLine();

        if (otp.equals(generatedOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0:
                    System.out.println("User registered");
                    break; // Added break
                case 1:
                    System.out.println("User already exists");
                    break; // Added break
                default:
                    System.out.println("Unexpected response from server.");
            }
        } else {
            System.out.println("Wrong OTP");
        }
    }
}
