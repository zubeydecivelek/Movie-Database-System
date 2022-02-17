public class Writer extends Artist{
    private String writingStyle;

    public Writer(String id,String name, String surname, String country,  String writingStyle) {
        super(name, surname, country, id);
        this.writingStyle = writingStyle;
    }

    public String getWritingStyle() {
        return writingStyle;
    }
}
