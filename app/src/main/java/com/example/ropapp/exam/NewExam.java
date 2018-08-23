package com.example.ropapp.exam;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.ropapp.Diagnose;
import com.example.ropapp.R;
import com.example.ropapp.ROPApplication;
import com.example.ropapp.data.Exam;
import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.viewmodel.NewExamViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class NewExam extends AppCompatActivity {

    ImageView leftEye, rightEye;
    Button leftPic, rightPic, save;
    EditText Enotes;
    Bitmap rightmap, leftmap, fin;
    String lastImagePath, mCurrentPhotoPath, imagepathR, imagepathL, date, patientName, birthDate, leftResult, rightResult, formatDate, key;
    boolean isLeft, isRight = false;
    int patientage;
    static final int REQUEST_TAKE_PHOTO_ = 1;
    PatientInfo patient;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    NewExamViewModel newExamViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //TODO: either delete the notes function or display notes somewhere in ExamDetails
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exam);

        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        newExamViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewExamViewModel.class);

        leftEye = findViewById(R.id.leftView);
        rightEye = findViewById(R.id.rightView);
        leftPic = findViewById(R.id.left);
        rightPic = findViewById(R.id.right);
        save = findViewById(R.id.saveExam);
        Enotes = findViewById(R.id.notesEdit);

        key = getIntent().getStringExtra("key");

        newExamViewModel.viewPatient(key).observe(NewExam.this, new Observer<PatientInfo>() {
            @Override
            public void onChanged(@Nullable PatientInfo patientInfo) {
                setPatient(patientInfo);
            }
        });

        //OnClicks
        leftPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLeft = true;
                dispatchTakePictureIntent();
            }
        });

        rightPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRight = true;
                dispatchTakePictureIntent();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                String pMenstrual = "" + patientage;
                Exam e = new Exam(date, key, pMenstrual, leftResult, rightResult, imagepathR, imagepathL, Enotes.getText().toString(), formatDate);
                newExamViewModel.NewExam(e);

                Intent viewExam = new Intent(getApplicationContext(), ExamDetails.class);
                viewExam.putExtra("date", date);
                startActivity(viewExam);
                finish();
            }
        });

        leftEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent viewLeft = new Intent(getApplicationContext(), Image.class);
//                viewLeft.putExtra("eye", imagepathL);
//                startActivity(viewLeft);

                PopupMenu popup = new PopupMenu(getApplicationContext(), leftEye);
                popup.getMenuInflater().inflate(R.menu.retake_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        isLeft= true;
                        dispatchTakePictureIntent();
                        return true;
                    }
                });
            }
        });


        rightEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent viewRight = new Intent(getApplicationContext(), Image.class);
//                viewRight.putExtra("eye", imagepathR);
//                startActivity(viewRight);

                PopupMenu popup = new PopupMenu(getApplicationContext(), rightEye);
                popup.getMenuInflater().inflate(R.menu.retake_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        isRight = true;
                        dispatchTakePictureIntent();
                        return true;
                    }
                });
            }
        });

    }

    //Take a picture and save it
    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        date = timeStamp;
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        lastImagePath = image.getAbsolutePath();
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException ex)
            {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.ropapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_);

            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TAKE_PHOTO_ && resultCode == Activity.RESULT_OK){

            //Ensure the path has been set
            if(isLeft)
            {
                leftmap = BitmapFactory.decodeFile(lastImagePath);
                imagepathL = lastImagePath;
                leftEye.setImageBitmap(leftmap);
                leftEye.setVisibility(View.VISIBLE);
                leftPic.setVisibility(View.INVISIBLE);
                leftResult = Processing(leftmap);
                isLeft = false;

            }

            else if(isRight)
            {
                rightmap = BitmapFactory.decodeFile(lastImagePath);
                imagepathR = lastImagePath;
                rightEye.setImageBitmap(rightmap);
                rightEye.setVisibility(View.VISIBLE);
                rightPic.setVisibility(View.INVISIBLE);
                rightResult = Processing(rightmap);
                isRight = false;

            }

        }
    }

    private void setPatient(PatientInfo p)
    {
        patient = p;
        patientName = p.getName();
        birthDate = p.getBirthday();
        patientage = postMenstrualAge(birthDate);
    }

    private int postMenstrualAge(String bday) //Calculates post menstrual age based on date
    {

        ArrayList<Integer> lengths = new ArrayList<Integer>(Arrays.asList(31,29,31,30,31,30,31,31,30,31,30,31));
        int weeksOld = 0;
        String monthString = bday.substring(0,2);
        int month = Integer.parseInt(monthString);
        String dayString = bday.substring(3,5);
        int day = Integer.parseInt(dayString);
        String yearString = bday.substring(6);
        int year = Integer.parseInt(yearString);

        int dayofyear = 0;
        if(month > 2 && year % 4 == 0)
        {
            dayofyear++;
        }

        for(int i = 0; i < month-1; i++)
        {
            dayofyear += lengths.get(i);
        }
        dayofyear += day;

        Calendar cal = Calendar.getInstance();
        int curryear = cal.get(Calendar.YEAR);
        int currday = cal.get(Calendar.DAY_OF_YEAR);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int monthOfYear = cal.get(Calendar.MONTH);
        formatDate = monthOfYear + "/" + dayOfMonth + "/" + curryear;
        int daysbtwn = 0;
        boolean leap = false;
        if(curryear % 4 == 0)
        {
            leap = true;
        }

        if(currday < dayofyear && curryear - year < 2)
        {
            if(leap)
            {
                daysbtwn += (366 - dayofyear) + currday;
            }
            else
            {
                daysbtwn += (365 - dayofyear) + currday;
            }
        }
        else if(curryear == year)
        {
            daysbtwn += currday - dayofyear;
        }
        else if(curryear - year >= 2)
        {
            if(currday >= dayofyear)
            {
                daysbtwn += (currday - dayofyear) + 365 * (curryear - year);
            }
            else if(currday < dayofyear)
            {
                if(leap)
                {
                    daysbtwn += (366 - dayofyear) + currday;
                }
                else {
                    daysbtwn += (365 - dayofyear) + currday;
                }
            }
            for(int i = curryear; i > year; i--)
            {
                if(i % 4 == 0)
                {
                    daysbtwn++;
                }
            }

        }

        double weeks = daysbtwn/7;
        weeksOld = (int)weeks + patient.getGestationalAge();
        return weeksOld;
    }

    public String Processing(Bitmap toProcess) //TODO: add python script preprocessing
    {
        //test code with real retinal image
        Drawable plus = this.getResources().getDrawable(R.drawable.plus);
        Drawable preplus = this.getResources().getDrawable(R.drawable.pre_plus);
        Drawable noplus = this.getResources().getDrawable(R.drawable.no_plus);
        Bitmap out = ((BitmapDrawable) noplus).getBitmap();


        fin = Bitmap.createScaledBitmap(toProcess,224, 224, false);
        return diagnosis(fin);

    }

    public String diagnosis(Bitmap fin)
    {

        Diagnose d = new Diagnose(getAssets(), fin);
        float[] hold = d.tensor();
        String diagnose = "";
        for(int i = 0; i < hold.length; i++)
            diagnose += hold[i] + "\n";

        float severity = hold[0] + (2*hold[1]) + (3*hold[2]);
        diagnose += "Severity: " + severity;
        return diagnose;
    }
}



