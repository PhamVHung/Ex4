package User;

import CourseData.Course;
import Interface.StudentInterface;
import Main.CRS;

public class Student extends User implements StudentInterface {

    public Student(String lastName, String firstName, String username, String password) {
        super(lastName, firstName, username, password);
    }

    @Override
    public void registerToCourse(String courseName, int section) {
        Course matchingCourse = CRS.findCourse(courseName, section);
        if (matchingCourse == null) {
            System.out.println("Course not found...");
            return;
        }
        if (matchingCourse.isFull()) {
            System.out.println("Course is full...");
            return;
        }
        if (matchingCourse.getRegisteredStudent().contains(this.getFullName())) {
            System.out.println("Already registered...");
            return;
        }
        matchingCourse.getRegisteredStudent().add(this.getFullName());
        matchingCourse.setCurrentStudents(matchingCourse.getCurrentStudents() + 1);
        System.out.println("Student registered in course " + matchingCourse.getCourseName());
    }

    @Override
    public void viewAllCourse() {
        for (Course course : CRS.courseList) {
            System.out.println("=====================================================");
            System.out.println(course);
        }
    }

    @Override
    public void viewAvailableCourse() {
        for (Course cousre : CRS.courseList) {
            if (!cousre.isFull()) {
                System.out.println("=====================================================");
                System.out.println(cousre);
            }
        }
    }

    @Override
    public void viewRegisteredCourses() {
        boolean found = false;
        for (Course course : CRS.courseList) {
            if (course.getRegisteredStudent().contains(this.getFullName())) {
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
        matchingCourse.getRegisteredStudent().remove(this.getFullName());
        matchingCourse.setCurrentStudents(matchingCourse.getCurrentStudents() - 1);
        System.out.println("Student removed from course " + matchingCourse.getCourseName());
    }

}
