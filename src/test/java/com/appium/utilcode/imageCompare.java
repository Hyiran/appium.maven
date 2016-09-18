package com.appium.utilcode;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 日志记录：
 * 版本   日期       修改者    更新内容
 * 1.0  2016-3-3    程文健   创建项目
 * 2.0  2016-3-15   程文健   添加方法 getScreenShot(AndroidDriver driver, String name), 用于截图
 * 
 */

/**
 * 用于图片对比
 * 
 * @author 程文健
 * @version 2.0 2016-3-15 <br>
 */

public class imageCompare {
  /**
   * 截图 文件名: 内容-年月日时分秒
   * 
   * @param driver
   *          AndroidDriver
   * @param filePath
   *          截图保存的路径
   * @param picName
   *          截图的名字前缀
   * @return 返回截图的文件名
   */
  public static String getScreenShot(AndroidDriver driver, String filePath, String picName) {
    String df = formatUtil.getSystemTime("yyyy.MM.dd-HH.mm.ss");
    String picfullName = filePath + picName + " - " + df + ".png";
    File screen = driver.getScreenshotAs(OutputType.FILE);
    System.out.println(picfullName);
    File screenFile = new File(picfullName);
    try {
      FileUtils.copyFile(screen, screenFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return picfullName;
  }

  /**
   * 图像对比
   * 
   * @param myImage
   *          one of the images
   * @param otherImage
   *          The other of images
   * @param percent
   *          The same rate of two images.
   * @return boolean
   */
  public static boolean sameAs(BufferedImage myImage, BufferedImage otherImage, double percent) {
    // BufferedImage otherImage = other.getBufferedImage();
    // BufferedImage myImage = getBufferedImage();
    if (otherImage.getWidth() != myImage.getWidth()) {
      System.out.println("size.width!");
      return false;
    }
    if (otherImage.getHeight() != myImage.getHeight()) {
      System.out.println("size.height!");
      return false;
    }
    // int[] otherPixel = new int[1];
    // int[] myPixel = new int[1];
    int width = myImage.getWidth();
    int height = myImage.getHeight();
    int numDiffPixels = 0;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
          numDiffPixels++;
        }
      }
    }
    double numberPixels = height * width;
    double diffPercent = numDiffPixels / numberPixels;
    return percent <= 1.0D - diffPercent;
  }

  /**
   * 获取部分图像
   * 
   * @param image
   *          The image
   * @param x
   *          the X coordinate of the upper-left corner of the specified rectangular region
   * @param y
   *          the Y coordinate of the upper-left corner of the specified rectangular region
   * @param w
   *          the width of the specified rectangular region
   * @param h
   *          the height of the specified rectangular region
   * @return return a <code>BufferedImage</code>.
   * @see BufferedImage#getSubimage(int x, int y, int w, int h)
   */
  public static BufferedImage getSubImage(BufferedImage image, int x, int y, int w, int h) {
    return image.getSubimage(x, y, w, h);
  }

  /**
   * 从文件中读出图像
   * 
   * @param file
   *          需要读取的图像文件
   * @return return a <code>BufferedImage</code>.
   */
  public static BufferedImage getImageFromFile(File file) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(file);
    } catch (IOException e) {
      // if failed, then copy it to local path for later check:TBD
      // FileUtils.copyFile(f, new File(p1));
      e.printStackTrace();
      System.exit(1);
    }
    return img;
  }

  /**
   * 从文件路径中读出图像
   * 
   * @param filePath
   *          文件路径
   * @return return a <code>BufferedImage</code>.
   */
  public static BufferedImage getImageFromPath(String filePath) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(filePath));
    } catch (IOException e) {
      // if failed, then copy it to local path for later check:TBD
      // FileUtils.copyFile(f, new File(p1));
      e.printStackTrace();
      System.exit(1);
    }
    return img;
  }
}
