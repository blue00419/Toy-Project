package com.example.test2.timeline;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test2.R;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    private List<TimelineData> timelineList;
    private TimelineAdapter timelineAdapter;
    private int timelinecount = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.timeline_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        timelineList = new ArrayList<>();

        timelineAdapter = new TimelineAdapter(timelineList);
        recyclerView.setAdapter(timelineAdapter);

        for(int i=0; i<20; i++){
            timelinecount++;
            TimelineData data = new TimelineData(R.drawable.image1,"승차권(대구 -> 서울)", "2022-11-17", "20002원");
            timelineList.add(data);
        }
        timelineAdapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}