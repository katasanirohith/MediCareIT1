package com.example.medicare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter <Adapter.myViewHolder> {

    Context mContext;
    List<item> mData;

    public Adapter(Context mContext, List<item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.cards, viewGroup,false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {

        myViewHolder.doctor_id.setText(mData.get(i).getDoctor_id());
        myViewHolder.disease_name.setText(mData.get(i).getDiagnosis());
        myViewHolder.date_visit.setText(mData.get(i).getDate());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView doctor_id,slot,pid,diagnosis,date_visit;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_id = itemView.findViewById(R.id.doctor_id);
            disease_name = itemView.findViewById(R.id.doctor_name2);
            date_visit = itemView.findViewById(R.id.date);

        }
    }
}
