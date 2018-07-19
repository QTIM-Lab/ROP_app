package com.example.ropapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ropapp.RecordDisplay.Records;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class NewPatient extends AppCompatActivity
{
    String mCurrentPhotoPath, patientname, Unotes, born;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String lastImagePath;
    ImageButton image1;
    Button retake, keep, camera, records;
    String result = "The method didn't run";
    Bitmap fin, image;
    EditText name, page, Usernotes, birthDate;
    int patientage = 0;
    TextView not, a, n, g;
    int postMenstrual;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        image1 = findViewById(R.id.imageButton2);
        camera = findViewById(R.id.button);
        keep = findViewById(R.id.yes);
        retake = findViewById(R.id.no);
        name = findViewById(R.id.name);
        page = findViewById(R.id.age);
        birthDate = findViewById(R.id.borth);
        Usernotes = findViewById(R.id.notes);
        Usernotes.setImeOptions(EditorInfo.IME_ACTION_DONE);
        Usernotes.setRawInputType(InputType.TYPE_CLASS_TEXT);
        n = findViewById(R.id.textViewName);
        a = findViewById(R.id.textView2);
        not = findViewById(R.id.textView3);
        g = findViewById(R.id.textViewBirth);



        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            boolean handled = false;
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    patientname = name.getText().toString();
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(name.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        page.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            boolean handled = false;
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    String a = page.getText().toString();
                    patientage = Integer.parseInt(a);
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(page.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        birthDate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            boolean handled = false;
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if(patientage == 0)
                    {
                        Toast.makeText(getApplicationContext(), "Enter gestational age first", Toast.LENGTH_SHORT).show();
                        birthDate.getText().clear();
                    }
                    else {
                        born = birthDate.getText().toString();
                        String match = "\\d{2}/\\d{2}/\\d{4}";
                        if(!born.matches(match))
                            Toast.makeText(getApplicationContext(), "Enter valid date format", Toast.LENGTH_SHORT).show();
                        else {
                            postMenstrual = postMenstrualAge(born);
                            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            mgr.hideSoftInputFromWindow(name.getWindowToken(), 0);
                            handled = true;
                        }
                    }
                }
                return handled;
            }
        });
        Usernotes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    Unotes = Usernotes.getText().toString();
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(Usernotes.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });

        camera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(name.getText().toString() == "")
                {
                    Toast.makeText(getApplicationContext(), "Enter patient name", Toast.LENGTH_SHORT).show();
                }
                else if(page.getText().toString() == "")
                {
                    Toast.makeText(getApplicationContext(), "Enter gestational age", Toast.LENGTH_SHORT).show();
                }
                else if(birthDate.getText().toString() == "")
                {
                    Toast.makeText(getApplicationContext(), "Enter birth date", Toast.LENGTH_SHORT).show();
                }
                else if(name.getText().toString() == "" && page.getText().toString() == "" && birthDate.getText().toString() == "")
                {
                    String a = page.getText().toString();
                    patientage = Integer.parseInt(a);

                    patientname = name.getText().toString();

                    born = birthDate.getText().toString();
                    String match = "\\d{2}/\\d{2}/\\d{4}";
                    if(!born.matches(match))
                        Toast.makeText(getApplicationContext(), "Enter valid date format", Toast.LENGTH_SHORT).show();
                    else
                    {
                        postMenstrual = postMenstrualAge(born);
                    }

                    dispatchTakePictureIntent();
                }
                else
                {
                    dispatchTakePictureIntent();
                }
            }
        });
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image1.setVisibility(View.INVISIBLE);
                keep.setVisibility(View.INVISIBLE);
                retake.setVisibility(View.INVISIBLE);
                camera.setVisibility(View.VISIBLE);
                Processing();
                String attempt = postMenstrual + "";

                Intent results = new Intent(getApplicationContext(), Popup.class);
                results.putExtra("Result", result);
                results.putExtra("imagepath", lastImagePath);
                results.putExtra("Name", patientname);
                results.putExtra("Age", patientage);
                results.putExtra("Notes", Unotes);
                results.putExtra("Birthday", born);
                results.putExtra("Post menstrual age", attempt);


                name.getText().clear();
                page.getText().clear();
                Usernotes.getText().clear();
                birthDate.getText().clear();
                startActivity(results);
                finish();
            }
        });

    }

    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK){

            //Ensure the path has been set
            if(lastImagePath !=null)
            {
                image = BitmapFactory.decodeFile(lastImagePath);
                image1.setImageBitmap(image);
                image1.setVisibility(View.VISIBLE);
                keep.setVisibility(View.VISIBLE);
                retake.setVisibility(View.VISIBLE);
                camera.setVisibility(View.INVISIBLE);
            }

            else
            {

            }

        }
    }

    public void Processing()
    {
        //send the image through cleaning and save it in new bitmap out

        Drawable plus = this.getResources().getDrawable(R.drawable.plus);
        Drawable preplus = this.getResources().getDrawable(R.drawable.pre_plus);
        Drawable noplus = this.getResources().getDrawable(R.drawable.no_plus);
        Bitmap out = ((BitmapDrawable) noplus).getBitmap();
        fin = Bitmap.createScaledBitmap(out,224, 224, false);
        diagnosis();

    }

    public void diagnosis()
    {

        Diagnose d = new Diagnose(getAssets(), fin);
        String[] hold = d.tensor();
        result = "";
        for(int i = 0; i < hold.length; i++)
            result += hold[i] + "\n";
    }

    private int postMenstrualAge(String bday)
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

        Date current = Calendar.getInstance().getTime();

        Calendar cal = Calendar.getInstance();
        int curryear = cal.get(Calendar.YEAR);
        int currday = cal.get(Calendar.DAY_OF_YEAR);
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
        weeksOld = (int)weeks + patientage;
        return weeksOld;
    }
}

