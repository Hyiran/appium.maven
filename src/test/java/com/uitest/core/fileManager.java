package com.uitest.core;

import java.io.File;
import java.io.IOException;

/**
 * 日志记录：
 * 版本   日期       修改者    更新内容
 * 1.0  2016-3-5    程文健   创建项目
 * 1.1  2016-3-7    程文健   更新：添加判断文件所在目录是否存在，若不存在则自动创建
 * 1.2  2016-4-1    程文健   更新：文件写入方法 writeContentToFile 移到 ioManager 类中
 * 
 */

/**
 * 用于文件管理，向文档进行增删改等操作
 * 
 * @author 程文健
 * @version 1.2 2016-4-1
 */

public class fileManager {
  // 判断文件是否存在
  protected static boolean isFileExists(File file) {
    if (file.getPath().endsWith(File.separator)) {
      System.out.println("创建单个文件" + file.getName() + "失败，目标文件不能为目录！");
      return false;
    }
    if (createDir(file.getParentFile().getPath())) { // 判断目录是否存在，不存在则自动创建
      if (!file.exists()) { // 如果文件不存在则先创建该文件
        try {
          file.createNewFile();
          System.out.println("创建文件" + file.getPath() + "成功！");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          System.out.println("创建文件" + file.getName() + "失败！失败信息：" + e.getMessage());
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  // 创建目录
  protected static boolean createDir(String destDirName) {
    File dir = new File(destDirName);
    if (!dir.exists()) {
      try {
        dir.mkdirs();
        System.out.println("创建目录" + dir.getPath() + "成功！");
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("创建目录" + dir.getPath() + "失败！失败信息：" + e.getMessage());
        return false;
      }
    }
    return true;
  }
}
