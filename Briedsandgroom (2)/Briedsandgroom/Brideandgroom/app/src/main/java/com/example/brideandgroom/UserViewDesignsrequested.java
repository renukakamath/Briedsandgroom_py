package com.example.brideandgroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewDesignsrequested extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {

    ListView lv1;
    String [] rid,title,description,budget,ed,date,val;
    public static String ridss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_designsrequested);

        lv1=(ListView)findViewById(R.id.lvdesigns);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) UserViewDesignsrequested.this;
        String q = "/Userviewdesignsrequested?lid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        ridss=rid[i];

        final CharSequence[] items = {"View Customized Design","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewDesignsrequested.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("View Customized Design"))
                {
                    startActivity(new Intent(getApplicationContext(),ViewCutomdesigned.class));
//                    JsonReq JR=new JsonReq();
//                    JR.json_response=(JsonResponse) UserViewDesigns.this;
//                    String q = "/userbookdesign?did="+dsids+"&lid="+Login.logid;
//                    q=q.replace(" ","%20");
//                    JR.execute(q);
                }

            }

        });
        builder.show();
    }

    public void response(JSONObject jo) {


        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("Userviewdesignsrequested")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    rid = new String[ja1.length()];
                    title = new String[ja1.length()];
                    description = new String[ja1.length()];
                    ed = new String[ja1.length()];
                    budget = new String[ja1.length()];
                    date = new String[ja1.length()];

                    val = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {


                        rid[i] = ja1.getJSONObject(i).getString("request_id");
                        title[i] = ja1.getJSONObject(i).getString("title");
                        description[i] = ja1.getJSONObject(i).getString("description");
                        ed[i] = ja1.getJSONObject(i).getString("budget");
                        budget[i] = ja1.getJSONObject(i).getString("estimated_date");
                        date[i] = ja1.getJSONObject(i).getString("date_time");



                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
                        val[i] = "Title: " + title[i] + "\nDescription:  " + description[i] + "\nEstimated Date: " + ed[i] + "\n Date: " + date[i];

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