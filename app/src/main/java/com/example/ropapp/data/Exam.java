package com.example.ropapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(foreignKeys = @ForeignKey(entity = PatientInfo.class, parentColumns = "date", childColumns = "pkey", onDelete = CASCADE))
public class Exam
{
    @PrimaryKey
    @NonNull
    private String date;
    private String pkey;
    private String postMenstrual;
    private String rightDiagnosis;
    private String leftDiagnosis;
    private String imagePathR;
    private String imagePathL;
    private String notes;
    private String writeDate;

    public Exam(String date, String pkey, String postMenstrual, String leftDiagnosis, String rightDiagnosis, String imagePathR, String imagePathL, String notes, String writeDate) {
        this.date = date;
        this.pkey = pkey;
        this.postMenstrual = postMenstrual;
        this.leftDiagnosis = leftDiagnosis;
        this.rightDiagnosis = rightDiagnosis;
        this.imagePathR = imagePathR;
        this.imagePathL = imagePathL;
        this.notes = notes;
        this.writeDate = writeDate;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getPostMenstrual() {
        return postMenstrual;
    }

    public void setPostMenstrual(String postMenstrual) {
        this.postMenstrual = postMenstrual;
    }

    public String getRightDiagnosis() {
        return rightDiagnosis;
    }

    public void setRightDiagnosis(String rightDiagnosis) {
        this.rightDiagnosis = rightDiagnosis;
    }

    public String getLeftDiagnosis() {
        return leftDiagnosis;
    }

    public void setLeftDiagnosis(String leftDiagnosis) {
        this.leftDiagnosis = leftDiagnosis;
    }

    public String getImagePathR() {
        return imagePathR;
    }

    public void setImagePathR(String imagePathR) {
        this.imagePathR = imagePathR;
    }

    public String getImagePathL() {
        return imagePathL;
    }

    public void setImagePathL(String imagePathL) {
        this.imagePathL = imagePathL;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
