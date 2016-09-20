package com.uitest.core;

import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class AppUtil {

	public static AndroidDriver<WebElement> driver;
	public static String imagePath = "";
	public static String myport = "";
	private static String appActivity = ".Main";
	private static String packageName = "com.chinamobile.contacts.im";
	
	// ////////////////////////////////////辅助方法//////////////////////////////////////////

	public static void sendMMS(){
		// 点击确定
		clickById("send_button");

		sleepTime(2000);
		
		if(isExistenceById("title"))
		{
			if(getTextViewNameById("title").equals("短信发送失败")){
				clickById("dialog_btn_negative");
			}
		}
	}
	
	
	/**
	 * 获取分组名称，根据用例名称生成
	 * @return
	 */
	public static String getTestGroupName(){
		return "group" + getSubString(getMethodName(), "_");
	}
	
	
	/**
	 * 截取子字符串
	 * @param str
	 * @param string
	 * @return
	 */
	public static String getSubString(String str, String string){
		return str.substring(str.indexOf(string));
	}
	
	/**
	 * 截取子字符串
	 * @param str
	 * @param string
	 * @return
	 */
	public static String getSubString(String str, String string, int index){
		return str.substring(str.indexOf(string) + index);
	}
	
	/**
	 * 将设置值抽象出来
	 * @param bl
	 * @param id
	 */
	public static void setValue(boolean bl, String id){
		String result = driver.findElementById(id)
				.getAttribute("checked");
		String state = bl + "";
		if (result.equals(state)) {
			// 什么都不做
		} else {
			clickById(id);
			sleepTime(1000);
		}
		
		Assert.assertTrue("设置失败",driver.findElementById(id)
						.getAttribute("checked").equals(state));
	}
	
	
	/**
	 * 设置拨号按键声音和震动
	 * @param bl
	 */
	public static void setCallShockAndVoice(boolean bl){
		
		clickById("tab_call");
		
		//选择通话设置
		clickMenuAndSelect(5);
		
		Myassert("没有进入通话设置页", getTextViewNameById("iab_title").equals("通话设置"));

		setValue(bl, "setting_img_shock");
		
		setValue(bl, "setting_img_voice");
		
		back("tab_call");
	}
	
	
	/**
	 * 通话记录生成
	 * @param phone 需要输入号码，生成该号码的记录
	 * @param time 通话时间大于0
	 * @param type 选择记录类型分别为：已接/已拨/未接来电，对应值(1/2/3)
	 * @param isRead type选择3后才有效，选择记录是否为已读/未读(1/2)
	 * <p>实例：prepareUnreadCall("13800138000", 15, 3, 2),生成一条未读的未接来电
	 */
	public static String prepareUnreadCall(String phone, int time, int type, int isRead) {
		String str = "";
		// 创建未读短信数量
		openAppByName("通话记录生成器");
		str = callHistory(phone, time, type, isRead);
		openAppByName("和通讯录");
		return str;
	}

	
	
	/**
	 * 通话记录生成
	 * @param phone 需要输入号码，生成该号码的记录
	 * @param time 通话时间大于0
	 * @param type 选择记录类型分别为：已接/已拨/未接来电，对应值(1/2/3)
	 * @param isRead type选择3后才有效，选择记录是否为已读/未读(1/2)
	 * <p>实例：callHistory("13800138000", 15, 3, 2),生成一条未读的未接来电
	 */
	public static String callHistory(String phone, int time, int type, int isRead){
		
		String date = "";
		
		sleepTime(2000);
		
		//输入号码
		intoContentEditTextById("com.ktls.tonghuaweizao:id/etxMobileNum", phone);
		
		//输入通话时间
		intoContentEditTextById("com.ktls.tonghuaweizao:id/etxDuration", time+"");
		
		//点击未接来电(1/2/3)
		clickById("com.ktls.tonghuaweizao:id/rg_OutOrIn_"+type);
		
		if(type == 3){
			//点击未读(1/2)
			clickById("com.ktls.tonghuaweizao:id/rg_IsRead_"+isRead);
		}
		
		
		//点击生成记录
		clickById("com.ktls.tonghuaweizao:id/btnCreate");
		
		//点击确定
		clickById("android:id/button1");
		
		sleepTime(2000);
		
		date = getTextViewNameById("com.ktls.tonghuaweizao:id/txtDate") 
				+ "_" + getTextViewNameById("com.ktls.tonghuaweizao:id/txtTime");
		
		return date;
	}
	
	
	// /////////////webView////////////////////

	public static void intoHelp(){
		// 点击和通讯录
		clickById("iab_title");

		// 点击设置
		clickById("setting_layout");
		
		//点击反馈与帮助
		clickById("setting_item_problem");
		
		//点击帮助
		clickByName("帮助");
		sleepTime(2000);
		
	}
	
	public static void IntoPwd() {
		// 点击和通讯录
		clickById("iab_title");

		// 点击设置
		clickById("setting_layout");


		if (isExistenceByName("登录")) {
			
			clickByName("登录");
			//
			sleepTime(2000);

			// 点击登录
			clickById("setting_item_login");

			// 点击互联网登录
			clickById("btn_login_dynamic");

			//点击忘记密码
			clickByName("忘记密码");

			// 等待登录时间(根据网络状态)
			sleepTime(2000);
		}
	}

	/**
	 * 切换WEB页面查找元素
	 */
	public static void switchtoWeb() {
		System.out.println("switchtoWeb");
		
		try {
			Set<String> contextNames = driver.getContextHandles();
			for (String contextName : contextNames) {
				System.out.println("contextName: "+ contextName);
				
				// 用于返回被测app是NATIVE_APP还是WEBVIEW，如果两者都有就是混合型App
				if (contextName.contains("WEBVIEW")
						|| contextName.contains("webview")) {
					driver.context(contextName);
					System.out.println("跳转到web页 开始操作web页面");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	// //////////////////////分组管理////////////////////////

	/**
	 * 选择列表中什么位置的文件(top \ mid \ bottom)
	 * 
	 * @param location
	 */
	public static void scrollAndSelect(String GroupName, String location) {

		// 进入我的分组
		intoMyGroupPage();

		// 选择一分组
		clickByName(GroupName);

		// 进入批量选择铃声
		clickMenuAndSelect(3);

		// 弹窗铃声选择框
		Assert.assertTrue(isExistenceById("alertTitle"));

		// 选择默认铃声下的第一个铃声
		if (location.equals("top")) {
			// 使用api提供的方法，获取列表中的元素列表
			List<WebElement> list = driver.findElementsById("text1");
			list.get(1).click();

			sleepTime(2000);

			clickByName("确定");
		}
		// 移动到中部，但不精准
		else if (location.equals("mid")) {
			scrollToMidAndSelect();
		} else if (location.equals("bottom")) {
			scrollToBottomAndSelect();
		}

		// 返回上一页
		backPage(1);
	}

	/**
	 * 选择列表中间的铃声
	 */
	public static void scrollToMidAndSelect() {
		List<WebElement> list;

		int num = 0;
		int tmp = 0;
		for (;tmp<10; tmp++) {
			// 第一次获取列表底部文字
			list = driver.findElementsById("text1");
			String str1 = list.get(list.size() - 1).getText();
			// 滑动屏幕
			swipeToUp();
			num++;
			sleepTime(1000);

			// 第二次获取
			list = driver.findElementsById("text1");
			String str2 = list.get(list.size() - 1).getText();

			// 如果相等，则退出
			if (str1.equals(str2)) {
				break;
			}
		}
		if(tmp > 7)
		{
			Myassert("scrollToMidAndSelect error", false);
		}

		num = num / 2 - 1;
		for (int i = 0; i < num; i++) {
			System.out.println("swipeToDown");
			swipeToDown();
		}

		// 点击列表中间的节点
		list = driver.findElementsById("text1");
		list.get((list.size() - 1) / 2).click();
		;
		sleepTime(2000);
		clickByName("确定");
	}

	/**
	 * 移动到列表的底部，并选择铃声
	 */
	public static void scrollToBottomAndSelect() {
		List<WebElement> list;
		int i = 0;
		for (;i < 10; i++) {
			// 第一次获取列表底部文字
			list = driver.findElementsById("text1");
			String str1 = list.get(list.size() - 1).getText();
			// 滑动屏幕
			swipeToUp();
			sleepTime(1000);
			// 第二次获取
			list = driver.findElementsById("text1");
			String str2 = list.get(list.size() - 1).getText();

			// 如果相等，则退出
			if (str1.equals(str2)) {
				list.get(list.size() - 1).click();
				clickByName("确定");
				break;
			}
		}

		if(i>8){
			Myassert("scrollToBottomAndSelect error", false);
		}
	}

	/**
	 * 删除分组联系人，输入组名及删除数量
	 * 
	 * @param GroupName
	 * @param num
	 */
	public static void deleteGroupMembers(String GroupName, int num) {
		// 点击我的分组
		intoMyGroupPage();

		// 进入分组
		clickByName(GroupName);

		// 点击添加成员
		clickByName("移除成员");

		// 如果移动数量为0，返回
		if (num == 0) {
			// 返回上一页
			backPage(1);
			return;
		}

		// 获取搜索框内的数字
		int members = getNumById("contact_search_bar");
		// 判断联系人数量，若为0，退出。
		if (members == 0) {
			// 返回上一页
			backPage(1);
			return;
		}

		// 获取单选框列表
		List<WebElement> list = getWebElementList("CheckBox", "contact_check");

		// 若联系人数量，超于列表的数量，按照列表的数量选择
		if (num > members) {
			int tmp = 1;
			// 遍历控件，点击
			for (WebElement we : list) {
				System.out.println("click");
				we.click();
				System.out.println("click");
				if (members == tmp) {
					break;
				}
				tmp++;
			}
		}
		// 若联系人数量，不超过列表数量，按照输入的数量选择
		else {

			int tmp = 1;
			// 遍历控件，点击
			for (WebElement we : list) {
				System.out.println("click");
				we.click();
				System.out.println("click");
				if (num == tmp) {
					break;
				}
				tmp++;
			}
		}
		// 点击移除
		clickById("mca_sure");
		clickById("dialog_btn_positive");
		
	}

	/**
	 * 参数num为添加人数，num必须少于7（控件仅获取当前页的控件） 参数GroupName分组名
	 * 
	 * @param GroupName
	 * @param num
	 */
	public static void addGroupMembers(String GroupName, int num) {
		// 点击我的分组
		intoMyGroupPage();

		// 进入分组
		clickByName(GroupName);

		// 点击添加成员
		clickByName("添加成员");

		// 获取搜索框内的数字
		int members = getNumById("contact_search_bar");
		// 判断联系人数量，若为0，退出。
		if (members == 0) {
			// 返回上一页
			backPage(1);
			return;
		}

		// 获取单选框列表
		List<WebElement> list = getWebElementList("CheckBox", "contact_check");

		// 若联系人数量，超于列表的数量，按照列表的数量选择
		if (num > members) {
			int tmp = 1;
			// 遍历控件，点击
			for (WebElement we : list) {
				System.out.println("click");
				we.click();
				System.out.println("click");
				if (members == tmp) {
					break;
				}
				tmp++;
			}
		}
		// 若联系人数量，不超过列表数量，按照输入的数量选择
		else {

			int tmp = 1;
			// 遍历控件，点击
			for (WebElement we : list) {
				System.out.println("click");
				we.click();
				System.out.println("click");
				if (num == tmp) {
					break;
				}
				tmp++;
			}
		}
		// 点击添加
		clickById("selection_ok");
	}

	/**
	 * 参数分别为用户名、移动方向(down or up)，移动到列表顶部或底部。 注意的是，分组列只能是少于或等于一页
	 * 
	 * @param GroupName
	 * @param direction
	 */
	public static void groupMoveto(String GroupName, String direction) {

		// 获取分组开始时的位置
		int first = getGroupNum(GroupName);

		// 获取移动的控件图标列表
		List<WebElement> list = getWebElementList("ImageView", "drag_handle");

		// 获取当前元素
		WebElement e1 = list.get(first);

		// 申明TouchAciton对象
		TouchAction ta = new TouchAction(driver);

		// 判断移动方向
		if (direction.equals("down")) {

			// 向下移动
			ta.longPress(e1).moveTo(list.get(list.size() - 1)).release()
					.perform();

		} else if (direction.equals("up")) {

			// 向上移动
			ta.longPress(e1).moveTo(list.get(0)).release().perform();

		}
	}

	/**
	 * 输入组名，获取在列表中，排第几
	 * 
	 * @param groupName
	 * @return
	 */
	public static int getGroupNum(String groupName) {
		int num = 0;
		for (WebElement webElement : getAllTextView()) {
			String str = webElement.getAttribute("resourceId");
			String subStr = str.substring(str.indexOf('/') + 1);
			if (subStr.equals("txt_group_name")) {

				if (webElement.getText().equals(groupName)) {
					System.out.println(groupName + " getGroupNum" + num);
					return num;
				}
				num++;
			}
		}
		return -1;
	}

	/**
	 * 确保进入我的分组页
	 */
	public static void intoMyGroupPage() {
		// 退出
		back("tab_contacts");

		// 进入我的分组
		if (isExistenceById("headview_item_name")) {
			clickById("headview_item_name");
		}

	}

	/**
	 * 点击进入已有分组，移除当前列表所有联系人
	 * 
	 * @param name
	 */
	public static void deleteAllGroupMembers(String name) {
		// 点击我的分组
		intoMyGroupPage();

		// 进入分组
		clickByName(name);

		// 点击添加成员
		clickByName("移除成员");
		// sleepTime(10000);

		// 获取联系人列表联系人数量
		if (getNumById("contact_search_bar") > 0) {
			// 点击更多按钮
			clickById("mca_ib_select");

			sleepTime(1000);
			// 点击移除
			clickById("mca_sure");

			// 确认移除
			clickById("dialog_btn_positive");

		} else {
			// 返回上一页
			backPage(1);
		}
		// 返回上一页
		backPage(1);
	}

	/**
	 * 点击进入已有分组，添加当前列表所有联系人
	 * 
	 * @param name
	 */
	public static void addAllGroupMembers(String name) {
		// 点击我的分组
		intoMyGroupPage();
		
		Myassert("没有进入我的分组", isExistenceById("iab_title") && getTextViewNameById("iab_title").equals("我的分组"));
		
		if(isExistenceByName(name))
		{
			swipeToUp();
		}
		// 进入分组
		clickByName(name);

		// 点击添加成员
		clickByName("添加成员");
		// sleepTime(10000);

		// 获取联系人列表联系人数量
		if (getNumById("contact_search_bar") > 0) {
			// 点击更多按钮
			clickById("iab_ib_more");
			// 点击添加
			clickById("selection_ok");
		} else {
			// 返回上一页
			backPage(1);
		}
		// 返回上一页
		backPage(1);
	}

	/**
	 * 在分组页内，长按分组名-重命名，修改已有的分组，参数分别是原有的分组名，修改后的分组名
	 * 
	 * @param srcName
	 * @param name
	 */
	public static void renameGroup(String srcName, String name) {
		// 如果分组名不存在，直接返回
		if (!isExistenceByName(srcName)) {
			return;
		}

		// 如果修改的名称为空，退出
		if (name.equals("")) {
			return;
		}

		// 长按分组名
		clickLongByNameUseJs(srcName);

		// 点击重命名
		clickByName("重命名分组");

		// 输入修改的名称
		intoContentEditTextById("content", name);

		// 点击保存
		clickById("dialog_btn_positive");

	}

	/**
	 * 在个别的分组内点击菜单-重命名，进入分组，参数分别是原有的分组名，修改后的分组名
	 * 
	 * @param srcName
	 * @param name
	 */
	public static void renameOneGroup(String srcName, String name) {
		// 进入我的分组
		intoMyGroupPage();

		// 如果分组名不存在，直接返回
		if (!isExistenceByName(srcName)) {
			return;
		}

		// 如果修改的名称为空，退出
		if (name.equals("")) {
			return;
		}

		// 点击进入分组
		clickByName(srcName);

		// 点击重命名
		clickMenuAndSelect(1);

		// 输入修改的名称
		intoContentEditTextById("content", name);

		// 点击保存
		clickById("dialog_btn_positive");

	}

	/**
	 * 在分组页内调用，长按删除，删除分组（先删除分组，再删除联系人）
	 */
	public static void deleteGroup(String name) {
		System.out.println("[ start ] deleteGroup: " + name);

		// 判断当前页面为我的分组
		if (getTextViewNameById("iab_title").equals("我的分组")) {
			// 什么都不做
		}
		// 不存在
		else {
			// 如果没我的分组，退出
			if (!isExistenceById("headview_item_name")) {
				System.out.println("[ end ] deleteGroup: 没有进入我的分组");
				return;
			} else {
				// 进入我的分组
				intoMyGroupPage();
			}

		}

		// 如果分组名不存在，直接返回
		if (!isExistenceByName(name)) {
			System.out.println("[ end ] deleteGroup: 分组名不存在");
			back("tab_contacts");
			return;
		}

		// 长按分组名
		clickLongByNameUseJs(name);
		// 点击解散分组
		clickByName("解散分组");
		// 点击解散
		clickByName("解散");
		System.out.println("[ end ] deleteGroup: ");
		back("tab_contacts");
	}

	/**
	 * 进入某个分组，点击菜单选择解散分组，删除分组
	 */
	public static void deleteOneGroup(String name) {
		// 进入我的分组
		intoMyGroupPage();
		// 如果分组名不存在，直接返回
		if (!isExistenceByName(name)) {
			back("tab_contacts");
			return;
		}

		// 进入分组
		clickByName(name);

		// 选择解散分组
		clickMenuAndSelect(2);

		// 点击解散
		clickByName("解散");

		back("tab_contacts");
	}

	/**
	 * 创建分组，若已存在后，直接放回；不存在，新建分组。
	 * <p>
	 * state
	 * </p>
	 * 0,创建后是否保存在当前页; 1,返回主页
	 */
	public static void createGroup(String name, int state) {

		Assert.assertTrue(isExistenceById("headview_item_name"));
		if (isExistenceByName("我的分组")) {

			// 点击我的分组
			clickById("headview_item_name");

			Assert.assertTrue(isExistenceByName("新建分组"));

			// 如果已存在分组名，退出
			if (isExistenceByName(name)) {
				return;
			}

			// 点击创建分组
			clickByName("新建分组");

			// 输入分组名
			intoContentEditTextById("content", name);

			// 点击保存
			clickById("dialog_btn_positive");
		}

		if (state == 0) {
			return;
		}

		back("tab_contacts");
	}

	

	// /////////////////登录模块/////////////////////////////

	/**
	 * 是否在登录状态，false为未登录状态，true为已登录
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isLoginState(String username) {
		try {
			// 验证第二元素是否包含"登录"
			WebElement el = driver.findElementsByClassName(
					"android.widget.TextView").get(1);
			String str = el.getText();
			Boolean bl = str.contains(username);
			System.out.println("isLoginState: " + bl);
			return bl;
		} catch (Exception ex) {
			// 找不到元素
		}
		System.out.println("isLoginState: false");
		return false;
	}

	/**
	 * 登录：输入用户、密码，登录
	 * 
	 * @param username
	 * @param password
	 */
	public static void Logout(String username) {

		// 点击和通讯录
		clickById("iab_title");

		// 点击设置
		clickById("setting_layout");

		// 判断是否为已登录，若已登录状态，直接返回
		if (isLoginState(username)) {
			// 点击退出
			clickById("setting_item_login_logout_text");

			// 点击确认
			clickById("dialog_btn_positive");
		}

		// 验证第二元素是否包含"登录"
		Assert.assertTrue(driver
				.findElementsByClassName("android.widget.TextView").get(1)
				.getText().contains("登录"));

		// 退出登录页
		driver.sendKeyEvent(AndroidKeyCode.BACK);

		sleepTime(1000);
		// 返回主界面
		driver.sendKeyEvent(AndroidKeyCode.BACK);

		sleepTime(5000);
	}

	/**
	 * 输入用户、密码，登录
	 * 
	 * @param username
	 * @param password
	 */
	public static void Login(String username, String password) {

		// 点击和通讯录
		clickById("iab_title");

		// 点击设置
		clickById("setting_layout");

		// System.out.println("isLoginState() " + isLoginState());
		// 判断是否为登录状态，若未登录状态，进行下一步；否则返回
		if (!isLoginState(username)) {
			//
			sleepTime(3000);

			// 点击登录
			clickById("setting_item_login");

			// 点击互联网登录
			clickById("btn_login_dynamic");

			// 输入用户名
			intoContentEditTextById("setting_new_login_mobile_et_num", username);

			// 输入密码
			intoContentEditTextById("setting_new_login_mobile_et_password",
					password);

			// 点击登录
			clickById("setting_new_login_mobile_btn_login");

			// 等待登录时间(根据网络状态)
			sleepTime(10000);
		}

		// 验证第二元素是否包含登录号码
		Assert.assertTrue(driver
				.findElementsByClassName("android.widget.TextView").get(1)
				.getText().contains(username));

		// 退出登录页
		driver.sendKeyEvent(AndroidKeyCode.BACK);

		sleepTime(1000);
		// 返回主界面
		driver.sendKeyEvent(AndroidKeyCode.BACK);
		sleepTime(1000);

	}

	// ///////////////////////////公共模块///////////////////

	/**
	 * 短信生成器，输入两个参数，分别是会话条数，信息数量。
	 * 
	 * @param cscnt
	 * @param msscnt
	 */
	public static void setSmsToolDemo(String cscnt, String msscnt) {

//		// 输入数字
//		intoContentEditTextByName("input conversation count", cscnt);
//		intoContentEditTextByName("input messages count", msscnt);
		
		intoContentEditTextById("com.er.zjj.demo.outbox:id/edit_create_input", cscnt);
		intoContentEditTextById("com.er.zjj.demo.outbox:id/edit_sms_count", msscnt);
		// 点击创建
		//clickByName("create conversation");
		
		// 点击创建
		clickById("com.er.zjj.demo.outbox:id/btn_create");
		
//		// 清除
//		intoContentEditTextByName(cscnt, "");
//		intoContentEditTextByName(msscnt, "");
		// 清除
		intoContentEditTextById("com.er.zjj.demo.outbox:id/edit_create_input", "");
		intoContentEditTextById("com.er.zjj.demo.outbox:id/edit_sms_count", "");

		Assert.assertTrue("还原不了默认内容，下次运行可能出错",
				isExistenceByName("input conversation count")
						&& isExistenceByName("input messages count"));

	}

	/**
	 * 该方法内不可使用控件的id（不同包名），只能用name
	 */
	public static void openAppByName(String name) {

		driver.sendKeyEvent(AndroidKeyCode.HOME);
		sleepTime(2000);

		clickByName("应用程序");
		sleepTime(2000);
		int i = 0;
		for (;i < 12; i++) {
			if (isExistenceByName(name)) {
				break;
			}
			swipeToRight();
		}

		if(i > 10){
			Myassert("openAppByName error", false);
		}
		
		clickByName(name);

		if (name.equals("SMSToolDemo")) {
			String tmp_activity = "com.er.zjj.demo.SendSmsToolsDemo.SmsToolDemo";
			
			Assert.assertTrue("没有进入短信生成器",
					driver.currentActivity().equals(tmp_activity));
		} else if (name.equals("和通讯录")) {
			//String appActivity = ".Main";
			clickById("tab_mms");
			sleepTime(1000);
			Assert.assertTrue("没有进入和通讯录主页",
					driver.currentActivity().equals(appActivity));
		}
		sleepTime(2000);
	}

	/**
	 * 清除搜索记录和搜索框内容
	 */
	public static void clearTextAndNote() {
		if (isExistenceById("contact_search_del_btn")) {
			clickById("contact_search_del_btn");
		}
		if (isExistenceById("clean_button")) {
			clickById("clean_button");
		}
		if (isExistenceById("contact_search_cancel_btn")) {
			clickById("contact_search_cancel_btn");
		}
	}

	/**
	 * 点击多少次返回主界面
	 * 
	 * @param num
	 */
	public static void backPage(int num) {
		// 返回主界面
		for (int i = 0; i < num; i++) {
			driver.sendKeyEvent(AndroidKeyCode.BACK);
			sleepTime(1000);
		}
	}

	// 返回
	public static void back(String name) {
		System.out.println("[start] back");
		int i = 0;
		for (; i < 7; i++) {
			
			if(notRootFloor()) {
				//返回上一层
				System.out.println("back-noroot");
				driver.sendKeyEvent(AndroidKeyCode.BACK);
				sleepTime(1000);
			}
			
			else if(isExistenceById("avatar_im")){
				System.out.println("back-seting");
				driver.sendKeyEvent(AndroidKeyCode.BACK);
				sleepTime(1000);
			}
			
			//在首层
			else if(rootFloor()){
				clickById("tab_contacts");
				clickById(name);
				sleepTime(1000);
				System.out.println("[ end ] back");
				return;
			}
			//弹窗
			else if(isPopup()){
				if(getTextViewNameById("title").equals("短信发送失败"));
				{
					//取消
					clickById("dialog_btn_negative");
				}
				//确定
				clickById("dialog_btn_positive");
			}
			else if(isExistenceById("contact_search_cancel_btn"))
			{
				clearTextAndNote();
			}
			else{
				clickById("tab_contacts");
				
				sleepTime(2000);
				
				clickById("tab_call");
			}
			//	
			
			//不在首层
			
			sleepTime(1000);
		}
	
	}
	
	/**
	 * 1、获取当前activity,
	 * 2、判读当前是否为系统主界面，截图（可能报错点击确定），启动应用
	 * 3、判读当前是否为和通讯录主界面（.Main）时，是否含有弹窗。
	 * 4、判读当前在和通讯录其他activity中，是否含弹窗
	 * @param name
	 */
	public static void back_all(String name) {
		String craty = driver.currentActivity();
		
		if(craty.equals("com.android.launcher2.Launcher")){
			//截图
			
			//判读是否含有确定，点击
			
			//启动和通讯录
			
			return;
		}
		
		if(craty.equals(".Main")){
			
		}
		
	}
	
	
	//不在首层
	public static boolean notRootFloor(){
		if(isExistenceById("iab_back")) {
			return true;
		}
		return false;
	}
	
	public static boolean rootFloor(){
		if(isExistenceById("iab_title"))
		{
			//在首层
			if(isExistenceById("iab_title") && getTextViewNameById("iab_title").equals("和通讯录"));
			{
				return true;
			}
			
		}
			return false;
	}
	
	//判断是否为弹窗
	public static boolean isPopup(){
		if(isExistenceById("title")){
			return true;
		}
		return false;
	}
	
	
	// /////////////////////////////通用基础方法////////////////////////////////////////////////////

	public static void line() {
		System.out.println("--------------------------");
	}



	/**
	 * 点击控件ID，粘贴已有的内容
	 */
	public static void pasteString(String id) {
		clickById(id);
		// 粘贴
		// 使用3.1版本：AndroidKeyCode才有定义具体静态常量
		driver.sendKeyEvent(AndroidKeyCode.KEYCODE_V,
				AndroidKeyCode.META_CTRL_MASK);
		// driver.sendKeyEvent(50, 28672);//1.3版本
	}

	/**
	 * 输入元素列表，查找的字段
	 * 
	 * @param list
	 * @param search
	 * @return
	 */
	public static boolean searListContainName(List<WebElement> list, String search) {
		// 如果等于空或者null返回false
		if ((list.isEmpty()) || list == null) {
			return false;
		}

		// 找到返回true
		for (WebElement we : list) {
			if (we.getText().contains(search)) {
				return true;
			}
		}

		// 找不到返回false
		return false;
	}

	/**
	 * 根据控件ID获取 元素列表
	 * 
	 * @param id
	 * @return
	 */
	public static List<WebElement> getLisWebElementById(String id) {
		List<WebElement> list = driver.findElementsById(id);
		return list;
	}

	/**
	 * 返回列表元素，根据列表的顶部，或底部 location: start\end
	 * 
	 * @param id
	 * @param location
	 * @return
	 */
	public static WebElement getWebElementInList(String id, String location) {

		// 获取元素列表
		List<WebElement> list = driver.findElementsById(id);
		// 获取列表长度
		int size = list.size();

		WebElement we = null;

		if (location.equals("start")) {
			System.out.println("getWebElementInList size: " + size);
			we = list.get(0);
		} else if (location.equals("end")) {
			System.out.println("getWebElementInList size: " + size);
			we = list.get(size - 1);
		} else {
			System.out.println("getWebElementInList size: " + size);
			we = null;
		}

		return we;
	}

	/**
	 * 对列表元素遍历，搜索“含有” content字段的元素。（使用contains方法）
	 * 
	 * @param list
	 * @param content
	 * @return
	 */
	public static WebElement getWebElementInList(List<WebElement> list, String content) {
		System.out.println("[start] getWebElementInList");
		// 如果为空，返回
		if (list == null) {
			System.out.println("[ end ] getWebElementInList is null");
			return null;
		}
		for (WebElement we : list) {
			System.out.println("text " + we.getText());
			if (we.getText().contains(content)) {
				System.out.println("[ end ] getWebElementInList not null");
				return we;
			}
		}
		System.out.println("[ end ] getWebElementInList is null");
		return null;
	}

	/**
	 * 根据控件ID获取元素列表，sytle分别是(Image、TextView、CheckBox)类型。
	 * 
	 * @param id
	 * @param sytle
	 * @return
	 */
	public static List<WebElement> getWebElementList(String sytle, String id) {
		System.out.println("[ start ] getWebElementList");
		List<WebElement> weList = new ArrayList<WebElement>();
		// 获取Image类型，且匹配的元素
		if (sytle.equals("ImageView")) {
			for (WebElement webElement : getAllImageView()) {
				String str = webElement.getAttribute("resourceId");
				String subStr = str.substring(str.indexOf('/') + 1);
				if (subStr.equals(id)) {
					weList.add(webElement);
				}
			}
			System.out.println("[ return ] ImageView size: " + weList.size());
		}// 获取TextView类型，且匹配的元素
		else if (sytle.equals("TextView")) {
			for (WebElement webElement : getAllTextView()) {
				String str = webElement.getAttribute("resourceId");
				String subStr = str.substring(str.indexOf('/') + 1);
				if (subStr.equals(id)) {
					weList.add(webElement);
				}
			}
			System.out.println("[ return ] TextView size: " + weList.size());
		}
		// 获取CheckBox类型，且匹配的元素
		else if (sytle.equals("CheckBox")) {
			for (WebElement webElement : getAllCheckBox()) {
				String str = webElement.getAttribute("resourceId");
				String subStr = str.substring(str.indexOf('/') + 1);
				if (subStr.equals(id)) {
					weList.add(webElement);
				}
			}
			System.out.println("[ return ] CheckBox size: " + weList.size());
		}
		// 若不匹配类型，返回空列表
		else {
			weList = null;
		}
		System.out.println("[ end ] getWebElementList");
		return weList;
	}

	/**
	 * 根据文字，获取其中的数字
	 */
	public static int getNumerByText(String txt) {
		// 提取数字
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(txt);
		// 发生异常时，返回-1
		int num = 0;
		try {
			// 将获取数据转为int类型
			num = Integer.parseInt(m.replaceAll("").trim());
		} catch (Exception ex) {

			num = -1;
			// System.out.println("");
		}
		System.out.println("getNumerByText: " + num);

		return num;

	}

	/**
	 * 根据控件的ID，获取控件文字中的数字
	 * 
	 * @param id
	 * @return
	 */
	public static int getNumById(String id) {
		// 获取元素ID
		WebElement we = driver.findElementById(id);

		// 获取控件text
		String str = we.getAttribute("text");

		// 提取数字
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		// 将获取数据转为int类型
		int num = Integer.parseInt(m.replaceAll("").trim());
		System.out.println("getNumById: " + num);
		return num;
	}

	/**
	 * 手势向左滑动
	 */
	public static void swipeToLeft() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, 500);
		System.out.println("[ doing ] swipeToLeft ");
	}

	/**
	 * 手势向右滑动
	 */
	public static void swipeToRight() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, 500);
		System.out.println("[ doing ] swipeToRight ");
	}

	/**
	 * 手势向右滑动,添加水平高度变量（即点击屏幕高、中、低，向右滑） location选项为high\mid\bottom
	 */
	public static void swipeToLeft(String location) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		if (location.equals("bottom")) {
			driver.swipe(width * 3 / 4, height * 3 / 4, width / 4,
					height * 3 / 4, 500);
			System.out.println("[ doing ] swipeToLeft ");
		} else if (location.equals("mid")) {
			driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, 500);
			System.out.println("[ doing ] swipeToLeft ");
		} else if (location.equals("high")) {
			driver.swipe(width * 3 / 4, height / 4, width / 4, height / 4, 500);
			System.out.println("[ doing ] swipeToLeft ");
		} else {
			System.out.println("[ error ] location null ");
		}

	}

	/**
	 * 手势向右滑动,添加水平高度变量（即点击屏幕高、中、低，向右滑） location选项为high\mid\bottom
	 */
	public static void swipeToRight(String location) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		if (location.equals("bottom")) {
			driver.swipe(width / 4, height * 3 / 4, width * 3 / 4,
					height * 3 / 4, 500);
			System.out.println("[ doing ] swipeToRight ");
		} else if (location.equals("mid")) {
			driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, 500);
			System.out.println("[ doing ] swipeToRight ");
		} else if (location.equals("high")) {
			driver.swipe(width / 4, height / 4, width * 3 / 4, height / 4, 500);
			System.out.println("[ doing ] swipeToRight ");
		} else {
			System.out.println("[ error ] location null ");
		}

	}

	/**
	 * 手势向下滑动
	 */
	public static void swipeToDown() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 1000);
		// wait for page loading
	}

	/**
	 * 
	 * 手势向上滑动
	 */
	public static void swipeToUp() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, 1000);
		// wait for page loading
	}


	/**
	 * 清空输入框内容
	 * 
	 * @param text
	 */
	public static void clearText(String text) {
		System.out.println("[start] Clear Text " + text);
		if (text == null) {
			return;
		}

		// 光标移动到末尾
		driver.sendKeyEvent(123);

		// 逐一删除内容
		for (int i = 0; i < text.length(); i++) {
			driver.sendKeyEvent(AndroidKeyCode.DEL);
		}
		System.out.println("[ end ] Clear Text finish ");
	}

	/**
	 * 获取当前界面上，所有的textView，运行比较慢； 注意：若列表过长不建议使用，容易出现找不到元素，返回null
	 * 
	 * @return
	 */
	public static List<WebElement> getAllTextView() {
		System.out.println("find All TextView");
		List<WebElement> lis = driver
				.findElementsByClassName("android.widget.TextView");
		return lis;
	}

	/**
	 * 获取当前界面上，所有的CheckBox，运行比较慢； 注意：若列表过长不建议使用，容易出现找不到元素，返回null
	 * 
	 * @return
	 */
	public static List<WebElement> getAllCheckBox() {
		System.out.println("find All CheckBox");
		List<WebElement> lis = driver
				.findElementsByClassName("android.widget.CheckBox");
		return lis;
	}

	/**
	 * 获取当前界面上，所有的ImageView，运行比较慢； 注意：若列表过长不建议使用，容易出现找不到元素，返回null
	 * 
	 * @return
	 */
	public static List<WebElement> getAllImageView() {
		System.out.println("find All ImageView");
		List<WebElement> lis = driver
				.findElementsByClassName("android.widget.ImageView");
		return lis;
	}

	/**
	 * 对上面的获取所有TextView进行优化，获取返回元素的，第二元素
	 * <p>
	 * 调整index可以获取第一元素：默认是1、其他情况为0
	 * </p>
	 * 返回时元素对象
	 * <p>
	 * name：查找的字符串
	 * </p>
	 * 
	 * @return
	 */
	public static WebElement getFirstTextView(String name, int index) {
		System.out.println("[start] getFirstTextView");
		for (WebElement el : getElementsByClassAndIndex(
				"android.widget.TextView", index)) {
			System.out.println("getFirstTextView " + el.getText().toString());
			if (el.getText().toString().equals(name)) {
				System.out.println("[ end ] getFirstTextView");
				return el;
			}
		}
		System.out.println("[ end ] getFirstTextView");
		return null;
	}

	/**
	 * 通过使用UiSelector，获取当前列表某个元素，比较高效。返回List<WebElement>
	 * 
	 * @param classname
	 * @param index
	 * @return
	 */
	public static List<WebElement> getElementsByClassAndIndex(String classname,
			int index) {
		List<WebElement> lis = null;
		System.out.println("[start] findElementsByAndroidUIAutomator");
		lis = driver
				.findElementsByAndroidUIAutomator("new UiSelector().className("
						+ classname + ").index(" + index + ")");
		System.out.println("[ end ] findElementsByAndroidUIAutomator");
		return lis;
	}

	/**
	 * 获取点击菜单，选择某个选项
	 * 
	 * @param num
	 */
	public static void clickMenuAndSelect(int num) {
		// 点击菜单按钮
		driver.sendKeyEvent(AndroidKeyCode.MENU);
		// pop_navi_text 菜单的listView id
		menuList("pop_navi_text", num);

	}

	/**
	 * 输入控件ID，获取点击菜单，选择某个选项
	 * 
	 * @param num
	 */
	public static void clickMenuAndSelect(String id, int num) {
		// 点击菜单按钮
		driver.sendKeyEvent(AndroidKeyCode.MENU);
		// pop_navi_text 菜单的listView id
		menuList(id, num);

	}

	/**
	 * 获取更多选项，点击菜单选项
	 * 
	 * @param num
	 */
	public static void contextMenuTitleSelect(int num) {
		// context_menu_title更多选项中的ListView id
		menuList("context_menu_title", num);
	}

	/**
	 * 获取点击菜单选项
	 * 
	 * @param id
	 * @param num
	 */
	public static void menuList(String id, int num) {
		// 获取列表所有元素
		List<WebElement> menulist = driver.findElementsById(id);
		int len = menulist.size();
		System.out.println("len " + len);
		if (num >= 1 && num <= len) {
			// 点击菜单中的选项
			WebElement tmpel = menulist.get(num - 1);
			System.out.println("menuList" + tmpel.getText());
			tmpel.click();
			// menulist.get(num-1).click();
		}
		sleepTime(2000);
	}

	/**
	 * 向输入框收入内容
	 * 
	 * @param id
	 * @param content
	 */
	public static void intoContentEditTextById(String id, String content) {

		try {

			System.out.println("[start] into name: " + id);

			WebElement e = driver.findElementById(id);
			e.click();
			// 清除搜索记录和清空内容
			clearTextAndNote();

			String text = e.getAttribute("text");
			clearText(text);
			e.sendKeys(content);
			// e.submit(); //用户提交内容，一般不用
			System.out.println("[ end ] into content: " + content);
		} catch (Exception ex) {
			System.out.println("[ error ]can not find " + id);
			ex.printStackTrace();
		}
	}

	/**
	 * 向输入框收入内容，若该控件没有ID，只有name，可以使用该方面
	 * 
	 * @param name
	 * @param content
	 */
	public static void intoContentEditTextByName(String name, String content) {

		try {

			System.out.println("[start] into name: " + name
					+ ", this name is Exist: " + isExistenceByName(name));

			WebElement e = driver.findElementByName(name);
			e.click();
			// 清除搜索记录和清空内容
			clearTextAndNote();

			String text = e.getAttribute("text");
			clearText(text);
			e.sendKeys(content);
			// e.submit(); //用户提交内容，一般不用
			System.out.println("[ end ] into content: " + content);
		} catch (Exception ex) {
			System.out.println("[ error ]can not find " + name);
			// ex.printStackTrace();
		}
	}

	/**
	 * 通过控件ID，获取输入框内容
	 * 
	 * @param name
	 * @return
	 */
	public static String getEditTextContent(String name) {

		try {
			System.out.println("[start] get EditText name: " + name);
			WebElement PhoneContent = driver.findElementById(name);
			PhoneContent.click();
			String text = PhoneContent.getAttribute("text");
			System.out.println("[ end ] get EditText text: " + text);
			return text;
		} catch (Exception ex) {
			System.out.println("can not find " + name);
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 这方法适用于查找联系人，不通用。 判断页面是否存在TextView name的元素
	 * mode等于1的时候，使用简单模式；等于0的时候，使用获取所有TextView元素 使用简单模式容易找不到内容，注意使用。
	 * 
	 * @param search
	 * @param mode
	 * @return
	 */
	public static boolean searchContact(String search, int mode) {
		System.out.println("[start] searchContact");
		
		if(isExistenceById("no_contact_text"))
		{
			System.out.println("[ end ] searchContact: no data");
			return false;
		}
		
		// mode 等于1的时候，使用简单模式；等于0的时候，使用获取所有TextView元素
		if (mode == 1) {
			// 搜索所有的textView，找到匹配到联系人
			WebElement el = getFirstTextView(search, 1);
			if (el.getText().equals(search)) {
				System.out.println("getFirstTextView search text: "
						+ el.getText());
				// 找到符合的显示true
				System.out.println("[ end ] searchContact");
				return true;
			}
			// 没有找到返回false
			System.out.println("[ end ] searchContact");
			return false;

		} else if (mode == 0) {
			// 搜索所有的textView，找到匹配到联系人
			for (WebElement el : getAllTextView()) {
				if (el.getText().equals(search)) {
					System.out.println("getAllTextView " + el.getText());
					System.out.println("[ end ] searchContact");
					// 找到符合的显示true
					return true;
				}
			}
			// 没有找到返回false
			System.out.println("[ end ] searchContact");
			return false;

		}
		System.out.println("[ end ] searchContact");
		return false;
	}

	/**
	 * 通过textView name 查找元素，返回找到WebElement对象
	 * 
	 * @param search
	 * @return
	 */
	public static WebElement searchWebElement(String search) {

		// 搜索所有的textView，找到匹配到联系人
		for (WebElement el : getAllTextView()) {
			if (el.getText().equals(search)) {
				System.out.println("find text: " + el.getText());
				// 找到符合的显示true
				return el;
			}
		}
		// 没有找到返回null
		return null;
	}

	/**
	 * 根据控件ID获取该控件的text
	 * 
	 * @param id
	 * @return
	 */
	public static String getTextViewNameById(String id) {
		try {
			System.out.println("[start] get TextView id: " + id);
			String text = driver.findElementById(id).getAttribute("text");
			System.out.println("[ end ] get TextView id: " + text);
			return text;
		} catch (Exception ex) {
			System.out.println("can not find " + id);
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 休眠 1000 = 1秒
	 * 
	 * @param num
	 */
	public static void sleepTime(int num) {
		try {
			System.out.println("Wait time: " + num / 1000
					+ "s, doing something...");
			Thread.sleep(num);
		} catch (Exception e) {
			System.out.println("[ error ] Time out");

		}
	}

	/**
	 * 获取调用该方法的名称
	 * 
	 * @return
	 */
	public static String getMethodName() {

		return Thread.currentThread().getStackTrace()[4].getMethodName();
	}

	public static String getTestCaseName(){
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}
	
	/**
	 * //此方法为屏幕截图
	 * 
	 * @param drivername
	 * @param filename
	 */
	public static void snapshot(AndroidDriver<WebElement> driver,
			String testCasename) {
		// 获取当前工作路径
		String currentPath = System.getProperty("user.dir");

		// 利用 TakesScreenshot接口提供的 getScreenshotAs()方法捕捉屏幕,会将获取到的截图存放到一个临时文件中
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		// 保存路径
		String filePath = currentPath + imagePath;

		// 文件名
		String filename = testCasename + "_" + getCurrentDateTime() + ".jpg";

		try {
			System.out.println("save snapshot path is:" + currentPath
					+ filePath + filename);
			// 将临时文件拷贝到当前工作路径
			FileUtils.copyFile(scrFile, new File(filePath + filename));
		} catch (IOException e) {
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		} finally {
			System.out.println("screen shot finished, it's in " + filePath
					+ " folder");
		}
	}
	
	public static void snapshot(AndroidDriver<WebElement> driver,
			String testCasename, String text){
		
		System.out.println("snapshot");
		// 获取当前工作路径
		String currentPath = System.getProperty("user.dir");

		//String tmpfile = currentPath + "tmp.png";
		//System.out.println("user.dir" + tmpfile);
		
//		 利用 TakesScreenshot接口提供的 getScreenshotAs()方法捕捉屏幕,会将获取到的截图存放到一个临时文件中
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		
		System.out.println("TakesScreenshot");

		//第一种张图片，源图像地址
		String filePath = currentPath + imagePath;
		String srcPath = filePath + "tmp.jpg";
		try {
			FileUtils.copyFile(scrFile, new File(srcPath));
			
			//第二种张图片，目标图像地址
			String filename = testCasename + "_" + getCurrentDateTime() + ".jpg";
			String newpath = filePath +  filename;
			new File(newpath);
			
			pressText2(text, srcPath, newpath, "黑体", 36, Color.RED, 35, 0, 0, 0.5f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	 /**
    * 给图片添加文字水印
    * @param pressText 水印文字
    * @param srcImageFile 源图像地址
    * @param destImageFile 目标图像地址
    * @param fontName 字体名称
    * @param fontStyle 字体样式
    * @param color 字体颜色
    * @param fontSize 字体大小
    * @param x 修正值
    * @param y 修正值
    * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
    */
   public final static void pressText2(String pressText, String srcImageFile,String destImageFile,
           String fontName, int fontStyle, Color color, int fontSize, int x,
           int y, float alpha) {
       try {
           File img = new File(srcImageFile);
           Image src = ImageIO.read(img);
           int width = src.getWidth(null);
           int height = src.getHeight(null);
           //System.out.println("width" + width);
           //System.out.println("height" + height);
           BufferedImage image = new BufferedImage(width, height,
                   BufferedImage.TYPE_INT_RGB);
           Graphics2D g = image.createGraphics();
           g.drawImage(src, 0, 0, width, height, null);
           g.setColor(color);
           g.setFont(new Font(fontName, fontStyle, fontSize));
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                   alpha));
           // 在指定坐标绘制水印文字
//           g.drawString(pressText, (width - (getLength(pressText) * fontSize))
//                   / 2 + x, (height - fontSize) / 2 + y);
           g.drawString(pressText, x, (height - fontSize)* 4 / 5 + y);
           g.dispose();
           ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
	
   /**
    * 给图片添加图片水印
    * @param pressImg 水印图片
    * @param srcImageFile 源图像地址
    * @param destImageFile 目标图像地址
    * @param x 修正值。 默认在中间
    * @param y 修正值。 默认在中间
    * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
    */
   public final static void pressImage(String pressImg, String srcImageFile,String destImageFile,
           int x, int y, float alpha) {
       try {
           File img = new File(srcImageFile);
           Image src = ImageIO.read(img);
           int wideth = src.getWidth(null);
           int height = src.getHeight(null);
           BufferedImage image = new BufferedImage(wideth, height,
                   BufferedImage.TYPE_INT_RGB);
           Graphics2D g = image.createGraphics();
           g.drawImage(src, 0, 0, wideth, height, null);
           // 水印文件
           Image src_biao = ImageIO.read(new File(pressImg));
           int wideth_biao = src_biao.getWidth(null);
           int height_biao = src_biao.getHeight(null);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                   alpha));
           g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                   (height - height_biao) / 2, wideth_biao, height_biao, null);
           // 水印文件结束
           g.dispose();
           ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }


   /**
    * 计算text的长度（一个中文算两个字符）
    * @param text
    * @return
    */
   public final static int getLength(String text) {
       int length = 0;
       for (int i = 0; i < text.length(); i++) {
           if (new String(text.charAt(i) + "").getBytes().length > 1) {
               length += 2;
           } else {
               length += 1;
           }
       }
       return length / 2;
   }

	
	public static String getCurrentDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");// 设置日期格式
		return df.format(new Date());
	}

	public static String getCurrentDate(){
		String date = getCurrentDateTime();
		return date.substring(0, 8);
	}
	
	
	// /获取截图，并对比
	public static boolean sameAs(BufferedImage myImage,
			BufferedImage otherImage, double percent) {
		// BufferedImage otherImage = other.getBufferedImage();
		// BufferedImage myImage = getBufferedImage();
		if (otherImage.getWidth() != myImage.getWidth()) {
			return false;
		}
		if (otherImage.getHeight() != myImage.getHeight()) {
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

	public  static BufferedImage getSubImage(BufferedImage image, int x, int y,
			int w, int h) {
		return image.getSubimage(x, y, w, h);
	}

	/**
	 * 获取文件
	 * 
	 * @param f
	 * @return
	 */
	public static BufferedImage getImageFromFile(File f) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// if failed, then copy it to local path for later check:TBD
			// FileUtils.copyFile(f, new File(p1));
			e.printStackTrace();
			System.exit(1);
		}
		return img;
	}

	// ///////////////////其他模块//////////////////////////////
	/**
	 * 图片保存，保存模块，用于学习
	 */
	public static void testDebug() {
		// createContacts("testCase_cmp", "13522132032");
		sleepTime(3000);
		String savePath = "C:/Users/Administrator/workspace_appium/AppiumTest_Appium/test-output/picture/";
		String fileName = "1.jpg";
		File path = new File(savePath + fileName);
		File srcFile = driver.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(srcFile, path);
			BufferedImage img = getImageFromFile(srcFile);
			// 截图图片大小
			BufferedImage subImg1 = getSubImage(img, 20, 427, 120 - 20,
					527 - 427);
			// 截图图片大小[20,793][120,893]
			BufferedImage subImg2 = getSubImage(img, 20, 793, 120 - 20,
					893 - 793);

			Boolean same = sameAs(subImg1, subImg2, 0.9);

			// Assert.assertTrue(same);

			File f3 = new File(savePath + "sub-1.png");
			ImageIO.write(subImg1, "PNG", f3);

			File f4 = new File(savePath + "sub-2.png");
			ImageIO.write(subImg2, "PNG", f4);

			Assert.assertTrue(same);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
