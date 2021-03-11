package com.example.FloodAlert;

        import android.graphics.Bitmap;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.Html;
        import android.view.MenuItem;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.FloodAlert.Citoyens.Profile;
        import com.example.FloodAlert.Model.ImageUpload;
        import com.example.FloodAlert.Model.User;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;

        import java.util.ArrayList;

public class EtatReclamation extends AppCompatActivity {

    private ListView listView;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private ArrayList<String> list;
    private ArrayAdapter <String> adapter;
    ImageUpload imageUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_reclamation);
        imageUpload = new ImageUpload();
        listView = findViewById(R.id.listView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ref= database.getReference("User/"+firebaseAuth.getUid()+"/ImageUpload" );
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.activity_retrieve_image,R.id.etat,list);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    imageUpload = ds.getValue(ImageUpload.class);
                    list.add(imageUpload.getName().toString()+"             "+imageUpload.getEtat().toString());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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