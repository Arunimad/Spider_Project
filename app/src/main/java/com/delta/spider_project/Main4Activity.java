package com.delta.spider_project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {




    ListView list1;
    ArrayAdapter adapter;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<Arun> pairs = new ArrayList<>();

    AdapterViewCustom adapternew;



    public class Arun {

        public String name;
        public String img;


        public Arun(String name, String img) {
            this.name = name;
            this.img = img;

        }

    }




    public class ViewHolder {

        public TextView txt;
        public ImageView img;

    }



    public class AdapterViewCustom extends BaseAdapter {


        private Activity context_1;
        public ArrayList<Arun> pairs;


        public AdapterViewCustom(Activity context,
                                 ArrayList<Arun> pairs) {
            context_1 = context;
            this.pairs = pairs;

        }

        @Override
        public int getCount() {
            return pairs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(context_1).inflate(
                        R.layout.custom_row, null);
                viewHolder = new ViewHolder();

                viewHolder.img = (ImageView) convertView
                        .findViewById(R.id.log_img);


                viewHolder.txt = (TextView) convertView
                        .findViewById(R.id.tv_view);

                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txt.setText(pairs.get(position).name);
            Picasso.with(getBaseContext()).load(pairs.get(position).img).resize(480,480).into(viewHolder.img);





            // BitmapFactory.Options bmOptions = new BitmapFactory.Options();



            //Bitmap bmp = BitmapFactory.decodeFile(pairs.get(position).img);
            //viewHolder.img.setImageBitmap(Bitmap.createScaledBitmap(bmp, 480, 480, false));



            return convertView;
        }


    }
    public void add(Arun e) {
        pairs.add(e);

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);







        adapternew = new AdapterViewCustom(this,pairs);
        list1 = (ListView) findViewById(R.id.list1);



        doing a = new doing();
        a.execute();





    }


    class doing extends AsyncTask<Void,Void,String>{


        Document document;

        @Override
        protected String doInBackground(Void... params) {


            String url = "http://indiatoday.intoday.in/section/120/1/top-stories.html";
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

            //Elements results = document.select("#tab1 a");

            Elements results = document.select(".box");

            for (Element result : results)
            {
                //items.add(result.text());
                Arun p =new Arun(result.select(".innerbox a").text(),result.select(".posrel a img ").attr("data-original"));
                add(p);


            }
            //list1.setAdapter(adapter);
            list1.setAdapter(adapternew);
        }
    }

}
