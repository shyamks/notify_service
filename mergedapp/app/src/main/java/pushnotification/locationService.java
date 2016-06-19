package pushnotification;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by meghashyam on 18/06/16.
 */

public class locationService extends AsyncTask<Void, Void, List<String>> {
    public List<String> aggregate = new ArrayList<>();
    static final String API_URL = "https://api.quikr.com/platform/v2/getAdByIds?id=269347584";

    @Override
    protected void onPreExecute(){

    }
    @Override
    protected List<String> doInBackground(Void... urls) {

        try {

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
    protected void onPostExecute(List<String> result) {
    }
}

