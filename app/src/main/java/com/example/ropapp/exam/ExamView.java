package com.example.ropapp.exam;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ropapp.R;
import com.example.ropapp.ROPApplication;
import com.example.ropapp.data.Exam;
import com.example.ropapp.viewmodel.ExamDetailsViewModel;

import javax.inject.Inject;

import me.relex.circleindicator.CircleIndicator;

public class ExamView extends AppCompatActivity {

    private String key;
    private Exam thisExam;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ExamDetailsViewModel examDetailsViewModel;
    ViewPager viewPager;
    ExamPagerAdapter examPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_view);

        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        examDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(ExamDetailsViewModel.class);

        key = getIntent().getStringExtra("date");

        Button close = findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        examDetailsViewModel.ViewExam(key).observe(ExamView.this, new Observer<Exam>() {
            @Override
            public void onChanged(@Nullable Exam exam) {
                setExam(exam);
            }
        });
    }

    private void setExam(Exam e)
    {
        thisExam = e;
        viewInfo();
    }

    private void viewInfo()
    {
        viewPager = findViewById(R.id.pager);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        examPagerAdapter = new ExamPagerAdapter(thisExam, inflater, this);
        CircleIndicator indicator = findViewById(R.id.indicator);
        viewPager.setAdapter(examPagerAdapter);
        indicator.setViewPager(viewPager);

        TextView notes = findViewById(R.id.notestextview);
        notes.setText(thisExam.getNotes());
        notes.setMovementMethod(new ScrollingMovementMethod());

//        ImageView left = findViewById(R.id.eyeView1);
//        TextView results = findViewById(R.id.resultsView);
//        ImageView right = findViewById(R.id.eyeView2);
//        TextView rightResults = findViewById(R.id.resultsView2);

//        Drawable noplus = this.getResources().getDrawable(R.drawable.no_plus);
//        left.setImageDrawable(noplus);

//        Bitmap leftEye = BitmapFactory.decodeFile(thisExam.getImagePathL());
//        left.setImageBitmap(leftEye);
//        results.setText(thisExam.getLeftDiagnosis());
//
//        Bitmap rightEye = BitmapFactory.decodeFile(thisExam.getImagePathR());
//        right.setImageBitmap(rightEye);
//        rightResults.setText(thisExam.getRightDiagnosis());
    }
}
