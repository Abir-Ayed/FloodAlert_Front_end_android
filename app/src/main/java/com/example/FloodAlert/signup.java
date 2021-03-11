package com.example.FloodAlert;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FloodAlert.Citoyens.NavigationCitoyens;
import com.example.FloodAlert.Model.User;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private ImageView sback;
    private EditText email;
    private EditText phone;
    private EditText password;
    private TextView Inscrire;
    private int pos;
    private String etat = "en cours";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        Inscrire = (TextView) findViewById(R.id.sin);
        email = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.password);
       // phone = (EditText) findViewById(R.id.phone);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");



        sback = (ImageView)findViewById(R.id.sback);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(signup.this, MainActivity.class);
                startActivity(it);

            }
        });

       Inscrire.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final ProgressDialog mDialog = new ProgressDialog(signup.this);
               mDialog.setMessage("Please waiting...");
               mDialog.show();
               myRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if((phone.getText().toString().equals("")) && (email.getText().toString().equals("")) &&  (password.getText().toString().equals(""))){
                           AlertDialog.Builder check1 = new AlertDialog.Builder(signup.this);

                           check1.setMessage("Veuillez remplir les champs !");
                           check1.setPositiveButton(
                                   "Ok",
                                   new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {
                                           dialog.cancel();
                                       }
                                   });
                           AlertDialog alert11 = check1.create();
                           alert11.show();

                       }
                       if (dataSnapshot.child(phone.getText().toString()).exists()) {

                           mDialog.dismiss();
                           Toast.makeText(signup.this, "numero de téléphone exist", Toast.LENGTH_SHORT).show();
                       }
                       else if (email.getText().toString().equals("")) {
                           AlertDialog.Builder check1 = new AlertDialog.Builder(signup.this);
                           check1.setTitle("Erreur Email");
                           check1.setMessage("Veuillez entrer votre Email !");
                           check1.setPositiveButton(
                                   "Ok",
                                   new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {
                                           dialog.cancel();
                                       }
                                   });
                           AlertDialog alert11 = check1.create();
                           alert11.show();
                       }
                       else if (!email.getText().toString().contains("@")) {
                           AlertDialog.Builder check1 = new AlertDialog.Builder(signup.this);
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

                       }
                       else if (phone.getText().toString().equals("")) {
                           AlertDialog.Builder check1 = new AlertDialog.Builder(signup.this);
                           check1.setTitle("Erreur telephone");
                           check1.setMessage("Veuillez entrer votre telephone !");
                           check1.setPositiveButton(
                                   "Ok",
                                   new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {
                                           dialog.cancel();
                                       }
                                   });
                           AlertDialog alert11 = check1.create();
                           alert11.show();

                       }

                       else if (password.getText().toString().equals("")) {
                           AlertDialog.Builder check1 = new AlertDialog.Builder(signup.this);
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

                       }
                       else {
                           mDialog.dismiss();
                     //      User user = new User(email.getText().toString(), password.getText().toString(),etat);
                           Toast.makeText(signup.this, "register successfull", Toast.LENGTH_SHORT).show();
                        //   myRef.child(phone.getText().toString()).setValue(user);
                           if(etat.toString().equals("en cours")){


                               Intent it = new Intent(signup.this,Activecompte.class);
                               startActivity(it);


                           }
                          else {
                               Intent it = new Intent(signup.this,NavigationCitoyens.class);
                               startActivity(it);

                           }

                           finish();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }
       });


    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sSelected=parent.getItemAtPosition(position).toString();
        Toast.makeText(this,sSelected,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
