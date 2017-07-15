package State;

import Entity.Country;
import Entity.User;
import JSONUtil.JSONArrayList;
import JSONUtil.JSONHashMap;
import JSONUtil.JSONSerializable;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import questions.Question;
import questions.QuestionLoader;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class ServerState implements JSONSerializable
{
	public static final String STATE_LOBBY       = "lobby";
	public static final String STATE_WORLD       = "world";
	public static final String STATE_QUESTION    = "question";
	public static final String STATE_ANSER_CHECK = "answerCheck";

	public static final String STATE_ANSWER_STATE_UNKNOWN   = "unknown";
	public static final String STATE_ANSWER_STATE_CORRECT   = "correct";
	public static final String STATE_ANSWER_STATE_INCORRECT = "incorrect";

	public static final int POINTS_CORRECT_ANSWER = 2;
	public static final int POINTS_CORRECT_REPEAT = 1;
	public static final int POINTS_CONQUER_COUNTRY = 1;
	public static final int ROUNDS_PER_COUNTRY = 3;

	private int maxUserId = 0;

	private String                 state     = STATE_LOBBY;
	private JSONArrayList<User>    users     = new JSONArrayList<>();
	private JSONArrayList<Country> countries = new JSONArrayList<>();

	private JSONHashMap<String, Integer> countryOwners = new JSONHashMap<>();
	private Question currentQuestionObject;
	private JSONHashMap<Integer, String> givenAnswers = new JSONHashMap<>();
	private JSONHashMap<Integer, Integer> answerStates = new JSONHashMap<>();
	private JSONArrayList<Integer> finishedIds = new JSONArrayList<Integer>();

//	private String currentQuestion;
//	private String currentAnswer;
	private Country currentCountry;

    private QuestionLoader questionLoader = new QuestionLoader(new String[]{
            "questions/files/Egypt.txt",
            "questions/files/France.txt",
            "questions/files/russia.txt",
            "questions/files/USA.txt"});

//    private QuestionLoader questionLoader = new QuestionLoader(new String[]{
//            "questions/Beispiel.csv"}); //zum Testen mit Beispiel :) :)



    public QuestionLoader getQuestionLoader(){
        return questionLoader;
    }

	private int currentRound = 0;

	public void addUser(User user)
	{
		this.users.add(user);
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public void setQuestion(Question question)
	{
		this.currentQuestionObject = question;
	}

	public void addAnswer(int userId, String answer)
	{
		this.givenAnswers.put(userId, answer);
		this.finishedIds.add(userId);
	}

	public void setAnswerState(int userId, int state)
	{
		this.answerStates.put(userId, state);
	}

	public void rewardCorrectAnswers()
	{

		for (Object o : answerStates.entrySet())
		{
			Map.Entry pair = (Map.Entry) o;

			int    userId      = (int) pair.getKey();
			int    answerState = (int) pair.getValue();

			if (answerState > 0)
			{

			    if(questionLoader.isRepeatedQuestion(currentQuestionObject, currentCountry.getCountryCode())){
                    this.getUser(userId).addPoints(POINTS_CORRECT_REPEAT * answerState);
                }else{
                    this.getUser(userId).addPoints(POINTS_CORRECT_ANSWER * answerState);
                }

				this.getUser(userId).addCorrectAnsweredQuestion(currentQuestionObject);
			}
		}

        questionLoader.addRepeatQuestion(currentQuestionObject, currentCountry.getCountryCode());
	}

	public void resetAnswers()
	{
		this.givenAnswers.clear();
		this.answerStates.clear();
		this.finishedIds.clear();
	}

	public int generateUserId()
	{
		return ++this.maxUserId;
	}

	public boolean didAllPlayersAnswer()
	{
		return this.givenAnswers.keySet().size() == this.users.size();
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

	public void setCurrentCountry(Country country)
	{
		this.currentCountry = country;
	}

	public Country getCurrentCountry()
	{
		return this.currentCountry;
	}

	public JSONAware toJSON()
	{
		JSONObject obj = new JSONObject();

		obj.put("users", users.toJSON());
		obj.put("countries", countries.toJSON());
		obj.put("state", state);

		JSONObject stateData = new JSONObject();

		switch (state)
		{
			case STATE_WORLD:
				stateData.put("countries", countryOwners.toJSON());
				break;

			case STATE_QUESTION:
                stateData.put("currentCountry", currentCountry.getCountryCode());
				stateData.put("question", currentQuestionObject.question);
				stateData.put("finishedUsers",this.finishedIds.toJSON());
				break;

			case STATE_ANSER_CHECK:
				stateData.put("realAnswer", currentQuestionObject.answers.get(0));	//todo mehrere Antworten ermöglichen?
				stateData.put("answers", givenAnswers.toJSON());
				stateData.put("answerStates", answerStates.toJSON());
				break;
		}

		obj.put("stateData", stateData);

		return obj;
	}

	private User getUser(int userId)
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

	public int getCurrentRound (){
	    return currentRound;
    }

    public void increaseCurrentRound(){
	    currentRound++;
    }

    public void setCurrentRound(int i){
        currentRound = i;
    }

    /**
     * Auswertung nach X Runden im Land und Umverteilung des Landes
     */
    public void evaluateCountry(){

        int highestScore = 0;
        User winner = null;

        for(User u : users){
            //Todo Gleichstände beachten
            if(u.getPoints() > highestScore){
                winner = u;
                highestScore = u.getPoints();
            }
        }

        //Todo Gleichstände beachten
        if(highestScore> currentCountry.getHighscore()){
            if(winner != null){
                winner.addPoints(POINTS_CONQUER_COUNTRY);   //Bonuspunkte für Übernahme des Landes
                countryOwners.put(currentCountry.getCountryCode(),  winner.getId());
            }
        }

        //todo irgendwie mitteilen wer das aktuelle Land jetzt übernommen hat + die Punktzahl

        for(User u : users){
            u.resetPoints();
        }

    }

}
