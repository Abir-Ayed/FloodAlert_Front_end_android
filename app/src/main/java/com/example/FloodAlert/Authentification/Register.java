package com.example.FloodAlert.Authentification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FloodAlert.Activecompte;
import com.example.FloodAlert.Citoyens.NavigationCitoyens;
import com.example.FloodAlert.MainActivity;
import com.example.FloodAlert.Model.User;
import com.example.FloodAlert.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Register extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    private EditText userName, userPassword, userEmail,userPhone;
    private TextView inscrire;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView sback;
    private Spinner spinner;
    String name, email,password,phone;
    private String region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setupUIViews();
        final Spinner spinner = (Spinner) findViewById(R.id.region_spinner);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this,R.layout.color_spinner_layout, getResources().getStringArray(R.array.region_array));
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        sback = (ImageView)findViewById(R.id.sback);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(Register.this, MainActivity.class);
                startActivity(it);

            }
        });
       inscrire.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(validate()){
                   String user_email = userEmail.getText().toString().trim();
                   String user_password = userPassword.getText().toString().trim();
                   firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               sendEmailVerification();

                               sendUserData();

                               Toast.makeText(Register.this, "inscription réussi", Toast.LENGTH_SHORT).show();

                               finish();

                           //  startActivity(new Intent(Register.this, Activecompte.class));
                           }else{
                               Toast.makeText(Register.this, "inscription échoue", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           }
       });

    }

    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.name);
        userPassword = (EditText)findViewById(R.id.password);
        userEmail = (EditText)findViewById(R.id.mail);
        inscrire = (TextView)findViewById(R.id.sin);
        userPhone = (EditText) findViewById(R.id.phone);

    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                      /// sendUserData();
                       Toast.makeText(Register.this, "Enregistré avec succès, courrier de vérification envoyé!", Toast.LENGTH_SHORT).show();

                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Register.this,Activecompte.class));
                    }else{
                        Toast.makeText(Register.this, "Le courrier de vérification n'a pas été envoyé!", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

    }

    private Boolean validate(){
        Boolean result = false;


        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        phone = userPhone.getText().toString();

       if (name.equals("")) {
            AlertDialog.Builder check1 = new AlertDialog.Builder(Register.this);
            check1.setTitle("Erreur Nom");
            check1.setMessage("Veuillez entrer votre Nom !");
            check1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = check1.create();
            alert11.show();
            result = false;
        }

        else if (email.equals("")) {
            AlertDialog.Builder check1 = new AlertDialog.Builder(Register.this);
            check1.setTitle("Erreur email");
            check1.setMessage("Veuillez entrer votre email !");
            check1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = check1.create();
            alert11.show();
            result = false;
        }
        else if (!email.contains("@")) {
            AlertDialog.Builder check1 = new AlertDialog.Builder(Register.this);
            check1.setTitle("Erreur format email");
            check1.setMessage("Veuillez entrer un vrai email contenant @ ");
            check1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = check1.create();
            alert11.show();
            result = false;
        }


        else if (password.equals("")) {
            AlertDialog.Builder check1 = new AlertDialog.Builder(Register.this);
            check1.setTitle("Erreur mot de passe");
            check1.setMessage("Veuillez entrer votre mot de passe !");
            check1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = check1.create();
            alert11.show();
            result = false;
        }
       else if (phone.equals("")) {
           AlertDialog.Builder check1 = new AlertDialog.Builder(Register.this);
           check1.setTitle("Erreur mot de passe");
           check1.setMessage("Veuillez entrer votre mot de passe !");
           check1.setPositiveButton(
                   "Ok",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.cancel();
                       }
                   });
           AlertDialog alert11 = check1.create();
           alert11.show();
           result = false;
       }


        else{
            result = true;
        }

        return result;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         region= parent.getItemAtPosition(position).toString();
        // Notify the selected item text
        Toast.makeText (getApplicationContext(), region, Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void sendUserData(){



        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("User/"+firebaseAuth.getUid());
        User userProfile = new User(email, phone, password, name, region);
        myRef.setValue(userProfile);
    }


}