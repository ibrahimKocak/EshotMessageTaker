package com.example.ibrahim.eshotmessagetaker;

import java.util.ArrayList;
import java.util.HashMap;

public class GetMessageList {

    private static String[] types, subjects;
    private static ArrayList<ArrayList<String>> list;
    private static HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>>> map;

    static ArrayList<ArrayList<String>> getList(String type, String subject){

        list = new ArrayList<>(2);

        list.add(new ArrayList<String>());
        list.add(new ArrayList<String>());


        if( map != null)
            solveByType(map,type,subject);

        return list;
    }

    private static void solveByType(HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>>> map, String type, String subject) {

        if(type.equals("Tümü")){

            for (String s : types)
                if(map.get(s) != null)
                    solveBySubject(s, map.get(s),subject);
        }
        else if(map.get(type) != null)
            solveBySubject(type, map.get(type),subject);     //doldur
    }


    private static void solveBySubject(String type, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>> map, String subject) {

        if(subject.equals("Tümü")){

            for (String s : subjects)
                if(map.get(s) != null)
                    addToList(type, s, map.get(s));
        }
        else if(map.get(subject) != null)
            addToList(type, subject, map.get(subject));
    }

    private static void addToList(String type, String subject, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> mapSubject){

        for (HashMap<String, HashMap<String, HashMap<String, String>>> mapTemp2 : mapSubject.values()) {

            for (HashMap<String, HashMap<String, String>> mapTemp : mapTemp2.values()) {

                String s = "Ad: \t" + mapTemp.get("Id").get("Name") + "\n\n";
                s += "Soyad: \t" + mapTemp.get("Id").get("Name2") + "\n\n";
                s += "Kimlik No: \t" + mapTemp.get("Id").get("Id") + "\n\n";
                s += "Gsm: \t" + mapTemp.get("Id").get("PhoneGsm") + "\n\n";
                s += "Telefon: \t" + mapTemp.get("Id").get("Phone") + "\n\n";
                s += "Email: \t" + mapTemp.get("Id").get("Mail");
                list.get(0).add(s);

                s = type + "\t\t / \t\t" + subject + "\n\n\n";
                s += "\t\t\t\t";
                s += mapTemp.get("Message").get("Message") + "\n\n";
                s += "\t\t\t\t\t\t\tTime Local\t\t\t\t" + "\t\t\t\t\t\t\t\tTime Database\n\t\t\t\t";
                s += mapTemp.get("Message").get("Time Local") + "\t\t\t\t\t\t\t\t" + GetDate.getDate(true, String.valueOf(mapTemp.get("Message").get("Time Database")));
                list.get(1).add(s);
            }
        }
    }

    static void setMap(HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>>> map2){

        map = map2;
    }

    static void setTypes(String[] types2){

        types = types2;
    }

    static void setSubjects(String[] subjects2){

        subjects = subjects2;
    }
}