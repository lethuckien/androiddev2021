package vn.edu.usth.usthweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                AsyncTask<URL, Integer, Bundle> task = new AsyncTask<URL, Integer, Bundle>() {


                    @Override
                    protected Bundle doInBackground(URL... urls) {
                        SystemClock.sleep(2000);
                        Bundle bundle = new Bundle();
                        bundle.putString("server_response", "some json here");
                        return bundle;
                    }

                    @Override
                    protected void onPostExecute(Bundle bundle) {
                        String context = bundle.toString();
                        Toast.makeText(WeatherActivity.this, context , Toast.LENGTH_SHORT).show();
                    }
                };
                task.execute();
                return true;
            }
            case (R.id.action_settings):{
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