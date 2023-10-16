package CourseData;

import java.util.ArrayList;
import java.util.Scanner;

import User.Staff;

public class Course implements Comparable<Course> {
    private static Scanner sc = new Scanner(System.in);

    private String courseName;
    private String courseID;
    private int maxStudents;
    private int currentStudents;
    private ArrayList<String> registeredStudents;
    private Staff instructor;
    private int section;
    private String location;

    public Course(String courseName, String courseID, int maxStudents, int section, String location) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.maxStudents = maxStudents;
        this.instructor = null;
        this.section = section;
        this.location = location;
        this.currentStudents = 0;
        this.registeredStudents = new ArrayList<>();
    }

    public Course(String courseName, String courseID, int maxStudents, Staff instructor, int section,
            String location) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.maxStudents = maxStudents;
        this.instructor = instructor;
        this.section = section;
        this.location = location;
        this.currentStudents = 0;
        this.registeredStudents = new ArrayList<>();
    }

    public static Course input() {
        System.out.print("Course name: ");
        String courseName = sc.nextLine();
        System.out.print("Course ID: ");
        String courseID = sc.nextLine();
        System.out.print("Course students limit: ");
        int maxStudents = Integer.parseInt(sc.nextLine());
        System.out.print("Course section: ");
        int section = Integer.parseInt(sc.nextLine());
        System.out.print("Course location: ");
        String location = sc.nextLine();
        return new Course(courseName, courseID, maxStudents, section, location);
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public void setCurrentStudents(int currentStudents) {
        this.currentStudents = currentStudents;
    }

    public ArrayList<String> getRegisteredStudent() {
        return registeredStudents;
    }

    public void setregisteredStudent(ArrayList<String> registeredStudentID) {
        this.registeredStudents = registeredStudentID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public Staff getInstructor() {
        return instructor;
    }

    public void setInstructor(Staff instructor) {
        this.instructor = instructor;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isFull() {
        return currentStudents == maxStudents;
    }

    public String toCSV() {
        return courseName + "," + courseID + "," + currentStudents + "," + maxStudents + ","
                + (instructor != null ? instructor.toNameCSV() : "" + " " + "") + "," + section
                + "," + location + "," + String.join(";", registeredStudents);
    }

    @Override
    public int compareTo(Course o) {
        return Integer.compare(o.getCurrentStudents(), this.getCurrentStudents());
    }

    @Override
    public String toString() {
        String instructorName = (instructor != null) ? instructor.getLastName() + " " + instructor.getFirstName()
                : "N/A";
        return "Course Name: " + courseName + "\n" +
                "Course ID: " + courseID + "\n" +
                "Instructor: " + instructorName + "\n" +
                "Section: " + section + "\n" +
                "Location: " + location + "\n" +
                "Current Students: " + currentStudents + " / " + maxStudents + "\n" +
                "Registered students: " + registeredStudents.toString();
    }

}
