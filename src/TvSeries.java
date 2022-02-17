import java.util.ArrayList;

public class TvSeries extends Film{
    private String genres;
    private ArrayList<String> writerIDs;
    private String startDate;
    private String endDate;
    private String seasons;
    private String episodes;

    public TvSeries(String filmID, String filmTitle, String language, ArrayList<String> directorIDs, String runtime_length, String country, ArrayList<String> cast_performers, String genres, ArrayList<String> writerIDs, String startDate, String endDate, String seasons, String episodes) {
        super(filmID, filmTitle, language, directorIDs, runtime_length, country, cast_performers);
        this.genres = genres;
        this.writerIDs = writerIDs;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seasons = seasons;
        this.episodes = episodes;
    }

    public String getGenres() {
        return genres;
    }

    public ArrayList<String> getWriterIDs() {
        return writerIDs;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSeasons() {
        return seasons;
    }

    public String getEpisodes() {
        return episodes;
    }
}
