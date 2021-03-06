package Actions;

import State.ServerState;

import java.util.Map;

public abstract class AbstractAction
{
	public static final String ACTION_LOGIN               = "login";
	public static final String ACTION_START               = "startgame";
	public static final String ACTION_ANSWER              = "answer";
	public static final String ACTION_ANSWER_CHECK        = "answercheck";
	public static final String ACTION_ANSWER_CHECK_SUBMIT = "answerchecksubmit";
	public static final String ACTION_EVALUATE_COUNTRY	  = "evaulatecountry";

	public abstract void execute(Map<String, AbstractAction> actions, ServerState state, Map<String, Object> params);

}
