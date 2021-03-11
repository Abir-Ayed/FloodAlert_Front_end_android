package com.example.FloodAlert.Citoyens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FloodAlert.Authentification.Login;
import com.example.FloodAlert.MainActivity;
import com.example.FloodAlert.Model.User;
import com.example.FloodAlert.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Profile extends AppCompatActivity {

    private TextView profileEmail;
    private TextView profileName,profilePhone,Desactivecompte,profileRegion,profilePassword;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private ProgressBar progressBar;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profileEmail = findViewById(R.id.maill);
        profileName = findViewById(R.id.ususr);
        profilePhone = findViewById(R.id.usrphone);
        Desactivecompte = findViewById(R.id.compte_desactivate);
        profileRegion = findViewById(R.id.usr_region);
     //  profilePassword = findViewById(R.id.usr_password);
        // profilePassword = findViewById(R.id.pswrdd);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("User/" + firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
        try{
    profileName.setText(userProfile.getName());
    profileEmail.setText(userProfile.getEmail());
    profilePhone.setText(userProfile.getPhone());
     // profilePassword.setText(userProfile.getPassword());

      profileRegion.setText(userProfile.getRegion());

        }
       catch(NullPointerException e){
           Log.e("error", e.toString());
       }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        Desactivecompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Profile.this);
                dialog.setTitle("Vous etes sur");
                dialog.setMessage("La suppression de ce compte entraînera la suppression complète de votre compte du système et vous ne pourrez pas accéder à l'application.");

                dialog.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                 firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                             if (task.isSuccessful()){

                                 Toast.makeText(Profile.this,"compte supprimer",Toast.LENGTH_SHORT).show();
                                  Intent intent = new Intent(Profile.this,MainActivity.class);
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                             }else{
                                 Toast.makeText(Profile.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                             }
                            }
                        });
                        databaseReference.removeValue();

                    }
                });
          dialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  dialogInterface.dismiss();
              }
          });
          AlertDialog alertDialog = dialog.create();
          alertDialog.show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}


