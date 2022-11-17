package com.example.test2.timeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.R;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

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

    public TimelineAdapter(List<TimelineData> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
