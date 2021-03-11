package com.example.FloodAlert.Citoyens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FloodAlert.Activecompte;
import com.example.FloodAlert.Authentification.Login;
import com.example.FloodAlert.Authentification.Register;
import com.example.FloodAlert.Model.User;
import com.example.FloodAlert.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AncienPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ViewDatabase";

    private EditText profilePassword,confirmPassword,newPassword;
    private TextView passwordAncien;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private String password,passwordConfirmer,passwordNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancien_password);

        profilePassword = findViewById(R.id.pass);
        passwordAncien = findViewById(R.id.AncienPassword);
        confirmPassword = findViewById(R.id.confirmpass);
        newPassword = (EditText)findViewById(R.id.newpass);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
       // FirebaseUser user = mAuth.getCurrentUser();
       // userID = user.getUid();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        passwordAncien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });


    }

    public void ChangePassword() {


        final FirebaseUser user = mAuth.getCurrentUser();
        if(confirmPassword.getText().toString().equals("") && newPassword.getText().toString().equals("") && profilePassword.getText().toString().equals(""))
        {
            Toast.makeText(AncienPasswordActivity.this,"remplir les champs vides",Toast.LENGTH_SHORT).show();
        }
            else{
        if (confirmPassword.getText().toString().equals(newPassword.getText().toString())) {

            if (user != null && user.getEmail() != null) {
                // Toast.makeText(AncienPasswordActivity.this,"user success",Toast.LENGTH_SHORT).show();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), profilePassword.getText().toString());

// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if (task.isSuccessful()) {
                                    String userPasswordNew = newPassword.getText().toString();
                                    user.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AncienPasswordActivity.this, "mot de passe changé", Toast.LENGTH_SHORT).show();
                                                finish();

                                                Intent intent = new Intent(AncienPasswordActivity.this, NavigationCitoyens.class);
                                                startActivity(intent);

                                            } else {
                                                Toast.makeText(AncienPasswordActivity.this,
                                                        "La mise à jour du mot de passe a échoué", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                                } else {

                                    Toast.makeText(AncienPasswordActivity.this, "échoué", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } else {


            }


        } else {
            Toast.makeText(this, "Mot de passe incompatible", Toast.LENGTH_SHORT).show();


        }


    }


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
