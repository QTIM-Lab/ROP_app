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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1 = findViewById(R.id.imageButton2);
        Button camera = findViewById(R.id.button);
        Button test = findViewById(R.id.button2);
        camera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dispatchTakePictureIntent();
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
                Bitmap image = BitmapFactory.decodeFile(lastImagePath);
                image1.setImageBitmap(image);
            }
            else
            {

            }
        }
    }
}
