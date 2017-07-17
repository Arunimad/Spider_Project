package com.delta.spider_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {


    TextView text1;
    ListView list1;
    ArrayAdapter adapter;
    ArrayList<String> items = new ArrayList<>();
    String s;


    TextView text2;
    ListView list2;
    ArrayAdapter adapter2;
    ArrayList<String> items2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent i =getIntent();
        s = i.getExtras().getString("url");


        text1 = (TextView) findViewById(R.id.text1);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        list1 = (ListView) findViewById(R.id.list1);


        text2 = (TextView) findViewById(R.id.text3);
        adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items2);
        list2 = (ListView) findViewById(R.id.list2);


        doing a = new doing();
        a.execute();


    }


    class doing extends AsyncTask<Void, Void, String> {


        Document document;



        @Override
        protected String doInBackground(Void... params) {
            String url = "https://stackoverflow.com"+s;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            String question = document.select("#question .post-text").text();
            //System.out.println("Question: " + question);
            text1.setText(question);


            Elements answerers = document.select("#answers .user-details a");
            for (Element answerer : answerers) {
                //System.out.println("Answerer: " + answerer.text());
                items.add(answerer.text());


            }
            list1.setAdapter(adapter);

            Elements answers = document.select("#answers .answer .post-text ");
            for(Element answer : answers){

                Log.wtf("am i in","done");

                items2.add(answer.text());

            }
            list2.setAdapter(adapter2);


        }
    }

}
