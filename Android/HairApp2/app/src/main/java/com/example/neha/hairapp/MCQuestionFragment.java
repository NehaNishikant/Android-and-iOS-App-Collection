package com.example.neha.hairapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Neha on 5/6/2017.
 */

public class MCQuestionFragment extends Fragment {

    Button choiceA;
    Button choiceB;
    Button choiceC;
    Button choiceD;
    Button submit;
    TextView question;
    View view;
    Bundle sis;
    String tempQ=null;
    String tempA=null;
    String tempB=null;
    String tempC=null;
    String tempD=null;

    String answer=" ";

    public MCQuestionFragment(){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        sis=savedInstanceState;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //saveInformation();

        view= inflater.inflate(R.layout.fragment_mcquestion, container, false);
        question=(TextView) view.findViewById(R.id.mcQuestion_id);

        choiceA =(Button) view.findViewById(R.id.choice1_id);
        choiceB =(Button) view.findViewById(R.id.choice2_id);
        choiceC =(Button) view.findViewById(R.id.choice3_id);
        choiceD =(Button) view.findViewById(R.id.choice4_id);
        submit =(Button) getActivity().findViewById(R.id.submit_id);

        choiceA.setBackgroundColor(Color.LTGRAY);
        choiceB.setBackgroundColor(Color.LTGRAY);
        choiceC.setBackgroundColor(Color.LTGRAY);
        choiceD.setBackgroundColor(Color.LTGRAY);

        choiceB.setText("i'm not null!");

        choiceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveInformation();
                v.setBackgroundColor(Color.BLUE);
                choiceB.setBackgroundColor(Color.LTGRAY);
                choiceC.setBackgroundColor(Color.LTGRAY);
                choiceD.setBackgroundColor(Color.LTGRAY);
                answer="A";
            }
        });
        choiceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveInformation();
                v.setBackgroundColor(Color.BLUE);
                choiceA.setBackgroundColor(Color.LTGRAY);
                choiceC.setBackgroundColor(Color.LTGRAY);
                choiceD.setBackgroundColor(Color.LTGRAY);
                answer="B";
            }
        });
        choiceC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveInformation();
                v.setBackgroundColor(Color.BLUE);
                choiceA.setBackgroundColor(Color.LTGRAY);
                choiceB.setBackgroundColor(Color.LTGRAY);
                choiceD.setBackgroundColor(Color.LTGRAY);
                answer="C";
            }
        });
        choiceD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.BLUE);
                choiceA.setBackgroundColor(Color.LTGRAY);
                choiceB.setBackgroundColor(Color.LTGRAY);
                choiceC.setBackgroundColor(Color.LTGRAY);
                answer="D";
            }
        });

        if (tempQ!=null){
            setChoices(tempQ, tempA, tempB, tempC, tempD);
            tempQ=null;
            tempA=null;
            tempB=null;
            tempC=null;
            tempD=null;
        }

        return view;
    }

    public void setChoices(String Q, String A, String B, String C, String D){
        if (choiceB!=null) {
            //saveInformation();
            choiceA.setBackgroundColor(Color.LTGRAY);
            choiceB.setBackgroundColor(Color.LTGRAY);
            choiceC.setBackgroundColor(Color.LTGRAY);
            choiceD.setBackgroundColor(Color.LTGRAY);
            question.setText(Q);
            choiceA.setText(A);
            choiceB.setText(B);
            choiceC.setText(C);
            choiceD.setText(D);
            if (answer.equals("A")){
                choiceA.setBackgroundColor(Color.BLUE);
            } else if (answer.equals("B")) {
                choiceB.setBackgroundColor(Color.BLUE);
            } else if (answer.equals("C")) {
                choiceC.setBackgroundColor(Color.BLUE);
            } else if (answer.equals("D")) {
                choiceD.setBackgroundColor(Color.BLUE);
            }
        } else {
            tempQ=Q;
            tempA=A;
            tempB=B;
            tempC=C;
            tempD=D;
        }
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer=answer;
    }

    /*public void saveInformation(){
        String filename="datafile.json";
        JSONObject obj=new JSONObject();
        try {
            obj.put("currentTab", HairAppCode.currentTab);
            obj.put("currentQuestion", HairAppCode.currentQuestion);
            if (HairAppCode.currentQuestion==1) {
                obj.put("answer", QuizFragment.mcfrag1.getAnswer());
            } else if ((HairAppCode.currentQuestion<=5)&&(HairAppCode.currentQuestion>0)) {
                obj.put("answer", HairAppCode.mcfrag.getAnswer());
            } else if ((HairAppCode.currentQuestion<=10)&&(HairAppCode.currentQuestion>0)) {
                obj.put("answer", HairAppCode.tffrag.getAnswer());
            } else obj.put("answer", " "); //cQ=0 or 11

            if (HairAppCode.currentQuestion==11) {
                obj.put("quizResult", "haha"); //change to quiz result
            } else obj.put("quizResult", " ");

            JSONObject obj2=new JSONObject();
            for (int i=0; i<10; i++){
                obj2.put(Integer.toString(i), HairAppCode.answers[i]);
            }
            obj.put("answerList", obj2);

            JSONObject obj3=new JSONObject();
            for (int i=0; i<HairAppCode.favstyles.size(); i++){
                obj3.put(Integer.toString(i), HairAppCode.favstyles.get(i));
            }
            obj.put("favorites", obj3);

        } catch (JSONException e){};

        try{
            OutputStreamWriter writer=new OutputStreamWriter(getActivity().openFileOutput(filename, Context.MODE_WORLD_WRITEABLE));
            writer.write(obj.toString());
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/
}
