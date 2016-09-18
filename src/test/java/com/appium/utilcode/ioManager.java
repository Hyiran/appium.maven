package com.appium.utilcode;

import io.appium.java_client.android.AndroidDriver;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志记录：
 * 版本     日期       修改者   更新内容
 * 0.1    2016-3-10   程文健   创建项目，初定用于输入输出、日志、报告产出等操作
 * 1.0    2016-3-31   程文健   更新：添加函数toUtf8String，用于把字符串转换为UTF-8编码
 * 1.1    2016-4-1    程文健   更新：修复写入到 Excel 表出现乱码的问题，引入POI输出Excel；
 *                                 从 fileManager 移植方法 writeContentToFile
 *                            新增：writeToExcelFile - 写入内容到 Excel 表中
 * 1.2    2016-4-10   程文健   新增：type(driver, by, text) - 用于在 Element 中输入文本内容
 * 1.3    2016-4-11   程文健   修复：修复当 excel 文件不存在时可能会导致新创建的 excel 文件不能写入的问题
 * 1.3.1  2016-4-11   程文健   修复：修复当 excel 文件的父目录不存在时会出现无法创建文件的问题
 * 
 */

/**
 * 用于输入输出、日志、报告产出等操作
 * 
 * @author 程文健
 * @version 1.3.1 2016-4-11
 */

public class ioManager {
  /**
   * 向文档中写入内容，如果文档不存在则先创建文档
   * 
   * @param fileUrl
   *          input a url of file (include file name).
   * @param content
   *          content write to file
   * @throws IOException
   *           IOException
   */
  public static void writeContentToFile(String fileUrl, String content) throws IOException {
    File file = new File(fileUrl);
    try {
      if (content != null && !content.equals("")) {
        if (fileUrl.endsWith(".xls") || fileUrl.endsWith(".xlsx") || fileUrl.endsWith(".csv")) {
          writeToExcelFile(fileUrl, "Sheet One", content);
        } else {
          if (fileManager.isFileExists(file)) {
            FileOutputStream out = new FileOutputStream(file, true);
            String str = new String(content.getBytes(), "UTF-8");
            out.write(str.getBytes());
            out.flush();
            out.close();
          }
        }
      } else {
        System.out.println("字符串为空！");
      }

    } catch (UnsupportedEncodingException e) {
      System.out.println("字符转换异常：" + e.getMessage());
    }
  }

  /**
   * 在 Element 中输入文本内容
   * 
   * @param driver
   *          WebDriver
   * @param by
   *          The element you want to type into.( Type:By, example:By.id("ElementId") )
   * @param text
   *          content type into field
   * @return boolean
   */
  public static boolean type(WebDriver driver, By by, String text) {
    try {
      WebElement element = driver.findElement(by);
      element.clear();
      element.sendKeys(text);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  /**
   * 在 Element 中输入文本内容
   * 
   * @param driver
   *          WebDriver
   * @param by
   *          The element you want to type into.( Type:By, example:By.id("ElementId") )
   * @param text
   *          content type into field
   * @return boolean
   */
  public static boolean type(AndroidDriver driver, By by, String text) {
    if (elementManager.doesWebElementExist(driver, by)) {
      WebElement element = driver.findElement(by);
      element.clear();
      element.sendKeys(text);
      return true;
    }
    return false;
  }

  /**
   * 输入字符串，系统自动模拟键盘输入
   * 
   * @param driver
   *          AndroidDriver
   * @param string
   *          需要输入的字符串
   */
  public void inputWithKeybroad(AndroidDriver driver, String string) {
    int key = 0;

    for (int i = 0; i < string.length(); i++) {
      driver.sendKeyEvent(key);
    }
  }

  /**
   * log记录
   * 
   * @param screenshotPath
   *          screenshotPath图片保存路径
   * @param error
   *          Exception参数
   * @param assertError
   *          AssertionError参数
   * @param testname
   *          测试用例名
   */
  public void logs(String screenshotPath, Exception error, AssertionError assertError,
      String testname) {
    /*
     * String df = formatUtil.getSystemTime("MM-dd-HH-mm"); System.out.println("当前时间" + df); String
     * filename = finalElement.errorfile + testname + "-" + df + ".txt"; File file = new
     * File(finalElement.errorfile); if (!file.exists()) file.mkdirs(); try { File f = new
     * File(filename); if (!f.exists()) f.createNewFile(); FileWriter fw = new FileWriter(f, true);
     * PrintWriter pw = new PrintWriter(fw); pw.append(testname + " 测试failed\r\n");
     * pw.append("截图保存为:" + screenshotPath + "\r\n"); try { pw.append("eclipse报错为:\n" +
     * error.toString() + "\r\n"); error.printStackTrace(pw); } catch (Exception e) { } try {
     * pw.append("断言报错为:" + assertError.toString() + "\r\n"); assertError.printStackTrace(pw); }
     * catch (Exception e) { } pw.flush(); pw.close(); file = new File(finalElement.errorlog); if
     * (!file.exists()) file.mkdirs(); String cmd =
     * "cmd /c \"adb logcat -d  *:E *:S |grep \"com.yiguo.app\" >" + finalElement.errorlog +
     * testname + "-" + df + ".txt\""; // System.out.println(cmd); Runtime.getRuntime().exec(cmd); }
     * catch (Exception e) { e.printStackTrace(); }
     */
  }

  /**
   * Get XML String of utf-8
   * 
   * @param str
   *          XML String
   * @return XML-Formed string
   */
  public static String toUtf8String(String str) {
    // A StringBuffer Object
    StringBuffer sb = new StringBuffer();
    sb.append(str);
    String xmString = "";
    String xmlUtf8 = "";
    try {
      xmString = new String(sb.toString().getBytes("UTF-8"));
      xmlUtf8 = URLEncoder.encode(xmString, "UTF-8");
      System.out.println("utf-8 编码：" + xmlUtf8);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    // return to String Formed
    return xmlUtf8;
  }

  /**
   * 写入到Excel文件
   * 
   * @param fileUrl
   *          输出文件的URL
   * @param sheetName
   *          表格的名字
   * @param content
   *          表格的内容
   * @return boolean
   * @throws IOException
   *           可能抛出文件不存在的错误
   */
  public static boolean writeToExcelFile(String fileUrl, String sheetName, String content)
      throws IOException {
    if (content != null && !"".equals(content)) {
      File file = new File(fileUrl);

      if (!file.exists()) {
        if (!file.getParentFile().exists()) {
          file.getParentFile().mkdirs();
        }
        @SuppressWarnings("resource")
        HSSFWorkbook newWb = new HSSFWorkbook(); // 新建一个工作簿
        FileOutputStream fos = new FileOutputStream(file);
        newWb.write(fos);
        fos.close();
      }

      FileInputStream fis = new FileInputStream(file);

      @SuppressWarnings("resource")
      HSSFWorkbook wb = new HSSFWorkbook(fis); // 新建一个工作簿

      HSSFSheet sheet = wb.getSheet(sheetName); // 获取表格
      // System.out.println("表格：" + sheet);
      if (sheet == null) {
        sheet = wb.createSheet(sheetName); // 新建一个表格
        // System.out.println(sheet.getSheetName() + "创建成功，创建后的对象：" + sheet);
      }

      int numberOfRows = sheet.getLastRowNum() + 1; // 获取已存在数据的行数
      // System.out.println("行数：" + numberOfRows);

      Map<Integer, List<String>> str = stringSplit(content); // 分割字符串

      // Debug
      /*
       * for (int m = 0; m < str.size(); m++) { for (int n = 0; n < str.get(m).size(); n++) {
       * System.out.println(str.get(m).get(n)); } }
       */

      // 将查询出的数据设置到sheet对应的单元格中
      for (int i = 0; i < str.size(); i++) {

        List<String> obj = str.get(i);// 遍历每个对象

        // Debug
        /*
         * for (int m = 0; m < obj.size(); m++) { System.out.print(obj.get(m)); }
         * System.out.println();
         */

        HSSFRow row = sheet.createRow(i + numberOfRows);// 创建所需的行数
        // System.out.println("第" + row.getRowNum() + 1 + "行");

        for (int j = 0; j < obj.size(); j++) {
          HSSFCell cell = null; // 设置单元格的数据类型
          cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
          if (!"".equals(obj.get(j)) && obj.get(j) != null) {
            cell.setCellValue(obj.get(j)); // 设置单元格的值
            // System.out.println("写入单元格内容：" + cell.getStringCellValue());
          }
        }
      }

      try {
        FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);
        fos.close();
      } catch (IOException e) {
        System.out.println("文件“" + file.getPath() + "”创建失败！");
        e.printStackTrace();
      }
    }
    return true;
  }

  /**
   * 分割字符串
   * 
   * @param str
   *          input content
   * @return String[][]
   */
  protected static Map<Integer, List<String>> stringSplit(String str) {
    if (str != null && !"".equals(str)) {
      // 声明一个动态二维数组
      Map<Integer, List<String>> resultSet = new HashMap<Integer, List<String>>();

      // 把str分割成多行
      String[] arrayWithRow = str.split("\n");

      // 为每行创建一个list
      for (int x = 0; x < arrayWithRow.length; x++) {
        resultSet.put(x, new ArrayList<String>());
      }

      for (int i = 0; i < arrayWithRow.length; i++) {
        if (arrayWithRow[i] != null && !"".equals(arrayWithRow[i])) {
          // 把各行分别分割成多个单元格
          String[] tmp = arrayWithRow[i].split("\t");

          for (int j = 0; j < tmp.length; j++) {
            resultSet.get(i).add(tmp[j]);
          }
        }
      }

      return resultSet;
    } else {
      System.out.println("warning: 字符串为空！");
      return null;
    }
  }

}
