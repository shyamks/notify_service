package com.example.quikr.apicall;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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
    List<String> aggregate;
    //static final String API_KEY = "USE_YOUR_OWN_API_KEY";
    static final String API_URL = "https://api.quikr.com/platform/v2/getAdByIds?id=269347584";

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

    class RetrieveFeedTask extends AsyncTask<Void, Void, List<String>> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");
        }

        protected List<String> doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here
            try {
                aggregate = new ArrayList<>();
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("X-Quikr-App-Id", "752");
                urlConnection.setRequestProperty("X-Quikr-Client", "realestate");
                urlConnection.setRequestProperty("X-Quikr-Token-Id", "73276267");
                urlConnection.setRequestProperty("X-Quikr-Signature-v2", "e4747e89adb065965eeda0921df2bee6c9023a83");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    System.out.println(stringBuilder.toString());
                    aggregate.add(stringBuilder.toString());
                } finally {
                    urlConnection.disconnect();
                }
                return aggregate;
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<String> response) {
            //responseView.setText(response);
            // TODO: check this.exception
            // TODO: do something with the feed

            System.out.println(response);
//                int likelihood = object.getInt("likelihood");
//                JSONArray photos = object.getJSONArray("photos");


        }
    }
}
