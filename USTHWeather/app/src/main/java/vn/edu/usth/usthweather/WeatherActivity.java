package vn.edu.usth.usthweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ForecastFragment first_fragment = new ForecastFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, first_fragment).commit();
        Log.i("WeatherActivity", "Create");
    }

    protected void onStart(){
        super.onStart();
        Log.i("WeatherActivity","Start");
    }

    protected void onResume(){
        super.onResume();
        Log.i("WeatherActivity","Resume");
    }

    protected void onPause(){
        super.onPause();
        Log.i("WeatherActivity","Pause");
    }

    protected void onStop(){
        super.onStop();
        Log.i("WeatherActivity","Stop");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i("WeatherActivity","Destroy");
    }
}