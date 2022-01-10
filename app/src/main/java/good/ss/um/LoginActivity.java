package good.ss.um;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// 앱실행시 첫화면 액티비티
public class LoginActivity extends AppCompatActivity {

  private static final int RC_SIGN_IN = 123;
  private static final String TAG = "Oauth2Google";

  SignInButton signBt;
  GoogleSignInClient mGoogleSignInClient;

  private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
  private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
  private EditText mEtEmail, mEtPwd; // 로그인 버튼 입력필드

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    signBt = findViewById(R.id.GoogleButton);
    signBt.setOnClickListener(this::onClick);

    // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정한다.
    // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
    GoogleSignInOptions gso =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail() // email addresses도 요청함
            .build();

    // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬
    mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
    findViewById(R.id.GoogleButton).setOnClickListener(this::onClick);
    // 기존에 로그인 했던 계정을 확인한다.
    GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);

    // 로그인 되있는 경우 (토큰으로 로그인 처리)
    if (gsa != null && gsa.getId() != null) {}

    mFirebaseAuth = FirebaseAuth.getInstance();
    mDatabaseRef = FirebaseDatabase.getInstance().getReference("Andromeda");

    mEtEmail = findViewById(R.id.et_email);
    mEtPwd = findViewById(R.id.et_pwd);

    Button btn_login = findViewById(R.id.btn_login);
    btn_login.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            // 로그인 요청
            String strEmail = mEtEmail.getText().toString(); // 문자열로 변환된 입력값을 string에 할당
            String strPwd = mEtPwd.getText().toString();

            mFirebaseAuth
                .signInWithEmailAndPassword(strEmail, strPwd)
                .addOnCompleteListener(
                    LoginActivity.this,
                    new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          // 로그인 성공
                          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                          startActivity(intent);
                          finish(); // 현재 액티비티 파괴 로그인을 다시 쓸 일이 없을거기 때문
                        } else {
                          Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                      }
                    });
          }
        });
    Button btn_register = findViewById(R.id.btn_register);
    btn_register.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            // 회원가입 화면으로 이동 (현재자신의 activity, 이동할 activity)
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
          }
        });
  }

  private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
    try {
      GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

      if (acct != null) {
        String personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();

        Log.d(TAG, "handleSignInResult:personName " + personName);
        Log.d(TAG, "handleSignInResult:personGivenName " + personGivenName);
        Log.d(TAG, "handleSignInResult:personEmail " + personEmail);
        Log.d(TAG, "handleSignInResult:personId " + personId);
        Log.d(TAG, "handleSignInResult:personFamilyName " + personFamilyName);
        Log.d(TAG, "handleSignInResult:personPhoto " + personPhoto);

        updateUI(acct);
      }
    } catch (ApiException e) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
      updateUI(null);
    }
  }

  private void updateUI(GoogleSignInAccount acct) {
    Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
    intent.putExtra(MypageActivity.GOOGLE_ACCOUNT, acct);
    startActivity(intent);
    ;
  }

  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.GoogleButton:
        signIn();
        break;
    }
  }

  private void signIn() {
    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      handleSignInResult(task);
    }
  }
}
