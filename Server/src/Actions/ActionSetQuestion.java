package Actions;

import ServerUtil.Timeout;
import State.ServerState;
import questions.Question;
import questions.QuestionLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionSetQuestion extends AbstractAction
{

	public ActionSetQuestion() {
	}

	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		Timeout.setTimeout(() ->
		{

		    state.increaseCurrentRound();
		    if(state.getCurrentRound() > ServerState.ROUNDS_PER_COUNTRY) {

		        boolean isGleichstand = state.evaluateCountry();

		        if(isGleichstand){
		        	fireQuestion(state);
				}

                //todo hier den Endstand des Landes irgendwie anzeigen....?

                state.setCurrentRound(0);

		        //ActionEvaluateCountry ----> ActionStart

                actions.get(AbstractAction.ACTION_CHOSE_COUNTRY).execute(actions, state, params);
            }

            fireQuestion(state);

		}, 5000);
	}


	private void fireQuestion (ServerState state){
		Question question = null;

		//Wiederholfrage
		if(state.getCurrentRound() == 1 && state.getTotalRoundsInCountry(state.getCurrentCountry()) > 0){
			question = state.getQuestionLoader().getRepeatedQuestion(state.getCurrentCountry().getCountryCode());

			//Schätzfrage
		}else if(state.getCurrentRound() > ServerState.ROUNDS_PER_COUNTRY){

			question = state.getQuestionLoader().getGuesstimationQuestion(state.getCurrentCountry().getCountryCode());

			//Normale Frage
		}else{
			question = state.getQuestionLoader().getQuestionForCountry(state.getCurrentCountry().getCountryCode());
		}


		if(question == null){
			List<String> temp = new ArrayList<String>();
			temp.add("");
			state.setQuestion(new Question("", false, "Keine Frage gefunden", temp)); //todo Was dann?
		}else {
			state.setQuestion(question);
		}

		state.setState(ServerState.STATE_QUESTION);
	}
}
