package Actions;

import State.ServerState;

import java.util.Map;

public class ActionStart extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		state.setState(ServerState.STATE_WORLD);
		actions.get(AbstractAction.ACTION_SET_QUESTION).execute(actions, state, params);
	}
}
