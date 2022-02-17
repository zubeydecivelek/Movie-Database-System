import java.util.ArrayList;

public class ShortFilm extends Film{
    private String genres;
    private String releaseDate;
    private ArrayList<String> writerIDs;

    public ShortFilm(String filmID, String filmTitle, String language, ArrayList<String> directorIDs, String runtime_length, String country, ArrayList<String> cast_performers, String genres, String releaseDate, ArrayList<String> writerIDs) {
        super(filmID, filmTitle, language, directorIDs, runtime_length, country, cast_performers);
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.writerIDs = writerIDs;
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
}
