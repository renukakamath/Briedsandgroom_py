package com.example.brideandgroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewBookings extends AppCompatActivity implements  JsonResponse {

    ListView lv1;
    String [] bid,date,statusss,dname,val;
    public static String dsids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_bookings);

        lv1=(ListView)findViewById(R.id.lvdesigns);
//        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) UserViewBookings.this;
        String q = "/Userviewbookings?lid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);


    }


    public void response(JSONObject jo) {


        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("Userviewbookings")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    bid = new String[ja1.length()];
                    dname = new String[ja1.length()];
                    date = new String[ja1.length()];
                    statusss = new String[ja1.length()];



                    val = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {


                        bid[i] = ja1.getJSONObject(i).getString("booking_id");
                        dname[i] = ja1.getJSONObject(i).getString("design_name");
                        date[i] = ja1.getJSONObject(i).getString("date_time");
                        statusss[i] = ja1.getJSONObject(i).getString("status");



                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
                        val[i] = "Design: " + dname[i] + "\nDate:  " + date[i] + "\nStatus: " + statusss[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    lv1.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
            if (method.equalsIgnoreCase("userbookdesign")) {
                String status = jo.getString("status");
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Booked!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Booked", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}