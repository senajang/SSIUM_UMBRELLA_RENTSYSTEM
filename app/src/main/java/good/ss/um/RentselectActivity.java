package good.ss.um;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RentselectActivity extends AppCompatActivity {
    ImageButton day1rent, day3rent, day5rent, day7rent, day10rent, day14rent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentselect);
        day1rent = findViewById(R.id.day1rent);
        day3rent = findViewById(R.id.day3rent);
        day5rent = findViewById(R.id.day5rent);
        day7rent = findViewById(R.id.day7rent);
        day10rent = findViewById(R.id.day10rent);
        day14rent = findViewById(R.id.day14rent);
        //Initialize & assign variable

        day1rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardinfoActivity.class);
                startActivity(intent);
            }
        });
        day3rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardinfoActivity.class);
                startActivity(intent);
            }
        });
        day5rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardinfoActivity.class);
                startActivity(intent);
            }
        });
        day7rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardinfoActivity.class);
                startActivity(intent);
            }
        });
        day10rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardinfoActivity.class);
                startActivity(intent);
            }
        });

        day14rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardinfoActivity.class);
                startActivity(intent);
            }
        });

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