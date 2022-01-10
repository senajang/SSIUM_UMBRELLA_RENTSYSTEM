package good.ss.um;

// public class user {
//
//    public String userName;
//    public String userLocation;
//    public String userUmbrella;
//
//    public user() {
//        // Default constructor required for calls to DataSnapshot.getValue(User.class)
//    }
//
//    public user(String userName, String userLocation, String userUmbrella) {
//        this.userName = userName;
//        this.userLocation = userLocation;
//        this.userUmbrella = userUmbrella;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getuserLocation() {
//        return userLocation;
//    }
//
//    public void setuserLocation(String userLocation) {
//        this.userLocation = userLocation;
//    }
//    public String getuserUmbrella() {
//        return userUmbrella;
//    }
//
//    public void setuserUmbrella(String userUmbrella) {
//        this.userUmbrella = userUmbrella;
//    }
//
//    @Override
//    public String toString() {
//        return "user{" +
//                "userUmbrella ='" + userUmbrella + '\'' +
//                ", userLocation ='" + userLocation + '\'' +
//                ", userName ='" + userName + '\'' +
//                '}';
//    }
// }

public class user {

  public String userName;
  public String userLocation;
  public String userUmbrella;

  public user() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
  }

  public user(String userLocation, String userUmbrella, String userName) {

    this.userLocation = userLocation;
    this.userUmbrella = userUmbrella;
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserLocation() {
    return userLocation;
  }

  public void setUserLocation(String userLocation) {
    this.userLocation = userLocation;
  }

  public String getUserUmbrella() {
    return userUmbrella;
  }

  public void setUserUmbrella(String userUmbrella) {
    this.userUmbrella = userUmbrella;
  }

  @Override
  public String toString() {
    return "user{"
        + "userLocation ='"
        + userLocation
        + '\''
        + ", userUmbrella ='"
        + userUmbrella
        + '\''
        + ", userName ='"
        + userName
        + '\''
        + '}';
  }
}
