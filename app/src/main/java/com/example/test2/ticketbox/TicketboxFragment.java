package com.example.test2.ticketbox;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test2.R;
import com.example.test2.ticketbox.TicketboxData;

import java.util.ArrayList;
import java.util.List;

public class TicketboxFragment extends Fragment {

    private List<TicketboxData> ticketboxList;
    private TickectboxAdapter tickectboxAdapter;
    private int tickboxcount = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ticketbox, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ticketbox_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ticketboxList = new ArrayList<>();

        tickectboxAdapter = new TickectboxAdapter(ticketboxList);
        recyclerView.setAdapter(tickectboxAdapter);

        for(int i=0; i<4; i++){
            tickboxcount++;
            TicketboxData data = new TicketboxData(R.drawable.image1, "내 영수증", "10" + "개");
            ticketboxList.add(data);
        }
        tickectboxAdapter.notifyDataSetChanged();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}