package good.ss.um;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

  private static final String TAG = "MapsActivity";

  private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
  private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
  private static final float DEFAULT_ZOOM = 15f;

  // vars
  private Boolean mLocationPermissionsGranted = false;
  private GoogleMap mMap;
  private FusedLocationProviderClient mFusedLocationProviderClient;

  private Button btn_startRent;
  private Button btn_startReturn;
  private Button btn_logout;
  private FirebaseAuth mFirebaseAuth;

  @Override
  public void onMapReady(GoogleMap googleMap) {
    Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onMapReady: map is ready");
    mMap = googleMap;

    if (mLocationPermissionsGranted) {
      getDeviceLocation();

      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
              != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
              != PackageManager.PERMISSION_GRANTED) {
        return;
      }
      mMap.setMyLocationEnabled(true);
      mMap.getUiSettings().setMyLocationButtonEnabled(true);
      mMap.getUiSettings().setMapToolbarEnabled(true);
      mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    // 지도에 마커띄우기
    LatLng cbnu_s4_1 = new LatLng(36.625698, 127.454405);
    googleMap.addMarker(
        new MarkerOptions().position(cbnu_s4_1).title("충북대 s4-1 대여소").snippet("우산 2개 보유"));

    LatLng cbnu_library = new LatLng(36.628404, 127.457398);
    googleMap.addMarker(
        new MarkerOptions().position(cbnu_library).title("충북대 중앙도서관 대여소").snippet("우산 0개 보유"));

    LatLng cbnu_coresidence = new LatLng(36.631461, 127.457711);
    googleMap.addMarker(
        new MarkerOptions().position(cbnu_coresidence).title("충북대 본관 기숙사 대여소").snippet("우산 1개 보유"));
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getLocationPermission();
    // bottomnavigationbar
    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
    // set home selected
    bottomNavigationView.setSelectedItemId((R.id.home));

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
            Intent intent = new Intent(MapsActivity.this, RentActivity.class);
            startActivity(intent); // 렌트 엑티비티로 이동
          }
        });

    btn_startReturn = findViewById(R.id.btn_startReturn);
    btn_startReturn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(MapsActivity.this, ReturnActivity.class);
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

            Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
          }
        });
    // 탈퇴 처리
    // mFirebaseAuth.getCurrentUser().delete();
  }

  private void getDeviceLocation() {
    Log.d(TAG, "getDeviceLocation: getting the devices current location");

    mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    try {
      if (mLocationPermissionsGranted) {

        final Task location = mFusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(
            new OnCompleteListener() {
              @Override
              public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                  Log.d(TAG, "onComplete: found location!");
                  Location currentLocation = (Location) task.getResult();

                  // moveCamera(new LatLng(currentLocation.getLatitude(),
                  // currentLocation.getLongitude()),
                  //       DEFAULT_ZOOM);
                  moveCamera(new LatLng(36.627227384740216, 127.45853455040243), DEFAULT_ZOOM);

                } else {
                  Log.d(TAG, "onComplete: current location is null");
                  Toast.makeText(
                          MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT)
                      .show();
                }
              }
            });
      }
    } catch (SecurityException e) {
      Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
    }
  }

  private void moveCamera(LatLng latLng, float zoom) {
    Log.d(
        TAG,
        "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
  }

  private void initMap() {
    Log.d(TAG, "initMap: initializing map");
    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

    mapFragment.getMapAsync(MapsActivity.this);
  }

  private void getLocationPermission() {
    Log.d(TAG, "getLocationPermission: getting location permissions");
    String[] permissions = {
      Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
      if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION)
          == PackageManager.PERMISSION_GRANTED) {
        mLocationPermissionsGranted = true;
        initMap();
      } else {
        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
      }
    } else {
      ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
    }
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    Log.d(TAG, "onRequestPermissionsResult: called.");
    mLocationPermissionsGranted = false;

    switch (requestCode) {
      case LOCATION_PERMISSION_REQUEST_CODE:
        {
          if (grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
              if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = false;
                Log.d(TAG, "onRequestPermissionsResult: permission failed");
                return;
              }
            }
            Log.d(TAG, "onRequestPermissionsResult: permission granted");
            mLocationPermissionsGranted = true;
            // initialize our map
            initMap();
          }
        }
    }
  }
}
