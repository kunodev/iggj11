package Actions;

import ServerUtil.Timeout;
import State.ServerState;

import java.util.Map;

public class ActionSetQuestion extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		Timeout.setTimeout(() ->
		{
			// todo: Frage aus GameDesign Daten generieren
			state.setQuestion("Warum macht deine Mudda Passfotos bei GoogleEarth?", "Weil sie ein Weltling ist!");
			state.setState(ServerState.STATE_QUESTION);
		}, 5000);
	}
}
