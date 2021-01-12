package vn.edu.usth.usthweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    public WeatherActivity() throws Exception {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        PagerAdapter adapter = new HomeFragmentPagerAdapter(
                getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(pager);

        MediaPlayer ring = MediaPlayer.create(WeatherActivity.this, R.raw.loud_thoughts);
        ring.start();
        ring.stop();

        Log.i("WeatherActivity", "Create");

    }

    protected void onStart() {
        super.onStart();
        Log.i("WeatherActivity", "Start");
    }

    protected void onResume() {
        super.onResume();
        Log.i("WeatherActivity", "Resume");
    }

    protected void onPause() {
        super.onPause();
        Log.i("WeatherActivity", "Pause");
    }

    protected void onStop() {
        super.onStop();
        Log.i("WeatherActivity", "Stop");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("WeatherActivity", "Destroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    private URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=21&lon=106&exclude=minutely,hourly,alerts&units=metric&appid=94ae595302fb496abdbffc67924dd8dd");
    private String apikey = "94ae595302fb496abdbffc67924dd8dd";
    private String obj = "";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // Date
                            long unixSeconds = jsonObject.getJSONObject("current").getLong("dt");
                            Date date = new java.util.Date(unixSeconds * 1000);
                            SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE dd/MM/yyyy HH:mm", Locale.US);
                            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+7"));
                            String formattedDate = sdf.format(date);
                            TextView today_date = (TextView) findViewById(R.id.today_date);
                            today_date.setText(formattedDate.split(" ",3)[1]);
                            TextView today_weekday = (TextView) findViewById(R.id.today_weekday);
                            today_weekday.setText(formattedDate.split(" ",3)[0]);

                            // Temp
                            String currentTemp = jsonObject.getJSONObject("current").getString("temp");
                            currentTemp = currentTemp + "°C";
                            TextView today_temp = (TextView) findViewById(R.id.today_temp);
                            today_temp.setText(currentTemp);

                            // Description
                            String descrip = jsonObject.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("main");
                            TextView today_descrip = (TextView) findViewById(R.id.today_descrip);
                            today_descrip.setText(descrip);

                            for (int i = 0; i < 5; i++){
                                long _unixSeconds = jsonObject.getJSONArray("daily").getJSONObject(i+1).getLong("dt");
                                Date _date = new java.util.Date(_unixSeconds * 1000);
                                SimpleDateFormat _sdf = new java.text.SimpleDateFormat("EE dd/MM/yyyy HH:mm", Locale.US);
                                _sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+7"));
                                String _formattedDate = _sdf.format(_date);
                                String name = "day"+i;
                                TextView _today_weekday = (TextView) findViewById(getResources().getIdentifier("day"+i,"id", getPackageName()));
                                _today_weekday.setText(_formattedDate.split(" ",3)[0]);

                                String _currentTemp = jsonObject.getJSONArray("daily").getJSONObject(i+1).getJSONObject("temp").getString("day");
                                _currentTemp = _currentTemp + "°C";
                                String _descrip = jsonObject.getJSONArray("daily").getJSONObject(i+1).getJSONArray("weather").getJSONObject(0).getString("main");
                                TextView _today_temp = (TextView) findViewById(getResources().getIdentifier("day"+i+"_descrip","id", getPackageName()));
                                _today_temp.setText(_currentTemp+"\n"+_descrip);
                            }
                        } catch (JSONException e) {
                            Log.i("onResponse", e.toString());
                        }

                    }
                };
                Response.ErrorListener error = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("JSON loader: ", error.toString());
                    }
                };
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(), listener, error);
                queue.add(stringRequest);

                return true;
            }
            case (R.id.action_settings): {
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                super.onOptionsItemSelected(item);
        }
        return false;
    }
}