package com.example.ropapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String lastImagePath;
    ImageButton image1;
    Button retake;
    Button keep;
    Button camera;
    Bitmap image;
    TextView t;
    String result = "The method didn't run";
    Bitmap fin;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1 = findViewById(R.id.imageButton2);
        camera = findViewById(R.id.button);
        keep = findViewById(R.id.yes);
        retake = findViewById(R.id.no);
        //t = findViewById(R.id.textView);
        name = findViewById(R.id.name);

        camera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dispatchTakePictureIntent();
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


//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                fin.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();

                Intent results = new Intent(getApplicationContext(), Popup.class);
                results.putExtra("Result", result);
                results.putExtra("imagepath", lastImagePath);
                startActivity(results);
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
                //t.setText("success");
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
                //Decode file
                image = BitmapFactory.decodeFile(lastImagePath);
                image1.setImageBitmap(image);
                image1.setVisibility(View.VISIBLE);
                keep.setVisibility(View.VISIBLE);
                retake.setVisibility(View.VISIBLE);
                camera.setVisibility(View.INVISIBLE);

            }

        }
    }

    public void Processing()
    {
        //send the image through cleaning and save it in new bitmap out
        Bitmap out = image;
        fin = out;
        diagnosis();

    }

    public void diagnosis()
    {
        double severity = 4.5;
        boolean diseased = true;
        if(diseased == true)
        {
            result = "Treat now. Severity = " + severity;
        }
        else
        {
            result = "No danger.";
        }
    }

    public Bitmap getBit()
    {
        return fin;
    }

    public String getResult()
    {
        return result;
    }
}
