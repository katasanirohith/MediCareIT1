package com.example.medicare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context mContext;
    private ArrayList<item> mList;
    Adapter(Context context, ArrayList<item> list) {
        mContext = context;
        mList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.cards, viewGroup,false );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        item items = mList.get(i);
        TextView pid,did,date;

        did = viewHolder.doc_id;
        pid = viewHolder.patient_id;
        date = viewHolder.date;

        date.setText(items.getDate());
        pid.setText(items.getPid());
        did.setText(items.getSymptoms());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        TextView patient_id,doc_id,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_id = itemView.findViewById(R.id.doctor_name);
            doc_id = itemView.findViewById(R.id.doctor_name2);
            date = itemView.findViewById(R.id.card_title);

        }
    }

}