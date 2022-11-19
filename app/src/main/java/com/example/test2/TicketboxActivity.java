package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.test2.ticketbox.TicketboxListAdapter;
import com.example.test2.timeline.TimelineAdapter;
import com.example.test2.timeline.TimelineData;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class TicketboxActivity extends AppCompatActivity {

    private List<TimelineData> timelineList;
    private TicketboxListAdapter tickectboxListAdapter;
    private int timelinecount = -1;
    private MaterialToolbar appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketbox);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ticketbox_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        timelineList = new ArrayList<>();

        tickectboxListAdapter = new TicketboxListAdapter(timelineList);
        recyclerView.setAdapter(tickectboxListAdapter);

        for(int i=0; i<20; i++){
            timelinecount++;
            TimelineData data = new TimelineData(R.drawable.image1,"승차권(대구 -> 서울)", "2022-11-17", "20002원");
            timelineList.add(data);
        }
        tickectboxListAdapter.notifyDataSetChanged();

        appbar = (MaterialToolbar) findViewById(R.id.topAppBar);
        appbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}