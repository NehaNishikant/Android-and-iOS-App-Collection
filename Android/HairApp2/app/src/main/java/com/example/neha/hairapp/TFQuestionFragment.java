package com.example.neha.hairapp;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Neha on 5/11/2017.
 */

public class TFQuestionFragment extends Fragment {

    String answer;
    TextView tfquestion;
    RadioButton radioTrue;
    RadioButton radioFalse;
    RadioGroup radioGroup;
    Button submit;
    String tempQ=null;

    public TFQuestionFragment(){
        answer=" ";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tfquestion, container, false);
        tfquestion =(TextView) view.findViewById(R.id.tfquestion);
        radioGroup=(RadioGroup) view.findViewById(R.id.radioGroup);
        radioTrue=(RadioButton)view.findViewById(R.id.radioButtonTrue);
        radioFalse=(RadioButton)view.findViewById(R.id.radioButtonFalse);
        submit =(Button) getActivity().findViewById(R.id.submit_id);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId==R.id.radioButtonTrue){
                    answer="A";
                } else if (checkedId==R.id.radioButtonFalse){
                    answer="B";
                }

            }
        });

        if (tempQ!=null){
            setChoices(tempQ);
            tempQ=null;
        }

        return view;

    }

    public void setChoices(String Q){
        if (radioGroup!=null) {
            tfquestion.setText(Q);
            if (answer.equals("A")) { //goes here
                radioTrue.setChecked(true);
            } else if (answer.equals("B")) {
                radioFalse.setChecked(true);
            } else radioGroup.clearCheck();
        } else {
            tempQ=Q;
        }
    }

    public String getAnswer(){
        return answer;
    }

    public void resetAnswer(){
        radioGroup.clearCheck();
        answer=" ";
    }

    public void setAnswer(String answer){
        this.answer=answer;
    }
}
