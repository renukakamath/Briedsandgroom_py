package com.example.brideandgroom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class UserShareDesignIdeas extends AppCompatActivity implements  JsonResponse {

    EditText e1,e2,e3,e4;
    Button bt1;
    String title,desc,budget,date;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_share_design_ideas);

        e1=(EditText)findViewById(R.id.ettitle);
        e2=(EditText)findViewById(R.id.etdesc);
        e3=(EditText)findViewById(R.id.etbudget);
        e4=(EditText)findViewById(R.id.etdate);
        bt1=(Button)findViewById(R.id.button);

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(UserShareDesignIdeas.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                e4.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                title=e1.getText().toString();
                desc=e2.getText().toString();
                budget=e3.getText().toString();
                date=e4.getText().toString();




//				startService(new Intent(getApplicationContext(),LocationService.class));


                if(title.equalsIgnoreCase(""))
                {
                    e1.setError("please enter your title");
                    e1.setFocusable(true);
                }
                else if(desc.equalsIgnoreCase(""))
                {
                    e2.setError("please enter description");
                    e2.setFocusable(true);
                }

                else if(budget.equalsIgnoreCase(""))
                {
                    e3.setError("please enter budget");
                    e3.setFocusable(true);
                }
                else if(date.equalsIgnoreCase(""))
                {
                    e4.setError("please enter your date");
                    e4.setFocusable(true);
                }

                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) UserShareDesignIdeas.this;
                    String q = "/usershareideas?did="+UserViewDesigners.dids+"&title=" + title + "&desc=" + desc + "&budget=" + budget + "&date=" + date+"&lid="+Login.logid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }

            }
        });
    }

    @Override
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