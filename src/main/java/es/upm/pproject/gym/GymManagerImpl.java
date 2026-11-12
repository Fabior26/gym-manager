package es.upm.pproject.gym;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GymManagerImpl implements GymManager{

    //Instance of the logger needed for this class.
    private static final Logger logger =
            LoggerFactory.getLogger(GymManagerImpl.class);

    //The members of the gym will be stored in a HashMap, using the email as their key.
    private Map<String, Person> listPeople = new HashMap<>();
    //The classes of the gym will be stored in a HashMap, using the class' name as their key.
    private Map<String, GymClass> listClasses = new HashMap<>();

    //Variable that will count the amount of current members in the gym.
    private int idCounter = 1;

    private static final String NOT_REGISTERED_MSG = "' is not registered in the system.";

    //Auxiliary method to check if str is null or blank.
    private boolean isNullOrBlank(String str){
        return str == null || str.isBlank();
    }

    @Override
    public void registerPerson(String name, String email) {

        logger.info("Attempting to register a new person. Email: '{}', Name: '{}'", email, name);

        if(isNullOrBlank(name) || isNullOrBlank(email)){
            logger.error("Registration failed: Name and email cannot be blank or null.");
            throw new GymException("The name and email cannot be blank or null.");
        }

        if(!email.contains("@") || email.endsWith(".")) {
            logger.error("Registration failed: Invalid email format '{}'.", email);
            throw new GymException("The email must contain an '@' and cannot end with '.' .");
        }

        if (listPeople.containsKey(email)) {
            logger.warn("Registration failed: A person with the email '{}' already exists.", email);
            throw new GymException("A person with the email '" + email + "' exists already.");
        }

        /*
        We create a new person with the details given in the arguments, increase after the counter
        and insert it in the list of members.
         */
        Person p = new Person(email, name, idCounter);
        idCounter++;
        listPeople.put(email, p);

        logger.info("Successfully registered person '{}' with ID: {}", email, p.getId());

    }

    @Override
    public void registerClass(String className, String trainer) {

        logger.info("Attempting to register a new class. Name: '{}', Trainer: '{}'", className, trainer);

        if(isNullOrBlank(className)|| isNullOrBlank(trainer)) {
            logger.error("Class registration failed: Class name and trainer cannot be blank or null.");
            throw new GymException("The class' name and trainer cannot be blank or null.");
        }

        if(listClasses.containsKey(className)) {
            logger.warn("Class registration failed: A class with the name '{}' already exists.", className);
            throw new GymException("A class with the name '" + className + "' already exists.");
        }

        /*
        We create the class with the details and put it in the list of classes.
         */
        GymClass c = new GymClass(className, trainer);
        listClasses.put(className, c);

        logger.info("Successfully registered class '{}' with trainer '{}'.", className, trainer);
    }

    @Override
    public void enrollClass(String email, String className) {

        logger.info("Attempting to enroll person '{}' into class '{}'.", email, className);

        if(!listPeople.containsKey(email)) {
            logger.warn("Enrollment failed: Person with email '{}' is not registered.", email);
            throw new GymException("The person with email '" + email + NOT_REGISTERED_MSG);
        }

        if(!listClasses.containsKey(className)) {
            logger.warn("Enrollment failed: Gym class '{}' is not registered.", className);
            throw new GymException("The gym class '" + className + "' is not registered.");
        }

        GymClass c = listClasses.get(className);
        Person p = listPeople.get(email);

        if(c.size() >= 20) {
            logger.warn("Enrollment failed: Class '{}' is already full.", className);
            throw new GymException("The class is already full (20 people at most in one class).");
        }

        if (c.containsStudent(p)) {
            logger.warn("Enrollment failed: Student '{}' is already enrolled in class '{}'.", email, className);
            throw new GymException("The student '" + p.toString() + "' is already enrolled in the class.");
        }

        c.addStudent(p);

        logger.info("Succesfully enrolled person '{}' into class '{}'. Current class size: {}", email, className, c.size());
    }

    @Override
    public List<Person> getStudentsFromClass(String className) {

        logger.info("Retrieving students for class '{}'.", className);

        if(!listClasses.containsKey(className)) {
            logger.warn("Retrieval failed: The class '{}' is not registered.", className);
            throw new GymException("The class is not registered.");
        }

        GymClass c = listClasses.get(className);


        /*
        We create a new list of students by copying the current one from the class, then we sort it by
        their names with a comparator.
         */
        List<Person> sortedListOfStudents = new ArrayList<>(c.getStudents());

        Collections.sort(sortedListOfStudents, (p1, p2) -> p1.getName().compareTo(p2.getName()));

        logger.info("Successfully retrieved {} students from class '{}'.", sortedListOfStudents.size(), className);

        return sortedListOfStudents;
    }


    @Override
    public void cancelEnroll(String email, String className) {

        logger.info("Attempting to cancel enrollment for person '{}' in class '{}'.", email, className);

        if(!listPeople.containsKey(email)) {
            logger.warn("Cancellation failed: Person with email '{}' is not registered.", email);
            throw new GymException("The person with email '" + email + NOT_REGISTERED_MSG);
        }

        Person p = listPeople.get(email);

        if(!listClasses.containsKey(className)) {
            logger.warn("Cancellation failed: Class '{}' is not registered.", className);
            throw new GymException("The class with name '" + className + NOT_REGISTERED_MSG);
        }

        GymClass c = listClasses.get(className);

        /*
        If the class contains the student, we remove him. Otherwise, we throw an exception.
         */
        if(c.containsStudent(p)) {
            c.removeStudent(p);
            logger.info("Successfully cancelled enrollment for person '{}' in class '{}'.", email, className);
        }
        else {
            logger.warn("Cancellation failed: Student '{}' is not enrolled in class '{}'.", email, className);
            throw new GymException("The student '" + p.toString() + "' is not enrolled in that class.");
        }

    }

    @Override
    public void restartClass(String className) {

        logger.info("Attempting to restart class '{}'.", className);

        if (!listClasses.containsKey(className)) {
            logger.warn("Restart failed: Class '{}' is not registered.", className);
            throw new GymException("The class with name '" + className + NOT_REGISTERED_MSG);
        }

        GymClass c = listClasses.get(className);
        int previousSize = c.size();

        c.clearStudents();

        logger.info("Successfully restarted class '{}'. Removed {} students.", className, previousSize);

    }

    @Override
    public List<Person> getAllPeople() {

        logger.info("Obtaining all registered people in the system.");

        List<Person> sortedListOfPeople = new ArrayList<>(listPeople.values());

        /*
        We sort the list of people by their emails using a comparator.
         */
        Collections.sort(sortedListOfPeople, (o1, o2) -> o1.getEmail().compareTo(o2.getEmail()));

        logger.info("Successfully obtained {} registered people.", sortedListOfPeople.size());

        return sortedListOfPeople;
    }

    @Override
    public List<GymClass> getAllClasses() {

        logger.info("Obtaining all registered classes in the system.");

        List<GymClass> sortedClasses = new ArrayList<>(listClasses.values());

        /*
        We sort the list of classes by their names using a comparator.
         */
        Collections.sort(sortedClasses, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        logger.info("Successfully obtained {} registered classes.", sortedClasses.size());

        return sortedClasses;
    }

}
