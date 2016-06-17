package com.example.quikr.apicall;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText emailText;
    TextView responseView;
    ProgressBar progressBar;
    //static final String API_KEY = "USE_YOUR_OWN_API_KEY";
    static final String API_URL = "http://192.168.124.53:9000/realestate/v1/getShortlistEntities?userId=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseView = (TextView) findViewById(R.id.responseView);
        //emailText = (EditText) findViewById(R.id.emailText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button queryButton = (Button) findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute();
            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("X-Quikr-App-Id", "752");
                urlConnection.setRequestProperty("X-Quikr-Client", "realestate");
                urlConnection.setRequestProperty("X-Quikr-Token-Id", "72364402");
                urlConnection.setRequestProperty("X-Quikr-Signature-v2", "573b66d3cafed4b0682152f3802b166e070f4398");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            //responseView.setText(response);
            // TODO: check this.exception
            // TODO: do something with the feed

            try {
                String getadurl = "http://192.168.124.53:7000/adById?id=";
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                JSONArray requestIDs = object.getJSONArray("data");
                List<String> aggregate = new ArrayList<String>();
                for (int i = 0; i < requestIDs.length(); i++) {
                    if (requestIDs.getJSONObject(i).getString("entityName") == "PROJECT") {
                        try {
                            URL url = new URL(getadurl + requestIDs.getJSONObject(i).getJSONObject("entityId"));
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestProperty("X-Quikr-App-Id", "752");
                            urlConnection.setRequestProperty("X-Quikr-Client", "realestate");
                            urlConnection.setRequestProperty("X-Quikr-Token-Id", "72364402");
                            urlConnection.setRequestProperty("X-Quikr-Signature-v2", "573b66d3cafed4b0682152f3802b166e070f4398");
                            urlConnection.setRequestProperty("Content-Type", "application/json");

                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line).append("\n");
                            }
                            bufferedReader.close();
                            String f_response = stringBuilder.toString();
                            aggregate.add(i, f_response);
                            urlConnection.disconnect();
                        } catch (Exception e) {
                            Log.e("ERROR", e.getMessage(), e);
                        }
                    }
                }
//                JSONObject f_res = (JSONObject) new JSONTokener(aggregate).nextValue();
                responseView.setText(aggregate.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//                int likelihood = object.getInt("likelihood");
//                JSONArray photos = object.getJSONArray("photos");


        }
    }
}
