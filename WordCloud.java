package wordCloud;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WordCloud extends Application {
    FileReader inputFile;
    String[] bannedList = {"a", "about", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "along", 
    		"already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any",
    		"anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back", "be","became", "because","become",
    		"becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond",
    		"both", "bottom","but", "by", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail",
    		"do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", 
    		"etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fifty", "first",
    		"five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "further", "get", "give", "go", "had", 
    		"has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him",
    		"himself", "his", "how", "however", "hundred", "i", "ie", "if", "in", "inc", "indeed", "into", "is", "it", "its", "itself", "keep",
    		"last", "latter", "latterly", "least", "less", "let", "ltd", "made", "many", "may", "me", "meanwhile", "might", "more", "moreover", 
    		"most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine",
    		"no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only",
    		"onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own", "part", "per", "perhaps",
    		"please", "put", "rather", "re", "said", "say", "says","same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "shall", 
    		"she", "should", "show", "side", "since", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere",
    		"still", "such",  "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter",
    		"thereby", "therefore", "therein", "thereupon", "these", "they", "thing", "things", "third", "this", "those", "though", "three", "through",
    		"throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under",
    		"until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever",
    		"where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who",
    		"whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours",
    		"yourself", "yourselves"};
    ArrayList<String> text = new ArrayList<String>();
    ArrayList<String> textParsed = new ArrayList<String>();
    ArrayList<String> bannedWords = new ArrayList<String>();
    ArrayList<Integer> wordCount = new ArrayList<Integer>();
   
    @Override
    public void start(Stage primaryStage) {
       
    	for (String s:bannedList)
    		bannedWords.add(s);
    	
        BorderPane bp = new BorderPane();
       
        TextField fileNameField = new TextField();
        fileNameField.setPromptText("Enter your file name");
        
        Text prompt = new Text("Enter your file name");
        prompt.setFont(new Font(16));
        
        ArrayList<Text> cloud = new ArrayList<Text>();
        ArrayList<HBox> lines = new ArrayList<HBox>();
        VBox column = new VBox();
                
        fileNameField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
               inputFile = openFile(fileNameField);
               try {
				loadArray(inputFile, text);
			} catch (IOException e1) {
				fileNameField.setText("");
				fileNameField.setPromptText("File Not Found");
			}
               parseArray(text, textParsed, bannedWords);
               Collections.sort(textParsed);
               wordCount = countArray(textParsed);
               showCloud(textParsed, wordCount, cloud, lines);
               column.getChildren().addAll(lines);
            }});
        
        Button okayBtn = new Button("Okay");
        okayBtn.setOnAction(e -> {
            openFile(fileNameField);
            try {
				loadArray(inputFile, text);
			} catch (IOException e1) {
				fileNameField.setText("");
				fileNameField.setPromptText("File Not Found");
			}
            parseArray(text, textParsed, bannedWords);
            Collections.sort(textParsed);
            wordCount = countArray(textParsed);
            showCloud(textParsed, wordCount, cloud, lines);
            column.getChildren().addAll(lines);
        });
        
        HBox box = new HBox(15);
        box.getChildren().addAll(prompt, fileNameField, okayBtn);
        bp.setBottom(box);
        bp.setCenter(column);
        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Word Cloud");
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(450);
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
    
    public static void loadArray(FileReader inputFile, ArrayList<String> text) throws IOException{
        String line;
        
        BufferedReader br = new BufferedReader(inputFile);
            while ((line = br.readLine()) != null){
                text.add(line);
            }
    }
    
    public static void parseArray(ArrayList<String> unParsed, ArrayList<String> parsed, ArrayList<String> bannedWords){
    	String[] parser;
    	for (String s:unParsed){
    		s = s.toLowerCase();
    		parser = (s.split("[\\s]"));
    		for (String x:parser){
    			x = x.replaceAll("[^a-zA-Z]", "");
    			if (!bannedWords.contains(x))
    				parsed.add(x);
    		}		
    	}
    }
    
    public static ArrayList<Integer> countArray(ArrayList<String> parsed){
		ArrayList<Integer> wordCount = new ArrayList<Integer>();
		
		for (int i = 0; i < parsed.size(); i++){
			wordCount.add(1);
		}
		
		for (int i = 0; i < parsed.size(); i++){
			parsed.set(i, parsed.get(i).toLowerCase());
		}
		
		for (int i = 1; i < parsed.size(); i++){
			while ((parsed.get(i).equals(parsed.get(i-1)))){
				wordCount.set(i -1, wordCount.get(i-1) + 1);
				parsed.remove(i);
			}
		}
		
		/*for (int i = 0; i < parsed.size(); i++){
			System.out.println(parsed.get(i) + "     " + wordCount.get(i));
		}*/
		
		return wordCount;
    }
    
    public static void showCloud(ArrayList<String> words, ArrayList<Integer> wordCount, ArrayList<Text> cloud, ArrayList<HBox> lines){
		
    	for (int i = 0; i < words.size(); i++){
    		if (wordCount.get(i) <= 2){
    			words.remove(i);
    			wordCount.remove(i);
    			i--;
    		}
    		
    		if (words.get(i).length() <= 2){
    			words.remove(i);
    			wordCount.remove(i);
    		}
    	}
    	
    	for (String s: words){
    		cloud.add(new Text(s + " "));
    	}
    	
    	int lineLength = 0;
    	
    	lines.add(new HBox());
    	int i = 0;
    	for (Text t: cloud){
    		lineLength += t.getText().length();
    		lines.get(i).getChildren().add(t);
    		if (lineLength >= 50){
    			i++;
    			lines.add(i, new HBox());
    			lineLength = 0;
    		}
    	}
    	    	
    	for (int j = 0; j < cloud.size(); j++){
    		cloud.get(j).setFont(new Font(10 + (wordCount.get(j) - 2) * 4));
    	}
    	
		/*for (int i = 0; i < words.size(); i++){
			System.out.println(words.get(i) + "     " + wordCount.get(i));
		}*/
    }
}
