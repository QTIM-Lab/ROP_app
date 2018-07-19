package com.example.ropapp;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.viewmodel.PatientListViewModel;
import com.example.ropapp.viewmodel.PopupViewModel;

import javax.inject.Inject;

public class Popup extends AppCompatActivity {

    Button close;
    ImageView ret;
    TextView nameView;
    PopupViewModel popupViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        popupViewModel = ViewModelProviders.of(this, viewModelFactory).get(PopupViewModel.class);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double width = dm.widthPixels*.8;
        double height = dm.heightPixels*.8;

        getWindow().setLayout((int)width, (int)height);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

        nameView = findViewById(R.id.nameView);
        nameView.requestLayout();
        nameView.getLayoutParams().width = (int)width;
        final String name = getIntent().getStringExtra("Name");
        nameView.setText(name);

        close = findViewById(R.id.save);
        ret = findViewById(R.id.imageView2);
        final String path = getIntent().getStringExtra("imagepath");
        Bitmap show = BitmapFactory.decodeFile(path);
        double h = show.getHeight()*.4;
        double w = show.getWidth()*.4;
        ret.requestLayout();
        ret.getLayoutParams().height = (int)h;
        ret.getLayoutParams().width = (int)w;
        ret.setImageBitmap(show);
        final String postMenstrual = getIntent().getStringExtra("Post menstrual age");

        final String results = getIntent().getStringExtra("Result");
        String towrite = results + "\nPost menstrual age: " + postMenstrual + " weeks";
        TextView display = findViewById(R.id.displayResults);
        display.setText(towrite);

        final int age = getIntent().getIntExtra("Age", 0);
        final String notes = getIntent().getStringExtra("Notes");
        final String birth = getIntent().getStringExtra("Birthday");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                PatientInfo save = new PatientInfo(name, birth, postMenstrual, results, path, "Date", notes, age);
                popupViewModel.NewPatient(save);
                finish();
            }
        });


    }
}
