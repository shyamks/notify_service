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
    static final String API_URL = "http://192.168.124.53:9000/realestate/v1/getShortlistEntities?userId=20622685";

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
//                URL url = new URL(API_URL);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestProperty("X-Quikr-App-Id", "752");
//                urlConnection.setRequestProperty("X-Quikr-Client", "realestate");
//                urlConnection.setRequestProperty("X-Quikr-Token-Id", "72364402");
//                urlConnection.setRequestProperty("X-Quikr-Signature-v2", "573b66d3cafed4b0682152f3802b166e070f4398");
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line).append("\n");
//                }
//                bufferedReader.close();
//                String s = stringBuilder.toString();
//                urlConnection.disconnect();


//                JSONObject object = (JSONObject) new JSONTokener(s).nextValue();
//                JSONArray requestIDs = object.getJSONArray("data");
            List<String> aggregate = new ArrayList<>();

            try {
                URL url = new URL("https://api.quikr.com/platform/v2/getAdByIds?id=269347584");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("X-Quikr-App-Id", "752");
                urlConnection.setRequestProperty("X-Quikr-Client", "realestate");
                urlConnection.setRequestProperty("X-Quikr-Token-Id", "73030538");
                urlConnection.setRequestProperty("X-Quikr-Signature-v2", "5e1c3e19c04ac9e280a510ad619adcb9beb02c5d");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                System.out.println("siddu  " + stringBuilder.toString());
                urlConnection.disconnect();
                aggregate.add(stringBuilder.toString());

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
            return aggregate;

        }

        protected void onPostExecute(List<String> response) {
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response.get(0));
            //responseView.setText(response);
            // TODO: check this.exception
            // TODO: do something with the feed

            try {
                JSONObject object = (JSONObject) new JSONTokener(response.get(0)).nextValue();
                //JSONArray requestID = object.getJSONArray("data");
                responseView.setText(object.toString());
//                int likelihood = object.getInt("likelihood");
//                JSONArray photos = object.getJSONArray("photos");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
