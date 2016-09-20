package com.uitest.core;
/**
 * 日志记录：
 * 版本   日期     修改者  更新内容
 * 0.1  2016-3-7  程文健 创建项目，初定用于 NATIVE_APP 转换为 WEBVIEW 以及相关操作
 * 
 */

/**
 * 用于 NATIVE_APP 转换为 WEBVIEW 以及相关操作
 * 
 * @author 程文健
 * @version 0.1 2016-3-7
 */

public class webViewContains {
  /*
   * interface MethodInterface { // 这个方法假如就是你想要传递的方法 public String test(String s); }
   * 
   * // 一个类中的方法的参数就是这个接口: class TestMethod { // java中很麻烦的，不过下面这样是可以达到目的得 public void
   * test(MethodInterface methodInterface) { System.out.println(methodInterface.test("test")); } }
   * 
   * // 因为这个接口是你定义的，所以你知道这个接口里面一定有一个叫test的方法 class Method implements MethodInterface { public String
   * test(String s) { return s; } }
   * 
   * public class Test { public static void main(String[] args) { new TestMethod().test(new
   * Method()); } }
   * 
   * public static void switchtoWeb() { try { Set<String> contextNames =
   * demotestcase.driver.getContextHandles(); for (String contextName : contextNames) { //
   * 用于返回被测app是NATIVE_APP还是WEBVIEW，如果两者都有就是混合型App if (contextName.contains("WEBVIEW") ||
   * contextName.contains("webview")) { demotestcase.driver.context(contextName);
   * 
   * System.out.println("跳转到web页 开始操作web页面"); 
   * 
   * } } } catch (Exception e) { e.printStackTrace(); } }
   */
}
