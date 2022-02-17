import java.util.ArrayList;

public class StundPerformer extends Performer{
    private String height;
    private ArrayList realActorIDs;

    public StundPerformer(String id,String name, String surname, String country,  String height, ArrayList realActorID) {
        super(name, surname, country, id);
        this.height = height;
        this.realActorIDs = realActorID;
    }

    public String getHeight() {
        return height;
    }

}
