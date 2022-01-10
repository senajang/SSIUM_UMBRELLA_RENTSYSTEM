package good.ss.um;

import android.app.Application;

public class RentTimeVar extends Application {
  private long gvalue;

  // 타 class에서 RentTimeVar class를 통해 해당 variable 값을 참조
  public long getGlobalValue() {
    return gvalue;
  }

  // 타 class에서 변경한 valuable을 RentTimeVar 에 저장
  public void setGlobalValue(long mValue) {
    this.gvalue = mValue;
  }
}
