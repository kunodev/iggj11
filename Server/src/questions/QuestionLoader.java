package questions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sneki on 15.07.2017.
 */
public class QuestionLoader {

    Map<String, List<Question>> questionsPerLand;

    public QuestionLoader(String[] csvFilePaths) {

        for(String s : csvFilePaths){

            if(s.isEmpty()){
                continue;
            }


            String line = "";
            String cvsSplitBy = ",";

            try (BufferedReader br = new BufferedReader(new FileReader(s))) {

                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] entries = line.split(cvsSplitBy);

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

//    public Question getQuestionForCountry(String country){
//
//
//
//    }



}
