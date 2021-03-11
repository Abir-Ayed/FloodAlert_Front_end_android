package com.example.FloodAlert.Conseils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.FloodAlert.Model.Conseil;
import com.example.FloodAlert.R;

import java.util.ArrayList;
import java.util.Date;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {



    private Context ctx ;
    private ArrayList<Conseil> mArrayList ;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{

        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public Adapter(Context ctx, ArrayList<Conseil> mArrayList) {
        this.ctx = ctx;
        this.mArrayList = mArrayList;
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {


       View v = LayoutInflater.from(ctx).inflate(R.layout.card_layout, parent, false);
       return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        Conseil conseils = mArrayList.get(position);

        String tv_objet = conseils.getObjet();

        String tv_periode = conseils.getPeriode();
        String tv_description  = conseils.getDescription();

        holder.tv_objet.setText(tv_objet);

        holder.tv_periode.setText(tv_periode);
        holder.tv_description .setText(tv_description);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tv_objet ;

        TextView tv_periode ;
        TextView tv_description ;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_objet = (TextView) itemView.findViewById(R.id.print_objet);
          

            tv_periode = (TextView) itemView.findViewById(R.id.print_periode);
            tv_description = (TextView) itemView.findViewById(R.id.print_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
