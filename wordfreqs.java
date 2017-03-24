import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WordFreqs {
    
    public static void main(String[] args){
        
        //Initiliaze variables
        List<String> stop_words = new ArrayList<String>(), words = new ArrayList<String>();
        HashMap<String,Integer> word_freqs = new HashMap<String,Integer>();
        int n = 0;

        try {

            //Read stop words file, replace non-letter characters by spaces, split by spaces, and store stop words
            for (String stop_word : ((new String(Files.readAllBytes(Paths.get("stop_words.txt")))).replaceAll("[^A-Za-z]", " ").toLowerCase()).split("\\s+")) 
                stop_words.add(stop_word);
            
            //Read non stop words file, replace non-letter character by spaces, split by spaces, and store non stop words
            for (String word : ((new String(Files.readAllBytes(Paths.get(args[0])))).replaceAll("[^A-Za-z]", " ").toLowerCase()).split("\\s+")) 
                if (!stop_words.contains(word) && word.length() > 1) 
                    words.add(word);

            //Loop through non stop words and store word frequencies into Hash Map
            for (String word : words) 
                if (word_freqs.containsKey(word)) 
                    word_freqs.put(word,word_freqs.get(word) + 1); 
                else 
                    word_freqs.put(word,1);

            //Sort the word frequencies
            List<Map.Entry<String,Integer>> sorted_entries = new LinkedList<Map.Entry<String,Integer>>(word_freqs.entrySet());
            Collections.sort(sorted_entries, new Comparator<Map.Entry<String,Integer>>() { 
                @Override public int compare(Map.Entry<String,Integer> e1, Map.Entry<String,Integer> e2){ 
                    return e2.getValue().compareTo(e1.getValue());
                }
            });

            //Print out the first 25 sorted word frequencies
            for (Map.Entry<String,Integer> entry : sorted_entries) 
                if (n++ < 25) 
                    System.out.println(entry.getKey() + "  -  " + entry.getValue());

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}