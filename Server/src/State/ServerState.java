package State;

import JSONUtil.JSONSerializable;
import ServerUtil.Timeout;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import questions.Question;
import questions.QuestionLoader;

import java.util.*;

public class ServerState implements JSONSerializable
{
	public static final String STATE_LOBBY       = "lobby";
	public static final String STATE_WORLD       = "world";
	public static final String STATE_QUESTION    = "question";
	public static final String STATE_ANSER_CHECK = "answerCheck";

	public static final int POINTS_CORRECT_ANSWER = 2;
	public static final int POINTS_CORRECT_REPEAT = 1;


	private String                 state     = STATE_LOBBY;

	private Question currentQuestionObject;

	public SessionClientInfo sessionJSON = new SessionClientInfo();
	public QuestionClientInfo questionJSON = new QuestionClientInfo();
	private FlowHandler flow = new FlowHandler();

    private QuestionLoader questionLoader = new QuestionLoader(new String[]{
            "questions/files/Egypt.txt",
            "questions/files/France.txt",
            "questions/files/russia.txt",
            "questions/files/USA.txt",
			"questions/files/australia.txt",
			"questions/files/japan.txt",
			"questions/files/Deutschland.txt"});

//    private QuestionLoader questionLoader = new QuestionLoader(new String[]{
//            "questions/Beispiel.csv"}); //zum Testen mit Beispiel :) :)

    public QuestionLoader getQuestionLoader(){
        return questionLoader;
    }

	public void setState(String state)
	{
		this.state = state;
	}

	public void setQuestion(Question question)
	{
		this.currentQuestionObject = question;
	}

	public void InitSession() {
    	this.flow.setNextRound(this.sessionJSON);
    	this.flow.initRandCountry(this.sessionJSON);
		setState(ServerState.STATE_WORLD);
		this.startNextQuestion();
	}

	public void rewardCorrectAnswers()
	{
		for (Object o : questionJSON.answerStates.entrySet())
		{
			Map.Entry pair = (Map.Entry) o;

			int    userId      = (int) pair.getKey();
			int    answerState = (int) pair.getValue();

			if (answerState > 0)
			{

			    if(questionLoader.isRepeatedQuestion(currentQuestionObject, flow.getCurrentCountry().getCountryCode())){
                    sessionJSON.getUser(userId).addPoints(POINTS_CORRECT_REPEAT * answerState);
                }else{
					sessionJSON.getUser(userId).addPoints(POINTS_CORRECT_ANSWER * answerState);
                }

				sessionJSON.getUser(userId).addCorrectAnsweredQuestion(currentQuestionObject);
			}
		}
        questionLoader.addRepeatQuestion(currentQuestionObject, flow.getCurrentCountry().getCountryCode());
	}

	public boolean didAllPlayersAnswer()
	{
		return questionJSON.givenAnswers.keySet().size() == sessionJSON.users.size();
	}
	public JSONAware toJSON()
	{
		JSONObject obj = new JSONObject();

		obj.put("users", sessionJSON.users.toJSON());
		obj.put("countries", sessionJSON.countries.toJSON());
		obj.put("state", state);

		JSONObject stateData = new JSONObject();

		switch (state)
		{
			case STATE_WORLD:
				stateData.put("countries", sessionJSON.countryOwners.toJSON());
				break;

			case STATE_QUESTION:
                stateData.put("currentCountry", flow.getCurrentCountry().getCountryCode());
				stateData.put("question", currentQuestionObject.question);
				stateData.put("finishedUsers", questionJSON.finishedIds.toJSON());
				break;

			case STATE_ANSER_CHECK:
				stateData.put("realAnswer", currentQuestionObject.answers.get(0));	//todo mehrere Antworten ermöglichen?
				stateData.put("answers", questionJSON.givenAnswers.toJSON());
				stateData.put("answerStates", questionJSON.answerStates.toJSON());
				boolean repeatedQuestion = questionLoader.isRepeatedQuestion(currentQuestionObject, flow.getCurrentCountry().getCountryCode());
				stateData.put("repeatMultiplier", repeatedQuestion ? POINTS_CORRECT_REPEAT : POINTS_CORRECT_ANSWER);
				break;
		}

		obj.put("stateData", stateData);

		return obj;
	}

	public void startNextQuestion() {

    	if(flow.wasLastRound()) {
			boolean draw = sessionJSON.evaluateCountryCheckDraw(flow.getCurrentCountry());
			if(draw) {

				//TODO: extra round and return

			}

			sessionJSON.evaluateCountry(flow.getCurrentCountry());
		}
		this.flow.setNextRound(this.sessionJSON);

		rollSimpleQuestions();
		setState(ServerState.STATE_QUESTION);
	}

	private void rollSimpleQuestions()  {
		currentQuestionObject = getQuestionLoader().getQuestionForCountry(flow.getCurrentCountry().getCountryCode());
	}
}
