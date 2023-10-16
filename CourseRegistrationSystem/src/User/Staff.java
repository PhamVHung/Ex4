package User;

import CourseData.Course;
import Interface.StaffInterface;
import Main.CRS;

public class Staff extends User implements StaffInterface {

    public Staff(String lastName, String firstName, String username, String password) {
        super(lastName, firstName, username, password);
    }

    @Override
    public void registereToCourse(String courseName, int section) {
        Course matchingCourse = CRS.findCourse(courseName, section);
        if (matchingCourse == null) {
            System.out.println("Course not found...");
            return;
        }
        if (matchingCourse.getInstructor() != null) {
            System.out.println("Course already registered...");
            return;
        }
        matchingCourse.setInstructor(this);
        System.out.println("Course registered successfully...");
    }

    @Override
    public void viewAllCourse() {
        for (Course course : CRS.courseList) {
            System.out.println("=====================================================");
            System.out.println(course);
        }
    }

    @Override
    public void viewRegisteredCourses() {
        boolean found = false;
        for (Course course : CRS.courseList) {
            if (this == course.getInstructor()) {
                System.out.println("=====================================================");
                System.out.println(course);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No courses registered...");
        }
    }

    @Override
    public void withdrawFromCourse(String courseName) {
        Course matchingCourse = CRS.findCourse(courseName);
        if (matchingCourse == null) {
            System.out.println("Course not found...");
            return;
        }
        matchingCourse.setInstructor(null);
    }

}
