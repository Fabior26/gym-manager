package es.upm.pproject.gym;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

 class GymManagerImplTest {

    private GymManager gym;

    // This method is executed before each test, guaranteeing a fresh start for each test.
    @BeforeEach
     void prepareTest(){
        gym = new GymManagerImpl();
    }


    // Tests for registerPerson

    @Test
    @DisplayName("Register person with valid data - checks name")
     void testRegisterPersonSuccess(){
        gym.registerPerson("Guillermo", "guillermoRoman@gym.com");
        List<Person> people = gym.getAllPeople();
        assertEquals(1, people.size());
        assertEquals("Guillermo", people.get(0).getName());
    }

    @Test
    @DisplayName("Register person with valid data - checks email and id")
     void testRegisterPersonSuccess2(){
        gym.registerPerson("Guillermo", "guillermoRoman@gym.com");
        List<Person> people = gym.getAllPeople();
        assertEquals("guillermoRoman@gym.com", people.get(0).getEmail());
        assertEquals(1, people.get(0).getId());
    }
    @Test
    @DisplayName("Register person throws exception when name is blank or null")
     void testRegisterPersonThrowsExceptionWhenNameIsBlankOrNull(){
        assertThrows(GymException.class, () -> gym.registerPerson("", "guillermoRoman@gym.com"));
        assertThrows(GymException.class, () -> gym.registerPerson(null, "guillermoRoman@gym.com"));
    }

    @Test
    @DisplayName("Register person throws exception when email is blank or null")
     void testRegisterPersonThrowsExceptionWhenEmailIsBlankOrNull(){
        assertThrows(GymException.class, () -> gym.registerPerson("Guillermo", ""));
        assertThrows(GymException.class, () -> gym.registerPerson("Guillermo", null));
    }

    @Test
    @DisplayName("Register person throws exception when email format is invalid")
     void testRegisterPersonThrowsExceptionWhenEmailIsInvalid(){
        assertThrows(GymException.class, () -> gym.registerPerson("Juan", "JuanWithoutAt"));
        assertThrows(GymException.class, () -> gym.registerPerson("Juan", "Juan@no."));

    }

    @Test
    @DisplayName("Register person throws exception when email is already registered")
     void testRegisterPersonWhenDuplicateEmail(){
        gym.registerPerson("Guillermo", "guillermoRoman@gym.com");
        assertThrows(GymException.class, () -> gym.registerPerson("Guille", "guillermoRoman@gym.com"));
    }


    // Tests for registerClass

    @Test
    @DisplayName("Register class with valid data - checks name")
     void testRegisterClassSuccess(){
        gym.registerClass("Yoga", "Fredy Mercury");
        List<GymClass> classes = gym.getAllClasses();
        assertEquals(1, classes.size());
        assertEquals("Yoga", classes.get(0).getName());
    }

    @Test
    @DisplayName("Register class with valid data - checks trainer")
     void testRegisterClassSuccess2(){
        gym.registerClass("Yoga", "Fredy Mercury");
        List<GymClass> classes = gym.getAllClasses();
        assertEquals("Fredy Mercury", classes.get(0).getTrainer());
    }

    @Test
    @DisplayName("Register class throws exception when class name is blank or null")
     void testRegisterClassThrowsExceptionWhenClassNameIsBlankOrNull(){
        assertThrows(GymException.class, () -> gym.registerClass("", "Dani"));
        assertThrows(GymException.class, () -> gym.registerClass(null, "Dani"));
    }

    @Test
    @DisplayName("Register class throws exception when trainer is blank or null")
     void testRegisterClassThrowsExceptionWhenTrainerIsBlankOrNull(){
        assertThrows(GymException.class, () -> gym.registerClass("Dani", ""));
        assertThrows(GymException.class, () -> gym.registerClass("Dani", null));
    }

    @Test
    @DisplayName("Register class throws exception when class already exists")
     void testRegisterClassWhenDuplicateClass(){
        gym.registerClass("Yoga", "Fredy Mercury");
        assertThrows(GymException.class, () -> gym.registerClass("Yoga", "Jose Jose"));
    }


    // Tests for enrollClass

    @Test
    @DisplayName("Enroll person in class successfully - checks email")
     void testEnrollSuccess(){
        gym.registerClass("Yoga", "Fredy Mercury");
        gym.registerPerson("Guillermo", "guillermoRoman@gym.com");
        gym.enrollClass("guillermoRoman@gym.com", "Yoga");
        assertEquals(1, gym.getStudentsFromClass("Yoga").size());
        assertEquals("guillermoRoman@gym.com", gym.getStudentsFromClass("Yoga").get(0).getEmail());
    }

    @Test
    @DisplayName("Enroll person in class successfully - checks name")
     void testEnrollSuccess2(){
        gym.registerClass("Yoga", "Fredy Mercury");
        gym.registerPerson("Guillermo", "guillermoRoman@gym.com");
        gym.enrollClass("guillermoRoman@gym.com", "Yoga");
        assertEquals("Guillermo", gym.getStudentsFromClass("Yoga").get(0).getName());
    }

    @Test
    @DisplayName("Enroll throws exception when person is not registered")
     void testEnrollPersonWhenNotRegistered(){
        gym.registerClass("Yoga", "Fredy Mercury");
        assertThrows(GymException.class, () -> gym.enrollClass("X@gym.com", "Yoga"));
    }

    @Test
    @DisplayName("Enroll throws exception when class is not registered")
     void testEnrollInClassWhenNotRegistered(){
        gym.registerPerson("Guillermo", "guillermoRoman@gym.com");
        assertThrows(GymException.class, () -> gym.enrollClass("guillermoRoman@gym.com", "Yoga"));
    }

    @Test
    @DisplayName("Enroll throws exception when class is full (20 people)")
     void testEnrollClassFull() {
        gym.registerClass("Yoga", "Fredy Mercury");
        for (int i = 0; i < 20; i++) {
            gym.registerPerson("Person " + i, "p" + i + "@gym.com");
            gym.enrollClass("p" + i + "@gym.com", "Yoga");
        }
        gym.registerPerson("Extra", "extra@gym.com");
        assertThrows(GymException.class, () -> gym.enrollClass("extra@gym.com", "Yoga"));
    }

    @Test
    @DisplayName("Enroll throws exception when person is already enrolled")
     void testEnrollDuplicate() {
        gym.registerClass("Yoga", "Fredy Mercury");
        gym.registerPerson("Guillermo", "guillermoRoman@gym.com");
        gym.enrollClass("guillermoRoman@gym.com", "Yoga");
        assertThrows(GymException.class, () -> gym.enrollClass("guillermoRoman@gym.com", "Yoga"));
    }


    // Tests for getStudentsFromClass

    @Test
    @DisplayName("Get students from class returns list sorted by name - checks first two")
     void testGetStudentsFromClassSortedByNameSuccess() {
        gym.registerClass("Yoga", "Fredy Mercury");
        gym.registerPerson("Zebra", "zebra@gym.com");
        gym.registerPerson("Ana", "ana@gym.com");
        gym.registerPerson("Marcos", "marcos@gym.com");
        gym.enrollClass("zebra@gym.com", "Yoga");
        gym.enrollClass("ana@gym.com", "Yoga");
        gym.enrollClass("marcos@gym.com", "Yoga");

        List<Person> result = gym.getStudentsFromClass("Yoga");
        assertEquals("Ana", result.get(0).getName());
        assertEquals("Marcos", result.get(1).getName());
    }

    @Test
    @DisplayName("Get students from class returns list sorted by name - checks last")
     void testGetStudentsFromClassSortedByNameSuccess2() {
        gym.registerClass("Yoga", "Fredy Mercury");
        gym.registerPerson("Zebra", "zebra@gym.com");
        gym.registerPerson("Ana", "ana@gym.com");
        gym.registerPerson("Marcos", "marcos@gym.com");
        gym.enrollClass("zebra@gym.com", "Yoga");
        gym.enrollClass("ana@gym.com", "Yoga");
        gym.enrollClass("marcos@gym.com", "Yoga");

        List<Person> result = gym.getStudentsFromClass("Yoga");
        assertEquals("Zebra", result.get(2).getName());
    }


    @Test
    @DisplayName("Get students throws exception when class is not registered")
     void testGetStudentsWhenClassNotRegistered(){
        assertThrows(GymException.class, () -> gym.getStudentsFromClass("Yoga"));
    }


    // Tests for cancellEnroll

    @Test
    @DisplayName("Cancel enrollment successfully - student is removed from class")
     void testCancelEnrollSuccess(){
        gym.registerPerson("Marcos", "marcos@gym.com");
        gym.registerClass("Yoga", "Fredy Mercury");
        gym.enrollClass("marcos@gym.com", "Yoga");
        gym.cancelEnroll("marcos@gym.com" , "Yoga");
        List<Person> result = gym.getStudentsFromClass("Yoga");
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Cancel enrollment throws exception when person is not registered")
     void testCancelEnrollWhenPersonNotRegistered(){
        gym.registerClass("Yoga", "Fredy Mercury");
        assertThrows(GymException.class, () -> gym.cancelEnroll("marcos@gym.com" , "Yoga"));
    }

    @Test
    @DisplayName("Cancel enrollment throws exception when class is not registered")
     void testCancelEnrollWhenClassNotRegistered(){
        gym.registerPerson("Marcos", "marcos@gym.com");
        assertThrows(GymException.class, () -> gym.cancelEnroll("marcos@gym.com" , "Yoga"));
    }

    @Test
    @DisplayName("Cancel enrollment throws exception when person is not enrolled")
     void testCancelEnrollWhenPersonNotEnrolled(){
        gym.registerPerson("Marcos", "marcos@gym.com");
        gym.registerClass("Yoga", "Fredy Mercury");
        assertThrows(GymException.class, () -> gym.cancelEnroll("marcos@gym.com" , "Yoga"));
    }


    // Tests for restartClass

    @Test
    @DisplayName("Restart class successfully removes all enrolled students")
     void testRestartClassSuccess(){

        gym.registerClass("Pilates", "Marta");
        gym.registerPerson("Carla", "carla@gym.com");
        gym.registerPerson("Maria", "maria@gym.com");
        gym.enrollClass("carla@gym.com", "Pilates");
        gym.enrollClass("maria@gym.com", "Pilates");

        assertEquals(2, gym.getStudentsFromClass("Pilates").size());

        gym.restartClass("Pilates");

        assertEquals(0, gym.getStudentsFromClass("Pilates").size());
    }

    @Test
    @DisplayName("Restart class throws exception when class is not registered")
     void testRestartClassWhenNotRegistered(){
        assertThrows(GymException.class, () -> gym.restartClass("Yoga"));
    }

    // Tests for getAllPeople

    @Test
    @DisplayName("Get all people returns list sorted by email - checks first")
     void testGetAllPeopleSortedByEmail(){

        gym.registerPerson("Marcos", "marcos@gym.com");
        gym.registerPerson("Ana", "ana@gym.com");
        gym.registerPerson("Belén", "belen@gym.com");

        List<Person> result = gym.getAllPeople();

        assertEquals(3, result.size());

        assertEquals("ana@gym.com", result.get(0).getEmail());

    }

    @Test
    @DisplayName("Get all people returns list sorted by email - checks second and third")
     void testGetAllPeopleSortedByEmail2(){

        gym.registerPerson("Marcos", "marcos@gym.com");
        gym.registerPerson("Ana", "ana@gym.com");
        gym.registerPerson("Belén", "belen@gym.com");

        List<Person> result = gym.getAllPeople();
        assertEquals("belen@gym.com", result.get(1).getEmail());
        assertEquals("marcos@gym.com", result.get(2).getEmail());
    }

    // Tests for getAllClasses

    @Test
    @DisplayName("Get all classes returns list sorted by name - checks first")
     void testGetAllClassesSortedByName(){

        gym.registerClass("Zumba", "Zahara");
        gym.registerClass("Aerobics", "Ariana");
        gym.registerClass("Boxing", "Belén");

        List<GymClass> result = gym.getAllClasses();

        assertEquals(3, result.size());

        assertEquals("Aerobics", result.get(0).getName());
    }

    @Test
    @DisplayName("Get all classes returns list sorted by name - checks second and third")
     void testGetAllClassesSortedByName2(){

        gym.registerClass("Zumba", "Zahara");
        gym.registerClass("Aerobics", "Ariana");
        gym.registerClass("Boxing", "Belén");

        List<GymClass> result = gym.getAllClasses();
        assertEquals("Boxing", result.get(1).getName());
        assertEquals("Zumba", result.get(2).getName());
    }















}
