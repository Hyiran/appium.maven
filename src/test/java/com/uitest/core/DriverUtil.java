package com.uitest.core;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;

/**
 * 日志记录：
 * 版本     日期     修改者  更新内容
 * 1.0    2016-3-22 程文健 创建项目
 * 1.0.1  2016-3-23 程文健 修正：注释内容
 * 1.0.2  2016-3-24 程文健 修正：swipeWithPercent 传入参数顺序
 * 
 */

/**
 * 用于直接操作设备，如按坐标点击，滑动等。
 * 
 * @author 陈柏辉
 * @version 1.0 2016-6-16
 */

public class DriverUtil {

  /**
   * 特殊上滑，按照坐标屏幕尺寸的比例滑动
   * 
   * @param driver
   *          AndroidDriver
   * @param beginXpercent
   *          传入滑动起点在从左到右宽度的百分比(1-99之间)
   * @param endXpercent
   *          传入滑动终点在从左到右宽度的百分比(1-99之间)
   * @param beginYpercent
   *          传入滑动起点在从上到下高度的百分比(1-99之间)
   * @param endYpercent
   *          传入滑动终点在从上到下高度的百分比(1-99之间)
   *          
   */
  public static void swipeWithPercent(AndroidDriver driver, int beginXpercent, 
      int beginYpercent, int endXpercent, int endYpercent) {
    Assert.assertFalse("参数传入错误", 
        (beginXpercent <= 0 || beginXpercent >= 100) && (beginYpercent <= 0 || beginYpercent >= 100)
        && (endXpercent <= 0 || endXpercent >= 100) && (endYpercent <= 0 || endYpercent >= 100));
    int x = driver.manage().window().getSize().width;
    int y = driver.manage().window().getSize().height;
    try {
      driver.swipe(x / 100 * beginXpercent, y / 100 * beginYpercent, 
          x / 100 * endXpercent, y / 100 * endYpercent, 300);
      Thread.sleep(1000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 点击坐标
   * 
   * @param driver
   *          AndroidDriver
   * @param x
   *          the X coordinate of the upper-left corner of the screen
   * @param y
   *          the Y coordinate of the upper-left corner of the screen
   */
  public static void clickByCoordinate(AndroidDriver driver, int x, int y) {
    TouchAction ta = new TouchAction(driver);
    try {
      ta.tap(x, y).release().perform();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 点击相对坐标 传入长宽的百分比
   * 
   * @param driver
   *          AndroidDriver
   * @param i
   *          宽度从左到右的百分比
   * @param j
   *          长度从上到下的百分比
   */
  public static void clickScreenWithPercentage(AndroidDriver driver, int i, int j) {
    int width = driver.manage().window().getSize().width;
    int height = driver.manage().window().getSize().height;
    try {
      driver.tap(1, width * i / 100, height * j / 100, 200);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
