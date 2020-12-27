package com.example.namaztimingusingsalatapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.azan.Azan;
import com.azan.PrayerTime;
import com.azan.astrologicalCalc.Location;
import com.azan.astrologicalCalc.SimpleDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    TextView fajr,dhur,asar,maghrib,isha;
    String url = "http://api.aladhan.com/v1/calendar?latitude=33.3402964&longitude=73.8005502&method=2&month=12&year=2020";
    //Progress Dialogue
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fajr = (TextView)findViewById(R.id.fajarTv);
//        dhur = (TextView)findViewById(R.id.duhurTv);
//        asar = (TextView)findViewById(R.id.asarTv);
//        maghrib = (TextView)findViewById(R.id.maghribTv);
//        isha = (TextView)findViewById(R.id.ishaTv);
        setContentView(R.layout.activity_main);
         pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        //Getting Data from json object;


                        try {
                            fajr = (TextView)findViewById(R.id.fajarTv);
                            dhur = (TextView)findViewById(R.id.duhurTv);
                            asar = (TextView)findViewById(R.id.asarTv);
                            maghrib = (TextView)findViewById(R.id.maghribTv);
                            isha = (TextView)findViewById(R.id.ishaTv);
                            //String jsonArray = response.getJSONObject("timings").get("Fajr").toString();
                            JSONArray jsonArray = response.getJSONArray("data");
                            JSONObject jsonObject = jsonArray.optJSONObject(28);
                            //String value = jsonObject.getString("Fajr:");
                           int value = jsonObject.length();
                           JSONObject timing= jsonObject.getJSONObject("timings");
                           String fajr1= timing.getString("Fajr");
                           fajr.setText(fajr1);
                           String dhur1= timing.getString("Dhuhr");
                          dhur.setText(dhur1);
                          String asr = timing.getString("Asr");
                          asar.setText(asr);
                          String magrib = timing.getString("Maghrib");
                          maghrib.setText(magrib);
                          String isha1 = timing.getString("Isha");
                          isha.setText(isha1);



                            //Toast.makeText(MainActivity.this,v,Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,"Error try catch",Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }


                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                Toast.makeText(MainActivity.this,"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();

                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);





    }
}