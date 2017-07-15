package Actions;

import Entity.User;
import State.ServerState;

import java.util.Map;

public class ActionLogin extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		state.sessionJSON.addUser(new User(state.sessionJSON.generateUserId(), (String) params.get("name")));
	}
}
