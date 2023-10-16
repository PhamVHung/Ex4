package Interface;

import CourseData.Course;
import User.Student;

public interface AdminInterface {
    void createCourse(Course coure);
    void deleteCourse(String coureID);
    void editCourse(String courseID);
    void displayCourse(String courseID);
    void registerStudent(Student student);

    void viewAllCourse();
    void viewFullCourse();
    void writeFullCourseToFile(String filename);
    void viewStudentsInCourse(String courseID);
    void viewStudentRegisteredCourses(String lastName, String firstName);
    void sortCoursesByEnrollment();
}
