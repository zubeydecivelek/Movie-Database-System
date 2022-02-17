public class ChildActor extends Performer{
    private String age;

    public ChildActor(String id, String name, String surname, String country,  String age) {
        super(name, surname, country, id);
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}
