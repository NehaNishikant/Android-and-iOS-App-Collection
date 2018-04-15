package com.example.tanviwagle.textfinder;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static android.R.attr.button;


public class MainActivity extends AppCompatActivity {
    Button button;
    Button enterData;
    TextView title;
    TextView eatenToday;
    int caloriesX=0;
    String bodyInput;
    final String TAG = "COUNTERTAG";
    int counter = 0;
    static final int NUMBER_CODE = 12345;
    static final String INTENT_CODE = "CODE";
    static final String IntentCodeX = "CODEX";
    String[] arr = new String[4];
    double q;
    Button reset;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if (caloriesX != null){
        if (data.hasExtra("CODE")){
            caloriesX +=data.getIntExtra("CODE",0);
        }
        else if (data.hasExtra(IntentCodeX))
        {
            bodyInput = data.getStringExtra(IntentCodeX);
            arr = bodyInput.split("/");
            if (arr[3].toUpperCase().equals("M"))
            {
                q= 66+(6.2*(Integer.parseInt(arr[1])))+(12.7*(Integer.parseInt(arr[0])))-(6.76*(Integer.parseInt(arr[2])));
            }
            else
            {
                q= 655.1+(4.35*(Integer.parseInt(arr[1])))+(4.7*(Integer.parseInt(arr[0])))-(4.7*(Integer.parseInt(arr[2])));

            }
            title.setText("Your Recommended Daily Calorie Intake "+(int)q);
        }
        //Log.d("CaroesX",caloriesX+"");
        Log.d("hi", caloriesX+" ");
        eatenToday.setText(caloriesX+"");


        //if (requestCode == NUMBER_CODE & resultCode == RESULT_OK){
        //Toast.makeText(this, data.getStringExtra(INTENT_CODE), Toast.LENGTH_SHORT).show();
        //}
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG,counter);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eatenToday = (TextView)findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        reset = (Button)findViewById(R.id.button3);
        enterData = (Button)findViewById(R.id.button4);
        title = (TextView)findViewById(R.id.textView3);
        Log.d("inoncreate", caloriesX+"");
//        eatenToday.setText("Your Recommended Daily Calorie Intake "+ caloriesX);
        title.setVisibility(View.VISIBLE);

        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToOpen = new Intent(MainActivity.this, Second.class);
                //intentToOpen.putExtra("TEST", "This is a test");
                startActivityForResult(intentToOpen, NUMBER_CODE);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToOpen2 = new Intent(MainActivity.this, text.class);
                startActivityForResult(intentToOpen2,NUMBER_CODE);
            }
        });
         reset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 caloriesX = 0;
                 eatenToday.setText(caloriesX+"");
             }
         });

    }




/*    @Override
    protected void onStart() {
        super.onStart();

        String[] g = read().split("/");
        for(int x = 0; x<4; x++)
        {
            arr[x]=g[x];
        }
        caloriesX = Integer.parseInt(g[4]);
        eatenToday.setText("0");
        Log.d("SAVED MATERIALSSSS", arr[0]);


    }

    @Override
    protected void onPause() {
        super.onPause();
        save(translate(arr, caloriesX));
        Log.d("Pause", "pause");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save(translate(arr, caloriesX));
        Log.d("Destroy", "destroy");

*/
    //}

    public void save(String a) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("everything", Context.MODE_PRIVATE));
            writer.write(a);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String translate(String[] arr, int caloriesX)
    {
        String b = "";
        for (int x = 0; x<4; x++)
        {
            b += arr[x] + "/";
        }
        b+= Integer.toString(caloriesX);
        return b;
    }

    public String read() {
        String f = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("everything")));
            f = reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
}

/*
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}*/