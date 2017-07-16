package Actions;

import State.ServerState;

import java.util.Map;

public class ActionAnswerCheck extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		int userId = Integer.parseInt((String) params.get("user_id"));
		String answerState = (String) params.get("answer_state");
		state.questionJSON.setAnswerState(userId, Integer.parseInt(answerState));
	}
}
