/**

 * 本类说明：
 * 此文件为包注解<br>
 * 对包的注解（版本号、用法等）都可以写在这里<br>
 * 包内常用常量/函数可以放在本文件内，以供包内函数访问<br>
 * 
 * <b>package-info不是平常类，其作用有三个:</b><br>
 * 1、为标注在包上Annotation提供便利；<br>
 * 2、声明包的私有类和常量；<br>
 * 3、提供包的整体注释说明。<br>
 * 
 * 日志记录：
 * 版本     日期     修改者    更新内容
 * 1.0    2016-3-8  程文健   创建项目
 * 2.0    2016-3-9  程文健   增加：fileManager 类库
 * 3.0    2016-3-10 程文健   增加：webViewContains, formatUtil 类库
 * 4.0    2016-3-15 程文健   增加：networkManager 类库；更新：elementManager,  imageCompare
 * 4.1    2016-3-17 程文健   更新：elementManager
 * 4.1.1  2016-3-18 程文健   修正：elementManager
 * 5.0    2016-3-22 程文健   增加：driverUtil 类库,更新：elementManager
 * 5.0.1  2016-3-24 程文健   修正：driverUtil - 注释内容、传入参数
 * 5.1    2016-4-6  程文健   修正：输出Excel乱码问题，引入POI输出Excel
 * 5.1.1  2016-4-11 程文健   修正：修复当 excel 文件不存在时可能会导致新创建的 excel 文件不能写入的问题
 * 5.2    2016-4-21 程文健   新增：elementManager - findText()， 用于查找页面是否存在文本，返回该元素
 * 
 * @author Vinken
 */

/**
 * 本包说明：对Appium API进行扩充，封装较常使用或者较为复杂的函数，供外部使用 <br>
 * 
 * @see <a href="http://book.51cto.com/art/201202/317538.htm"> 本文档说明 </a> <br>
 * 
 * @version 5.2 - 20160421
 * @author 程文健
 */

package com.uitest.core;

//这里是包类，声明一个包使用的公共类  
class PkgClass {
  // public void test() {}
}

// 包常量，只允许包内访问
class PkgConst {
  static String ROOT_PATH = System.getProperty("user.dir");
}