package com.example.tanviwagle.textfinder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Second extends AppCompatActivity {

    EditText height;
    Button button;
    EditText weight;
    EditText age;
    EditText sex;
    String total = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button=(Button)findViewById(R.id.button2);
        height=(EditText)findViewById(R.id.editText6);
        weight=(EditText)findViewById(R.id.editText7);
        age=(EditText)findViewById(R.id.editText8);
        sex=(EditText)findViewById(R.id.editText9);



        //Toast.makeText(this,getIntent().getStringExtra("TEST"),Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = height.getText()+"/"+weight.getText()+"/"+age.getText()+"/"+sex.getText();

                Intent sendInfoBack = new Intent();
                sendInfoBack.putExtra(MainActivity.IntentCodeX,total);

                setResult(RESULT_OK,sendInfoBack);
                finish();
            }
        });
    }

}
