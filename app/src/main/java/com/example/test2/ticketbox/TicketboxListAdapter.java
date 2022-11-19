package com.example.test2.ticketbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.R;
import com.example.test2.timeline.TimelineData;

import org.w3c.dom.Text;

import java.util.List;

public class TicketboxListAdapter extends RecyclerView.Adapter<TicketboxListAdapter.ViewHolder>{

    private List<TimelineData> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView info1;
        private TextView info2;
        private TextView price;

        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.image);
            info1 = view.findViewById(R.id.info1);
            info2 = view.findViewById(R.id.info2);
            price = view.findViewById(R.id.price);
        }
    }

    public TicketboxListAdapter(List<TimelineData> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public TicketboxListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticketboxlist_list, parent, false);

        TicketboxListAdapter.ViewHolder viewHolder = new TicketboxListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketboxListAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(mList.get(position).getImage());
        holder.info1.setText(mList.get(position).getInfo1());
        holder.info2.setText(mList.get(position).getInfo2());
        holder.price.setText(mList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
