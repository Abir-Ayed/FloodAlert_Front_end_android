package com.example.FloodAlert.Authentification;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FloodAlert.Citoyens.NavigationCitoyens;
import com.example.FloodAlert.MainActivity;
import com.example.FloodAlert.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener {



    private EditText Email;
    private EditText Password;

    private TextView login;
    private TextView btnForgotPassord;
    private ImageView sinb;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String etat = " en cours";
    String  email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Email = (EditText) findViewById(R.id.email);
        login = (TextView) findViewById(R.id.sin);
        btnForgotPassord= (TextView) findViewById(R.id.tvForgotPassword);
        Password = (EditText) findViewById(R.id.password);

        sinb = (ImageView)findViewById(R.id.sinb);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        final DatabaseReference databaseReference = firebaseDatabase.getReference("User/" + firebaseAuth.getUid());

        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(),Login.class));
        }


        progressDialog = new ProgressDialog(this);
        //attaching click listener



        login.setOnClickListener( this);

        btnForgotPassord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Password.class));
            }
        });

        sinb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(Login.this, MainActivity.class);
                startActivity(it);

            }
        });
    }





    @Override
    public void onClick(View v) {
        if(v == login){
            userLogin();
        }
    }

    public void userLogin() {



        String email = Email.getText().toString().trim();
        String password  = Password.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            progressDialog.dismiss();
            AlertDialog.Builder check1 = new AlertDialog.Builder(Login.this);

            check1.setMessage("Veuillez entrer email !");
            check1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = check1.create();
            alert11.show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            progressDialog.dismiss();
            AlertDialog.Builder check1 = new AlertDialog.Builder(Login.this);

            check1.setMessage("Veuillez entrer password !");
            check1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = check1.create();
            alert11.show();
            return;
        }

        if (!email.contains("@")) {
            android.support.v7.app.AlertDialog.Builder check1 = new android.support.v7.app.AlertDialog.Builder(Login.this);
            check1.setTitle("Erreur format email");
            check1.setMessage("Veuillez entrer un vrai email contenant @ ");
            check1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = check1.create();
            alert11.show();
            return;
        }



        progressDialog.setMessage("Connexion en cours, veuillez patienter...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){

                                Toast.makeText(Login.this,"votre adresse email est verifiée",Toast.LENGTH_SHORT).show();


                                startActivity(new Intent(getApplicationContext(), NavigationCitoyens.class));
                            }else{
                                Toast.makeText(Login.this,"S'il te plait verifier votre adresse email",Toast.LENGTH_SHORT).show();

                            }

                        }else {

                            Toast.makeText(Login.this,"Votre email ou mot de passe est incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}


