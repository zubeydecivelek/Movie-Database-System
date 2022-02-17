import java.util.ArrayList;

public class FeatureFilm extends Film{
    private String genres;
    private String releaseDate;
    private ArrayList<String> writerIDs;
    private String budget;

    public FeatureFilm(String filmID, String filmTitle, String language, ArrayList<String> directorIDs, String runtime_length, String country, ArrayList<String> cast_performers, String genres, String releaseDate, ArrayList<String> writerIDs, String budget) {
        super(filmID, filmTitle, language, directorIDs, runtime_length, country, cast_performers);
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.writerIDs = writerIDs;
        this.budget = budget;
    }

    public String getGenres() {
        return genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public ArrayList<String> getWriterIDs() {
        return writerIDs;
    }

    public String getBudget() {
        return budget;
    }
}
