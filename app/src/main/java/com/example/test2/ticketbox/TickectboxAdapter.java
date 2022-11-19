package com.example.test2.ticketbox;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.AccountActivity;
import com.example.test2.R;
import com.example.test2.TicketboxActivity;

import java.util.List;

public class TickectboxAdapter extends RecyclerView.Adapter<TickectboxAdapter.ViewHolder>{
    private List<TicketboxData> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView info1;
        private TextView info2;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            info1 = view.findViewById(R.id.ticketinfo1);
            info2 = view.findViewById(R.id.ticketinfo2);
        }
    }

    public TickectboxAdapter(List<TicketboxData> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public TickectboxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticketbox_list, parent, false);

        TickectboxAdapter.ViewHolder viewHolder = new TickectboxAdapter.ViewHolder(view);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ticket_linear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TicketboxActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TickectboxAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(mList.get(position).getImage());
        holder.info1.setText(mList.get(position).getTicketinfo1());
        holder.info2.setText(mList.get(position).getTickectinfo2());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
