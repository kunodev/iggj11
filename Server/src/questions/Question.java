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

    public Question(String category, String question, List<String> answers) {
        this.question = question;
        this.category = category;
        this.answers = answers;
    }

    public JSONObject getAsJSONObject(){

        return null;
    }

}
