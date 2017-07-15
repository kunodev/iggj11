package Actions;

import Entity.User;
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
			if(state.getCurrentQuestionObject().isGuesstimation){
				User winner = state.getUserById( state.getQuestionLoader().evaluateGuesstimationQuestion(actions, state));
				if(winner == null){
					return;
				}else{
					winner.addPoints(ServerState.POINTS_CONQUER_COUNTRY);

				}
			}else {
				state.setState(ServerState.STATE_ANSER_CHECK);
			}
		}
	}
}
