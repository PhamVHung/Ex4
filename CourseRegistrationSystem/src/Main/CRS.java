package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import CourseData.Course;
import User.Admin;
import User.Staff;
import User.Student;
import User.User;

public class CRS {

    public static boolean courseFirstLine = true;
    public static boolean userFirstLine = true;
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<User> userList = new ArrayList<>();
    public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<Course> fullCourseList = new ArrayList<>();

    public static String COURSE_DATA_CSV = "CourseRegistrationSystem\\src\\File\\courses.csv";
    public static String USER_DATA_CSV = "CourseRegistrationSystem\\src\\File\\users.csv";
    public static String FULL_COURSE_CSV = "CourseRegistrationSystem\\src\\File\\fullCourses.csv";

    public static ArrayList<User> getUserList() {
        return userList;

    }

    public static ArrayList<Course> getCourseList() {
        return courseList;
    }

    public static ArrayList<Course> getFullCourseList() {
        return fullCourseList;
    }

    public static Student findStudentByUsername(String studentUsername) {
        for (User user : userList) {
            if (user.getUsername().toLowerCase().equals(studentUsername.toLowerCase()) && user instanceof Student) {
                return (Student) user;
            }
        }
        return null;
    }

    public static ArrayList<Student> findStudentByName(String lastName, String firstName) {
        ArrayList<Student> matchingStudents = new ArrayList<>();
        for (User user : userList) {
            if (user.getLastName().toLowerCase().equals(lastName.toLowerCase())
                    && user.getFirstName().toLowerCase().equals(firstName.toLowerCase()) && user instanceof Student) {
                matchingStudents.add((Student) user);
            }
        }
        return matchingStudents;
    }

    public static Staff findStaffByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().toLowerCase().equals(username.toLowerCase()) && user instanceof Staff) {
                return (Staff) user;
            }
        }
        return null;
    }

    public static Staff findStaffByName(String name) {
        for (User user : userList) {
            if (user.getFullName().toLowerCase().equals(name.toLowerCase()) && user instanceof Staff) {
                return (Staff) user;
            }
        }
        return null;
    }

    public static Course findCourseByID(String courseID) {
        for (Course course : courseList) {
            if (course.getCourseID().toLowerCase().equals(courseID.toLowerCase())) {
                return course;
            }
        }
        return null;
    }

    public static Course findCourse(String courseName, int section) {
        for (Course course : courseList) {
            if (course.getCourseName().toLowerCase().equals(courseName.toLowerCase())
                    && course.getSection() == section) {
                return course;
            }
        }
        return null;
    }

    public static Course findCourse(String courseName) {
        for (Course course : courseList) {
            if (course.getCourseName().toLowerCase().equals(courseName.toLowerCase())) {
                return course;
            }
        }
        return null;
    }

    private static void createFileIfNotExists(String filename) {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File " + filename + " created successfully");
            } else {
                System.out.println("File " + filename + " already exists");
            }
        } catch (IOException e) {
            System.out.println("Failed to create file...");
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=====================================================");
        createFileIfNotExists(COURSE_DATA_CSV);
        createFileIfNotExists(USER_DATA_CSV);
        createFileIfNotExists(FULL_COURSE_CSV);
        loadUserFromCSV(userList, USER_DATA_CSV);
        loadCourseFromCSV(courseList, COURSE_DATA_CSV);
        loadCourseFromCSV(fullCourseList, FULL_COURSE_CSV);
        
        int query;
        do {
            showLoginMenu();
            query = enterChoice();
            switch (query) {
                case 1:
                    System.out.println("=====================================================");
                    userLogin();
                    break;
                case 2:
                    System.out.println("=====================================================");
                    userRegister();
                    break;
                case 3:
                    break;
            }
        } while (query != 3);

    }

    public static void userRegister() {
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already taken, try again...");
                return;
            }
        }
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        System.out.print("Enter your first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = sc.nextLine();
        System.out.println("""
                =====================================================
                Select your role (Enter 1/2/3):
                1. Admin
                2. Staff
                3. Student
                =====================================================
                """);
        User newUser = new User();
        int role = enterChoice();
        switch (role) {
            case 1:
                newUser = new Admin(lastName, firstName, username, password);
                break;
            case 2:
                newUser = new Staff(lastName, firstName, username, password);
                break;
            case 3:
                newUser = new Student(lastName, firstName, username, password);
                break;
            default:
                System.out.println("Invalid choice...");
                return;
        }
        userList.add(newUser);
        saveUserToCSV(userList, USER_DATA_CSV);
        System.out.println("Registered successfully as " + newUser.getRole());
    }

    public static void userLogin() {
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful as " + user.getRole());
                action(user);
                return;
            }
        }
        System.out.println("Login failed");
        userLogin();
    }

    public static void action(User user) {
        if (user.getRole().equals("Admin"))
            adminAction(user);
        if (user.getRole().equals("Staff"))
            staffAction(user);
        if (user.getRole().equals("Student"))
            studentAction(user);
    }

    private static void saveUserToCSV(ArrayList<User> users, String fileName) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
               pw.println("Role,Name,Username,Password");
            for (User user : users) {
                pw.println(user.getRole() + "," + user.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static void loadUserFromCSV(ArrayList<User> users, String fileName) {
        boolean firstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (firstLine == true) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                String role = data[0];
                String name = data[1];
                String username = data[2];
                String password = data[3];
                String[] parts = name.split("\\s");
                String lastName = parts[0];
                String firstName = parts[1];
                User user = null;
                switch (role) {
                    case "Admin":
                        user = new Admin(lastName, firstName, username, password);
                        break;
                    case "Staff":
                        user = new Staff(lastName, firstName, username, password);
                        break;
                    case "Student":
                        user = new Student(lastName, firstName, username, password);
                        break;
                    default:
                        System.out.println("Invalid role...");
                }
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveCourseToCSV(ArrayList<Course> courses, String fileName) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("Course Name,Course ID,Max Students,Current Students,Instructor,Section,Location");
            for (Course course : courses) {
                pw.println(course.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCourseFromCSV(ArrayList<Course> courses, String fileName) {
        boolean firstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (firstLine == true) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                String courseName = data[0];
                String courseID = data[1];
                int currentStudents = Integer.parseInt(data[2]);
                int maxStudents = Integer.parseInt(data[3]);
                String instructorName = data[4];
                int section = Integer.parseInt(data[5]);
                String location = data[6];
                Course course = new Course(courseName, courseID, maxStudents, section, location);
                if (instructorName != null) {
                    Staff staff = findStaffByName(instructorName);
                    course.setInstructor(staff);
                }
                if (!course.getRegisteredStudent().isEmpty()) {
                    String[] studentUsername = data[7].split(";");
                    course.getRegisteredStudent().addAll(Arrays.asList(studentUsername));
                }
                course.setCurrentStudents(currentStudents);
                courseList.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int enterChoice() {
        System.out.print("Enter an option: ");
        int choice = Integer.parseInt(sc.nextLine());
        return choice;
    }

    private static void showLoginMenu() {
        System.out.println("""
                =====================================================
                Login menu
                1. Login
                2. Register
                3. Exit
                =====================================================
                """);
    }

    private static void adminAction(User user) {
        Admin admin = (Admin) user;
        int query;
        do {
            showAdminMenu();
            query = enterChoice();
            switch (query) {
                case 1:
                    createCourse(admin);
                    break;
                case 2:
                    deleteCourse(admin);
                    break;
                case 3:
                    editCourse(admin);
                    break;
                case 4:
                    displayCourse(admin);
                    break;
                case 5:
                    registereStudent(admin);
                    break;
                case 6:
                    viewAllCourses(admin);
                    break;
                case 7:
                    viewFullCourse(admin);
                    break;
                case 8:
                    writeFullCourseToFile(admin);
                    break;
                case 9:
                    viewStudentsInCourse(admin);
                    break;
                case 10:
                    viewStudentRegisteredCourses(admin);
                    break;
                case 11:
                    sortCoursesByEnrollment(admin);
                    break;
                case 12:
                    break;
                default:
                    System.out.println("=====================================================");
                    System.out.println("Invalid choice...");
            }
        } while (query != 12);
    }

    private static void viewStudentsInCourse(Admin admin) {
        System.out.print("Enter course ID: ");
        String courseID = sc.nextLine();
        System.out.println("=====================================================");
        admin.viewStudentsInCourse(courseID);
    }

    private static void showAdminMenu() {
        System.out.println("""
                =====================================================
                Admin Menu
                1. Create Course
                2. Delete Course
                3. Edit Course
                4. Display info for a course
                5. Register a student
                6. View All Courses
                7. View Full Course
                8. Write Full Course to File
                9. View names of students registered to specific course
                10. View list of courses a student is registered in
                11. Sort Courses By Enrollment //Unavailable//
                12. Exit
                =====================================================
                """);
    }

    private static void sortCoursesByEnrollment(Admin admin) {
        System.out.println("=====================================================");
        admin.sortCoursesByEnrollment();
        saveCourseToCSV(courseList, COURSE_DATA_CSV);
    }

    private static void viewStudentRegisteredCourses(Admin admin) {
        System.out.print("Enter student last name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter student first name: ");
        String firstName = sc.nextLine();
        System.out.println("=====================================================");
        admin.viewStudentRegisteredCourses(lastName, firstName);
    }

    private static void writeFullCourseToFile(Admin admin) {
        admin.writeFullCourseToFile(FULL_COURSE_CSV);
        saveCourseToCSV(fullCourseList, FULL_COURSE_CSV);
    }

    private static void viewFullCourse(Admin admin) {
        System.out.println("=====================================================");
        admin.viewFullCourse();
    }

    private static void viewAllCourses(Admin admin) {
        System.out.println("=====================================================");
        admin.viewAllCourse();
    }

    private static void registereStudent(Admin admin) {
        System.out.print("Enter student last name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter student first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter student username: ");
        String username = sc.nextLine();
        System.out.print("Enter student password: ");
        String password = sc.nextLine();
        Student student = new Student(lastName, firstName, username, password);
        System.out.println("=====================================================");
        admin.registerStudent(student);
        saveUserToCSV(userList, USER_DATA_CSV);
    }

    private static void displayCourse(Admin admin) {
        System.out.print("Enter course ID: ");
        String ID = sc.nextLine();
        System.out.println("=====================================================");
        admin.displayCourse(ID);
    }

    private static void editCourse(Admin admin) {
        System.out.print("Enter course ID: ");
        String ID = sc.nextLine();
        System.out.println("=====================================================");
        admin.editCourse(ID);
        saveCourseToCSV(courseList, COURSE_DATA_CSV);
    }

    private static void deleteCourse(Admin admin) {
        System.out.print("Enter course ID: ");
        String ID = sc.nextLine();
        System.out.println("=====================================================");
        admin.deleteCourse(ID);
        saveCourseToCSV(courseList, COURSE_DATA_CSV);
    }

    private static void createCourse(Admin admin) {
        Course course = Course.input();
        System.out.println("=====================================================");
        admin.createCourse(course);
        saveCourseToCSV(courseList, COURSE_DATA_CSV);
    }

    private static void staffAction(User user) {
        Staff staff = (Staff) user;
        int query;
        do {
            showStaffMenu();
            query = enterChoice();
            switch (query) {
                case 1:
                    viewAllCourses(staff);
                    break;
                case 2:
                    registerToCourses(staff);
                    break;
                case 3:
                    withdrawFromCourse(staff);
                    break;
                case 4:
                    viewRegisteredCourse(staff);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("=====================================================");
                    System.out.println("Invalid choice...");
            }
        } while (query != 5);
    }

    private static void showStaffMenu() {
        System.out.println("""
                =====================================================
                Staff Menu
                1. View All Courses
                2. Register To Courses
                3. Withdraw From Course
                4. View Registered Courses
                5. Exit
                =====================================================
                """);
    }

    private static void viewRegisteredCourse(Staff staff) {
        staff.viewRegisteredCourses();
    }

    private static void withdrawFromCourse(Staff staff) {
        String courseName = sc.nextLine();
        System.out.println("=====================================================");
        staff.withdrawFromCourse(courseName);
        saveCourseToCSV(courseList, COURSE_DATA_CSV);
    }

    private static void viewAllCourses(Staff staff) {
        System.out.println("=====================================================");
        staff.viewAllCourse();
    }

    private static void registerToCourses(Staff staff) {
        System.out.print("Enter the course name: ");
        String courseName = sc.nextLine();
        System.out.print("Enter the section: ");
        int section = Integer.parseInt(sc.nextLine());
        System.out.println("=====================================================");
        staff.registereToCourse(courseName, section);
        saveCourseToCSV(courseList, COURSE_DATA_CSV);
    }

    private static void studentAction(User user) {
        Student student = (Student) user;
        int query;
        do {
            showStudentMenu();
            query = enterChoice();
            switch (query) {
                case 1:
                    viewAllCourses(student);
                    break;
                case 2:
                    viewAvailableCourse(student);
                    break;
                case 3:
                    registerToCourse(student);
                    break;
                case 4:
                    withdrawFromCourse(student);
                    break;
                case 5:
                    viewRegisteredCourses(student);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("=====================================================");
                    System.out.println("Invalid choice...");

            }
        } while (query != 6);
    }

    private static void showStudentMenu() {
        System.out.println("""
                =====================================================
                Student Menu
                1. View All Courses
                2. View Available Course
                3. Register To Course
                4. Withdraw From Course
                5. View Registered Courses
                6. Exit
                =====================================================
                """);
    }

    private static void viewRegisteredCourses(Student student) {
        System.out.println("=====================================================");
        student.viewRegisteredCourses();
    }

    private static void withdrawFromCourse(Student student) {
        System.out.print("Enter course name: ");
        String courseName = sc.nextLine();
        System.out.println("=====================================================");
        student.withdrawFromCourse(courseName);
        saveCourseToCSV(courseList, COURSE_DATA_CSV);
    }

    private static void registerToCourse(Student student) {
        System.out.print("Enter course name: ");
        String courseName = sc.nextLine();
        System.out.print("Enter course section: ");
        int section = Integer.parseInt(sc.nextLine());
        System.out.println("=====================================================");
        student.registerToCourse(courseName, section);
        saveCourseToCSV(courseList, COURSE_DATA_CSV);
    }

    private static void viewAvailableCourse(Student student) {
        System.out.println("=====================================================");
        student.viewAvailableCourse();

    }

    private static void viewAllCourses(Student student) {
        System.out.println("=====================================================");
        student.viewAllCourse();
    }

}
