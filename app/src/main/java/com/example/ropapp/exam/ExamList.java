package com.example.ropapp.exam;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ropapp.R;
import com.example.ropapp.ROPApplication;
import com.example.ropapp.data.Exam;
import com.example.ropapp.viewmodel.ExamListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ExamList extends AppCompatActivity {

    private String key;
    private RecyclerView list;
    private RecyclerView.Adapter adapter;
    private ArrayList<Exam> examList = new ArrayList<>();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ExamListViewModel examListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        examListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ExamListViewModel.class);

        key = getIntent().getStringExtra("key");
        examListViewModel.getPatientsExams(key).observe(ExamList.this, new Observer<List<Exam>>() {
            @Override
            public void onChanged(@Nullable List<Exam> exams) {
                setList(exams);
            }
        });
    }

    private void setList(List<Exam> exams)
    {
        for(int i = 0; i < exams.size(); i++)
            examList.add(exams.get(i));

        list = findViewById(R.id.examRecycler);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ExamAdapter(examList, this);
        list.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), 1);
        list.addItemDecoration(dividerItemDecoration);

    }
}
