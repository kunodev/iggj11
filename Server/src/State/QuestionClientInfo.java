package State;

import JSONUtil.JSONArrayList;
import JSONUtil.JSONHashMap;

/**
 * Created by kuro on 7/16/17.
 */
public class QuestionClientInfo {
    // Question Mode Persistent
    protected JSONHashMap<Integer, String> givenAnswers = new JSONHashMap<>();
    protected JSONHashMap<Integer, Integer> answerStates = new JSONHashMap<>();
    protected JSONArrayList<Integer> finishedIds = new JSONArrayList<Integer>();


    public void addAnswer(int userId, String answer)
    {
        this.givenAnswers.put(userId, answer);
        this.finishedIds.add(userId);
    }

    public void setAnswerState(int userId, int state)
    {
        this.answerStates.put(userId, state);
    }
    public void resetAnswers()
    {
        givenAnswers.clear();
        answerStates.clear();
        finishedIds.clear();
    }



}
