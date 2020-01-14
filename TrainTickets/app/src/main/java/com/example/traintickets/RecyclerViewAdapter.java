package com.example.traintickets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<String> fromTownList;
    private final List<String> toTownList;
    private final List<String> intervalList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<String> fromTownList, List<String> toTownList, List<String> intervalList) {
        this.context = context;
        this.fromTownList = fromTownList;
        this.toTownList = toTownList;
        this.intervalList = intervalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View recyclerRow = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.fromTownField.setText(fromTownList.get(position));
        holder.toTownField.setText(toTownList.get(position));
        holder.intervalField.setText(intervalList.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SelectedTrainActivity.class);
                intent.putExtra("FROM", fromTownList.get(position));
                intent.putExtra("TO", toTownList.get(position));
                intent.putExtra("INTERVAL", intervalList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fromTownList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fromTownField;
        private final TextView toTownField;
        private final TextView intervalField;
        private RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fromTownField = itemView.findViewById(R.id.from_text);
            toTownField = itemView.findViewById(R.id.to_text);
            intervalField = itemView.findViewById(R.id.interval);
            relativeLayout = itemView.findViewById(R.id.list_item_layout);
        }
    }
}
