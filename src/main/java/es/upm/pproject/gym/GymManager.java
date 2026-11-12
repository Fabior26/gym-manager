package es.upm.pproject.gym;

import java.util.List;

public interface GymManager {

    /**
     * Registers a new person in the gym system.
     * @param name  The name of the person. Cannot be blank or null.
     * @param email The email address of the person. Cannot be blank or null.
     * Must contain '@' and cannot end with '.'.
     * @throws GymException if pre-conditions are not met or the email is already registered.
     */
    void registerPerson(String name, String email);

    /**
     * Registers a new gym class in the system.
     * @param name    The name of the class. Cannot be blank or null.
     * @param trainer The trainer's name. Cannot be blank or null.
     * @throws GymException if pre-conditions are not met or the class name already exists.
     */
    void registerClass(String name, String trainer);

    /**
     * Enrolls a person into a class using their email and the class name.
     * @param email     The registered email of the person.
     * @param className The name of the registered class.
     * @throws GymException if the person or class are not registered.
     * @throws GymException if the class is full (maximum 20 people).
     * @throws GymException if the person is already enrolled in the class.
     */
    void enrollClass(String email, String className);

    /**
     * Returns the list of people enrolled in a specific class.
     * @param className The name of the class to query.
     * @return A list of {@link Person} objects, sorted alphabetically by name.
     * @throws GymException if the class is not registered.
     */
    List<Person> getStudentsFromClass(String className);

    /**
     * Cancels a person's enrollment in a specific class.
     * @param email     The email of the person.
     * @param className The name of the class.
     * @throws GymException if the person is not registered or not enrolled in the class.
     * @throws GymException if the class is not registered.
     */
    void cancelEnroll(String email, String className);

    /**
     * Removes all people currently enrolled in a specific class.
     * @param className The name of the class to restart.
     * @throws GymException if the class is not registered.
     */
    void restartClass(String className);

    /**
     * Retrieves all people registered in the gym system.
     * @return A list of all {@link Person} objects, sorted by email.
     */
    List<Person> getAllPeople();

    /**
     * Retrieves all classes registered in the gym system.
     * @return A list of all {@link GymClass} objects (name and trainer), sorted by name.
     */
    List<GymClass> getAllClasses();

}
