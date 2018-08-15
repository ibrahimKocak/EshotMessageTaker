package com.example.ibrahim.eshotmessagetaker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        init();
    }

    private final DatabaseReference messages = FirebaseDatabase.getInstance().getReference("Messages");
    private DatabaseReference ref;
    private ValueEventListener listenerDb;
    private ArrayAdapter<String> adapterList;
    private AdapterView.OnItemSelectedListener listenerSpinners, listenerSpinnerDateDay, listenerSpinnerDateMonth, listenerSpinnerDateYear,listenerSpinnerDateDay2, listenerSpinnerDateMonth2, listenerSpinnerDateYear2;
    private AdapterView.OnItemClickListener listenerListview;
    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String >>>>>>  map;
    private Spinner spinnerDateDay, spinnerDateMonth, spinnerDateYear,spinnerDateDay2, spinnerDateMonth2, spinnerDateYear2, spinnerType,spinnerSubject;
    private ListView listView;
    private ArrayList<ArrayList<String>> list;

    private void init() {

        map = new HashMap<>();
        list = new ArrayList<>();

        spinnerDateDay = findViewById(R.id.spinnerDateDay);
        spinnerDateMonth = findViewById(R.id.spinnerDateMonth);
        spinnerDateYear = findViewById(R.id.spinnerDateYear);
        spinnerDateDay2 = findViewById(R.id.spinnerDateDay2);
        spinnerDateMonth2 = findViewById(R.id.spinnerDateMonth2);
        spinnerDateYear2 = findViewById(R.id.spinnerDateYear2);
        spinnerDateDay2.setEnabled(false);
        spinnerDateMonth2.setEnabled(false);
        spinnerDateYear2.setEnabled(false);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        listView = findViewById(R.id.lvMsg);

        setAdapters();
        initListener();

        GetMessageList.setTypes(getResources().getStringArray(R.array.listType));
        GetMessageList.setSubjects(getResources().getStringArray(R.array.listSubject));

        addListeners();
    }

    private MySpinnerAdapters mySpinnerAdapters;

    private void setAdapters() {

        mySpinnerAdapters = new MySpinnerAdapters(MainActivity.this);
        spinnerDateYear.setAdapter(mySpinnerAdapters.getAdapterDateYear());
        spinnerDateYear2.setAdapter(mySpinnerAdapters.getAdapterDateYear());
        spinnerType.setAdapter(mySpinnerAdapters.getAdapterType());
        spinnerSubject.setAdapter(mySpinnerAdapters.getAdapterSubject());
    }

    private void addListeners() {

        spinnerDateDay.setOnItemSelectedListener(listenerSpinnerDateDay);
        spinnerDateMonth.setOnItemSelectedListener(listenerSpinnerDateMonth);
        spinnerDateYear.setOnItemSelectedListener(listenerSpinnerDateYear);
        spinnerDateDay2.setOnItemSelectedListener(listenerSpinnerDateDay2);
        spinnerDateMonth2.setOnItemSelectedListener(listenerSpinnerDateMonth2);
        spinnerDateYear2.setOnItemSelectedListener(listenerSpinnerDateYear2);
        spinnerType.setOnItemSelectedListener(listenerSpinners);
        spinnerSubject.setOnItemSelectedListener(listenerSpinners);
        listView.setOnItemClickListener(listenerListview);
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

        listenerSpinnerDateDay2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                connection();

                int[] date1 = new int[3];
                int[] date2 = new int[3];

                date1[0] = Integer.valueOf(spinnerDateDay.getSelectedItem().toString());
                date1[1] = Integer.valueOf(spinnerDateMonth.getSelectedItem().toString());
                date1[2] = Integer.valueOf(spinnerDateYear.getSelectedItem().toString());

                date2[0] = Integer.valueOf(spinnerDateDay2.getSelectedItem().toString());
                date2[1] = Integer.valueOf(spinnerDateMonth2.getSelectedItem().toString());
                date2[2] = Integer.valueOf(spinnerDateYear2.getSelectedItem().toString());

                ArrayList<String> listDate = new ArrayList<>();
                String s = "";

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

        listenerSpinnerDateMonth2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mySpinnerAdapters.setMonths(Integer.valueOf(spinnerDateMonth.getSelectedItem().toString()));
                spinnerDateDay2.setAdapter(mySpinnerAdapters.getAdapterDateDay());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        listenerSpinnerDateYear2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mySpinnerAdapters.setYears(Integer.valueOf(spinnerDateYear.getSelectedItem().toString()));
                spinnerDateMonth2.setAdapter(mySpinnerAdapters.getAdapterDateMonth());
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
                    adapterList = new  ArrayAdapter<>(MainActivity.this,R.layout.mytextview,list.get(1));
                    listView.setAdapter(adapterList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        listenerListview = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                connection();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog, null);
                final TextView tvDialog = mView.findViewById(R.id.tvDialog);
                tvDialog.setText(list.get(0).get(i));
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        };

        listenerDb = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map = (HashMap<String, HashMap<String,HashMap<String,HashMap<String,HashMap<String,HashMap<String,String>>>>>>) dataSnapshot.getValue();

                if(map != null) {

                    GetMessageList.setMap(map);
                    list = GetMessageList.getList(spinnerType.getSelectedItem().toString(), spinnerSubject.getSelectedItem().toString());
                    adapterList = new ArrayAdapter<>(MainActivity.this, R.layout.mytextview, list.get(1));
                    listView.setAdapter(adapterList);
                }
                else{

                    list.get(0).clear();
                    list.get(1).clear();
                    adapterList = new ArrayAdapter<>(MainActivity.this, R.layout.mytextview, list.get(1));
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