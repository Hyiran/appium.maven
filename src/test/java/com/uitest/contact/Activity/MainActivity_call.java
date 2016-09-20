package com.uitest.contact.Activity;

import io.appium.java_client.TouchAction;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

public class MainActivity_call {
/**
 * 
 *  拨号盘页面：
 *   属性：
 *  	常用固定号码如：10086、10010、10000
 *  	常用暗码：*#06#、*#*#4636#*#*
 *  	组件ID: 页面各个组件ID按功能命名
 *  	页面对象：页面各个UI对象
 *  方法：
 *  	输入号码
 *  	清除号码、删除一个号码
 *  	拨号
 *  	其他设置（添加联系人、暂停、等待、发送短信）
 *  	选择号码（常用、企业通讯录）
 * 		搜索号码（拼音、号码）
 * 		一键拨号
 * 		入口（一键拨号、设置、未接来电、通话设置）
 * 
 */
	
	// ///////////拨号模块/////////////////////////////////

	
	/**
	 * 获取"标"对应的手机号码
	 * 
	 */
	public static String getFirstCallRecorder() {
		System.out.println("getFirstCallRecorder start");
		String UiSelector = ""
				+ "new UiSelector().resourceId(\"com.chinamobile.contacts.im:id/tvStrangeMark\").fromParent("
				+ "new UiSelector().className(\"android.widget.TextView\").index(0))";

		WebElement we = driver.findElementByAndroidUIAutomator(UiSelector);
		String str = we.getAttribute("text").toString();
		System.out.println("getFirstCallRecorder: " + str);
		
		return str;
	}
	
	
	
	/**
	 * 隐藏拨号盘的输入盘
	 */
	public static void hidekeyboardCall() {
		if (isExistenceById("dialpad_layout")) {
			// true为显示键盘，点击键盘隐藏
			clickById("tab_call");
		}
	}

	/**
	 * 显示拨号盘的输入键盘
	 */
	public static void displaykeyboardCall() {
		if (isExistenceById("two")) {

		} else {
			// flase为显示键盘，点击键盘显示
			clickById("tab_call");
		}

	}

	/**
	 * 获取字符串数组 2到9
	 * 
	 * @return
	 */
	public static ArrayList<String> getNumberList() {
		ArrayList<String> strArray = new ArrayList<String>();
		strArray.add("two");
		strArray.add("three");
		strArray.add("four");
		strArray.add("five");
		strArray.add("six");
		strArray.add("seven");
		strArray.add("eight");
		strArray.add("nine");
		return strArray;
	}

	/*
	 * 创建测试数据集(num2 - num9),用于一键
	 */
	public static void createDate(String num, String phone) {

		deleteAllContacts();

		// 创建
		for (int i = 2; i < 10; i++) {
			createContacts("num" + i, phone + i);
		}

	}

	/**
	 * 创建测试数据，num是联系人数量
	 * 
	 * @param num
	 */
	public static void createTestDate(int num) {
		if (num <= 0) {
			return;
		} else if (num < 10) {
			// 1-9个联系人
			for (int i = 1; i < num; i++) {
				createContacts("TestDate_" + i, "1352211203" + i);
			}
		} else {
			// 10个以上
			for (int i = 1; i <= num; i++) {
				if (i <= 9) {
					createContacts("TestDate_0" + i, "1352211203" + i);
				} else {
					createContacts("TestDate_" + i, "135221120" + i);
				}

			}
		}

	}

	/**
	 * 点击屏幕，其他坐标添加偏移值，容易触发事件
	 * 
	 * @param point
	 */
	public static void touchScreen(Point point) {
		System.out.println("[start] touchScreen");
		System.out.println("point" + point.toString());
		new TouchAction(driver).tap(point.x + 5, point.y + 5).waitAction()
				.perform();
		System.out.println("[ end ] touchScreen");
	}

	/**
	 * 这个方法必须是在未设置快捷键时运行。否则获取不全或为空。
	 * 
	 * @return
	 */
	public static List<Point> getPointList() {

		// 切换到拨号页
		clickById("tab_call");

		// 选择一键拨号
		clickMenuAndSelect(2);

		// 清空所有已设置按钮
		deleteAllOneCall();

		List<Point> list = new ArrayList<Point>();

		for (WebElement webElement : getAllImageView()) {
			if (getAttributeResourceId(webElement) == 1) {
				// System.out.println("point " +
				// webElement.getLocation().toString());
				list.add(webElement.getLocation());
			}
		}
		back("tab_call");

		return list;
	}

	/**
	 * 清除一键拨号页所有已设置的按键,其中算法有问题，添加一个计数器，加快清理
	 */
	public static void deleteAllOneCall() {
		int i = 1;
		for (WebElement we : getAllImageView()) {
			// 清除已设置的快捷键
			if (getAttributeResourceId(we) == 2) {
				i++;
				//
				we.click();
				// 清除
				contextMenuTitleSelect(3);
			}
			if (i > 8) {
				break;
			}
		}
	}

	/**
	 * 判断当前元素是否设置为一键拨号
	 * <p>
	 * 返回 1 为未设置快捷;
	 * </p>
	 * <p>
	 * 返回2 为已设置；
	 * </p>
	 * 返回0为都不符合
	 * 
	 * @param WebElement
	 * @return
	 */
	public static int getAttributeResourceId(WebElement we) {
		System.out.println("[start] getAttributeResourceId");
		String str = we.getAttribute("resourceId");
		String str1 = str.substring(str.indexOf('/') + 1);
		// 未设置的快捷键
		if (str1.equals("nofastcontactpic")) {
			System.out.println("[end] getAttributeResourceId nofastcontactpic");
			return 1;
		}
		// 已经设置
		else if (str1.equals("fastcontactpic")) {
			System.out.println("[end] getAttributeResourceId nofastcontactpic");
			return 2;
		}
		// 都不符合
		System.out.println("[end] getAttributeResourceId null");
		return 0;
	}

	/**
	 * 清空黑名单管理中的内容
	 */
	public static void deleteBlacklist() {
		// 进入管理黑名单
		OpenTabMenu("防打扰", "黑名单");

		// 判断清空按钮是否存在
		if (isExistenceById("iab_ib_action")) {
			// 点击清空
			clickById("iab_ib_action");

			// 点击清空
			clickById("dialog_btn_positive");
		}

		// 返回主界面
		back("tab_contacts");
	}

	/**
	 * 进入防打扰设置
	 */
	public static void OpenTabMenu(String tab1, String tab2) {
		// 点击和通讯录
		clickById("iab_title");

		sleepTime(1000);

		// 点击防打扰
		clickByName(tab1);

		// 检测当前界面为防打扰页
		Assert.assertTrue(driver.findElementByName(tab1).isDisplayed());

		// 点击更多
		clickById("iab_ib_more");

		sleepTime(1000);

		// 点击黑名单
		clickByName(tab2);

	}

	/**
	 * 清空通话记录
	 */
	public static void deleteAllCall() {
		// 点击拨号
		clickById("tab_call");

		// 隐藏输入盘
		hidekeyboardCall();

		if(isExistenceById("no_calls_bigtext")){
			return;
		}
		
		// 点击清空通话记录
		clickMenuAndSelect(1);

		sleepTime(1000);

		// 点击清空按钮
		clickById("dialog_btn_positive");

	}

	/**
	 * 清理拨号记录
	 */
	public static void deleteCall(String phone) {
		// 拨号
		// callNumber("13813881499");

		// sleepTime(8000);

		clickById("tab_call");

		if (searchContact("13813881499", 0)) {
			// 长按记录
			clickLongByNameUseJs("13813881499");

			// 点击删除
			clickByName("删除");

			// 确认删除
			clickByName("删除");
		}

	}

	/**
	 * 拨号盘点击号码
	 * 
	 * @param str
	 */
	public static void touchCallNumber(String str) {
		int len = str.length();
		int i;
		for (i = 0; i < len; i++) {
			char chr = str.charAt(i);
			if(chr == '*'){
				digitsChangeName(11);
			}
			else if(chr == '#'){
				digitsChangeName(12);
			}
			else{
				digitsChangeName(Integer.parseInt(chr + ""));
			}
			//digitsChangeName(Integer.parseInt(str.charAt(i) + ""));
		}
	}

	/**
	 * 仅用于拨号盘点击数字
	 * 
	 * @param chr
	 */
	public static void digitsChangeName(int chr) {
		switch (chr) {
		case 1:
			clickById("one");
			break;
		case 2:
			clickById("two");
			break;
		case 3:
			clickById("three");
			break;
		case 4:
			clickById("four");
			break;
		case 5:
			clickById("five");
			break;
		case 6:
			clickById("six");
			break;
		case 7:
			clickById("seven");
			break;
		case 8:
			clickById("eight");
			break;

		case 9:
			clickById("nine");
			break;

		case 0:
			clickById("zero");
			break;
			
		case 11:
			clickById("star");
			break;

		case 12:
			clickById("pound");
			break;
		}
	}

	/**
	 * 拨号：拨打号码（在断网情况下使用）
	 */
	public static void callNumber(String number) {
		// 点击拨号
		clickById("tab_call");

		// 点击联系人
		clickById("tab_contacts");

		// 点击拨号
		clickById("tab_call");

		// 点击输入框
		clickById("digits");

		// 点击键盘数字
		touchCallNumber(number);

		// 点击拨号
		clickById("tab_dialer");

		// 点击点击确定
		clickByName("确定");

	}

	
}
