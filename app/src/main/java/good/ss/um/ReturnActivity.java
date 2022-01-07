package good.ss.um;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ReturnActivity extends AppCompatActivity {

    int a = 0;

    private TextView textViewLocation, textViewUmbrella, textViewName, textViewUsingTime;
    private Button btn_info;
    private Button btn_return;
    private DatabaseReference userUmDb;
    private DatabaseReference returnUmDb;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        RentTimeVar GlobalVar = (RentTimeVar) getApplication();

        btn_info = (Button) findViewById(R.id.btn_info);
        btn_return = (Button) findViewById(R.id.btn_return);

        textViewLocation = (TextView) findViewById(R.id.textViewLocation_return);
        textViewUmbrella = (TextView) findViewById(R.id.textViewUmbrella_return);
        textViewName = (TextView)  findViewById(R.id.textViewName_return);
        textViewUsingTime = (TextView)  findViewById(R.id.textViewName_usingtime);

        //대여 정보 확인 버튼
        btn_info.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                long endTime = System.currentTimeMillis();
                long usingTime=(endTime-GlobalVar.getGlobalValue())/1000;
                textViewUsingTime.setText(usingTime/86400+"일 "+usingTime%86400/3600+"시 "+usingTime%3600/60+"분 "+usingTime%3600%60+"초" );
                userUmDb= FirebaseDatabase.getInstance().getReference().child("users").child("1");
                userUmDb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String location = dataSnapshot.child("userLocation").getValue().toString();
                        String umbrella = dataSnapshot.child("userUmbrella").getValue().toString();
                        String name = dataSnapshot.child("userName").getValue().toString();

                        textViewLocation.setText(location);
                        textViewUmbrella.setText(umbrella);
                        textViewName.setText(name);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //반납하기 버튼
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getuserLocation = textViewLocation.getText().toString();
                String getUserUmbrella = textViewUmbrella.getText().toString();
                String getUserName = textViewName.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("위치", getuserLocation);
                result.put("우산", getUserUmbrella);
                result.put("이름",getUserName);

                a = a+1;
                returnUmbrella(a,getuserLocation,getUserUmbrella,getUserName);
            }
        });

    }

    private void returnUmbrella(int userId, String userName, String userUmbrella, String userLocation) {
        user user = new user(userName, userUmbrella, userLocation);
        returnUmDb= FirebaseDatabase.getInstance().getReference();
        returnUmDb.child("ReturnList").child(String.valueOf(userId)).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(ReturnActivity.this, "반납이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(ReturnActivity.this, "반납에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}