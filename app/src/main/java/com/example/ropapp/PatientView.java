package com.example.ropapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.exam.ExamList;
import com.example.ropapp.exam.NewExam;
import com.example.ropapp.viewmodel.ViewInfoViewModel;

import javax.inject.Inject;

public class PatientView extends AppCompatActivity {

    Button newexam, viewexams;
    TextView nameView, bdayView, ageView, notesView;
    ViewInfoViewModel viewInfoViewModel;
    PatientInfo curr;
    String key;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);

        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        viewInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewInfoViewModel.class);
        key = getIntent().getStringExtra("key");

        final Observer<PatientInfo> patientObserver = new Observer<PatientInfo>() {
            @Override
            public void onChanged(@Nullable PatientInfo patientInfo) {
                setPatient(patientInfo);
            }
        };

        viewInfoViewModel.viewPatient(key).observe(this, patientObserver);

        nameView = findViewById(R.id.nameView);
        bdayView = findViewById(R.id.Birthday);
        ageView = findViewById(R.id.Gestational_age);
        notesView = findViewById(R.id.notes);
        newexam = findViewById(R.id.newExam);
        viewexams = findViewById(R.id.viewExams);

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        double width = dm.widthPixels*.8;
//        double height = dm.heightPixels*.8;
//
//        getWindow().setLayout((int)width, (int)height);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.gravity = Gravity.CENTER;
//        params.x = 0;
//        params.y = -20;
//        getWindow().setAttributes(params);

//        nameView = findViewById(R.id.nameView);
//        nameView.requestLayout();
//        nameView.getLayoutParams().width = (int)width;
//        final String name = getIntent().getStringExtra("Name");
//        nameView.setText(name);
//
//        close = findViewById(R.id.save);
//        ret = findViewById(R.id.imageView2);
//        final String path = getIntent().getStringExtra("imagepath");
//        Bitmap show = BitmapFactory.decodeFile(path);
//        double h = show.getHeight()*.4;
//        double w = show.getWidth()*.4;
//        ret.requestLayout();
//        ret.getLayoutParams().height = (int)h;
//        ret.getLayoutParams().width = (int)w;
//        ret.setImageBitmap(show);
//        final String postMenstrual = getIntent().getStringExtra("Post menstrual age");
//
//        final String results = getIntent().getStringExtra("Result");
//        String towrite = results + "\nPost menstrual age: " + postMenstrual + " weeks";
//        TextView display = findViewById(R.id.displayResults);
//        display.setText(towrite);
//
//        final int age = getIntent().getIntExtra("Age", 0);
//        final String notes = getIntent().getStringExtra("Notes");
//        final String birth = getIntent().getStringExtra("Birthday");

        newexam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent next = new Intent(getApplicationContext(), NewExam.class);
                next.putExtra("key", key);
                next.putExtra("Birthday", curr.getBirthday());
                startActivity(next);
            }
        });

        viewexams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startView = new Intent(getApplicationContext(), ExamList.class);
                startView.putExtra("key", key);
                startActivity(startView);
            }
        });


    }

    private void setPatient(PatientInfo p)
    {
        curr = p;
        nameView.setText("Name: " + curr.getName());
        bdayView.setText("Birthday: " + curr.getBirthday());
        ageView.setText("Gestational age: " + curr.getGestationalAge() + " weeks");
        notesView.setText(curr.getNotes());
    }
}
