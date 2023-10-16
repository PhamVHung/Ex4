package FileGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CourseGenerator {
    static String[] COURSES = { "Introduction to Computer Science", "Calculus I", "English Composition",
            "History of Art",
            "Physics for Engineers", "Introduction to Psychology", "Microeconomics", "Organic Chemistry",
            "Principles of Marketing", "World History" };
    static String[] COURSEID = { "CS101", "MATH101", "ENGL101", "ART200", "PHYS201", "PSYC101", "ECON101", "CHEM201",
            "MKTG101", "HIST200" };

    static int[] MAX = { 1, 2, 4, 8, 16, 32, 8, 4, 2, 1 };

    static int[] SECTIONS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    static String[] LOCATION = { "RM101", "LH-A", "CR302", "LB201", "AU-B", "ST103", "SR202", "CR-C", "WS301", "GYM" };

    public static void main(String[] args) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("CourseRegistrationSystem\\src\\File\\courses.csv"))) {
            pw.println("Course,CourseID,CurrentStudents,MaxStudents,Instructor,Section,Location,StudentList"); // Header

            for (int i = 0; i < 10; i++) {
                pw.println(COURSES[i] + "," + COURSEID[i] + "," + 0 + "," + MAX[i] + "," + "" + "," + SECTIONS[i] + ","
                        + LOCATION[i] + ",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
