package wordcloud;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WordCloud extends Application {
    FileReader inputFile;
    String[] bannedList = {"a", "about", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "both", "bottom","but", "by", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fifty", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own", "part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such",  "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves"};
    ArrayList<String> text;
   
    @Override
    public void start(Stage primaryStage) {
       
        BorderPane bp = new BorderPane();
       
        TextField fileNameField = new TextField();
        fileNameField.setPromptText("Enter your file name");
        
        //TODO: Add Prompt
        
        fileNameField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
               inputFile = openFile(fileNameField);
               loadArray(inputFile, text);
               /*parseArray();
               sortArray();
               countArray();
               showCloud();*/
            }});
        
        Button okayBtn = new Button("Okay");
        okayBtn.setOnAction(e -> {
            openFile(fileNameField);
            loadArray(inputFile, text);
            /*parseArray();
            sortArray();
            countArray();
            showCloud();*/
        });
        
        HBox box = new HBox(15);
        box.getChildren().addAll(fileNameField, okayBtn);
        bp.setCenter(box);
        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static FileReader openFile(TextField fileNameField){
        FileReader inputFile = null;
        String fileName = fileNameField.getText();
        try {inputFile = new FileReader(fileName);}
        catch (FileNotFoundException ex)
        {System.out.println("File not found: " + ex);}
        return inputFile;
    }
    
    public static void loadArray(FileReader inputFile, ArrayList<String> text){
        String line;
        int i = 0;
        BufferedReader br = new BufferedReader(inputFile);
        try {
            while ((line = br.readLine()) != null){
                text.set(i, line);
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(WordCloud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void parseArray(ArrayList<String> unParsed, ArrayList<String> parsed){
    }
    
}
