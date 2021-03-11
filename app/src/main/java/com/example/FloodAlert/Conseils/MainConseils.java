package com.example.FloodAlert.Conseils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.FloodAlert.Model.Conseil;
import com.example.FloodAlert.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainConseils extends AppCompatActivity implements Adapter.OnItemClickListener {

    public static final String url = "http://192.168.1.9/rec_conseils.php";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private ArrayList<Conseil> arrayList;
    private RecyclerView recyclerView;
    private Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conseil_activity);

         recyclerView = (RecyclerView) findViewById(R.id.rv);

         recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        ParseData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



   private  void ParseData() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray jsonArray = response.getJSONArray("conseils");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject cons = jsonArray.getJSONObject(i);

              String objet = cons.getString("objet");
              String periode = cons.getString("periode");

             //Date gebDate = df.parse(date);
            //  Date date = df.parse("24-05-2019 ");

                                String description = cons.getString("description");

                                arrayList.add(new Conseil(objet,periode, description));




                            adapter = new Adapter(MainConseils.this,arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(MainConseils.this);}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {

        Conseil clickitem = arrayList.get(position);

        String objet = clickitem.getObjet();
        Toast.makeText(this,objet,Toast.LENGTH_SHORT).show();


    }

    public void conseilAvant(View view){


        Toast.makeText(this, "conseils avant", Toast.LENGTH_SHORT).show();
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
