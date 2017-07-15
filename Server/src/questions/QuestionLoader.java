package questions;

import java.io.*;
import java.util.*;

/**
 * Created by sneki on 15.07.2017.
 */
public class QuestionLoader {

    Map<String, List<Question>> questionsPerLand;

    public QuestionLoader(String[] csvFilePaths) {

        questionsPerLand = new HashMap<String, List<Question>>();

        for(String s : csvFilePaths){

            if(s.isEmpty()){
                continue;
            }

            String line = "";
            String cvsSplitBy = ",";

            InputStream is =ClassLoader.getSystemResourceAsStream(s);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                while ((line = br.readLine()) != null) {

                    String[] entries = line.split(cvsSplitBy);

                    for(int i = 0; i<entries.length;i++){
                        entries[i] = entries[i].replaceAll("\"", "");
                    }

                    if(entries.length>=2){
                        if(!questionsPerLand.containsKey(entries[0])){
                            questionsPerLand.put(entries[0], new ArrayList<Question>());
                        }

                        List<String> answers = new ArrayList<>();

                        for(int i=2; i<entries.length;i++){
                            answers.add(entries[i]);
                        }

                        questionsPerLand.get(entries[0]).add(new Question(entries[1], answers));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Map<String, List<Question>> getQuestionMap(){
        return questionsPerLand;
    }


    /**
     * Spuckt ein zufälliges Question-Objekt aus
     * @param country
     * @return wenn das Land nicht in der Map ist -> null
     */
    public Question getQuestionForCountry(String country){

        if(questionsPerLand.containsKey(country)){
            return giveRandomQuestion(questionsPerLand.get(country));
        }

        return null;
    }


    private Question giveRandomQuestion (List<Question> questions){

        Random random = new Random();
        return questions.get(random.nextInt(questions.size()-1));
    }

}
