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

public class UserViewDesigns extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {

    ListView lv1;
    String [] did,model,material,dname,details,photo,price,stock,val;
    public static String dsids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_designs);

        lv1=(ListView)findViewById(R.id.lvdesigns);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) UserViewDesigns.this;
        String q = "/Userviewdesigns?did="+UserViewDesigners.dids;
        q=q.replace(" ","%20");
        JR.execute(q);



    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        dsids=did[i];

        final CharSequence[] items = {"Book Design","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewDesigns.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Book Design"))
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) UserViewDesigns.this;
                    String q = "/userbookdesign?did="+dsids+"&lid="+Login.logid;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }

            }

        });
        builder.show();
    }



    @Override
    public void response(JSONObject jo) {


        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Userviewdesigns")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    did=new String[ja1.length()];
                    dname=new String[ja1.length()];
                    model=new String[ja1.length()];
                    material=new String[ja1.length()];
                    details=new String[ja1.length()];
                    photo=new String[ja1.length()];
                    price=new String[ja1.length()];
                    stock=new String[ja1.length()];



                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {


                        did[i]=ja1.getJSONObject(i).getString("design_id");
                        dname[i]=ja1.getJSONObject(i).getString("design_name");
                        model[i]=ja1.getJSONObject(i).getString("model");
                        material[i]=ja1.getJSONObject(i).getString("material");
                        details[i]=ja1.getJSONObject(i).getString("details");
                        photo[i]=ja1.getJSONObject(i).getString("photo");
                        price[i]=ja1.getJSONObject(i).getString("price");
                        stock[i]=ja1.getJSONObject(i).getString("made_stock");


                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Design: "+dname[i]+"\nModel:  "+model[i]+"\nMaterial: "+material[i]+"\n Details: "+details[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
			if(method.equalsIgnoreCase("userbookdesign"))
			{
				String status=jo.getString("status");
				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(),"Booked!", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(getApplicationContext(),"Not Booked", Toast.LENGTH_LONG).show();
				}
			}
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }
}