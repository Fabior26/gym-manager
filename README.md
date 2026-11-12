# Gym Manager System

This project is a Java-based system developed to manage the daily operations of a gym, specifically focusing on handling members and class enrollments. It provides a backend API to register users, create classes, and manage the associations between them.

## Environment & Prerequisites

This project is designed and tested to run properly in the production Virtual Machine (Ubuntu Desktop 24.04) provided for the course. The exact specifications required are:
* **Java:** OpenJDK 11.0.30
* **Maven:** Apache Maven 3.8.7
* **Git:** Version 2.43

## Logging

The system includes a logging mechanism using **SLF4J**. It tracks all relevant operations, such as successful enrollments, business logic warnings (e.g., full classes), and input errors. The execution logs are automatically generated and saved in the `logs/` directory.
## Build and Testing

The project uses Maven for lifecycle management and JUnit 5.6 for testing. To compile the code and execute the automated test suite, run the following command in the root directory of the project:

```bash
mvn test
```

## System Interface (Public API)

The core functionality of the system is exposed through the `GymManager` interface. Below is a description of the available public methods and how to use them.

### Registration Methods

* **`void registerPerson(String name, String email)`**
  Registers a new person in the gym system. 
  * *Constraints:* The name and email cannot be blank or null. The email must contain an '@' and cannot end with a '.'.
  * *Exceptions:* Throws a `GymException` if the input is invalid or if the email is already registered.

* **`void registerClass(String name, String trainer)`**
  Registers a new gym class.
  * *Constraints:* The class name and trainer name cannot be blank or null.
  * *Exceptions:* Throws a `GymException` if the input is invalid or if a class with the same name already exists.

### Enrollment Methods

* **`void enrollClass(String email, String className)`**
  Enrolls an existing person into an existing class.
  * *Constraints:* A maximum of 20 people can be enrolled in a single class.
  * *Exceptions:* Throws a `GymException` if the person or class does not exist, if the class is full, or if the user is already enrolled in the specified class.

* **`void cancelEnroll(String email, String className)`**
  Cancels a person's enrollment from a specific class.
  * *Exceptions:* Throws a `GymException` if the person or class is not registered, or if the person is not currently enrolled in that class.

### Management and Query Methods

* **`List<Person> getStudentsFromClass(String className)`**
  Retrieves a list of all people enrolled in a specific class.
  * *Returns:* A list of `Person` objects, sorted alphabetically by their names.
  * *Exceptions:* Throws a `GymException` if the class is not registered.

* **`void restartClass(String className)`**
  Clears a class by removing all currently enrolled students, leaving the class empty but still registered in the system.
  * *Exceptions:* Throws a `GymException` if the class is not registered.

* **`List<Person> getAllPeople()`**
  Obtains a complete list of all people registered in the gym system.
  * *Returns:* A list of all `Person` objects, sorted alphabetically by their email addresses.

* **`List<GymClass> getAllClasses()`**
  Obtains a complete list of all classes registered in the system.
  * *Returns:* A list of all `GymClass` objects, sorted alphabetically by the class name.