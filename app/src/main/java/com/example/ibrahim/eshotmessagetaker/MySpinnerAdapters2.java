package com.example.ibrahim.eshotmessagetaker;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MySpinnerAdapters2 {

    private Calendar calendar;
    private String[] years, months, days;
    private int year1, month1, day1, year2, month2, day2, count;
    private ArrayAdapter adapterDateYear,adapterDateMonth,adapterDateDay,adapterType,adapterSubject;
    private Context context;


    MySpinnerAdapters2(Context context, int day1, int month1, int year1, int day2, int month2, int year2){

        this.context = context;
        this.day1 = day1;
        this.month1 = month1;
        this.year1 = year1;
        this.day2 = day2;
        this.month2 = month2;
        this.year2 = year2;

        setAdapterType();
        setAdapterSubject();

        setYears(year1,year2);
    }

    //Set Methots

    //Arrays
    public void setYears(int year1, int year2) {

        count = year2 - year1 + 1;
        years = new String[count];

        for(int i=0; i<count; i++)
            years[year2-year1 - i] = String.valueOf(year1 + i);

        setAdapterDateYear();
/*
        if(year1 == year2)
            setMonths(month1,month2);
        else
            setMonths(month1,12);
            */
    }
/*
    public void setMonths(int month1, int month2) {

        count = month2 - month1 + 1;
        months = new String[count];

        for(int i=0; i<count; i++)
            months[month2 - month1 -1] = String.format("%02d", i+1);

        setAdapterDateMonth();

        if(year1 == year2 && month1 == month2)
            setDays(day1,day2);
        else {
            calendar = new GregorianCalendar(year,month-1,1);
            setDays(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
    }

    private void setDays(int day1, int day2) {

        day = dayMax;
        days = new String[dayMax];

        for(int i=0; i<dayMax; i++)
            days[dayMax - i -1] = String.format("%02d", i+1);

        setAdapterDateDay();
    }
*/
    //Adapters
    private void setAdapterDateYear() {

        adapterDateYear = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, years);
        adapterDateYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void setAdapterDateMonth() {

        adapterDateMonth = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, months);
        adapterDateMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void setAdapterDateDay() {

        adapterDateDay = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, days);
        adapterDateDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void setAdapterType() {

        adapterType = ArrayAdapter.createFromResource(context,
                R.array.listType, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void setAdapterSubject() {

        adapterSubject = ArrayAdapter.createFromResource(context,
                R.array.listSubject, android.R.layout.simple_spinner_item);
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    //Get Methots

    //Adapters

    public ArrayAdapter getAdapterDateYear() {
        return adapterDateYear;
    }
    public ArrayAdapter getAdapterDateMonth() {
        return adapterDateMonth;
    }
    public ArrayAdapter getAdapterDateDay() {
        return adapterDateDay;
    }

    public ArrayAdapter getAdapterType() {
        return adapterType;
    }
    public ArrayAdapter getAdapterSubject() {
        return adapterSubject;
    }
}