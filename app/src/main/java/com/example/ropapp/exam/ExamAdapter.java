package com.example.ropapp.exam;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ropapp.R;
import com.example.ropapp.data.Exam;

import java.util.ArrayList;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.Viewholder>
{
    private ArrayList<Exam> exams;
    private Context context;

    public ExamAdapter(ArrayList<Exam> exams, Context context)
    {
        this.exams = exams;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exam_row, viewGroup, false);
        Viewholder hold = new Viewholder(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewHolder, final int pos)
    {
        viewHolder.examDate.setText(exams.get(pos).getWriteDate());
        viewHolder.examNum.setText("Exam " + (pos + 1));
        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openExam = new Intent(context, ExamDetails.class);
                openExam.putExtra("date", exams.get(pos).getDate());
                context.startActivity(openExam);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        ConstraintLayout parent;
        TextView examNum;
        TextView examResults;
        TextView examDate;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            parent = itemView.findViewById(R.id.exam_row);
            examNum = itemView.findViewById(R.id.examNum);
            examDate = itemView.findViewById(R.id.examDate);
        }
    }
}
