package questions;

import Actions.AbstractAction;
import JSONUtil.JSONHashMap;
import State.ServerState;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by sneki on 15.07.2017.
 */
public class
QuestionLoader {

    Map<String, List<Question>> questionsPerCountry;
    Map<String, List<Question>> alreadyAskedQuestions;

    private Guesstimator guesstimator;

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

    /**
     * Return die UserId des Gewinners
     * @param actions
     * @param state
     * @return
     */
    public int evaluateGuesstimationQuestion(Map<String, AbstractAction> actions, ServerState state){

        JSONHashMap<Integer, String> answers = state.getGivenAnswers();

        int winner = -1;
        BigDecimal realAnswer = new BigDecimal(state.getCurrentQuestionObject().answers.get(0));
        BigDecimal closestAnswer = null;

        for(Map.Entry<Integer, String> entry : answers.entrySet()){

            BigDecimal answer = new BigDecimal(entry.getValue());

            BigDecimal differenz = realAnswer.subtract(answer);

            differenz = differenz.abs();

            if(closestAnswer == null){
                closestAnswer = differenz;
                winner = entry.getKey();
            }else {

                if (differenz.compareTo(closestAnswer) == -1) {
                    winner = entry.getKey();
                }
            }
        }

        return winner;
    }


    public Question getGuesstimationQuestion(String country){
        if(guesstimator.getGuesstimationQuestions().containsKey(country)){
            return giveRandomQuestion(guesstimator.getGuesstimationQuestions().get(country), true);
        }

        return null;
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

    //CSV-Aufbau: Land, Kategorie, Schätzfrage, Frage, Antworten..
    private void loadQuestions(String[] csvFilePaths){

        for(String s : csvFilePaths){

            if(s.isEmpty()){
                continue;
            }

            String line = "";
            String cvsSplitBy = "\",\"";

            InputStream is =ClassLoader.getSystemResourceAsStream(s);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                while ((line = br.readLine()) != null) {

                    String[] entries = line.split(cvsSplitBy);

                    //" zu Anfang und Ende der Zeile rauswerfen
                    for(int i = 0; i<entries.length;i++){
                        entries[i] = entries[i].replaceAll("\"", "");
                    }

                    //Wenn das Entry länger als xx ist, gibt es Antworten
                    if(entries.length>=4){
                        if(!questionsPerCountry.containsKey(entries[0])){
                            questionsPerCountry.put(entries[0], new ArrayList<Question>());
                        }

                        List<String> answers = new ArrayList<>();

                        for(int i=4; i<entries.length;i++){
                            answers.add(entries[i]);
                        }
                        boolean isGuesstimation = entries[2].equals("s");
                        questionsPerCountry.get(entries[0]).add(new Question(entries [1], isGuesstimation, entries[3], answers));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        guesstimator = new Guesstimator(questionsPerCountry);

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
