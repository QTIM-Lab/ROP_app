package com.example.ropapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class PatientInfo
{
    @PrimaryKey
    @NonNull
    private String date;
    private String name;
    private String birthday;
    //private String postMenstrual;
    private int gestationalAge;
//    private String diagnosis;
//    private String imagePath;
    private String notes;


    public PatientInfo(String date, String name, String birthday, int gestationalAge, String notes) //, String postMenstrual, String diagnosis, String imagePath, String date, String notes
    {
        this.date = date;
        this.name = name;
        this.birthday = birthday;
//        this.postMenstrual = postMenstrual;
//        this.diagnosis = diagnosis;
//        this.imagePath = imagePath;
//        this.date = date;
//        this.notes = notes;
        this.gestationalAge = gestationalAge;
        this.notes = notes;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

//    public String getPostMenstrual() {
//        return postMenstrual;
//    }
//
//    public void setPostMenstrual(String postMenstrual) {
//        this.postMenstrual = postMenstrual;
//    }
//
//    public String getDiagnosis() {
//        return diagnosis;
//    }
//
//    public void setDiagnosis(String diagnosis) {
//        this.diagnosis = diagnosis;
//    }
//
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getGestationalAge() {
        return gestationalAge;
    }

    public void setGestationalAge(int gestationalAge) {
        this.gestationalAge = gestationalAge;
    }
}
