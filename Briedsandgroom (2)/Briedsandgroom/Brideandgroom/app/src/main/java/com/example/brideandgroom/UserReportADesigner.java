package com.example.brideandgroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class UserReportADesigner extends AppCompatActivity implements JsonResponse {

    EditText e1;
    Button b1;
    String reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_a_designer);

        e1=(EditText)findViewById(R.id.etreason);
        b1=(Button)findViewById(R.id.btreport);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                reason=e1.getText().toString();




//				startService(new Intent(getApplicationContext(),LocationService.class));


                if(reason.equalsIgnoreCase(""))
                {
                    e1.setError("please enter your reason");
                    e1.setFocusable(true);
                }


                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) UserReportADesigner.this;
                    String q = "/userreported?did="+UserViewDesigners.dids+"&reason=" + reason+"&lid="+Login.logid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }

            }
        });
    }

    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){





                Toast.makeText(getApplicationContext(), "shared", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),UserViewDesigners.class));

            }

            else
            {
//                startActivity(new Intent(getApplicationContext(),Register.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),UserShareDesignIdeas.class);
        startActivity(b);
    }
}