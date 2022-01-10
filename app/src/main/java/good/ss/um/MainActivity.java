package good.ss.um;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private static final int ERROR_DIALOG_REQUEST = 9001;

  private Button btn_startRent;
  private Button btn_startReturn;
  private Button btn_logout;
  private FirebaseAuth mFirebaseAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialize & assign variable
    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
    // set home selected

    bottomNavigationView.setSelectedItemId((R.id.home));

    if (isServicesOK()) {
      startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }

    // perform itemselectedlistner
    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.weather:
                startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
                overridePendingTransition(0, 0);
                return true;

              case R.id.home:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                overridePendingTransition(0, 0);
                return true;

              case R.id.mypage:
                startActivity(new Intent(getApplicationContext(), MypageActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
          }
        });

    btn_startRent = findViewById(R.id.btn_startRent);
    btn_startRent.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RentActivity.class);
            startActivity(intent); // 렌트 엑티비티로 이동
          }
        });

    btn_startReturn = findViewById(R.id.btn_startReturn);
    btn_startReturn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ReturnActivity.class);
            startActivity(intent); // 렌트 엑티비티로 이동
          }
        });

    mFirebaseAuth = FirebaseAuth.getInstance();

    btn_logout = findViewById(R.id.btn_logout);
    btn_logout.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            // 로그아웃 하기
            mFirebaseAuth.signOut();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
          }
        });
    // 탈퇴 처리
    // mFirebaseAuth.getCurrentUser().delete();
  }

  public boolean isServicesOK() {
    Log.d(TAG, "isServicesOK: checking google servises version");
    int available =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

    if (available == ConnectionResult.SUCCESS) {
      // everything is gine and the user can make map requests
      Log.d(TAG, "isServicesOK: Google Play services is working");
      return true;
    } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
      // an error occured but we can resolve it
      Log.d(TAG, "isServicesOK: an error occured but we can fix it");
      Dialog dialog =
          GoogleApiAvailability.getInstance()
              .getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
      dialog.show();
    } else {
      Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
    }
    return false;
  }
}
