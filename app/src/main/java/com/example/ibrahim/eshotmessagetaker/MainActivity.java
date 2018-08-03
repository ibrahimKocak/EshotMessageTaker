package com.example.ibrahim.eshotmessagetaker;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private final DatabaseReference messages = FirebaseDatabase.getInstance().getReference("Messages");
    private DatabaseReference ref;
    private ValueEventListener listenerDb;
    private ArrayAdapter adapterList;
    private AdapterView.OnItemSelectedListener listenerSpinners, listenerSpinnerDateDay, listenerSpinnerDateMonth, listenerSpinnerDateYear;
    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>  map;
    private Spinner spinnerDateDay, spinnerDateMonth, spinnerDateYear, spinnerType,spinnerSubject;
    private ListView listView;
    private ArrayList list;

    private void init() {

        map = new HashMap<>();
        list = new ArrayList();

        spinnerDateDay = findViewById(R.id.spinnerDateDay);
        spinnerDateMonth = findViewById(R.id.spinnerDateMonth);
        spinnerDateYear = findViewById(R.id.spinnerDateYear);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        listView = findViewById(R.id.lvMsg);

        initAdapters();
        setAdapters();
        initListener();

        GetMessageList.setTypes(getResources().getStringArray(R.array.listType));
        GetMessageList.setSubjects(getResources().getStringArray(R.array.listSubject));

        addListeners();
    }

    private MySpinnerAdapters mySpinnerAdapters;

    private void initAdapters() {

        mySpinnerAdapters = new MySpinnerAdapters(MainActivity.this);
        adapterList = new  ArrayAdapter<>(this,R.layout.mytextview,list);
    }

    private void setAdapters() {

        spinnerDateYear.setAdapter(mySpinnerAdapters.getAdapterDateYear());
        spinnerType.setAdapter(mySpinnerAdapters.getAdapterType());
        spinnerSubject.setAdapter(mySpinnerAdapters.getAdapterSubject());
    }

    private void addListeners() {

        spinnerDateDay.setOnItemSelectedListener(listenerSpinnerDateDay);
        spinnerDateMonth.setOnItemSelectedListener(listenerSpinnerDateMonth);
        spinnerDateYear.setOnItemSelectedListener(listenerSpinnerDateYear);
        spinnerType.setOnItemSelectedListener(listenerSpinners);
        spinnerSubject.setOnItemSelectedListener(listenerSpinners);
    }

    private void initListener() {

        listenerSpinnerDateDay = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                connection();
                String date = spinnerDateYear.getSelectedItem().toString() + "-" + spinnerDateMonth.getSelectedItem().toString() + "-" + spinnerDateDay.getSelectedItem().toString();

                if(ref != null)
                    ref.removeEventListener(listenerDb);
                ref = messages.child(date);
                ref.addValueEventListener(listenerDb);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        listenerSpinnerDateMonth = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mySpinnerAdapters.setMonths(Integer.valueOf(spinnerDateMonth.getSelectedItem().toString()));
                spinnerDateDay.setAdapter(mySpinnerAdapters.getAdapterDateDay());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        listenerSpinnerDateYear = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mySpinnerAdapters.setYears(Integer.valueOf(spinnerDateYear.getSelectedItem().toString()));
                spinnerDateMonth.setAdapter(mySpinnerAdapters.getAdapterDateMonth());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        listenerSpinners = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                connection();
                if(map != null) {

                    list = GetMessageList.getList(spinnerType.getSelectedItem().toString(), spinnerSubject.getSelectedItem().toString());
                    adapterList = new  ArrayAdapter<>(MainActivity.this,R.layout.mytextview,list);
                    listView.setAdapter(adapterList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        listenerDb = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map = (HashMap<String, HashMap<String,HashMap<String,HashMap<String,String>>>>) dataSnapshot.getValue();

                if(map != null) {

                    GetMessageList.setMap(map);
                    list = GetMessageList.getList(spinnerType.getSelectedItem().toString(), spinnerSubject.getSelectedItem().toString());
                    adapterList = new ArrayAdapter<>(MainActivity.this, R.layout.mytextview, list);
                    listView.setAdapter(adapterList);
                }
                else{

                    list.clear();
                    adapterList = new ArrayAdapter<>(MainActivity.this, R.layout.mytextview, list);
                    listView.setAdapter(adapterList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(MainActivity.this,"Ağ hatası",Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void connection(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm.getActiveNetworkInfo() == null) {
            Toast.makeText(MainActivity.this, "İnternet Yok!", Toast.LENGTH_SHORT).show();

        }
    }
}