package Actions;

import State.ServerState;

import java.util.Map;

public class ActionAnswerCheckSubmit extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		state.rewardCorrectAnswers();
		state.questionJSON.resetAnswers();
		state.startNextQuestion();
	}
}
