import java.util.*;

public class User extends Person{
    private LinkedHashMap<String,Integer> rateDict = new LinkedHashMap<>();

    public User( String id, String name, String surname, String country) {
        super(name, surname, country, id);
    }

    public HashMap<String, Integer> getRateDict() {
        return rateDict;
    }

    public void Rate(String title, int rate){
        rateDict.put(title,rate);
    }
    public void EditRate(String title, int rate){
        rateDict.replace(title,rate);
    }
    public void RemoveRate(String title){
        rateDict.remove(title);
    }
}
