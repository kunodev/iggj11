package Actions;

import State.ServerState;

import java.util.Map;

public class ActionChoseCountry extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		// todo only do this every X turns
		state.setCurrentCountry(state.getRandomCountry(state.getCurrentCountry().getCountryCode()));
	}
}
