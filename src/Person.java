public class Person {
    private String name;
    private String surname;
    private String country;
    private String id;

    public Person(String name, String surname, String country, String id) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
