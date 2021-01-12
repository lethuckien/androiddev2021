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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
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
                AsyncTask<Void, Void, Void> task;
                task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                ImageView iv = (ImageView) findViewById(R.id.logo);
                                iv.setImageBitmap(response);
                            }
                        };
                        Response.ErrorListener error = new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("image loader", error.toString());
                            }
                        };
                        ImageRequest imageRequest = new ImageRequest("https://usth.edu.vn/uploads/logo_moi-eng.png", listener, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, error);
                        queue.add(imageRequest);
                        Log.i("Image loader","added");
                        return null;
                    }

                };
                task.execute();
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