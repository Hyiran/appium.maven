package com.appium.utilcode;

import io.appium.java_client.android.AndroidDriver;

public class networkManager {
  /**
   * 检查网络
   * @since 2016.3.3
   * @param driver
   *          AndroidDriver
   * @return boolean
   */
  public static boolean checkNet(AndroidDriver driver) {
    String text = driver.getNetworkConnection().toString();
    if (text.contains("Data: true")) {
      return true;
    } else {
      return false;
    }
  }
}
