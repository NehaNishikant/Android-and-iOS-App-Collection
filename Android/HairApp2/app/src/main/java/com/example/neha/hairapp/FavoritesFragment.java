package com.example.neha.hairapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static com.example.neha.hairapp.HairAppCode.styles;
import static com.example.neha.hairapp.HairAppCode.tutffrag;
import static com.example.neha.hairapp.R.id.favList_id;
import static com.example.neha.hairapp.R.id.open_id;

/**
 * Created by Neha on 5/11/2017.
 */

public class FavoritesFragment extends Fragment {

    ListView listView;
    Button open;
    Button favorite;
    TextView name;
    String clicked=null;
    ImageView imageView;

    public FavoritesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //saveInformation();
        View view= inflater.inflate(R.layout.fragment_fav, container, false);
        listView=(ListView)view.findViewById(R.id.favList_id);
        CustomAdapter custom=new CustomAdapter(getActivity().getApplicationContext(), R.layout.custom_obj, HairAppCode.favstyles); //list can't be null or cant call List.size() on it when running getView
        listView.setAdapter(custom);
        name=(TextView)view.findViewById(R.id.main_id);
        open=(Button)view.findViewById(R.id.open_id);
        favorite=(Button)view.findViewById(R.id.favorite_id);

        return view;
    }

    public class CustomAdapter extends ArrayAdapter<HairStyle> { //inner class

        Context context;
        int layoutid;
        List<HairStyle> styleList;

        public CustomAdapter(Context context, int resource, List<HairStyle> objects){
            super(context, resource, objects);
            this.context=context;
            layoutid=resource;
            styleList=objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE); //need an inflater to render the xml
            View adapterLayout=layoutInflater.inflate(layoutid, null); //idk what the null is for

            ImageView imageView=(ImageView)adapterLayout.findViewById(R.id.imageView);
            TextView name=(TextView)adapterLayout.findViewById(R.id.main_id);
            name.setTextColor(Color.BLACK);
            Button open=(Button)adapterLayout.findViewById(R.id.open_id);
            Button favorite=(Button)adapterLayout.findViewById(R.id.favorite_id);

            imageView.setImageResource(styleList.get(position).getImage());
            name.setText(styleList.get(position).getName());
            favorite.setTag(position); //sets position tag onto favorite button so you can access it from the inner class of favorite's onClickListener
            open.setTag(position);

            if (styleList.get((int)favorite.getTag()).getFavorite()){ //it is a favorite
                favorite.setText("Click to Unavorite");
            } else {
                favorite.setText("Click to Favorite");
                styleList.remove(position);
                Toast.makeText(getActivity().getApplicationContext(), "y u here doe", Toast.LENGTH_SHORT).show();
            }

            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //saveInformation();
                    HairAppCode.tutffrag=new TutorialFFragment();
                    FragmentManager fragmentManager6= getFragmentManager();
                    FragmentTransaction fragmentTransaction6 = fragmentManager6.beginTransaction();
                    fragmentTransaction6.addToBackStack("xyz");
                    fragmentTransaction6.remove(FavoritesFragment.this);
                    fragmentTransaction6.replace(R.id.fragment_id, HairAppCode.tutffrag);
                    fragmentTransaction6.commit();
                    fragmentManager6.executePendingTransactions();
                    HairAppCode.tutffrag.setAll(styleList.get((int)v.getTag()).getName());
                    notifyDataSetChanged();
                }
            });

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //saveInformation();
                    styleList.get((int)v.getTag()).changeFavorite();
                    if (styleList.get((int)v.getTag()).getFavorite()){ //it is now a favorite
                        ((TextView)v).setText("Click to Unfavorite");
                    } else {
                        ((TextView)v).setText("Click to Favorite");
                        styleList.remove((int)v.getTag());
                    }
                    notifyDataSetChanged();
                }
            });

            return adapterLayout;
        }
    }

    public String isClicked(){
        return clicked;}

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