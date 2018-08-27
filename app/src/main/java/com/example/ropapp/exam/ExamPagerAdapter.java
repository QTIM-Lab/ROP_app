package com.example.ropapp.exam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ropapp.R;
import com.example.ropapp.data.Exam;

public class ExamPagerAdapter extends PagerAdapter
{
    private Exam display;
    private LayoutInflater inflater;
    private Context context;

    public ExamPagerAdapter(Exam exam, LayoutInflater inflater, Context context)
    {
        display = exam;
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View page = inflater.inflate(R.layout.fragment_collection_object, null);
        if(position == 0)
        {
            ((TextView)page.findViewById(R.id.diagnosisView)).setText(display.getLeftDiagnosis());
            Bitmap mm = BitmapFactory.decodeFile(display.getImagePathL());
            ((ImageView)page.findViewById(R.id.eyeView)).setImageBitmap(mm);
            ((TextView)page.findViewById(R.id.Identifier)).setText("Left eye");
        }
        else if(position == 1)
        {
            ((TextView)page.findViewById(R.id.diagnosisView)).setText(display.getRightDiagnosis());
            Bitmap mm = BitmapFactory.decodeFile(display.getImagePathR());
            ((ImageView)page.findViewById(R.id.eyeView)).setImageBitmap(mm);
            ((TextView)page.findViewById(R.id.Identifier)).setText("Right eye");
        }
        //Add the page to the front of the queue
        container.addView(page, 0);
        return page;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        object = null;
    }
}
