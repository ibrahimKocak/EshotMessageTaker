package com.example.ibrahim.eshotmessagetaker;

import java.util.ArrayList;
import java.util.HashMap;

public class GetMessageList {

    private static String[] types, subjects;
    private static ArrayList<String> list;
    private static HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> map;

    static ArrayList<String> getList(String type, String subject){

        list = new ArrayList<>();

        if( map != null)
            solveByType(map,type,subject);

        return list;
    }

    private static void solveByType(HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> map, String type, String subject) {

        if(type.equals("Tümü")){

            for (String s : types)
                if(map.get(s) != null)
                    solveBySubject(s, map.get(s),subject);
        }
        else if(map.get(type) != null)
            solveBySubject(type, map.get(type),subject);     //doldur
    }


    private static void solveBySubject(String type, HashMap<String, HashMap<String, HashMap<String, String>>> map, String subject) {

        if(subject.equals("Tümü")){

            for (String s : subjects)
                if(map.get(s) != null)
                    addToList(type, s, map.get(s));
        }
        else if(map.get(subject) != null)
            addToList(type, subject, map.get(subject));
    }

    private static void addToList(String type, String subject, HashMap<String, HashMap<String, String>> mapSubject){

        for (HashMap<String, String> mapTemp : mapSubject.values()) {

            String s = type + "\t\t / \t\t" + subject + "\n\n\n";
            s += "\t\t\t\t";
            s += mapTemp.get("Message") + "\n\n";
            s += "\t\t\t\t\t\t\tLocal Date\t\t\t\t" + "\t\t\t\t\t\t\t\tDatabase Date\n\t\t\t\t";
            s += mapTemp.get("Local Date") + "\t\t\t\t\t\t\t\t" + GetDate.getDate(true, String.valueOf(mapTemp.get("Server Timestamp")));

            list.add(s);
        }
    }

    static void setMap(HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> map2){

        map = map2;
    }

    static void setTypes(String[] types2){

        types = types2;
    }

    static void setSubjects(String[] subjects2){

        subjects = subjects2;
    }
}