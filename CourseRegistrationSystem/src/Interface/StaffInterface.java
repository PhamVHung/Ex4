package Interface;

public interface StaffInterface {
    void viewAllCourse();
    void registereToCourse(String courseName, int section);
    void withdrawFromCourse(String courseName);
    void viewRegisteredCourses();
}
