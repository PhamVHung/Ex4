package User;

import java.util.Scanner;

public class User {

    private static Scanner sc = new Scanner(System.in);
    private String firstName, lastName;
    private String username, password;
    private String fullName;

    public User(String lastName, String firstName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.fullName = lastName + " " + firstName;
    }

    public User(String lastName, String firstName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = lastName + " " + firstName;
    }

    public User() {
    }

    public static User input() {
        System.out.print("Enter your first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        return new User(lastName, firstName, username, password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        if (this instanceof Admin)
            return "Admin";
        if (this instanceof Student)
            return "Student";
        if (this instanceof Staff)
            return "Staff";
        return "Unknown User";
    }

    public String toCSV() {
        return lastName + " " + firstName + "," + username + "," + password;
    }

    public String toNameCSV() {
        return lastName + " " + firstName;
    }

    @Override
    public String toString() {
        return "Name: " + fullName;
    }
}
