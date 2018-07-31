package com.example.ibrahim.eshotmessagetaker;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MySpinnerAdapters {

    private Calendar calendar;
    private String[] years, months, days;
    private int year, month, day, yearNow, monthNow, dayNow;
    private ArrayAdapter adapterDateYear,adapterDateMonth,adapterDateDay,adapterType,adapterSubject;
    private Context context;


    MySpinnerAdapters(Context context){

        this.context = context;

        calendar = GregorianCalendar.getInstance();

        yearNow = calendar.get(Calendar.YEAR);
        monthNow = calendar.get(Calendar.MONTH)+1;
        dayNow = calendar.get(Calendar.DAY_OF_MONTH);

        setAdapterType();
        setAdapterSubject();

        setYears(yearNow);
    }


    //Set Methots

    //Arrays
    public void setYears(int yearMax) {

        year = yearMax;
        years = new String[year - 2014];

        for(int i=0; i<year-2014; i++)
            years[year-2015 - i] = String.valueOf(2015 + i);

        setAdapterDateYear();

        if(year == yearNow)
            setMonths(monthNow);
        else
            setMonths(12);
    }

    public void setMonths(int monthMax) {

        month = monthMax;
        months = new String[month];

        for(int i=0; i<month; i++)
            months[month - i -1] = String.format("%02d", i+1);

        setAdapterDateMonth();

        if(year == yearNow && month == monthNow)
            setDays(dayNow);
        else {
            calendar = new GregorianCalendar(year,month-1,1);
            setDays(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
    }

    private void setDays(int dayMax) {

        day = dayMax;
        days = new String[dayMax];

        for(int i=0; i<dayMax; i++)
            days[dayMax - i -1] = String.format("%02d", i+1);

        setAdapterDateDay();
    }

    //Adapters
    public void setAdapterDateYear() {

        adapterDateYear = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, years);
        adapterDateYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void setAdapterDateMonth() {

        adapterDateMonth = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, months);
        adapterDateMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void setAdapterDateDay() {

        adapterDateDay = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, days);
        adapterDateDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void setAdapterType() {

        adapterType = ArrayAdapter.createFromResource(context,
                R.array.listType, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void setAdapterSubject() {

        adapterSubject = ArrayAdapter.createFromResource(context,
                R.array.listSubject, android.R.layout.simple_spinner_item);
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    //Get Methots

    //Arrays
    public String[] getYears() {
        return years;
    }
    public String[] getMonths() {
        return months;
    }
    public String[] getDays() {
        return days;
    }

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