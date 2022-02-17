public class Actor extends Performer{
    private String height;

    public Actor(String id,String name, String surname, String country,  String height) {
        super(name, surname, country, id);
        this.height = height;
    }

    public String getHeight() {
        return height;
    }
}
