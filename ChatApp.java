package chatapp.part.pkg1;


import java.util.Scanner;
import java.util.regex.Pattern;

class Login {
    // Where we keep the user's info after they register
    private String firstName;
    private String lastName;
    private String username;
    private String password; 
    private String cellNumber;
    private boolean registered = false;   // becomes true after successful sign-up

    // ---------- 1. check username ----------
    // Returns true if username has an underscore _ and is 5 characters or less
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // ---------- 2. check password strength ----------
    // Password must be at least 8 chars, have an uppercase, a digit, and a special character
    public boolean checkPasswordComplexity(String password) {
        boolean longEnough = password.length() >= 8;
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = !password.matches("[A-Za-z0-9]*");
        return longEnough && hasUpper && hasDigit && hasSpecial;
    }

    // ---------- 3. check South African cell number ----------
    // Must start with +27 and then exactly 9 digits.
    // I used a simple regex here – it's short and does the job.
    public boolean checkCellPhoneNumber(String cellNumber) {
        // pattern: +27 then nine digits, nothing else
        String regex = "\\+27[0-9]{9}$";
        return Pattern.matches(regex, cellNumber);
    }

    // ---------- 4. register the user ----------
    // Calls the three checks above. If any fails, returns the exact error message.
    // If all pass, stores the data and returns success message.
    public String registerUser(String firstName, String lastName, String username,
                               String password, String cellNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        // everything is good – save the details
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.registered = true;
        return "Registration successful! You are now good to go , Please login .";
    }

    // ---------- 5. check login credentials ----------
    // Returns true if username and password match the stored ones (and a user exists)
    public boolean loginUser(String username, String password) {
        if (!registered) return false;
        return this.username.equals(username) && this.password.equals(password);
    }

    // ---------- 6. return the login status message ----------
    // If loginUser returns true, prints welcome message with first and last name.
    // Otherwise prints the error message.
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // main menu to know if anyone has registered
    public boolean isRegistered() {
        return registered;
    }
}

// ============================================================
// The main class – this runs the menu and talks to the user
// ============================================================
public class ChatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();   // this object holds everything
        int choice;

        System.out.println("===============================================================================================");
        System.out.println(    " WELCOME TO THE CHAT APP , Please follow the instructions below to register and login ");
        System.out.println("==============================================================================================");

        // Loop the menu until the user picks exit
        do {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Register a new account");
            System.out.println("2. Login to your account");
            System.out.println("3. Exit");
            System.out.println("Your choice: ");

            // make sure the user types a number
            while (!scanner.hasNextInt()) {
                System.out.print("Please select the following 1, 2 or 3: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();  

            // ---------- REGISTER ----------
            if (choice == 1) {
                System.out.println("\n--- REGISTRATION ---");
                System.out.print(" Enter your First name: ");
                String fName = scanner.nextLine();
                System.out.print(" Enter your Last name: ");
                String lName = scanner.nextLine();
                System.out.print("Username (must contain _ and be <= 5 character): ");
                String user = scanner.nextLine();
                System.out.print("Password (8+ characters, one uppercase, one number, one special): ");
                String pass = scanner.nextLine();
                System.out.print("Cell number (SA format: +27 then 9 digits, e.g. +27831234567): ");
                String cell = scanner.nextLine();

                String result = loginSystem.registerUser(fName, lName, user, pass, cell);
                System.out.println("\n>> " + result);
                System.out.println("-----------------------------");
            }
            // ---------- LOGIN ----------
            else if (choice == 2) {
                System.out.println("\n--- LOGIN ---");
                // first check if there is any registered user at all
                if (!loginSystem.isRegistered()) {
                    System.out.println("Account does not exist . Please return to (option 1) and register first.");
                    System.out.println("-----------------------------");
                    continue;
                }
                System.out.print(" Please provide a Username: ");
                String loginUser = scanner.nextLine();
                System.out.print(" Please enter your Password: ");
                String loginPass = scanner.nextLine();

                String status = loginSystem.returnLoginStatus(loginUser, loginPass);
                System.out.println("\n>> " + status);
                System.out.println("-----------------------------");
            }
            // ---------- EXIT ----------
            else if (choice == 3) {
                System.out.println("\n Thank you for using the CHATAPP, have a great day! :)");
            }

            else {
                System.out.println("\n Incorrect input please pick 1, 2 or 3.");
            }

        } while (choice != 3);

        scanner.close();
    }
}
    
    

