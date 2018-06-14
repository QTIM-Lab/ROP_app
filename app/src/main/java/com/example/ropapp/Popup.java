package com.example.ropapp;

import android.app.Activity;
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

public class Popup extends Activity {

    Button close;
    ImageView ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

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

//        Bundle extras = getIntent().getExtras();


        close = findViewById(R.id.save);
        ret = findViewById(R.id.imageView2);
        String path = getIntent().getStringExtra("imagepath");
        Bitmap show = BitmapFactory.decodeFile(path);
        ret.setImageBitmap(show);

        String results = getIntent().getStringExtra("Result");
        TextView display = findViewById(R.id.displayResults);
        display.setText(results);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


    }
}
