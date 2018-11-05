package com.a59070180.skyzwing.healthy.desll.healthy.Sleep;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sleep implements Serializable{
    private int id;
    private String date;
    private String startSleepTime;
    private String endSleepTime;
    private String awake;

    public Sleep(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartSleepTime() {
        return startSleepTime;
    }

    public void setStartSleepTime(String startSleepTime) {
        this.startSleepTime = startSleepTime;
    }

    public String getEndSleepTime() {
        return endSleepTime;
    }

    public void setEndSleepTime(String endSleepTime) {
        this.endSleepTime = endSleepTime;
    }

    public String getAwake() {
        return awake;
    }

    public void setAwake(String awake) {
        this.awake = awake;
    }


    public void calculateSleepTime(){
        try {

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date _sleep = format.parse(startSleepTime);
            Date _awake = format.parse(endSleepTime);
            long differece = 0 ;
            boolean after = false;
            if (_sleep.after(_awake)){
                differece = ((_awake.getTime() - _sleep.getTime()) / 1000) / 60;
                after = true;
            }
            else if (_sleep.after(_awake)){
                differece = ((_sleep.getTime() - _awake.getTime()) / 1000) /60;
            }
            differece = Math.abs(differece);
            // hour
            int hour = (int) differece/60;
            if (after)
                hour = 24-hour;
            //minute
            int minute = (int) differece % 60;
            endSleepTime = String.format("%02d", hour + ":" + "02d", minute);
        }catch (Exception e){
            Log.e("Error", "Warning calculatedSleeptime");
        }
    }
}
