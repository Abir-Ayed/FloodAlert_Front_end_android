package com.example.FloodAlert.Reclamation;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FloodAlert.Conseils.Adapter;
import com.example.FloodAlert.Model.ImageUpload;
import com.example.FloodAlert.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReclamationActivity extends AppCompatActivity  {
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<ImageUpload> list;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));


        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("User/"+firebaseAuth.getUid()+"/ImageUpload");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<ImageUpload>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ImageUpload p = dataSnapshot1.getValue(ImageUpload.class);
                    list.add(p);
                }
                adapter = new MyAdapter(ReclamationActivity.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ReclamationActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }

/*    private void DeleteEtat(){


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.delete_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.name);
        final EditText editTextEtat = (EditText) dialogView.findViewById(R.id.etat);
        final TextView buttonDelete = (TextView) dialogView.findViewById(R.id.DeleteEtat);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }*/
@Override
public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()){
        case android.R.id.home:
            onBackPressed();
    }
    return super.onOptionsItemSelected(item);
}

}
