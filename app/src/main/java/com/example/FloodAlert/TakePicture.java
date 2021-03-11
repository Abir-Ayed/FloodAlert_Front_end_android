package com.example.FloodAlert;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.FloodAlert.Authentification.Login;
import com.example.FloodAlert.Citoyens.NavigationCitoyens;
import com.example.FloodAlert.Model.ImageUpload;
import com.example.FloodAlert.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.BitSet;

public class TakePicture extends Activity {


    private ImageView imageView;
    private ImageView btn;
    private TextView btnInsert;
    private EditText description;
    private StorageReference mStorageRef;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private DatabaseReference mDatabaseRef;
    private EditText txtImageName;
    private Uri imgUri;
    private Bitmap imageBitmap;
    public static final String FB_STORAGE_PATH = "image/";
  //  public static final String FB_DATABASE_PATH = "ImageUpload";
   private FirebaseAuth firebaseAuth;

    private String etat = "en cours";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        imageView = findViewById(R.id.imageView3);
        txtImageName =  findViewById(R.id.editText);
        description = findViewById(R.id.description);
        btnInsert = findViewById(R.id.btnPicture);
        btn = findViewById(R.id.btnimage);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("User/"+firebaseAuth.getUid()+"/ImageUpload");



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TakePicture.this,NavigationCitoyens.class);
                startActivity(it);
            }
        });

    }


    public void takePicture(View view) {

        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (imageTakeIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);

        }
    }
    public String getImageExt(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            imgUri = data.getData();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }



    }

    @SuppressWarnings("visibleForTests")
    public void Btninsert(View view){

        if ( imgUri!=null){
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("image insert");
            dialog.show();
            // get the storage reference
            final StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() +"."+getImageExt(imgUri));

            //add file to reference

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //dismiss dialog when success
                            dialog.dismiss();
                            //display success toast msg
                            Toast.makeText(getApplicationContext(), "image insert", Toast.LENGTH_SHORT).show();
                            ImageUpload imageUpload = new ImageUpload(txtImageName.getText().toString(),uri.toString(),description.getText().toString(),etat  );
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(imageUpload);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //dismiss dialog when serror
                    dialog.dismiss();
                    //display success toast msg

                    Toast.makeText(getApplicationContext(),  e.getMessage(),Toast.LENGTH_SHORT).show();




                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {





                }
            });
        }



    }
}



