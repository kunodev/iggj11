package State;

import Entity.Country;
import Entity.User;
import JSONUtil.JSONArrayList;
import JSONUtil.JSONHashMap;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kuro on 7/16/17.
 */
public class SessionClientInfo {

    //Session Persistent
    protected JSONArrayList<User>    users     = new JSONArrayList<>();
    protected JSONArrayList<Country> countries = new JSONArrayList<>();
    protected JSONHashMap<String, Integer> countryOwners = new JSONHashMap<>();
    public static final int POINTS_CONQUER_COUNTRY = 1;

    private int maxUserId = 0;

    public int generateUserId()
    {
        return this.maxUserId++;
    }

    public void addUser(User user)
    {
        users.add(user);
    }

    public void addCountry(Country c)
    {
        this.countries.add(c);
    }


    public Country getRandomCountry(String excludedCountry)
    {
        ArrayList<Country> validCountries = new ArrayList<>();
        for (Country c : this.countries)
        {
            if (c.getCountryCode().equals(excludedCountry))
            {
                continue;
            }

            validCountries.add(c);
        }

        int index = (new Random()).nextInt(validCountries.size());
        return validCountries.get(index);
    }



    public User getUser(int userId)
    {
        for (User u : users)
        {
            if (u.getId() == userId)
            {
                return u;
            }
        }
        return null;
    }


    /**
     * Auswertung nach X Runden im Land und Umverteilung des Landes
     */
    public boolean	evaluateCountryCheckDraw(Country currentCountry){

        int highestScore = getMaxPoints();

        if(users.stream().filter(u -> u.getPoints() == highestScore).count() > 1) {
            return true;
        }
        return false;
    }

    private int getMaxPoints() {
        int highestScore = users.stream().mapToInt(u -> u.getPoints()).max().getAsInt();
        return highestScore;
    }

    public void evaluateCountry(Country current) {
        int highestScore = getMaxPoints();
        User winner = users.stream().filter(u -> u.getPoints() == highestScore).findFirst().get();
        if(highestScore > current.getHighscore()){
            if(winner != null){
                winner.addPoints(POINTS_CONQUER_COUNTRY);   //Bonuspunkte für Übernahme des Landes
                countryOwners.put(current.getCountryCode(),  winner.getId());
            }
        }

        //todo irgendwie mitteilen wer das aktuelle Land jetzt übernommen hat + die Punktzahl

        for(User u : users){
            u.resetPoints();
        }
    }
}
