package com.uitest.log;

import org.junit.Assert;
import org.testng.Reporter;

import com.uitest.core.AppUtil;

public class LogManager {

	/**
	 * 报告输入用到
	 * 
	 * @param name
	 * @param log
	 */
	public static void reportLog(String name, String log) {
		Reporter.log(name + " 测试通过；用例（功能点）描述: " + log);
	}

	/**
	 * 默认添加用例名，自定义输出日志
	 * 
	 * @param log
	 */
	public static void reportLog(String log) {
		String mothodName = getMethodName();
		System.out.println("MyTestCaseResult: port is " + myport + ", "
				+ mothodName + ", OK");
		Reporter.log(mothodName + " 测试通过；功能点: " + log);
	}

	/**
	 * 用例运行，用于检测用例执行进度
	 */
	public static void startTestCase() {
		//获取当前用例名称
		String mothodName = getMethodName();
		System.out.println("MyTestCaseResult: port is " + myport + ", "
				+ mothodName + ", start");
	
		//用于测试，每条用例开始前都截图
		snapshot(driver, "Test_"+getMethodName(), "startTest");
		
		String curactivity = driver.currentActivity();
		
		//当前运行程序不是和通讯录
		if(!curactivity.equals(appActivity)){
			
			snapshot(driver, getMethodName(), "上一用例出错，当前不在和通讯录");
			
			if(isExistenceByName("确定"))
			{
				clickByName("确定");
			}
			sleepTime(2000);
			
			driver.startActivity(packageName, appActivity);
			
			sleepTime(2000);
		}
		
		
		back("tab_contacts");
		
		sleepTime(2000);
		//返回不了主页面，重启应用
		if(!(isExistenceById("iab_title") && isExistenceByName("和通讯录"))){
			snapshot(driver, getMethodName(), "当前不在和通讯录主页面");
			sleepTime(2000);
			driver.startActivity(packageName, appActivity);
			sleepTime(2000);
		}

	}

	
	
	
	/**
	 * 判断是否通过验证，如果不通过结束并截图
	 * 
	 * @param bl
	 * @param caseName
	 */
	public static void Myassert(boolean bl, String caseName) {
		if (bl == false) {
			AppUtil.snapshot(driver, caseName);
		}
		sleepTime(3000);
		Assert.assertTrue(bl);
	}

	/**
	 * 判断是否通过验证，如果不通过结束并截图,并添加具体问题描述
	 * 
	 * @param bl
	 * @param caseName
	 */
	public static void Myassert(String message, boolean bl, String caseName) {
		if (bl == false) {
			AppUtil.snapshot(driver, caseName, message);
		}
		sleepTime(3000);
		Assert.assertTrue(message, bl);
	}

//	/**
//	 * 判断是否通过验证，如果不通过结束并截图,并添加具体问题描述
//	 * 
//	 * @param bl
//	 * @param caseName
//	 */
//	public static void Myassert(String message, boolean bl, String caseName) {
//		if (bl == false) {
//			AppUtil.snapshot(driver, caseName);
//		}
//		sleepTime(3000);
//		Assert.assertTrue(message, bl);
//	}
//	
	/**
	 * 判断是否通过验证，如果不通过结束并截图,并添加具体问题描述,自动添加用例名称
	 * 
	 * @param bl
	 * @param caseName
	 */
	public static void Myassert(String message, boolean bl) {
		if (bl == false) {
			AppUtil.snapshot(driver, getMethodName(), message);
		}
		sleepTime(3000);
		Assert.assertTrue(message, bl);
	}

}
