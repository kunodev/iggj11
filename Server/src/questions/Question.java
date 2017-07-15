package questions;

import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by sneki on 15.07.2017.
 */
public class Question {

    public String question;
    public String category;
    public List<String> answers;
    public boolean isGuesstimation;

    public Question(String category, boolean isGuesstimation, String question, List<String> answers) {
        this.question = question;
        this.category = category;
        this.answers = answers;
        this.isGuesstimation = isGuesstimation;
    }

    public JSONObject getAsJSONObject(){

        return null;
    }

}
