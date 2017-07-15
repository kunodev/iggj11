package Actions;

import ServerUtil.Timeout;
import State.ServerState;
import questions.Question;
import questions.QuestionLoader;

import java.util.ArrayList;
import java.util.Map;

public class ActionSetQuestion extends AbstractAction
{
	private QuestionLoader questionLoader;

	public ActionSetQuestion() {
		questionLoader = new QuestionLoader(new String[]{"questions/Beispiel.csv"});
	}

	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		Timeout.setTimeout(() ->
		{
			actions.get(AbstractAction.ACTION_CHOSE_COUNTRY).execute(actions, state, params);

			Question question = questionLoader.getQuestionForCountry(state.getCurrentCountry().getCountryCode());
			if(question == null){
				state.setQuestion(new Question("","Keine Frage gefunden", new ArrayList<>())); //todo Was dann?
			}else {
				state.setQuestion(question);
			}
			state.setState(ServerState.STATE_QUESTION);
		}, 5000);
	}
}
