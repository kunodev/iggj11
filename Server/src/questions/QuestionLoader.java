package questions;

import java.io.*;
import java.util.*;

/**
 * Created by sneki on 15.07.2017.
 */
public class QuestionLoader {

    Map<String, List<Question>> questionsPerCountry;
    Map<String, List<Question>> alreadyAskedQuestions;
    private String[] csvfiles;


    public QuestionLoader(String[] csvFilePaths) {

        this.csvfiles = csvFilePaths;
        alreadyAskedQuestions = new HashMap<String, List<Question>>();
        questionsPerCountry = new HashMap<String, List<Question>>();
        loadQuestions(csvFilePaths);
    }


    public Map<String, List<Question>> getQuestionMap(){
        return questionsPerCountry;
    }


    /**
     * Spuckt ein zufälliges Question-Objekt aus und löscht die gewählte
     * Frage aus der Fragen-Map
     *
     * @param country
     * @return wenn das Land nicht in der Map ist -> null
     */
    public Question getQuestionForCountry(String country){

        if(questionsPerCountry.containsKey(country)){
            return giveRandomQuestion(questionsPerCountry.get(country), true);
        }

        return null;
    }


    public void addRepeatQuestion(Question question, String country){

        if(alreadyAskedQuestions.containsKey(country)){
            alreadyAskedQuestions.get(country).add(question);
        }else{
            alreadyAskedQuestions.put(country, new ArrayList<Question>());
            alreadyAskedQuestions.get(country).add(question);
        }

    }


    public Question getRepeatedQuestion(String country){

        if(alreadyAskedQuestions.containsKey(country)){

            return giveRandomQuestion(alreadyAskedQuestions.get(country), false);

        }else{
            if(questionsPerCountry.containsKey(country)){

                return giveRandomQuestion(questionsPerCountry.get(country), true);

            }else{

                return null;

            }
        }

    }


    /**
     * Läd alle Fragen neu rein
     */
    public void reloadAllQuestions (){
        loadQuestions(csvfiles);
    }


    public boolean isRepeatedQuestion(Question question, String country){

        if(alreadyAskedQuestions.containsKey(country) && alreadyAskedQuestions.get(country).contains(question)){
            return true;
        }

        return false;
    }




    //-----------------

    //CSV-Aufbau: Land, Kategorie, Frage, Antworten..
    private void loadQuestions(String[] csvFilePaths){

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
                        if(!questionsPerCountry.containsKey(entries[0])){
                            questionsPerCountry.put(entries[0], new ArrayList<Question>());
                        }

                        List<String> answers = new ArrayList<>();

                        for(int i=3; i<entries.length;i++){
                            answers.add(entries[i]);
                        }

                        questionsPerCountry.get(entries[0]).add(new Question(entries [1], entries[2], answers));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Question giveRandomQuestion (List<Question> questions, boolean remove){

        Random random = new Random();
        if(remove){
            switch (questions.size()){
                case 0: return null;
                case 1: return questions.remove(0);
                default: return questions.remove(random.nextInt(questions.size()-1));
            }
        }else{
            switch (questions.size()){
                case 0: return null;
                case 1: return questions.get(0);
                default: return questions.get(random.nextInt(questions.size()-1));
            }
        }

    }


}
