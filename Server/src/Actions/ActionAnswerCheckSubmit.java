package Actions;

import State.ServerState;

import java.util.Map;

public class ActionAnswerCheckSubmit extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		state.rewardCorrectAnswers();
		state.resetAnswers();
		actions.get(AbstractAction.ACTION_START).execute(actions, state, params);
	}
}
