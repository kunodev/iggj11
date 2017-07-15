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


		        state.evaluateCountry();

                //todo hier den Endstand des Landes irgendwie anzeigen....?

                state.setCurrentRound(0);

		        //ActionEvaluateCountry ----> ActionStart

                actions.get(AbstractAction.ACTION_CHOSE_COUNTRY).execute(actions, state, params);
            }

            Question question = null;
            if(state.getCurrentRound() == ServerState.ROUNDS_PER_COUNTRY){
		        question = state.getQuestionLoader().getRepeatedQuestion(state.getCurrentCountry().getCountryCode());
            }else {
                question = state.getQuestionLoader().getQuestionForCountry(state.getCurrentCountry().getCountryCode());
            }

			if(question == null){
                List<String> temp = new ArrayList<String>();
                temp.add("");
				state.setQuestion(new Question("","Keine Frage gefunden", temp)); //todo Was dann?
			}else {
				state.setQuestion(question);
			}

			state.setState(ServerState.STATE_QUESTION);
		}, 5000);
	}
}
