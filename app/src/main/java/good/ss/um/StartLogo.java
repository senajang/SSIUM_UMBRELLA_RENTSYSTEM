package good.ss.um;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class StartLogo extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start_logo);

    Handler hand = new Handler();

    hand.postDelayed(
        new Runnable() {
          @Override
          public void run() {
            // TODO Auto-generated method stub
            Intent intent = new Intent(StartLogo.this, LoginActivity.class);
            startActivity(intent);
            finish();
          }
        },
        2000);
  }
}
