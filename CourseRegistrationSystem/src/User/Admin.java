package User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import CourseData.Course;
import Interface.AdminInterface;
import Main.CRS;

public class Admin extends User implements AdminInterface {

    private static Scanner sc = new Scanner(System.in);

    public Admin(String lastName, String firstName, String username, String password) {
        super(lastName, firstName, username, password);
    }

    @Override
    public void createCourse(Course course) {
        CRS.courseList.add(course);
    }

    @Override
    public void deleteCourse(String coureID) {
        for (Course course : CRS.courseList) {
            if (course.getCourseID().equals(coureID)) {
                CRS.courseList.remove(course);
                System.out.println("Course deleted successfully...");
                break;
            }
        }
        System.out.println("Course not found...");
    }

    @Override
    public void sortCoursesByEnrollment() {
        Collections.sort(CRS.courseList);
        System.out.println("Course sorted...");
    }

    @Override
    public void editCourse(String courseID) {
        for (Course course : CRS.courseList) {
            if (course.getCourseID().equals(courseID)) {
                System.out.println("""
                        =====================================================
                        What would you like to change?
                        (Enter 1/2/3/4/5)
                        1. Edit course students limit
                        2. Edit course instructor
                        3. Edit course section
                        4. Edit course location
                        5. None
                        =====================================================
                        """);
                System.out.print("Enter option: ");
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1: {
                        System.out.print("Enter new students limit: ");
                        int studentsLimit = Integer.parseInt(sc.nextLine());
                        course.setMaxStudents(studentsLimit);
                        break;
                    } 
                    case 2: {
                        System.out.print("Enter new instructor: ");
                        Staff instructor = (Staff) User.input();
                        course.setInstructor(instructor);
                        break;
                    }
                    case 3: {
                        System.out.print("Enter new section: ");
                        int section = Integer.parseInt(sc.nextLine());
                        course.setSection(section);
                        break;
                    }
                    case 4: {
                        System.out.print("Enter new location: ");
                        String location = sc.nextLine();
                        course.setLocation(location);
                        break;
                    }
                    case 5: {
                        System.out.println("Course unchanged...");
                        return;
                    }
                    default:
                        System.out.println("Invalid choice...");
                        break;
                }
                System.out.println("Course edited successfully...");
                return;
            }
        }
        System.out.println("Course not found...");
    }

    @Override
    public void displayCourse(String courseID) {
        for (Course course : CRS.courseList) {
            if (course.getCourseID().equals(courseID)) {
                System.out.println(course);
                break;
            }
        }
    }

    @Override
    public void registerStudent(Student student) {
        for (User existingUser : CRS.userList) {
            if (existingUser.getUsername().equals(student.getUsername())) {
                System.out.println("Student already registered...");
                return;
            }
        }
        CRS.userList.add(student);
        System.out.println("Student registered successfully...");
    }

    @Override
    public void viewAllCourse() {
        for (Course course : CRS.courseList) {
            System.out.println("=====================================================");
            System.out.println(course);
        }
    }

    @Override
    public void viewFullCourse() {
        for (Course course : CRS.courseList) {
            if (course.isFull()) {
                System.out.println("=====================================================");
                System.out.println(course);
            }
        }
    }

    @Override
    public void writeFullCourseToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Course course : CRS.courseList) {
                if (course.isFull()) {
                    bw.write(course.toCSV());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewStudentsInCourse(String courseID) {
        Course foundCourse = CRS.findCourseByID(courseID);
        if (foundCourse != null) {
            ArrayList<String> registeredStudents = foundCourse.getRegisteredStudent();
            if (registeredStudents != null) {
                System.out.println("Student of course " + courseID + ":");
                for (String student : registeredStudents) {
                    System.out.println(student);
                }
            } else {
                System.out.println("No student registered in this course...");
            }
            return;
        }
        System.out.println("Course not found...");
    }

    @Override
    public void viewStudentRegisteredCourses(String lastName, String firstName) {
        ArrayList<Student> matchingStudents = CRS.findStudentByName(lastName, firstName);
        if (matchingStudents.isEmpty()) {
            System.out.println("No students have registered in this code...");
            return;
        }
        for (User student : matchingStudents) {
            System.out.println((Student) student);
            for (Course course : CRS.courseList) {
                if (course.getRegisteredStudent().contains(student.getFullName())) {
                    System.out.println(course.getCourseName() + ", " + course.getCourseID() + ", " + course.getSection());
                }
            }
        }
    }
}