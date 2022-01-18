package com.example.speechtotext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

public class Command {

    private static HashMap<String,String[]> myCommands = new HashMap<String, String[]>();
    private static HashSet<String> myPlaces = new HashSet<String>();
    static{
        String[] commands={"navigate","guide","show","path","way","take","tell",};
        myCommands.put("navigate",commands);
        commands= new String[]{"help","emergency"};
        myCommands.put("help",commands);
        String[] places = {"tube Station", "parking", "elivator", "platform", "washroom"};
        myPlaces.addAll(Arrays.asList(places));


    }


    public ArrayList<String>  cleanSentence(String sentence){

        ArrayList<String> words = new ArrayList<String>();


        String cleanSentence =  sentence.replaceAll("[^ a-z A-Z 0-9]"," ");
        String[] tokens  = cleanSentence.split(" ");
        for (String token: tokens) {
            if(!StopWords.getStopwords().contains(token.toLowerCase())){
                words.add(token.toLowerCase());
            }
        }


        return words;
    }
    public String getCommand(ArrayList<String> inputs){

        for (String input : inputs) {
            ArrayList<String> choices = cleanSentence(input);
            for(Map.Entry<String,String[]> command : myCommands.entrySet()) {
                for (String query : command.getValue()) {



                    System.out.println(choices.toString());
                    ExtractedResult result = FuzzySearch.extractOne(query, choices);
                    System.out.println(result.toString());
                    if(result.getScore()>80){
                        if(command.getKey().equals("navigate"))
                            return "Command : Navigate"+"\n"+"Place : "+getPlace(choices);
                        else if(command.getKey().equals("help"))
                            return "help command";

                    }

                }
            }
        }
        return "no command found";
    }

    public static String getPlace(ArrayList<String> choices){
        for (String place:  myPlaces) {
            ExtractedResult result = FuzzySearch.extractOne(place,choices);
            if(result.getScore()>70){
                return place;
            }

        }

        return "No Places Found";
    }
}

