package Actions;

import Entity.Country;
import State.ServerState;

import java.util.Map;

public class ActionChoseCountry extends AbstractAction
{
	@Override
	public void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params)
	{
		// todo only do this every X turns
		String  excludeCountry = "";
		Country currentCountry = state.getCurrentCountry();
		if (currentCountry != null) {
			excludeCountry = currentCountry.getCountryCode();
		}
		state.setCurrentCountry(state.getRandomCountry(excludeCountry));
	}
}
