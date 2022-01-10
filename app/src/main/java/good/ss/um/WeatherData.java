package good.ss.um;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {
  private String mTemperature, mIcon, mCity, mWeatherType;
  private int mCondition;

  public static WeatherData fromJson(JSONObject jsonObject) {
    try {
      WeatherData weatherD = new WeatherData();
      weatherD.mCity = jsonObject.getString("name");
      weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
      weatherD.mWeatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
      weatherD.mIcon = updateWeatherIcon(weatherD.mCondition);
      double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.75;
      int roundedValue = (int) Math.rint(tempResult);
      weatherD.mTemperature = Integer.toString(roundedValue);
      return weatherD;
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static String updateWeatherIcon(int condition) {
    if (condition >= 200 && condition <= 232) {
      return "thunder";
    } else if (condition >= 300 && condition <= 531) {
      return "rain";
    } else if (condition >= 600 && condition <= 622) {
      return "snow";
    } else if (condition >= 701 && condition <= 781) {
      return "fog";
    } else if (condition == 800) {
      return "sunny";
    } else if (condition >= 801 && condition <= 804) {
      return "cloudy";
    }
    return "dunno";
  }

  public String getmTemperature() {
    return mTemperature + "°C";
  }

  public String getmIcon() {
    return mIcon;
  }

  public String getmCity() {
    return mCity;
  }

  public String getmWeatherType() {
    return mWeatherType;
  }
}
