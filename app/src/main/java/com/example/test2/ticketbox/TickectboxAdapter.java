package com.example.test2.ticketbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.R;
import com.example.test2.ticketbox.TicketboxData;

import java.util.List;

public class TickectboxAdapter extends RecyclerView.Adapter<TickectboxAdapter.ViewHolder>{
    private List<TicketboxData> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView info1;
        private TextView info2;

        public ViewHolder(View view){
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
