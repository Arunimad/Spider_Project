package com.delta.spider_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    EditText edit1;
    String s;
    TextView text1;
    ListView list1;
    ArrayAdapter adapter;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> items2 = new ArrayList<>();
    int value;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        edit1= (EditText) findViewById(R.id.edit1);


        text1 = (TextView) findViewById(R.id.text1);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        list1 = (ListView) findViewById(R.id.list1);

        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = edit1.getText().toString();

                doing a = new doing();
                a.execute();
            }
        });




        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                value = position;
                //Toast.makeText(Main3Activity.this,""+value,Toast.LENGTH_LONG).show();
                //text1.setText(position);
                String url =items2.get(value);
                Intent i =new Intent(Main3Activity.this,Main2Activity.class);
                i.putExtra("url",url);
                startActivity(i);

            }
        });
    }



    class doing extends AsyncTask<Void,Void,String>{

        Document document;


        @Override
        protected String doInBackground(Void... params) {
            String url = "https://stackoverflow.com/search?q="+s;
            //String url = "https://stackoverflow.com/search?q=android";
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





            text1.setText(s);




            if(document.body().hasClass("tagged-questions-page new-topbar")){
                Toast.makeText(Main3Activity.this,"Loading-tagged",Toast.LENGTH_LONG).show();



                Elements results = document.select("#questions 1.question-summary .summary .question-hyperlink");

                for (Element result : results) {
                    Log.wtf("wtf", "done");
                    items.add(result.text());




                }
                list1.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                Elements results2 = document.select("#questions .question-summary .summary .question-hyperlink");

                for (Element result : results2) {
                    Log.wtf("wtf", "done");
                    items2.add(result.attr("href"));




                }






            }
            else
            {

                String ss = document.select("#mainbar p").toString();
                Toast.makeText(Main3Activity.this, ss, Toast.LENGTH_LONG).show();







                if (ss.equals("<p>Your search returned no matches.</p>")){

                    Toast.makeText(Main3Activity.this,"0 Results Found",Toast.LENGTH_LONG).show();
                }

                else {

                    Toast.makeText(Main3Activity.this, "Loading-alternate", Toast.LENGTH_LONG).show();

                    Elements results = document.select("#mainbar .summary");

                    for (Element result : results) {
                        Log.wtf("wtf", "done");
                        items.add(result.select(".result-link a").text());


                    }
                    list1.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    Elements results2 = document.select(" .result-link a");
                    for (Element result : results2) {
                        Log.wtf("wtf2", "done");
                        items2.add(result.attr("href"));


                    }


                }








            }










        }
    }









}
