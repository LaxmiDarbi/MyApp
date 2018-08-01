package com.example.dslab.myapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView ryList;
    StudAdapter studAdapter;
    List<Student> studentList = new ArrayList<>();
    StudentDB studentDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ryList = findViewById(R.id.ryList);
        //studentList = getStudentList();

        studentDB=new StudentDB(MainActivity.this);
        new MyAsyncTask().execute("https://api.androidhive.info/contacts/");
    }

    List<Student> getStudentList() {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student(
                    R.drawable.taj, "Student" + i, (i + 1) + "/07/2018");
            list.add(student);
        }
        return list;
    }

    class MyAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }





        @Override
        protected String doInBackground(String... strings) {
            String URL = strings[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL);

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status == 200) {
                    String responseString = EntityUtils.toString(httpResponse.getEntity());
                    System.out.println(responseString);
                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject subJson= jsonArray.getJSONObject(i);
                        String name = subJson.getString("name");
                        String dob = subJson.getString("email");
                        Student student = new Student(R.drawable.taj,name,dob);
                        studentList.add(student);
                        System.out.println(i+"."+"Name:"+name);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //showList();
            storeList(studentList);
        }


    }
    void showList(){
       // studAdapter = new StudAdapter(studentList);
        studAdapter = new StudAdapter( getListFromDb());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        ryList.setLayoutManager(linearLayoutManager);
        ryList.setAdapter(studAdapter);
        studAdapter.notifyDataSetChanged();
    }

    void storeList(List<Student> list){
        for(int i=0;i<list.size();i++){
            Student student = list.get(i);
           studentDB.openToWrite();
           studentDB.insert(student);
           studentDB.close();
        }
        showList();
    }
    List<Student> getListFromDb(){
        List<Student> list = new ArrayList<>();
        studentDB.openToRead();
        list=studentDB.getStudentList();
        studentDB.close();
        return list;
    }
}