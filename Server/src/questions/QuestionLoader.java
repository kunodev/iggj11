package questions;

import java.io.*;
import java.util.*;

/**
 * Created by sneki on 15.07.2017.
 */
public class QuestionLoader {

    Map<String, List<Question>> questionsPerLand;
    private String[] csvfiles;


    public QuestionLoader(String[] csvFilePaths) {

        this.csvfiles = csvFilePaths;
        loadQuestions(csvFilePaths);
    }


    public Map<String, List<Question>> getQuestionMap(){
        return questionsPerLand;
    }


    /**
     * Spuckt ein zufälliges Question-Objekt aus und löscht die gewählte
     * Frage aus der Fragen-Map
     *
     * @param country
     * @return wenn das Land nicht in der Map ist -> null
     */
    public Question getQuestionForCountry(String country){

        if(questionsPerLand.containsKey(country)){
            return giveRandomQuestion(questionsPerLand.get(country));
        }

        return null;
    }


    /**
     * Läd alle Fragen neu rein
     */
    public void reloadAllQuestions (){
        loadQuestions(csvfiles);
    }


    //-----------------

    //CSV-Aufbau: Land, Kategorie, Frage, Antworten..
    private void loadQuestions(String[] csvFilePaths){

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

                    if(entries.length>=3){
                        if(!questionsPerLand.containsKey(entries[0])){
                            questionsPerLand.put(entries[0], new ArrayList<Question>());
                        }

                        List<String> answers = new ArrayList<>();

                        for(int i=3; i<entries.length;i++){
                            answers.add(entries[i]);
                        }

                        questionsPerLand.get(entries[0]).add(new Question(entries [1], entries[2], answers));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Question giveRandomQuestion (List<Question> questions){

        Random random = new Random();
        switch (questions.size()){
            case 0: return null;
            case 1: return questions.remove(0);
            default: return questions.remove(random.nextInt(questions.size()-1));
        }
    }
}
