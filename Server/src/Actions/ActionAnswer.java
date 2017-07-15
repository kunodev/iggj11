package Actions;

import State.ServerState;

import java.util.Map;

public class ActionAnswer extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		int userId = Integer.parseInt((String) params.get("user_id"));
		String answer = (String) params.get("answer");

		state.addAnswer(userId, answer);

		if (state.didAllPlayersAnswer())
		{
			state.setState(ServerState.STATE_ANSER_CHECK);
		}
	}
}
