import java.util.ArrayList;

public class Documentary extends Film{
    private String releaseDate;

    public Documentary(String filmID, String filmTitle, String language, ArrayList<String> directorIDs, String runtime_length, String country, ArrayList<String> cast_performers, String releaseDate) {
        super(filmID, filmTitle, language, directorIDs, runtime_length, country, cast_performers);
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
