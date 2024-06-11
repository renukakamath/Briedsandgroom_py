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

public class UserViewDesigners extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {

    ListView lv1;
    String [] did,dname,place,landmark,pincode,phone,email,val;
    public static String dids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_designers);




        lv1=(ListView)findViewById(R.id.lvdesigners);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) UserViewDesigners.this;
        String q = "/Userviewdesiners";
        q=q.replace(" ","%20");
        JR.execute(q);



        }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        dids=did[i];

        final CharSequence[] items = {"View Designs","Users Design","Report Designer","Add Rating","Add Feedback","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewDesigners.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("View Designs"))
                {
                    startActivity(new Intent(getApplicationContext(),UserViewDesigns.class));
                }
                else if (items[item].equals("Users Design"))
                {
                    startActivity(new Intent(getApplicationContext(),UserShareDesignIdeas.class));
                }
                else if (items[item].equals("Report Designer"))
                {
                    startActivity(new Intent(getApplicationContext(),UserReportADesigner.class));
                }

                else if (items[item].equals("Add Rating"))
                {
                    startActivity(new Intent(getApplicationContext(),User_review_rating.class));
                }
                else if (items[item].equals("Add Feedback"))
                {
                    startActivity(new Intent(getApplicationContext(),User_Send_feedback.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }



    @Override
    public void response(JSONObject jo) {


        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Userviewdesiners")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    did=new String[ja1.length()];
                    dname=new String[ja1.length()];
                    place=new String[ja1.length()];
                    landmark=new String[ja1.length()];
                    pincode=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    email=new String[ja1.length()];



                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {


                        did[i]=ja1.getJSONObject(i).getString("designer_id");
                        dname[i]=ja1.getJSONObject(i).getString("designer_name");
                        landmark[i]=ja1.getJSONObject(i).getString("landmark");
                        pincode[i]=ja1.getJSONObject(i).getString("pincode");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        place[i]=ja1.getJSONObject(i).getString("place");


                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Designer: "+dname[i]+"\nPlace:  "+place[i]+"\nLandmark: "+landmark[i]+"\n Pincode: "+pincode[i]+"\nPhone:  "+phone[i]+"\nEmail:  "+email[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
//			if(method.equalsIgnoreCase("buyprod"))
//			{
//				String status=jo.getString("status");
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//				if(status.equalsIgnoreCase("success"))
//				{
//					Toast.makeText(getApplicationContext(),"Your order is submitted!", Toast.LENGTH_LONG).show();
//				}
//				else{
//					Toast.makeText(getApplicationContext(),"Your order is not submitted", Toast.LENGTH_LONG).show();
//				}
//			}
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }
}