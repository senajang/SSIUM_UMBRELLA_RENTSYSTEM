package good.ss.um;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MypageActivity extends AppCompatActivity {

    private TextView textviewUsingTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        textviewUsingTime = (TextView)  findViewById(R.id.myrenttimeis);
        RentTimeVar GlobalVar = (RentTimeVar) getApplication();
        long endTime = System.currentTimeMillis();
        long usingTime=(endTime-GlobalVar.getGlobalValue())/1000;
        textviewUsingTime.setText(usingTime/86400+"days "+usingTime%86400/3600+"h "+usingTime%3600/60+"m "+usingTime%3600%60+"s");
        //Initialize & assign variable

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId((R.id.mypage));

        //perform itemselectedlistner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.weather:
                        startActivity(new Intent(getApplicationContext()
                                , WeatherActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.mypage:
                        return true;
                }
                return false;
            }
        });
    }
}