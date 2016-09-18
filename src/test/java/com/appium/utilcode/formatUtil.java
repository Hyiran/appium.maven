package com.appium.utilcode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志记录：
 * 版本   日期     修改者   更新内容
 * 1.0  2016-3-9  程文健   创建项目
 * 
 */

/**
 * 用于数据的格式化操作
 * 
 * @author 程文健
 * @version 1.0 2016-3-9
 */

public class formatUtil {
  /**
   * 获取当前的系统时间并格式化
   * 
   * @param dateFormat
   *          输入时间的格式
   * @return String 格式化后的字符串
   * @see <a href="https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html">Java
   *      Date Format Pattern Syntax</a><br>
   * @see SimpleDateFormat#SimpleDateFormat(String pattern)
   */
  public static String getSystemTime(String dateFormat) {
    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    Date curDate = new Date(System.currentTimeMillis());
    return formatter.format(curDate);
  }
}
