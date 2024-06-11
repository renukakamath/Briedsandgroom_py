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

public class ViewCutomdesigned extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {

    ListView lv1;
    String [] cdid,details,material,price,image,date,statusss,val;
    public static String cdids,statussss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cutomdesigned);

        lv1=(ListView)findViewById(R.id.lvdesigns);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewCutomdesigned.this;
        String q = "/viewcustom?rid="+UserViewDesignsrequested.ridss;
        q=q.replace(" ","%20");
        JR.execute(q);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        cdids=cdid[i];
        statussss=statusss[i];

        if(statussss.equalsIgnoreCase("pending")) {
            final CharSequence[] items = {"Accept", "Reject", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewCutomdesigned.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Accept")) {

                        JsonReq JR=new JsonReq();
                        JR.json_response=(JsonResponse) ViewCutomdesigned.this;
                        String q = "/acceptcustomrequest?rid="+cdids+"&lid="+Login.logid;
                        q=q.replace(" ","%20");
                        JR.execute(q);
                    }
                    else if (items[item].equals("Reject")) {

                            JsonReq JR=new JsonReq();
                            JR.json_response=(JsonResponse) ViewCutomdesigned.this;
                            String q = "/rejectcustomrequest?rid="+cdids+"&lid="+Login.logid;
                            q=q.replace(" ","%20");
                            JR.execute(q);
                        }


                }

            });
            builder.show();
        }
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

                    cdid = new String[ja1.length()];
                    details = new String[ja1.length()];
                    material = new String[ja1.length()];
                    price = new String[ja1.length()];
                    image = new String[ja1.length()];
                    date = new String[ja1.length()];
                    statusss = new String[ja1.length()];

                    val = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {


                        cdid[i] = ja1.getJSONObject(i).getString("custom_design_id");
                        details[i] = ja1.getJSONObject(i).getString("details");
                        material[i] = ja1.getJSONObject(i).getString("material");
                        price[i] = ja1.getJSONObject(i).getString("price");
                        image[i] = ja1.getJSONObject(i).getString("image");
                        date[i] = ja1.getJSONObject(i).getString("date_time");
                        statusss[i] = ja1.getJSONObject(i).getString("status");


                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
                        val[i] = "Details: " + details[i] + "\nMaterial:  " + material[i] + "\nPrice: " + price[i] + "\n Date: " + date[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    lv1.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
            if (method.equalsIgnoreCase("requests")) {
                String status = jo.getString("status");
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Suucessfully!", Toast.LENGTH_LONG).show();
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