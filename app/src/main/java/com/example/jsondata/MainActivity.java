package com.example.jsondata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView lvPost;
    ArrayList<Object> arrayPost;
    ArrayAdapter<Object> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvPost = (ListView) findViewById(R.id.listviewPost);
        arrayPost = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayPost);
        lvPost.setAdapter(adapter);

        new ReadJSON().execute("https://jsonplaceholder.typicode.com/posts");

    }

    private class ReadJSON extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {

            StringBuilder content = new StringBuilder();

            try {
                URL url = new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";

                while((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array = new JSONArray(s);

                for(int i=0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    int userId = object.getInt("userId");
                    int id = object.getInt("id");
                    String title = object.getString("title");
                    String body = object.getString("body");

                    arrayPost.add("User ID: " + userId + "\n" + "Post ID: " + id + "\n" + "Title: " + title + "\n" + "Content: " + body);
                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}