package com.example.ibrahim.eshotmessagetaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
    private ValueEventListener listenerDb;
    private ArrayAdapter adapterType, adapterSubject;
    private ArrayAdapter<String> adapterList;
    private AdapterView.OnItemSelectedListener listenerSpinners;
    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>> mapMsgType;
    private HashMap<String, HashMap<String, String>> mapMsgType2;
    private Spinner spinnerType,spinnerSubject;
    private ListView listView;
    private ArrayList list;

    private void init() {

        mapMsgType = new HashMap<>();
        list = new ArrayList();

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
        //listView.setAdapter(adapterList);
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

        list.clear();
        String s;

        if(map != null)
        for ( HashMap<String, String> map2: map.values()) {

            s = "\t\t\t\t";
            s += map2.get("Message") + "\n\n\n";
            s += "\t\t\t\t\t\t\t\tLocal\t\t\t\t" + "\t\t\t\t\t\t\t\t\t\t\t\tDatabase\n\t\t\t\t";
            s += map2.get("Local Date") + "\t\t\t\t\t\t\t\t" + String.valueOf(map2.get("Server Timestamp"));


/*
            s = "\n\t\t";
            s += map2.get("Message") + "\n\n\n";
            s += "Server Timestamp = " + String.valueOf(map2.get("Server Timestamp"));
            s += " Local Date = " + map2.get("Local Date") + "\n";
*/
            list.add(s);
        }

        listView.setAdapter(adapterList);
    }

    private void initListener() {

        listenerDb = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //if(dataSnapshot.getValue() != null) {
                    HashMap<String, HashMap<String, String>> map = new HashMap<>();

                    map = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();

                    updateList(map);
                //}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listenerSpinners = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ref = messages.child(getLocalDate(false)).child(spinnerType.getSelectedItem().toString()).child(spinnerSubject.getSelectedItem().toString());
                ref.addValueEventListener(listenerDb);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }

    public String getLocalDate(boolean type) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf;

        if(type)
            sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        else
            sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(c.getTime());
    }

}
