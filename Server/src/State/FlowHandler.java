package State;

import Entity.Country;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kuro on 7/16/17.
 */
public class FlowHandler {

    private Country currentCountry;
    private Country lastCountry;

    private int currentRound = 0;
    private Map<String, Integer> totalCountryRounds = new HashMap<String, Integer>();

    public static final int ROUNDS_PER_COUNTRY = 3;

    public Country getCurrentCountry()
    {
        return this.currentCountry;
    }

    public boolean wasLastRound() {
        return this.currentRound >= ROUNDS_PER_COUNTRY-1;
    }

    public void setNextRound(SessionClientInfo sessionJSON) {
        if(this.currentRound >= ROUNDS_PER_COUNTRY) {
            String  excludeCountry = "";
            Country currentCountry = getCurrentCountry();
            if (currentCountry != null) {
                excludeCountry = currentCountry.getCountryCode();
            }
            this.changeCurrentCountry(sessionJSON.getRandomCountry(excludeCountry));
            this.resetCurrentRound();
        } else {
            currentRound++;
        }
    }

    private void changeCurrentCountry(Country country)
    {
        if(currentCountry != null ) {
            //Increment passed rounds

            int amountRoundsPassed = this.getTotalRoundsInCountry(currentCountry);
            this.totalCountryRounds.put(this.currentCountry.getCountryCode(), amountRoundsPassed +1);
        }
        this.currentCountry = country;
    }

    private void resetCurrentRound() {
        currentRound = 0;
    }

    public int getTotalRoundsInCountry(Country currentCountry) {
        if(this.totalCountryRounds.containsKey(currentCountry.getCountryCode())) {
            return totalCountryRounds.get(currentCountry.getCountryCode());
        }
        return 0;
    }

    public boolean isRepeatRoound() {
        return currentRound == 1 && this.getTotalRoundsInCountry(getCurrentCountry()) > 0;
    }

    public void initRandCountry(SessionClientInfo sessionJSON) {
       this.changeCurrentCountry(sessionJSON.getRandomCountry(null ));
    }
}
