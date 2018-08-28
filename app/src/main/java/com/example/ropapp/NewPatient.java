package com.example.ropapp;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.viewmodel.PopupViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

public class NewPatient extends AppCompatActivity
{
    String patientname, Unotes = "", born;
    ImageButton image1;
    Button retake, keep, save;
    Bitmap image;
    EditText name, page, Usernotes, birthDate;
    TextView not, a, n, g;
    int gestationalAge;
    PopupViewModel popupViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        image1 = findViewById(R.id.imageButton2);

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
        save = findViewById(R.id.saveButton);


        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        popupViewModel = ViewModelProviders.of(this, viewModelFactory).get(PopupViewModel.class);


        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            boolean handled = false;

            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
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
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String a = page.getText().toString();
                    gestationalAge = Integer.parseInt(a);
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
                    born = birthDate.getText().toString();
                    String match = "\\d{2}/\\d{2}/\\d{4}";
                    if (!born.matches(match))
                        Toast.makeText(getApplicationContext(), "Enter valid date format", Toast.LENGTH_SHORT).show();
                    else {
                        //postMenstrual = postMenstrualAge(born);
                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(name.getWindowToken(), 0);
                        handled = true;
                    }

                }
                return handled;
            }
        });
        Usernotes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    Unotes = Usernotes.getText().toString();
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(Usernotes.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hold = name.getText().toString().length();
                if (hold == 0) {
                    Toast.makeText(getApplicationContext(), "Enter patient name", Toast.LENGTH_SHORT).show();
                } else if (page.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter gestational age", Toast.LENGTH_SHORT).show();
                } else if (birthDate.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter birth date", Toast.LENGTH_SHORT).show();
                } else if (name.getText().toString().length() != 0 && page.getText().toString().length() != 0 && birthDate.getText().toString().length() != 0) {
                    String a = page.getText().toString();
                    gestationalAge = Integer.parseInt(a);
                    patientname = name.getText().toString();
                    String check = birthDate.getText().toString();
                    String match = "\\d{2}/\\d{2}/\\d{4}";
                    if (!check.matches(match))
                        Toast.makeText(getApplicationContext(), "Enter valid date format", Toast.LENGTH_SHORT).show();
                    else {
                        born = birthDate.getText().toString();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                        PatientInfo p = new PatientInfo(timeStamp, patientname, born, gestationalAge, Unotes);
                        popupViewModel.NewPatient(p);
                        Intent viewPatient = new Intent(getApplicationContext(), PatientView.class);
                        viewPatient.putExtra("key", p.getDate());
                        startActivity(viewPatient);
                        finish();
                    }
                }
            }
        });

    }
}

