package vn.edu.usth.usthweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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

    URL url = new URL("https://usth.edu.vn/uploads/logo_moi-eng.png");

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                AsyncTask<URL, Integer, Bitmap> task;
                task = new AsyncTask<URL, Integer, Bitmap>() {
                    Bitmap bitmap = null;
                    @Override
                    protected Bitmap doInBackground(URL... urls) {
                        HttpURLConnection connection = null;
                        try {
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setDoInput(true);
                            connection.connect();
                            int response = 0;
                            response = connection.getResponseCode();
                            Log.i("USTHWeather", "The response is: " + response);
                            InputStream is = connection.getInputStream();
                            bitmap = BitmapFactory.decodeStream(is);
                        } catch (Exception e) {
                            Log.i("Logo loader", String.valueOf(e));
                        }
                        connection.disconnect();
                        return bitmap;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        ImageView logo = (ImageView) findViewById(R.id.logo);
                        logo.setImageBitmap(bitmap);
                    }
                };
                task.execute(url);
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