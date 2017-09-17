package com.example.neha.hairapp;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.neha.hairapp.HairAppCode.mcfrag;
import static com.example.neha.hairapp.HairAppCode.qfrag;
import static com.example.neha.hairapp.HairAppCode.tffrag;

/**
 * Created by 10010422 on 5/12/2017.
 */

public class QuizEndFragment extends Fragment {

    ImageView styleResult;
    Button retake;
    TextView styleName;
    Button submit;
    String result;

    public QuizEndFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //saveInformation();
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view= lf.inflate(R.layout.fragment_quizend, container, false);
        submit =(Button) getActivity().findViewById(R.id.submit_id);
       // styleResult=(ImageView)view.findViewById(R.id.styleResult_id);
        retake=(Button)view.findViewById(R.id.retakeQuiz_id);
        styleName=(TextView)view.findViewById(R.id.styleName_id);

        if (HairAppCode.answers[9]=="B"){
            if (HairAppCode.answers[3]=="A"){
                result=("SIDE BRAID");
            } else if (HairAppCode.answers[3]=="B"){
                result=("FRENCH BUN");
            } else if (HairAppCode.answers[3]=="C"){
                result=("FRENCH BRAID");
            } else{
                result=("MESSY BUN");
            }
        } else {
            if (HairAppCode.answers[3]=="A"){
                result=("WATERFALL BRAID");
            } else if (HairAppCode.answers[3]=="B"){
                result=("BALLERINA BUN");
            } else if (HairAppCode.answers[3]=="C"){
                result=("FISHTAIL BRAID");
            } else{
                result=("THREE BUNS");
            }
        }

        styleName.setText("Your perfect hairstyle: "+result);
        styleName.setTextSize(20);

        submit.setVisibility(View.GONE);

        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //saveInformation();
            HairAppCode.currentQuestion=0;
            for (int i=0; i<10; i++){
                HairAppCode.answers[i]=" ";
            }
               /* QuizFragment.mcfrag1.setAnswer(" ");
                HairAppCode.mcfrag.setAnswer(" ");
                HairAppCode.tffrag.resetAnswer();*/
            FragmentManager fragmentManager3 = getFragmentManager();
            FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
            fragmentTransaction3.addToBackStack("xyz");
            fragmentTransaction3.hide(QuizEndFragment.this);
                HairAppCode.qfrag=new QuizFragment();
            fragmentTransaction3.add(R.id.fragment_id, HairAppCode.qfrag);
            fragmentTransaction3.commit();

            }
        });

        return view;
    }

    public String getResult(){
        return result;
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
                obj2.put(i+" ", HairAppCode.answers[i]);
            }
            obj.put("answerList", obj2);

            JSONObject obj3=new JSONObject();
            for (int i=0; i<HairAppCode.favstyles.size(); i++){
                obj3.put(i+" ", HairAppCode.favstyles.get(i));
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