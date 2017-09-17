package com.example.neha.weatherdemo;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WeatherDemo extends AppCompatActivity {

    JSONObject inform = new JSONObject();
    JSONObject inform2 = new JSONObject();
    JSONObject inform3 = new JSONObject();
    JSONObject inform4 = new JSONObject();
    JSONObject inform5 = new JSONObject();
    JSONArray inform6 = new JSONArray();
    JSONObject inform7=new JSONObject();
    ArrayList<Day> days = new ArrayList<Day>();

    EditText editText;
    TextView location;
    TextView day;
    TextView weather;
    TextView quote;
    ImageView imageView;

    TextView d1;
    TextView d2;
    TextView d3;
    TextView d4;
    TextView d5;

    Button get;
    TextView textView;
    String place;
    String urll;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_demo);

        for (int x=0;x<6;x++){
            days.add(null);
        }

        editText = (EditText) findViewById(R.id.editText);
        location=(TextView)findViewById(R.id.place);
        day=(TextView)findViewById(R.id.day);
        weather = (TextView) findViewById(R.id.weather);
        quote = (TextView) findViewById(R.id.quote);
        imageView = (ImageView) findViewById(R.id.imageView);

        d1=(TextView)findViewById(R.id.d1);
        d2=(TextView)findViewById(R.id.d2);
        d3=(TextView)findViewById(R.id.d3);
        d4=(TextView)findViewById(R.id.d4);
        d5=(TextView)findViewById(R.id.d5);


        get=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);

        get.setBackgroundColor(Color.MAGENTA);
        textView.setTextColor(Color.WHITE);
        location.setTextColor(Color.WHITE);
        weather.setTextColor(Color.WHITE);
        quote.setTextColor(Color.WHITE);
        day.setTextColor(Color.WHITE);
        d1.setTextColor(Color.WHITE);
        d2.setTextColor(Color.WHITE);
        d3.setTextColor(Color.WHITE);
        d4.setTextColor(Color.WHITE);
        d5.setTextColor(Color.WHITE);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //cplace = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                place=s.toString();

            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (place!=null){
                    final WeatherThread downloadWeather = new WeatherThread();
                    downloadWeather.execute(place);
                }
            }
        });
    }

    public class WeatherThread extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                urll="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22PLACEHERE%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
                String loki=params[0];
                if (loki!=null) {
                    if (loki.matches(".*\\d.*")){ //if number
                        urll = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + loki + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
                    } else {
                        loki= Uri.encode(loki);
                        urll = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + loki + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
                    }
                }
                URL weather = new URL(urll);

                URLConnection UC=null;

                try{
                    UC=weather.openConnection();
                }catch (IOException e){
                    Log.d(" IOException", e.toString());
                };

                try{
                    InputStream IS = UC.getInputStream();
                    BufferedReader br= new BufferedReader(new InputStreamReader(IS));
                    s=br.readLine();
                    if (s.contains("null")){
                        Log.d("backup", s);
                        s="{\"query\":{\"count\":1,\"created\":\"2016-12-14T13:15:56Z\",\"lang\":\"en-US\",\"results\":{\"channel\":{\"units\":{\"distance\":\"mi\",\"pressure\":\"in\",\"speed\":\"mph\",\"temperature\":\"F\"},\"title\":\"Yahoo! Weather - South Brunswick, NJ, US\",\"link\":\"http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-12761289/\",\"description\":\"Yahoo! Weather for South Brunswick, NJ, US\",\"language\":\"en-us\",\"lastBuildDate\":\"Wed, 14 Dec 2016 08:15 AM EST\",\"ttl\":\"60\",\"location\":{\"city\":\"South Brunswick\",\"country\":\"United States\",\"region\":\" NJ\"},\"wind\":{\"chill\":\"25\",\"direction\":\"300\",\"speed\":\"11\"},\"atmosphere\":{\"humidity\":\"83\",\"pressure\":\"1011.0\",\"rising\":\"0\",\"visibility\":\"16.1\"},\"astronomy\":{\"sunrise\":\"7:14 am\",\"sunset\":\"4:33 pm\"},\"image\":{\"title\":\"Yahoo! Weather\",\"width\":\"142\",\"height\":\"18\",\"link\":\"http://weather.yahoo.com\",\"url\":\"http://l.yimg.com/a/i/brand/purplelogo//uh/us/news-wea.gif\"},\"item\":{\"title\":\"Conditions for South Brunswick, NJ, US at 07:00 AM EST\",\"lat\":\"40.389568\",\"long\":\"-74.539688\",\"link\":\"http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-12761289/\",\"pubDate\":\"Wed, 14 Dec 2016 07:00 AM EST\",\"condition\":{\"code\":\"31\",\"date\":\"Wed, 14 Dec 2016 07:00 AM EST\",\"temp\":\"31\",\"text\":\"Clear\"},\"forecast\":[{\"code\":\"30\",\"date\":\"14 Dec 2016\",\"day\":\"Wed\",\"high\":\"40\",\"low\":\"30\",\"text\":\"Partly Cloudy\"},{\"code\":\"30\",\"date\":\"15 Dec 2016\",\"day\":\"Thu\",\"high\":\"29\",\"low\":\"20\",\"text\":\"Partly Cloudy\"},{\"code\":\"30\",\"date\":\"16 Dec 2016\",\"day\":\"Fri\",\"high\":\"28\",\"low\":\"18\",\"text\":\"Partly Cloudy\"},{\"code\":\"5\",\"date\":\"17 Dec 2016\",\"day\":\"Sat\",\"high\":\"47\",\"low\":\"24\",\"text\":\"Rain And Snow\"},{\"code\":\"39\",\"date\":\"18 Dec 2016\",\"day\":\"Sun\",\"high\":\"56\",\"low\":\"32\",\"text\":\"Scattered Showers\"},{\"code\":\"28\",\"date\":\"19 Dec 2016\",\"day\":\"Mon\",\"high\":\"31\",\"low\":\"23\",\"text\":\"Mostly Cloudy\"},{\"code\":\"28\",\"date\":\"20 Dec 2016\",\"day\":\"Tue\",\"high\":\"31\",\"low\":\"20\",\"text\":\"Mostly Cloudy\"},{\"code\":\"28\",\"date\":\"21 Dec 2016\",\"day\":\"Wed\",\"high\":\"40\",\"low\":\"21\",\"text\":\"Mostly Cloudy\"},{\"code\":\"28\",\"date\":\"22 Dec 2016\",\"day\":\"Thu\",\"high\":\"45\",\"low\":\"31\",\"text\":\"Mostly Cloudy\"},{\"code\":\"12\",\"date\":\"23 Dec 2016\",\"day\":\"Fri\",\"high\":\"43\",\"low\":\"35\",\"text\":\"Rain\"}],\"description\":\"<![CDATA[<img src=\\\"http://l.yimg.com/a/i/us/we/52/31.gif\\\"/>\\n<BR />\\n<b>Current Conditions:</b>\\n<BR />Clear\\n<BR />\\n<BR />\\n<b>Forecast:</b>\\n<BR /> Wed - Partly Cloudy. High: 40Low: 30\\n<BR /> Thu - Partly Cloudy. High: 29Low: 20\\n<BR /> Fri - Partly Cloudy. High: 28Low: 18\\n<BR /> Sat - Rain And Snow. High: 47Low: 24\\n<BR /> Sun - Scattered Showers. High: 56Low: 32\\n<BR />\\n<BR />\\n<a href=\\\"http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-12761289/\\\">Full Forecast at Yahoo! Weather</a>\\n<BR />\\n<BR />\\n(provided by <a href=\\\"http://www.weather.com\\\" >The Weather Channel</a>)\\n<BR />\\n]]>\",\"guid\":{\"isPermaLink\":\"false\"}}}}}}";
                        place="08852";
                    } else Log.d("realtime", s);

                    try {
                        inform = new JSONObject(s);
                        inform2=inform.getJSONObject("query"); //results, channel, item, forecast(array) //how does one get an object out of a string that makes no sense tbh
                        inform3=inform2.getJSONObject("results");
                        inform4=inform3.getJSONObject("channel");
                        inform4=inform3.getJSONObject("channel");
                        inform5=inform4.getJSONObject("item");
                        inform6=inform5.getJSONArray("forecast");
                        inform7=inform5.getJSONObject("condition");

                        for (int x=0; x<6;x++){
                            days.set(x, new Day(inform6.getJSONObject(x).getString("text"), inform7.getInt("temp"), inform6.getJSONObject(x).getInt("high"), inform6.getJSONObject(x).getInt("low"), inform6.getJSONObject(x).getString("date")));
                            Log.d("forecasty", days.get(x).toString()+", "+days.get(x).getWeather());
                        }

                        Log.d("weathero", inform.toString());
                        Log.d("weathero", inform2.toString());
                        Log.d("weathero", inform3.toString());
                        Log.d("weathero", inform4.toString());
                        Log.d("weathero", inform5.toString());
                        Log.d("weathero", inform6.toString());



                    } catch (JSONException e){
                        Log.d(" JSONException", e.toString());
                    }

                }catch (IOException e){
                    Log.d(" IOException", e.toString());
                };


            } catch (MalformedURLException e){
                //weather.setText("Malformed URL");
                Log.d("malformed url Exception", e.toString());
            };

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            Boolean n = false;
            for (Day d : days) {
                if (d == null) {
                    n = true;
                }
            }
            if (!n) {
                if (days.get(0).getWeather().contains("Rain")) {
                    imageView.setImageResource(R.drawable.rain);
                    quote.setText("How to get drunk today: \"Eye of rabbit, harp string hum, turn this water into rum!\"- Seamus Finnigan");
                } else if (days.get(0).getWeather().contains("Snow")) {
                    imageView.setImageResource(R.drawable.snow);
                    quote.setText("It's snowier than Hedwig's coat");
                } else if (days.get(0).getWeather().contains("Sunny")) {
                    imageView.setImageResource(R.drawable.sun);
                    quote.setText("Even a deluminator can't take today's light away");
                } else if (days.get(0).getWeather().contains("Cloudy")) {
                    imageView.setImageResource(R.drawable.cloud);
                    quote.setText("Hope you don't see the Grim omen in today's clouds!");
                } else if (days.get(0).getWeather().contains("Wind")) {
                    imageView.setImageResource(R.drawable.wind);
                    quote.setText("(Wind)ardium Leviosa!");
                } else if (days.get(0).getWeather().contains("Clear")) {
                    imageView.setImageResource(R.drawable.clear);
                    quote.setText("There aren't enough clouds in the sky for Ron and Harry to fly the Ford Anglia");
                } else if (days.get(0).getWeather().contains("fog")) {
                    imageView.setImageResource(R.drawable.fog);
                    quote.setText("It looks like a foggy pensive bowl outside today");
                } else if (days.get(0).getWeather().contains("Thunder")) {
                    imageView.setImageResource(R.drawable.thunder);
                    quote.setText("The thunder outside is louder than the hungarian horntail dragon's roar");
                } else if (days.get(0).getWeather().contains("Shower")) {
                    imageView.setImageResource(R.drawable.rain);
                    quote.setText("someone in the clouds must have cast an \"Aguamenti\"");
                } else if (days.get(0).getWeather().contains("Flurr")) {
                    imageView.setImageResource(R.drawable.flurry);
                    quote.setText("Today will remind you of harry's invisible snowball fight at Hogsmeade");
                } else {
                    imageView.setImageResource(R.drawable.question);
                    quote.setText("This obscure weather type is just as mysterious and the Hogwarts World");
                }

                location.setText(place);
                day.setText(days.get(0).toString());
                weather.setText(days.get(0).getWeather()+", current temp: "+days.get(0).getCondition());

                d1.setText(days.get(1).toString());
                d2.setText(days.get(2).toString());
                d3.setText(days.get(3).toString());
                d4.setText(days.get(4).toString());
                d5.setText(days.get(5).toString());
            }
        }

        //@Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);

        }
    }

    public class Day{
        String weather;
        int cond; //current
        int temp;
        int temp2;
        String date;
        public Day(String weather, int cond, int temp, int temp2, String date){
            this.weather=weather;
            this.cond=cond;
            this.temp=temp; //high
            this.temp2=temp2; //low
            this.date=date;
        }
        public String getWeather(){
            return weather;
        }
        public int getCondition(){
            return cond;
        }
        public int getTemp(){
            return temp;
        }
        public String getDate(){
            return date;
        }
        public String toString(){
            return (date+"- high: "+temp+", low: "+temp2);
        }
    }
}