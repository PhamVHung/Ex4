package FileGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class UserGenerator {
    String[] FIRSTNAME = { "Liam", "Olivia", "Noah", "Emma", "Ava", "Charlotte", "Sophia", "Mia", "Jackson", "Aiden" };
    String[] LASTNAME = { "Smith", "Johnson", "Brown", "Taylor", "Anderson", "Harris", "Clark", "Lewis", "Walker", "Hall" };

    String[] ROLE = { "Admin", "Staff", "Student" };

    String[] USERNAME = { "cooluser123", "rainbow7", "soccerfanatic", "musiclover22", "adventureseeker", "techguru88",
            "bookworm99", "beachbum23", "sciencegeek42", "starwarsfanatic" };

    String[] PASSWORD = { "P@ss12", "abc123", "qwerty", "pass12", "hello1", "test01", "abcd12", "letmein", "123456", "password" };

    public void generateUsers(int count) {
        Random random = new Random();

        try (PrintWriter pw = new PrintWriter(new FileWriter("CourseRegistrationSystem\\src\\File\\users.csv"))) {
            pw.println("Role,Name,Username,Password"); // Header

            for (int i = 0; i < count; i++) {
                String role = ROLE[random.nextInt(ROLE.length)];
                String firstName = FIRSTNAME[random.nextInt(FIRSTNAME.length)];
                String lastName = LASTNAME[random.nextInt(LASTNAME.length)];
                String username = USERNAME[i];
                String password = PASSWORD[random.nextInt(PASSWORD.length)];

                pw.println(role + "," + lastName + " " + firstName + "," + username + "," + password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserGenerator generator = new UserGenerator();
        generator.generateUsers(10); // Generate 10 random users
    }
}
