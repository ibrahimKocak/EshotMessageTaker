package com.example.ibrahim.eshotmessagetaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListener();
        init();
    }

    private final DatabaseReference messages = FirebaseDatabase.getInstance().getReference("Messages");
    private DatabaseReference ref;
    private ValueEventListener listenerDb, listenerDbDate, listenerDbType, listenerDbSubject;
    private ArrayAdapter adapterDate, adapterType, adapterSubject;
    private ArrayAdapter adapterList;
    private AdapterView.OnItemSelectedListener listenerSpinners;
    private HashMap<String, HashMap<String, String>> map;
    private Spinner spinnerDate, spinnerType,spinnerSubject;
    private ListView listView;
    private ArrayList<String> list;

    private void init() {

        map = new HashMap<>();
        list = new ArrayList();

        spinnerDate = findViewById(R.id.spinnerDateDay);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        listView = findViewById(R.id.lvMsg);

        initAdapters();
        initListener();
        setAdapters();
        addListeners();
    }

    private void addListeners() {

        spinnerType.setOnItemSelectedListener(listenerSpinners);
        spinnerSubject.setOnItemSelectedListener(listenerSpinners);
    }

    private void setAdapters() {

        spinnerType.setAdapter(adapterType);
        spinnerSubject.setAdapter(adapterSubject);
    }

    private void initAdapters() {

        adapterType = ArrayAdapter.createFromResource(this,
                R.array.listType, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterSubject = ArrayAdapter.createFromResource(this,
                R.array.listSubject, android.R.layout.simple_spinner_item);
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterList = new  ArrayAdapter<>(this,R.layout.mytextview,list);
    }

    //ListView'deki listenin spinner secimine gore atanma islemi
    private void updateList(HashMap<String, HashMap<String, String>> map) {

        String s;
        if(map != null)
        for ( HashMap<String, String> map2: map.values()) {

            s = "\t\t\t\t";
            s += map2.get("Message") + "\n\n\n";
            s += "\t\t\t\t\t\t\tLocal Date\t\t\t\t" + "\t\t\t\t\t\t\t\tDatabase Date\n\t\t\t\t";
            //s += map2.get("Local Date") + "\t\t\t\t\t\t\t\t" + String.valueOf(map2.get("Server Timestamp"));
            s += map2.get("Local Date") + "\t\t\t\t\t\t\t\t" + getLocalDate(true, String.valueOf(map2.get("Server Timestamp")));

            list.add(s);
        }
    }

    int spinnerCount = 0;
    private void initListener() {

        listenerSpinners = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(spinnerCount != 0) {
                    list.clear();
                    downloadData("27-07-2018", spinnerType.getSelectedItem().toString(), spinnerSubject.getSelectedItem().toString());
                }
                spinnerCount++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        listenerSpinners = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(spinnerCount != 0) {
                    list.clear();
                    downloadData("27-07-2018", spinnerType.getSelectedItem().toString(), spinnerSubject.getSelectedItem().toString());
                }
                spinnerCount++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        listenerDb = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, HashMap<String, String>> map = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                updateList(map);

                listView.setAdapter(adapterList);
                //ref.removeEventListener(listenerDb);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void downloadData(String date, String type, String subject) {

        if(date.equals("Tümü")){

            downloadData("27-07-2018",type,subject);    //doldur
        }
        else{

            if(type.equals("Tümü")){

                downloadData(date,"Öneri",subject);     //doldur
                downloadData(date,"İstek",subject);     //doldur
                downloadData(date,"Memnuniyet",subject);     //doldur
                downloadData(date,"Şikayet",subject);     //doldur
                downloadData(date,"Diğer",subject);     //doldur
            }
            else{

                if(subject.equals("Tümü")){

                    downloadData(date,type,"Hat ve Sefer Saatleri");    //doldur
                    downloadData(date,type,"Duraklar");    //doldur
                    downloadData(date,type,"Otobüs");    //doldur
                    downloadData(date,type,"Personel");    //doldur
                    downloadData(date,type,"İzmirim Kart");    //doldur
                }
                else{
                    ref = messages.child(date).child(type).child(subject);
                    ref.addValueEventListener(listenerDb);
                }
            }
        }

    }

    public String getLocalDate(boolean type, String timestamp) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf;

        if(type)
            sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        else
            sdf = new SimpleDateFormat("dd-MM-yyyy");

        if(timestamp == null)
            return String.valueOf(sdf.format(c.getTime()));
        else{
            c.setTimeInMillis(Long.valueOf(timestamp));
            return String.valueOf(sdf.format(c.getTime()));
        }
    }
}
