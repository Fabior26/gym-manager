package es.upm.pproject.gym;

public class Person {

    private int id;
    private String name;
    private String email;

    public Person(String email, String name, int id) {
            this.email = email;
            this.name = name;
            this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    /*
    Used in an exception if thrown.
     */
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}