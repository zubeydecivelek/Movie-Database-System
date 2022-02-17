import java.util.*;

public class Film {
    private String filmID;
    private String filmTitle;
    private String language;
    private ArrayList<String> directorIDs;
    private String runtime_length;
    private String country;
    private ArrayList<String> cast_performers;
    private ArrayList<Integer> ratingList = new ArrayList<>();

    public Film(String filmID, String filmTitle, String language, ArrayList<String> directorIDs, String runtime_length, String country, ArrayList<String> cast_performers) {
        this.filmID = filmID;
        this.filmTitle = filmTitle;
        this.language = language;
        this.runtime_length = runtime_length;
        this.country = country;
        this.directorIDs = directorIDs;
        this.cast_performers = cast_performers;
    }

    public double AverageRate(){
        int sum = 0;
        for (int i:ratingList){
            sum+=i;
        }
        double average = (double)sum/(double)ratingList.size();
        if (ratingList.isEmpty()){
            average = 0;
        }
        return Math.round(average * 10.0) / 10.0;
    }

    public String AverageRateString(Film film){
        double averageRate = film.AverageRate();
        String rate = String.valueOf(averageRate);
        rate = rate.replaceAll("\\.",",");
        if (rate.charAt(rate.length() - 1) == '0'){
            rate = rate.substring(0,rate.length()-2);
        }
        return rate;
    }

    public void EditRate(int index,int newRate){
        ratingList.remove(index);
        ratingList.add(newRate);
    }

    public void RemoveRate(int index){
        ratingList.remove(index);
    }

    public ArrayList<Integer> getRatingList() {
        return ratingList;
    }

    public void Rate(int rate){
        ratingList.add(rate);
    }

    public String getFilmID() {
        return filmID;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public String getLanguage() {
        return language;
    }

    public ArrayList<String> getDirectorIDs() {
        return directorIDs;
    }

    public String getRuntime_length() {
        return runtime_length;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getCast_performers() {
        return cast_performers;
    }

}
