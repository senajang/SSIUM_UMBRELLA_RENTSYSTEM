package good.ss.um;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MypageActivity extends AppCompatActivity {

    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileEmail, textviewUsingTime;
    private GoogleSignInAccount mGoogleSignInAccount;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            profileEmail = findViewById(R.id.myemail);
            mGoogleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
            profileEmail.setText(mGoogleSignInAccount.getEmail());
        } catch (NullPointerException e) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String Email;
                Email = user.getEmail();
                profileEmail.setText(user.getEmail());
            } else {
                //No user is signed in
            }
        }

        textviewUsingTime = (TextView) findViewById(R.id.myrenttimeis);
        RentTimeVar GlobalVar = (RentTimeVar) getApplication();
        long endTime = System.currentTimeMillis();
        long usingTime = (endTime - GlobalVar.getGlobalValue()) / 1000;
        textviewUsingTime.setText(usingTime / 86400 + "days " + usingTime % 86400 / 3600 + "h " + usingTime % 3600 / 60 + "m " + usingTime % 3600 % 60 + "s");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId((R.id.mypage));

        //perform itemselectedlistner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.weather:
                        startActivity(new Intent(getApplicationContext()
                                , WeatherActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.mypage:
                        return true;
                }
                return false;
            }
        });
    }


}