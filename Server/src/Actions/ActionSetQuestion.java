package Actions;

import ServerUtil.Timeout;
import State.ServerState;
import questions.Question;
import questions.QuestionLoader;

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
			Question question = questionLoader.getQuestionForCountry(state.getCurrentCountry().getCountryCode());
			if(question == null){
				state.setQuestion("Keine Frage gefunden", ""); //todo Was dann?
			}else {
				state.setQuestion(question.question, question.answers.get(0)); //todo ggf. für mehrere Antworten optimieren :)
			}
			state.setState(ServerState.STATE_QUESTION);
		}, 5000);
	}
}
