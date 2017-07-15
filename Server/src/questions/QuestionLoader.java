package questions;

import java.io.*;
import java.util.*;

/**
 * Created by sneki on 15.07.2017.
 */
public class QuestionLoader {

    Map<String, List<Question>> questionsPerCountry;
    private String[] csvfiles;

    public QuestionLoader(String[] csvFilePaths) {

        this.csvfiles = csvFilePaths;
        loadQuestionsFromCSV(csvFilePaths);
    }


    private void loadQuestionsFromCSV(String[] csvFilePaths){

        questionsPerCountry = new HashMap<String, List<Question>>();

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
                        if(!questionsPerCountry.containsKey(entries[0])){
                            questionsPerCountry.put(entries[0], new ArrayList<Question>());
                        }

                        List<String> answers = new ArrayList<>();

                        for(int i=2; i<entries.length;i++){
                            answers.add(entries[i]);
                        }

                        questionsPerCountry.get(entries[0]).add(new Question(entries[1], answers));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public Map<String, List<Question>> getQuestionMap(){
        return questionsPerCountry;
    }


    /**
     * Spuckt ein zufälliges Question-Objekt zu dem Land aus, was übergeben wird.
     * Die gefundene Frage wird aus der Liste aller Fragen gelöscht.
     *
     * @param country Land für das man eine Frage will
     * @return wenn das Land nicht in der Map ist oder keine Fragen mehr da sind -> null
     */
    public Question getQuestionForCountry(String country){

        if(questionsPerCountry.containsKey(country)){
            return giveRandomQuestion(questionsPerCountry.get(country));
        }

        return null;
    }


    private Question giveRandomQuestion (List<Question> questions){

        Random random = new Random();
        return questions.size()>0 ? questions.remove(random.nextInt(questions.size()-1)) : null;
    }

    /**
     * Läd alle Fragen neu aus den Anfangs übergebenen CSV-Dateien ein,
     * falls man die Fragen zurücksetzen will
     */
    public void reloadAllQuestions(){
        loadQuestionsFromCSV(csvfiles);
    }

}
