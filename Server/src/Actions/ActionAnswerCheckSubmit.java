package Actions;

import ServerUtil.Timeout;
import State.ServerState;

import java.util.Map;

public class ActionAnswerCheckSubmit extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		state.rewardCorrectAnswers();
		state.questionJSON.resetAnswers();
		state.setState(ServerState.STATE_WORLD);
		Timeout.setTimeout(() -> {state.startNextQuestion();}, 3000);
	}
}
