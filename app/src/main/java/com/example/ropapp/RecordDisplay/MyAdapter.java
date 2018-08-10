package com.example.ropapp.RecordDisplay;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ropapp.Popup;
import com.example.ropapp.R;
import com.example.ropapp.data.PatientInfo;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{

    private ArrayList<PatientInfo> display = new ArrayList<>();
    private Context context;

    public MyAdapter(ArrayList<PatientInfo> display, Context context) {
        this.display = display;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_row, viewGroup, false);
        ViewHolder hold = new ViewHolder(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos)
    {
        viewHolder.info.setText(display.get(pos).getName());
        viewHolder.birth.setText(display.get(pos).getBirthday());
        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewDetails = new Intent(context, Popup.class);
                viewDetails.putExtra("key", display.get(pos).getDate());
                context.startActivity(viewDetails);

            }
        });
    }

    @Override
    public int getItemCount() {
        return display.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView info;
        TextView birth;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.patientView);
            birth = itemView.findViewById(R.id.birthday);
            parent = itemView.findViewById(R.id.recyclerview_row);
        }
    }
}












