package good.ss.um;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CardinfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardinfo);
        EditText etUsername = findViewById(R.id.et_username);
        EditText etCardNumber = findViewById(R.id.et_cardnumber);
        EditText etPassword = findViewById(R.id.et_password);
        EditText etCvc = findViewById(R.id.et_cvc);
        Button button=findViewById(R.id.bt_submt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"결제가 완료되었습니다. 우산 대여를 시작합니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}