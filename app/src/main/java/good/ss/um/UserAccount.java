package good.ss.um;

/**
 * 사용자 계정 정보 모델 클래스
 */


public class UserAccount
{
    private String idToken;   // Firebase Uid (고유 토큰 정보) 유일하게 가질 수 있는 key값
    private String emailId;   // 이메일 아이디
    private String password;  // 비밀번호

    // 추후 사용자 닉네임, 프로필 이미지url 유저 메세지 등을 저장할 수 있고 모델에서 관리하면 된다.

    public UserAccount() { } //파이어베이스에서는 빈생성자를 만들어야 한다 아니면 오류가 난다.

    public String getIdToken() { return idToken; }

    public void setIdToken(String idToken) { this.idToken = idToken; }

    public String getEmailId() { return emailId; }

    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
