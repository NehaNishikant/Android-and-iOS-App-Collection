package com.example.neha.hairapp;

/**
 * Created by Neha on 5/15/2017.
 */

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * Created by Neha on 5/15/2017.
 */

public class TutorialLFragment extends Fragment {

    TextView name;
    TextView tutorial;
    ImageView pic;
    Button back;
    String tempname=null;

    public TutorialLFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_tutorial, container, false);
        name=(TextView)view.findViewById(R.id.title_id);
        back=(Button)view.findViewById(R.id.back_id);
        tutorial=(TextView)view.findViewById(R.id.tutorial_id);
        //pic=(ImageView)view.findViewById(R.id.imageView2);*/

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveInformation();
                HairAppCode.listfrag=new ListFragment();
                FragmentManager fragmentManager5 = getFragmentManager();
                FragmentTransaction fragmentTransaction5 = fragmentManager5.beginTransaction();
                fragmentTransaction5.remove(TutorialLFragment.this);
                fragmentTransaction5.replace(R.id.fragment_id, HairAppCode.listfrag);
                fragmentTransaction5.addToBackStack("xyz");
                fragmentTransaction5.commit();
                fragmentManager5.executePendingTransactions();
                //listfrag1.set()...?????????
            }
        });

        if (tempname!=null){
            setAll(tempname);
            tempname=null;
        }

        return view;
    }

    public void setAll(String name){
        //saveInformation();
        if (this.name!=null) {
            this.name.setText(name); //etc;
            if (name.equals("FRENCH BRAID")){
                tutorial.setText("Part your hair in three sections at the top of your head. Begin to do a braid down your head. However, every time you cross a strand, pull in a strand from the die of your head and twist it in the mix");
                tutorial.setTextSize(20);
            } else if (name.equals("THREE BUNS")){
                tutorial.setText("Make three ponytails in the back of your head. Then indivisually wrap each ponytail clockwise around itself. Secure each bun with pins and  hairties.");
                tutorial.setTextSize(20);
            } else if (name.equals("FISHTAIL BRAID")){
                tutorial.setText("Part you hair into two sections. Pull one small strand from the right section over to the left section. Then pull one small strand from the left section over to the right section. Repeat down the length of your  hair");
                tutorial.setTextSize(20);
            } else if (name.equals("FRENCH BUN")){
                tutorial.setText("Start with a french braid at the side of your head. If you do not know what a french braid it, see the french braid tutorial. Run the french braid down to the nape of your neck and gather the rest of you hair in a bun");
                tutorial.setTextSize(20);
            } else if (name.equals("SIDE BRAID")){
                tutorial.setText("Start with a french braid at the side of your head. If you do not know what a french braid it, see the french braid tutorial. Run the french braid down to the nape of your neck and twist the rest of your hair into a normal braid on the same side as your french braid");
                tutorial.setTextSize(20);
            } else if (name.equals("MESSY BUN")){
                tutorial.setText("Tie your hair in a really high ponytail, but leave a small strand out. Twist the ponytail around itself and secure with a hair tie. Use the samll strand to wrp around the base of the hair tie to cover it. Pull out strand of hair to frame your face");
                tutorial.setTextSize(20);
            }  else if (name.equals("WATERFALL BRAID")){
                tutorial.setText("Start with a french braid at the side of your head. If you do not know what a french braid it, see the french braid tutorial. Run the french braid down to the name of your neck but every time you twist each strand, let one strand of hair fall down");
                tutorial.setTextSize(20);
            } else { //ballerina
                tutorial.setText("Tie a high ponytail. Invest in a ballerina bunmaker or cut up a used sock and roll it into a buns shape. Pull the top of your ponytail through this bunmaker/sock. Roll your ponytail down and around the bumaker");
                tutorial.setTextSize(20);
            }
        } else {
            tempname=name;
        }
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
