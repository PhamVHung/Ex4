package Interface;

public interface StudentInterface {
    void viewAllCourse();
    void viewAvailableCourse();
    void registerToCourse(String courseName, int section);
    void withdrawFromCourse(String courseName); 
    void viewRegisteredCourses();   
}
