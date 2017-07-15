package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sneki on 15.07.2017.
 */
public class Guesstimator {

    Map<String, List<Question>> guesstimationQuestions;

    public Guesstimator(Map<String, List<Question>> allQuestions) {
        guesstimationQuestions = new HashMap<String, List<Question>>();
        extractGuesstimations(allQuestions);
    }

    private void extractGuesstimations(Map<String, List<Question>> allQuestions){

        for(Map.Entry<String, List<Question>> entry : allQuestions.entrySet()){

            if(!guesstimationQuestions.containsKey(entry.getKey())){
                guesstimationQuestions.put(entry.getKey(), new ArrayList<Question>());
            }

            List<Question> deleteQuestions = new ArrayList<Question>();
            for(Question q : entry.getValue()){

                if(q.isGuesstimation){
                    guesstimationQuestions.get(entry.getKey()).add(q);
                    deleteQuestions.add(q);
                }

            }

            entry.getValue().removeAll(deleteQuestions);

        }
    }


    public Map<String, List<Question>> getGuesstimationQuestions(){
        return guesstimationQuestions;
    }




}
