package es.upm.pproject.gym;

import java.util.ArrayList;
import java.util.List;

public class GymClass {

    private String name;
    private String trainer;

    private List<Person> students;

    public GymClass(String name, String trainer){

        this.name= name;
        this.trainer=trainer;
        students = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public String getTrainer() {
        return trainer;
    }

    // Adds student p to the class.
    public void addStudent(Person p){
        students.add(p);
    }

    // Returns the amount of students enrolled in given class.
    public int size(){
        return students.size();
    }

    // Returns true if the person p is enrolled in the class.
    public boolean containsStudent(Person p){
        return students.contains(p);
    }

    // Removes student p from the class.
    public void removeStudent(Person p){
        students.remove(p);
    }

    // Returns a list with all the students in the class.
    public List<Person> getStudents() {
        return students;
    }

    // Clears the list of students in the class.
    public void clearStudents(){
        students.clear();
    }


}
