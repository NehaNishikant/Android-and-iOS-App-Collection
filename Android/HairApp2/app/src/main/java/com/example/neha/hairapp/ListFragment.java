package com.example.neha.hairapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by Neha on 5/11/2017.
 */

public class ListFragment extends Fragment {

    ListView listView;
    Button open;
    Button favorite;
    TextView name;
    String clicked=null;
    ImageView imageView;

    public ListFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //saveInformation();
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        listView=(ListView)view.findViewById(R.id.list_id);
        CustomAdapter custom=new CustomAdapter(getActivity().getApplicationContext(), R.layout.custom_obj, HairAppCode.styles); //list can't be null or cant call List.size() on it when running getView
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
            final Button favorite=(Button)adapterLayout.findViewById(R.id.favorite_id);

            imageView.setImageResource(styleList.get(position).getImage());
            name.setText(styleList.get(position).getName());
            favorite.setTag(position); //sets position tag onto favorite button so you can access it from the inner class of favorite's onClickListener
            open.setTag(position);

            if (styleList.get((int)favorite.getTag()).getFavorite()){
                favorite.setText("Click to Unavorite");
            } else favorite.setText("Click to favorite");//AAAAAAAAAAAAAAAAAAAAAAA

            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //saveInformation();
                    HairAppCode.tutlfrag=new TutorialLFragment();
                    FragmentManager fragmentManager7= getFragmentManager();
                    FragmentTransaction fragmentTransaction7 = fragmentManager7.beginTransaction();
                    fragmentTransaction7.addToBackStack("xyz");
                    fragmentTransaction7.remove(ListFragment.this);
                    fragmentTransaction7.replace(R.id.fragment_id, HairAppCode.tutlfrag);
                    fragmentTransaction7.commit();
                    fragmentManager7.executePendingTransactions();
                    HairAppCode.tutlfrag.setAll(styleList.get((int)v.getTag()).getName());
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
                        HairAppCode.favstyles.add(styleList.get((int)v.getTag()));
                    } else {
                        ((TextView)v).setText("Click to Favorite");
                        int x=styleList.get((int)v.getTag()).getTag();
                        int r=HairAppCode.favstyles.size();
                        for (int i=0; i<HairAppCode.favstyles.size();i++){
                            if (HairAppCode.favstyles.get(i).getTag()==x){
                                r=i;
                            }
                        }
                        if (r<HairAppCode.favstyles.size()) {
                            HairAppCode.favstyles.remove(r);
                        } else Toast.makeText(getActivity().getApplicationContext(), "yikes", Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                }
            });

            return adapterLayout;
        }
    }

    public String isClicked(){
        return clicked;
    }

   /* public void saveInformation(){
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