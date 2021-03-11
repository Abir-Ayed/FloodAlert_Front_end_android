package com.example.FloodAlert.Reclamation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FloodAlert.Authentification.Login;
import com.example.FloodAlert.Conseils.Adapter;
import com.example.FloodAlert.Model.ImageUpload;
import com.example.FloodAlert.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<ImageUpload> imageUpload;


    public MyAdapter(Context c, ArrayList<ImageUpload> img) {

        context = c;
        imageUpload = img;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name.setText(imageUpload.get(position).getName());
        holder.etat.setText(imageUpload.get(position).getEtat());
        Picasso.get().load(imageUpload.get(position).getUrl()).into(holder.url);



        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                deleteItemFromList(v, position);


            }
        });

    }

    @Override
    public int getItemCount() {
        return imageUpload.size();
    }

    private void deleteItemFromList(View v, final int position) {
   final FirebaseStorage mStorageRef = FirebaseStorage.getInstance();
  final   FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        //builder.setTitle("Dlete ");
        builder.setMessage("Supprimer etat ?")
                .setCancelable(false)
                .setPositiveButton("CONFIRMATION",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                final ImageUpload data = imageUpload.get(position);
                            if(firebaseAuth.getCurrentUser() != null) {
     final DatabaseReference databaseReference = database.getReference().child("User").child(firebaseAuth.getUid()).child("ImageUpload");
        /*  StorageReference imageRef = mStorageRef.getReferenceFromUrl(data.getUrl());

                                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        databaseReference.removeValue();

                                        notifyItemRemoved(position);
                                    }
                                });*/

                                StorageReference imageRef = mStorageRef.getReferenceFromUrl(data.getUrl());
                                imageRef.delete();

                                Query mQuery = databaseReference.orderByChild("url").equalTo(data.getUrl());

                                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                          for (DataSnapshot ds: dataSnapshot.getChildren()){

                              ds.getRef().removeValue();
                          }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });




                                    notifyDataSetChanged();

                                }
                            }
                        })
                .setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

        builder.show();

    }





    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, etat;
        ImageView url,btn_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            etat = (TextView) itemView.findViewById(R.id.etat);
            url = (ImageView) itemView.findViewById(R.id.observationCitoyens);
            btn_delete = (ImageView) itemView.findViewById(R.id.iconDelete);



        }


    }

}