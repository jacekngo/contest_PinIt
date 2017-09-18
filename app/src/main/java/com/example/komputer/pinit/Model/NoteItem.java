package com.example.komputer.pinit.Model;


import android.support.annotation.Nullable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class NoteItem extends RealmObject  {
    @PrimaryKey
    private int id;
    private long systemTime;
    private double latitude;
    private double longitude;
    private double accuracy;
    @Nullable
    private String title;
    @Nullable
    private String notes;

    public NoteItem() {
    }

    public NoteItem(int id, long systemTime, double latitude, double longitude, double accuracy, String title, String notes) {
        this.id = id;
        this.systemTime = systemTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        this.title = title;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAccuracy() {
        return accuracy;
    }


    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getNotes() {
        return notes;
    }

    public void setNotes(@Nullable String notes) {
        this.notes = notes;
    }
}
