 

import java.util.Scanner;

public class LoginSystem {

    public static boolean login() {

        Scanner sc = new Scanner(System.in);

        String user = "admin";
        String pass = "1234";

        int attempts = 3;

        while (attempts > 0) {

            System.out.print("Username: ");
            String u = sc.nextLine();

            System.out.print("Password: ");
            String p = sc.nextLine();

            if (u.equals(user) && p.equals(pass)) {
                System.out.println("Login successful.");
                return true;
            }

            attempts--;
            System.out.println("Wrong login. Attempts left: " + attempts);
        }

        System.out.println("System locked.");
        return false;
    }
}