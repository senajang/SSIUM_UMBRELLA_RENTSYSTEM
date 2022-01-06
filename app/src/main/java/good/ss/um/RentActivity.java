package good.ss.um;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RentActivity extends AppCompatActivity {
    //view Objects
    private Button btn_qrScan,btn_save, btn_rent;
    private TextView textViewLocation, textViewUmbrella, textViewName;
    int a = 0;
    long startTime;


    //qr code scanner object
    private IntentIntegrator qrScan;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        //Intent intent = getIntent();

        RentTimeVar GlobalVar = (RentTimeVar) getApplication();

        btn_save = findViewById(R.id.btn_rent);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        readUser();

        //저장하기 버튼
        //스캔한 다음에 텍스트뷰에서 데이터 가져와서 저장실행
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getuserLocation = textViewLocation.getText().toString();
                String getUserUmbrella = textViewUmbrella.getText().toString();
                String getUserName = textViewName.getText().toString();

                startTime = System.currentTimeMillis();
                GlobalVar.setGlobalValue(startTime);
                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("위치", getuserLocation);
                result.put("우산", getUserUmbrella);
                result.put("이름",getUserName);

                a = a+1;
                writeNewUser(a,getuserLocation,getUserUmbrella,getUserName);


                Intent intent = new Intent(getApplicationContext(), DepositActivity.class);
                startActivity(intent);


            }
        });

        //View Objects
        btn_qrScan = (Button) findViewById(R.id.btn_qrScan);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewUmbrella = (TextView) findViewById(R.id.textViewUmbrella);
        textViewName = (TextView)  findViewById(R.id.textViewName);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);

        //button onClick
        btn_qrScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //scan option
                qrScan.setPrompt("Scanning...");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(RentActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(RentActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    //data를 json으로 변환
                    //스캔 결과 보여줌
                    JSONObject obj = new JSONObject(result.getContents());
                    textViewLocation.setText(obj.getString("userLocation"));
                    textViewUmbrella.setText(obj.getString("userUmbrella"));
                    textViewName.setText(obj.getString("userName"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                    textViewName.setText(result.getContents());
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void writeNewUser(int userId, String userName, String userUmbrella, String userLocation) {
        user user = new user(userName, userUmbrella, userLocation);

        mDatabase.child("users").child(String.valueOf(userId)).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        //Toast.makeText(RentActivity.this, "우산대여가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(RentActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void readUser(){
        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(user.class) != null){
                    user post = dataSnapshot.getValue(user.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    //Toast.makeText(RentActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }








}