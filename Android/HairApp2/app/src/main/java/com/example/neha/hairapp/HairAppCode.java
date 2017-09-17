package com.example.neha.hairapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class HairAppCode extends AppCompatActivity {

    LinearLayout fragmentContainer;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Button quizTab;
    Button listTab;
    Button favsTab;
    Button submit;
    Button save;
    TextView currentFrag;
    RelativeLayout rel;

    public static ArrayList<HairStyle> styles=new ArrayList<HairStyle>();
    public static ArrayList<HairStyle> favstyles=new ArrayList<HairStyle>();
    public static String[]answers=new String[10];
    public static Fragment[]frags=new Fragment[2];
    public static int currentQuestion;

    public static QuizFragment qfrag;
    public static MCQuestionFragment mcfrag;
    public static TFQuestionFragment tffrag;
    public static QuizEndFragment quizendfrag;
    public static ListFragment listfrag;
    public static FavoritesFragment favfrag;
    public static TutorialFFragment tutffrag;
    public static TutorialLFragment tutlfrag;
    public static String currentTab;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_app_code);

        qfrag= new QuizFragment();
        mcfrag= new MCQuestionFragment();
        tffrag= new TFQuestionFragment();

        styles=new ArrayList<HairStyle>();
        favstyles=new ArrayList<HairStyle>();

        HairStyle frenchbraid=new HairStyle("FRENCH BRAID", R.drawable.frenchbraid, 1); //yeah you need better styles
        HairStyle threebuns=new HairStyle("THREE BUNS", R.drawable.threebuns, 2); //fix all drawables
        HairStyle fishtail=new HairStyle("FISHTAIL BRAID", R.drawable.fishtailbraid, 3);
        HairStyle frenchbun=new HairStyle("FRENCH BUN", R.drawable.frenchbun, 4);
        HairStyle sidebraid=new HairStyle("SIDE BRAID", R.drawable.sidebraid, 5);
        HairStyle messybun=new HairStyle("MESSY BUN", R.drawable.messybun, 6);
        HairStyle waterfallbraid=new HairStyle("WATERFALL BRAID", R.drawable.waterfallbraid, 7);
        HairStyle ballerinabun=new HairStyle("BALLERINA BUN", R.drawable.ballerinabun, 8);
        styles.add(frenchbraid);
        styles.add(threebuns);
        styles.add(fishtail);
        styles.add(frenchbun);
        styles.add(sidebraid);
        styles.add(messybun);
        styles.add(waterfallbraid);
        styles.add(ballerinabun);

        try{
            BufferedReader reader= new BufferedReader (new InputStreamReader(openFileInput("datafile.json")));
            String s=(reader.readLine());
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            JSONObject obj=new JSONObject(s);
            currentQuestion=obj.getInt("currentQuestion");
            currentTab=obj.getString("currentTab");
            if (currentQuestion>0){
                if (currentQuestion<=5){
                    mcfrag.setAnswer(obj.getString("answer"));
                } else if (currentQuestion<=10){
                    tffrag.setAnswer(obj.getString("answer"));
                }
            }
            int sizeFav = obj.getJSONObject("favorites").length();
            Toast.makeText(getApplicationContext(), sizeFav+" ", Toast.LENGTH_SHORT).show();
            for(int i=0; i<sizeFav;i++)
            {
                favstyles.add(getStyle(obj.getJSONObject("favorites").getInt(i+" ")));
                if (!getStyle(obj.getJSONObject("favorites").getInt(i+" ")).isFavorite()){
                    getStyle(obj.getJSONObject("favorites").getInt(i+" ")).changeFavorite();
                }
                Toast.makeText(getApplicationContext(), obj.getJSONObject("favorites").getString(i+" "), Toast.LENGTH_SHORT).show();
            }

            JSONObject obj2=obj.getJSONObject("answerList");
            for (int i=0; i<10; i++){
                answers[i]=obj2.getString(i+" ");
            }
            //int i=0;

            //while (obj.getJSONObject("favorites").getString(i+" ")!=null){
            //    favstyles.add(getStyle(obj.getJSONObject("favorites").getString(i+" ")));
            //    i++;
            //}

            //quiz result

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        };

        quizendfrag= new QuizEndFragment();
        listfrag=new ListFragment();
        favfrag=new FavoritesFragment();
        tutffrag=new TutorialFFragment();
        tutlfrag=new TutorialLFragment();

        fragmentContainer=(LinearLayout)findViewById(R.id.fragment_id);
        quizTab=(Button)findViewById(R.id.quizTab_id);
        listTab=(Button)findViewById(R.id.listTab_id);
        favsTab=(Button)findViewById(R.id.favsTab_id);
        submit=(Button)findViewById(R.id.submit_id);
        currentFrag=(TextView)findViewById(R.id.currentFrag_id);
        save=(Button)findViewById(R.id.saveAll_id);
        rel=(RelativeLayout)findViewById(R.id.relLay_id);
        rel.setBackgroundColor(Color.CYAN);

        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();

        if (currentTab==null) {
            currentQuestion = 0;
            submit.setVisibility(View.GONE);
            /*if (qfrag.isAdded()){
                transaction = fragmentManager.beginTransaction();
                transaction.remove(qfrag);
                transaction.commit();
                fragmentManager.executePendingTransactions();
            }*/
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_id, qfrag);
            transaction.commit();
            fragmentManager.executePendingTransactions();
            quizTab.setTextColor(Color.LTGRAY);
            currentTab = "quiz";
            currentFrag.setText("Current Tab: Quiz");
        } else if (currentTab.equals("quiz")){
            if(currentQuestion==11){
                QuizEndFragment quizendfrag=new QuizEndFragment();
                submit.setVisibility(View.GONE);
                /*if (quizendfrag.isAdded()){
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(quizendfrag);
                    transaction.commit();
                    fragmentManager.executePendingTransactions();
                }*/
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_id, quizendfrag);
                transaction.commit();
                fragmentManager.executePendingTransactions();
                quizTab.setTextColor(Color.LTGRAY);
                currentTab = "quiz";
                currentFrag.setText("Current Tab: Quiz");
            }

            if (currentQuestion==0){
                submit.setVisibility(View.GONE);
                /*if (qfrag.isAdded()){
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(qfrag);
                    transaction.commit();
                    fragmentManager.executePendingTransactions();
                }*/
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_id, qfrag);
                transaction.commit();
                fragmentManager.executePendingTransactions();
                quizTab.setTextColor(Color.LTGRAY);
                currentTab = "quiz";
                currentFrag.setText("Current Tab: Quiz");
            } else if (currentQuestion<=5){
                submit.setVisibility(View.VISIBLE);
                transaction = fragmentManager.beginTransaction();
                /*if (mcfrag.isAdded()){
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(mcfrag);
                    transaction.commit();
                    fragmentManager.executePendingTransactions();
                }*/
                transaction.replace(R.id.fragment_id, mcfrag);
                transaction.addToBackStack(null);
                transaction.commit();
                fragmentManager.executePendingTransactions();
                quizTab.setTextColor(Color.LTGRAY);
                currentTab = "quiz";
                currentFrag.setText("Current Tab: Quiz");
                if (currentQuestion==1){
                    mcfrag.setChoices("1. How long do you want your style to last?", "I don't care", "A few hours", "For school", "Over night");
                } else if (currentQuestion==2){
                    mcfrag.setChoices("2. How much time do you have?", "1 minute", "5 minutes", "10 minutes", "15+ minutes");
                } else if (currentQuestion==3){
                    mcfrag.setChoices("3. Which type of hair do you have?", "Straight", "Wavy", "Curly", "Kinky");
                } else if (currentQuestion==4){
                    mcfrag.setChoices("4. Choose a style: ", "Casual", "Fun", "Formal", "Messy");
                } else {
                    mcfrag.setChoices("5. How long is your hair", "Short", "Medium", "Long", "Rapunzel");
                }
            } else if (currentQuestion<=10){
                submit.setVisibility(View.VISIBLE);
                transaction = fragmentManager.beginTransaction();
                /*if (tffrag.isAdded()){
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(tffrag);
                    transaction.commit();
                    fragmentManager.executePendingTransactions();
                }*/
                transaction.replace(R.id.fragment_id, tffrag);
                transaction.commit();
                fragmentManager.executePendingTransactions();
                quizTab.setTextColor(Color.LTGRAY);
                currentTab = "quiz";
                currentFrag.setText("Current Tab: Quiz");
                if (currentQuestion==6){
                    tffrag.setChoices("6. Asymmetry is awesome");
                } else if (currentQuestion==7){
                    tffrag.setChoices("7. A good hairstyle is never messy");
                } else if (currentQuestion==8){
                    tffrag.setChoices("8. I love braids");
                } else if (currentQuestion==9){
                    tffrag.setChoices("9. Always try to stand out");
                } else {
                    tffrag.setChoices("10. I am comfortable with difficult styles");
                }
            }
        } else if (currentTab.equals("list")){
            submit.setVisibility(View.GONE);
            transaction = fragmentManager.beginTransaction();
            /*if (listfrag.isAdded()){
                transaction = fragmentManager.beginTransaction();
                transaction.remove(listfrag);
                transaction.commit();
                fragmentManager.executePendingTransactions();
            }*/
            transaction.replace(R.id.fragment_id, listfrag);
            transaction.commit();
            fragmentManager.executePendingTransactions();
            listTab.setTextColor(Color.LTGRAY);
            currentTab = "list";
            currentFrag.setText("Current Tab: List");
        } else if (currentTab.equals("favs")){
            submit.setVisibility(View.GONE);
            transaction = fragmentManager.beginTransaction();
            /*if (favfrag.isAdded()){
                transaction = fragmentManager.beginTransaction();
                transaction.remove(favfrag);
                transaction.commit();
                fragmentManager.executePendingTransactions();
            }*/
            transaction.replace(R.id.fragment_id, favfrag);
            transaction.commit();
            fragmentManager.executePendingTransactions();
            favsTab.setTextColor(Color.LTGRAY);
            currentTab = "favs";
            currentFrag.setText("Current Tab: Favorites");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInformation();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("datafile.json")));
                    String s = (reader.readLine());
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                } catch (IOException e){};
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQuestion<=5) {
                    answers[currentQuestion - 1] = mcfrag.getAnswer();
                } else if (currentQuestion<=10) {
                    answers[currentQuestion - 1] = tffrag.getAnswer();
                }

                if (answers[currentQuestion-1].equals(" ")) {
                    Toast.makeText(getApplicationContext(), "Please choose an answer", Toast.LENGTH_SHORT).show();
                } else {
                    currentQuestion++;
                    if (currentQuestion == 6) {
                        /*if (tffrag.isAdded()) {
                            transaction=fragmentManager.beginTransaction();
                            transaction.remove(tffrag);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            fragmentManager.executePendingTransactions();
                        }*/
                        submit.setVisibility(View.VISIBLE);
                        transaction = fragmentManager.beginTransaction();
                        transaction.remove(mcfrag);
                        transaction.replace(R.id.fragment_id, tffrag);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        fragmentManager.executePendingTransactions();
                    } else if (currentQuestion == 11) {
                        /*if (quizendfrag.isAdded()) {
                            transaction = fragmentManager.beginTransaction();
                            transaction.remove(quizendfrag);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            fragmentManager.executePendingTransactions();
                        }*/
                        submit.setVisibility(View.VISIBLE);
                        QuizEndFragment quizendfrag = new QuizEndFragment();
                        transaction = fragmentManager.beginTransaction();
                        transaction.remove(tffrag);
                        transaction.replace(R.id.fragment_id, quizendfrag);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        fragmentManager.executePendingTransactions();
                    }

                    if (currentQuestion<=5){
                        mcfrag.setAnswer(" ");
                        if (currentQuestion==1){
                            mcfrag.setChoices("1. How long do you want your style to last?", "I don't care", "A few hours", "For school", "Over night");
                        } else if (currentQuestion==2){
                            mcfrag.setChoices("2. How much time do you have?", "1 minute", "5 minutes", "10 minutes", "15+ minutes");
                        } else if (currentQuestion==3){
                            mcfrag.setChoices("3. Which type of hair do you have?", "Straight", "Wavy", "Curly", "Kinky");
                        } else if (currentQuestion==4){
                            mcfrag.setChoices("4. Choose a style: ", "Casual", "Fun", "Formal", "Messy");
                        } else {
                            mcfrag.setChoices("5. How long is your hair", "Short", "Medium", "Long", "Rapunzel");
                        }
                    } else if (currentQuestion <= 10) {
                        tffrag.resetAnswer();
                        if (currentQuestion==6){
                            tffrag.setChoices("6. Asymmetry is awesome");
                        } else if (currentQuestion==7){
                            tffrag.setChoices("7. A good hairstyle is never messy");
                        } else if (currentQuestion==8){
                            tffrag.setChoices("8. I love braids");
                        } else if (currentQuestion==9){
                            tffrag.setChoices("9. Always try to stand out");
                        } else {
                            tffrag.setChoices("10. I am comfortable with difficult styles");
                        }
                    }
                }

            }
        });

        quizTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTab="quiz";
                if (currentQuestion==0) {
                    /*if (qfrag.isAdded()) {
                        transaction = fragmentManager.beginTransaction();
                        transaction.remove(qfrag);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        fragmentManager.executePendingTransactions();
                        submit.setVisibility(View.VISIBLE);
                    }*/
                    submit.setVisibility(View.GONE);
                    for(Fragment fragment:getSupportFragmentManager().getFragments()){
                        transaction = fragmentManager.beginTransaction();
                        transaction.remove(fragment).commit();
                    }
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_id, qfrag);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    fragmentManager.executePendingTransactions();
                } else if (currentQuestion<=5) {
                    /*if (mcfrag.isAdded()) {
                        transaction = fragmentManager.beginTransaction();
                        transaction.remove(mcfrag);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        fragmentManager.executePendingTransactions();
                    }*/
                    submit.setVisibility(View.VISIBLE);
                    for(Fragment fragment:getSupportFragmentManager().getFragments()){
                        transaction = fragmentManager.beginTransaction();
                        transaction.remove(fragment).commit();
                    }
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_id, mcfrag);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    fragmentManager.executePendingTransactions();
                    if (currentQuestion == 1){
                        mcfrag.setChoices("1. How long do you want your style to last?", "I don't care", "A few hours", "For school", "Over night");
                    } else if (currentQuestion==2){
                        mcfrag.setChoices("2. How much time do you have?", "1 minute", "5 minutes", "10 minutes", "15+ minutes");
                    } else if (currentQuestion==3){
                        mcfrag.setChoices("3. Which type of hair do you have?", "Straight", "Wavy", "Curly", "Kinky");
                    } else if (currentQuestion==4){
                        mcfrag.setChoices("4. Choose a style: ", "Casual", "Fun", "Formal", "Messy");
                    } else {
                        mcfrag.setChoices("5. How long is your hair", "Short", "Medium", "Long", "Rapunzel");
                    }
                } else if (currentQuestion<=10){
                    submit.setVisibility(View.VISIBLE);
                    /*if (tffrag.isAdded()){
                        transaction=fragmentManager.beginTransaction();
                        transaction.remove(tffrag);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        fragmentManager.executePendingTransactions();
                    }*/
                    for(Fragment fragment:getSupportFragmentManager().getFragments()){
                        transaction = fragmentManager.beginTransaction();
                        transaction.remove(fragment).commit();
                    }
                    transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_id, tffrag);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    fragmentManager.executePendingTransactions();
                    if (currentQuestion==6){
                        tffrag.setChoices("6. Asymmetry is awesome");
                    } else if (currentQuestion==7){
                        tffrag.setChoices("7. A good hairstyle is never messy");
                    } else if (currentQuestion==8){
                        tffrag.setChoices("8. I love braids");
                    } else if (currentQuestion==9){
                        tffrag.setChoices("9. Always try to stand out");
                    } else {
                        tffrag.setChoices("10. I am comfortable with difficult styles");
                    }
                } else {
                    //code to find out quiz results
                    for(Fragment fragment:getSupportFragmentManager().getFragments()){
                        transaction = fragmentManager.beginTransaction();
                        transaction.remove(fragment).commit();
                    }
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_id, new QuizEndFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    fragmentManager.executePendingTransactions();
                    submit.setVisibility(View.GONE);
                }
                currentFrag.setText("Current Tab: Quiz");
                quizTab.setTextColor(Color.LTGRAY);
                listTab.setTextColor(Color.BLACK);
                favsTab.setTextColor(Color.BLACK);
            }
        });
        listTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.GONE);
                currentTab="list";
                for(Fragment fragment:getSupportFragmentManager().getFragments()){
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(fragment).commit();
                }
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_id, new ListFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                fragmentManager.executePendingTransactions();
                currentFrag.setText("Current Tab: Recommendations");
                quizTab.setTextColor(Color.BLACK);
                listTab.setTextColor(Color.LTGRAY);
                favsTab.setTextColor(Color.BLACK);
            }
        });
        favsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.GONE);
                currentTab="favs";
                for(Fragment fragment:getSupportFragmentManager().getFragments()){
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(fragment).commit();
                }
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_id, new FavoritesFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                fragmentManager.executePendingTransactions();
                currentFrag.setText("Current Tab: Favorites");
                quizTab.setTextColor(Color.BLACK);
                listTab.setTextColor(Color.BLACK);
                favsTab.setTextColor(Color.LTGRAY);
            }
        });
    }

    public HairStyle getStyle(int tag){
        for (int i=0; i<styles.size(); i++){
            if (tag==styles.get(i).getTag())
            return styles.get(i);
        }
        return new HairStyle("fake", R.drawable.frenchbraid, 0);
    }

    public void saveInformation(){
        String filename="datafile.json";
        JSONObject obj=new JSONObject();
        try {
            obj.put("currentTab", currentTab);
            obj.put("currentQuestion", currentQuestion);
            if ((currentQuestion<=5)&&(currentQuestion>0)) {
                obj.put("answer", mcfrag.getAnswer());
            } else if ((currentQuestion<=10)&&(currentQuestion>0)) {
                obj.put("answer", tffrag.getAnswer());
            } else obj.put("answer", " "); //cQ=0 or 11

            if (currentQuestion==11) {
                obj.put("quizResult", quizendfrag.getResult()); //change to quiz result
            } else obj.put("quizResult", " ");

            JSONObject obj2=new JSONObject();
            for (int i=0; i<10; i++){
                obj2.put(i+" ", answers[i]);
            }
            obj.put("answerList", obj2);

            JSONObject obj3=new JSONObject();
            for (int i=0; i<favstyles.size(); i++){
                obj3.put(i+" ", favstyles.get(i).getTag());
            }
            obj.put("favorites", obj3);

        } catch (JSONException e){};

        try{
            OutputStreamWriter writer=new OutputStreamWriter(openFileOutput(filename, Context.MODE_WORLD_WRITEABLE));
            writer.write(obj.toString());
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}