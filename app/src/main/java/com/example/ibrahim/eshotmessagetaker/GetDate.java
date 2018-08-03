package com.example.ibrahim.eshotmessagetaker;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetDate {

    private static Calendar calendarToday;
    private static SimpleDateFormat sdf;

    @SuppressLint("SimpleDateFormat")
    public static String getDate(boolean withClock, String timestamp) {

        calendarToday = Calendar.getInstance();

        if(withClock)
            sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        else
            sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(timestamp != null)
            calendarToday.setTimeInMillis(Long.valueOf(timestamp));

        return String.valueOf(sdf.format(calendarToday.getTime()));
    }
}
