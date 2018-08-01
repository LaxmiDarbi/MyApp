package com.example.dslab.myapp;

import android.app.Notification;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    Button alert,toast,notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        alert = findViewById(R.id.button1);
        toast = findViewById(R.id.button2);
        notification = findViewById(R.id.button3);

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                View view1 = LayoutInflater.from(Main2Activity.this).inflate(R.layout.item_view, (ViewGroup) findViewById(R.id.testDemo));
                builder.setView(view1);
                builder.setTitle("Alert Dialog Box");
                builder.setMessage("Are you sure,you want delete?");


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Main2Activity.this, "You clicked OK button", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        }
                        });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Main2Activity.this, "You clicked CANCEL button", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        }
                        });
                    }
                });
                toast.setOnClickListener(new View.OnClickListener(){
                  @Override
                   public void onClick(View view) {
                    Toast toast= new Toast(Main2Activity.this);
                    View view1 = LayoutInflater.from(Main2Activity.this).inflate(R.layout.item_view,(ViewGroup)findViewById(R.id.testDemo));
                    toast.setView(view1);
                    toast.show();
                         }
                    });
                    notification.setOnClickListener(new View.OnClickListener() {
                        @Override
                         public void onClick(View view) {


                            }
                    });

    }
}



