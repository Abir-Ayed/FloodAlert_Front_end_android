package com.example.FloodAlert.Citoyens;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FloodAlert.Authentification.Register;
import com.example.FloodAlert.Model.ImageUpload;
import com.example.FloodAlert.Model.User;

import com.example.FloodAlert.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {


    private static final String TAG = "ViewDatabase";

    private EditText newUserName,newUserRegion,newUserPhone;
    private TextView update,updatePassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        newUserName = findViewById(R.id.name);
       // newUserEmail = findViewById(R.id.mail);
     //   newUserPassword = findViewById(R.id.password);
        newUserPhone = findViewById(R.id.phone);
        newUserRegion = findViewById(R.id.region);
        update = findViewById(R.id.UpdateProfile);
     //   updatePassword = findViewById(R.id.editPassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        final DatabaseReference databaseReference = firebaseDatabase.getReference("User/"+firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User userProfile = dataSnapshot.getValue(User.class);
                newUserName.setText(userProfile.getName());
              //  newUserEmail.setText(userProfile.getEmail());
                newUserPhone.setText(userProfile.getPhone());
                newUserRegion.setText(userProfile.getRegion());
             //   newUserPassword.setText(userProfile.getPassword());
              // newUserPassword.setText(userProfile.getPassword());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
     update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String name = newUserName.getText().toString();

        String phone = newUserPhone.getText().toString();
        String region = newUserRegion.getText().toString();

       // String password = newUserPassword.getText().toString();

          FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){

            email = user.getEmail();

        }

        User userProfile = new User(email,phone,name,region);

        Toast.makeText(UpdateProfile.this, "vous avez modifier votre profile", Toast.LENGTH_SHORT).show();

        databaseReference.setValue(userProfile);

       finish();

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

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
