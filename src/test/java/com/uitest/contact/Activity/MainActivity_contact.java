package com.uitest.contact.Activity;

import io.appium.java_client.TouchAction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.uitest.uiautomatorUtil.DriverManager;
import com.uitest.uiautomatorUtil.ElementManager;

public class MainActivity_contact{
/**
 * 
 *  拨号盘页面：
	 属性：
		常用固定号码如：10086、10010、10000
		常用暗码：*#06#、*#*#4636#*#*
		组件ID: 页面各个组件ID按功能命名
		页面对象：页面各个UI对象
	 方法：
		创建联系人（仅名称、号码）
 * 		创建联系人（名称、号码、其他）
 * 		删除联系人（）
 * 		清空联系人（）
 */
	//页面元素
	private static String addBtnId = "iab_ib_action"; //新增按钮
	private static String contactBtnId = "tab_contacts"; //联系人模块
	private static String contactName = "contact_name"; //列表中的用户名
	private static String contactPhone = "contact_number"; //列表中的号码名
	private static String searchEtId = "contact_search_bar"; //搜索框
	private static String searchContactPhone = "contact_search_content"; //搜索页面联系人号码
	
	//长按元素，屏幕选项
	private static String deleteLayout = "mca_delete_layout"; //删除
	private static String shareLayout = "mca_share_layout"; //分享名片
	private static String msgLayout = "mca_msg_layout"; //发短信
	private static String callLayout = "mca_call_layout"; //加入白名单
	private static String selectAll = "mca_ib_select"; //全选
	
	//搜索页
	private static String noContact = "contact_empty_view"; //没记录时，背景显示的id
	private static String searchDel = "contact_search_del_btn"; //搜索框的情况记录按钮
	private static String searchCancel = "contact_search_cancel_btn"; //取消搜索按钮
	
	private static String confirmBtnId = "dialog_btn_positive"; //确认按钮 
	
	public void newContact(String name, String phone){
		ElementManager.clickById(contactBtnId);
		//点击添加
		ElementManager.clickById(addBtnId);
		sleep(2000);
		EditContactActivity.setContactName(name);
		sleep(2000);
		EditContactActivity.setPhone(phone);
		EditContactActivity.saveContact();
		sleep(2000);
	}
	
	public void swipeDownNewContact(String name, String phone){
		ElementManager.clickById(contactBtnId);
		//点击添加
		DriverManager.swipeToDown();
		sleep(2000);
		EditContactActivity.setContactName(name);
		sleep(2000);
		EditContactActivity.setPhone(phone);
		EditContactActivity.saveContact();
		sleep(2000);
	}
	
	
	/**
	 * 通过姓名删除
	 * @param name
	 */
	public void deleteContactByName(String name){
		
		ElementManager.clickById(contactBtnId);
		
		//联系人个数为零
		if(ElementManager.isExistById("btResolve")){
			return;
		}
		System.out.println("one");
		int num = ElementManager.getChildCountByClassId("ListView", contactName);
		//只有一个联系人，且名称相同，删除
		if(num == 1 && ElementManager.getViewTextById(contactName).equals(name)){
			//如果相等，删除
			deleteOneOper(ElementManager.getUiObjectByResourceIdMatches(contactName));
			return;
		}else if(num > 1 && search(name)){
			//查找，并删除
			deleteOneOper(ElementManager.getUiObjectByResourceIdMatchesIndex(contactName, 0));
			clearRecord();
			searchCancel();
			return;
		}else{
			return;
		}

	}
	
	/**
	 * 通过号码删除 
	 * @param name
	 */
	public void deleteContactByPhone(String phone){
		
		ElementManager.clickById(contactBtnId);
		
		//联系人个数为零
		if(ElementManager.isExistById("btResolve")){
			return;
		}
		System.out.println("one");
		
		int num = ElementManager.getChildCountByClassId("ListView",contactPhone);
		//只有一个联系人，且名称相同，删除
		if(num == 1 && ElementManager.getViewTextById(contactPhone).equals(phone)){
			//如果相等，删除
			deleteOneOper(ElementManager.getUiObjectByResourceIdMatches(contactPhone));
			return;
		}else if(num > 1 && search(phone)){
			//查找，并删除
			deleteOneOper(ElementManager.getUiObjectByResourceIdMatchesIndex(contactPhone, 1));
			clearRecord();
			searchCancel();
			return;
		}else{
			return;
		}

	}
	/**
	 * 根据条件搜索联系人，存在返回true,否则返回false
	 * @param name
	 */
	public boolean search(String name){
		ElementManager.clickById(contactBtnId);
		
		//联系人个数为零
		if(ElementManager.isExistById("btResolve")){
			return false;
		}
		
		//输入条件
		ElementManager.inputTextById(searchEtId, name);
		
		//获取list数量
		//结果列表返回数量为0
		if(ElementManager.isExistById(noContact)){
			return false;
		}
		
		//返回一个或多条记录都定义为true
		return true;
		
	}
	
	/**
	 * 清除记录
	 */
	public void clearRecord(){
		ElementManager.clickById(searchDel);
	}
	
	/**
	 * 取消搜索
	 */
	public void searchCancel(){
		ElementManager.clickById(searchCancel);
	}
	
	
	
	/**
	 * 清理所有联系人
	 */
	public void clearContact(){
		
		ElementManager.clickById(contactBtnId);
		
		//联系人个数为零
		if(ElementManager.isExistById("btResolve")){
			return;
		}

		int cnt = ElementManager.getChildCountByClassId("ListView", contactName);
		
		System.out.println("collection: " + cnt);
		
		UiObject uo ;
		//删除联系人
		if(cnt == 1){
			uo = ElementManager.getUiObjectByResourceIdMatches(contactName);
			deleteOneOper(uo);
		}else if(cnt > 1){
			//获取元素
			uo = ElementManager.getUiObjectByResourceIdMatchesIndex(contactName, 0);
			deleteAllOper(uo);
		}else{
			
		}
	}
	
	//删除单个联系的操作
	public void deleteOneOper(UiObject uo){
		//长按元素
		ElementManager.clickLongByUiObject(uo);
		// 点击删除
		ElementManager.clickById(deleteLayout);
		// 点击确认删除
		ElementManager.clickById(confirmBtnId);
	}
	
	//删除所有联系的操作
	public void deleteAllOper(UiObject uo){
		
		//长按元素
		ElementManager.clickLongByUiObject(uo);

		// 点击全选
		ElementManager.clickById(selectAll);

		// 点击删除
		ElementManager.clickById(deleteLayout);

		// 点击确认删除
		ElementManager.clickById(confirmBtnId);
	}
	

	
	
	/**
	 * 获取列表中第一个名称
	 * @return
	 */
	public UiObject getListViewFirstName(){
		return ElementManager.getUiObjectByResourceIdMatchesIndex(contactName, 0);
	}
	
	public UiObject getListViewFirstPhoneInMain(){
		return ElementManager.getUiObjectByResourceIdMatchesIndex(contactPhone, 1);
	}
	
	public UiObject getListViewFirstPhoneInSearch(){
		return ElementManager.getUiObjectByResourceIdMatchesIndex(searchContactPhone, 1);
	}
	
	///////////////////////////////////////////////
	
	public static void back(String name) {
		System.out.println("[start] back");
		int i = 0;
		for (; i < 7; i++) {
			//首页
			if(rootFloor()){
				ElementManager.clickById(name);
				ElementManager.clickById(name);
				//sleep(500);
				System.out.println("[ end ] back");
				return;
			}else if(!notRootFloor() || 
					!settingPage() || !searchPage()) {
				//返回上一层
				System.out.println("notRootFloor or settingPage or searchPage");
				UiDevice.getInstance().pressBack();
				sleep(500);
			}
			//弹窗
			else if(isPopup() && ElementManager.getViewTextById("title").equals("短信发送失败")){
				//取消
				ElementManager.clickById("dialog_btn_negative");
			}
			else if(isPopup()){
				//确定
				ElementManager.clickById("dialog_btn_positive");				
			}
			else{
				ElementManager.clickById("tab_contacts");
				
				sleep(2000);
				
				ElementManager.clickById("tab_call");
			}
			//不在首层
			sleep(500);
		}
	}
	
	private static void sleep(int i) {
			//System.out.println("no sleep time");
			//UiDevice.getInstance().wait(i);
	}

	//不在首层
	public static boolean notRootFloor(){
		if(ElementManager.isExistById("iab_back")) {
			return true;
		}
		return false;
	}
	
	public static boolean searchPage(){
		if(ElementManager.isExistById("contact_search_cancel_btn")){
			return true;
		}
		return false;
	}
	
	/**
	 * 在首层
	 * @return
	 */
	public static boolean rootFloor(){
		if(ElementManager.isExistById("tab_bar") && ElementManager.isExistById("iab_view") && (!ElementManager.isExistById("user_info_layout")))
		{
			System.out.println("rootFloor");
			return true;
		}
		System.out.println("not rootFloor");
		return false;
	}
	
	/**
	 * s设置页面
	 * @return
	 */
	public static boolean settingPage(){
		if(ElementManager.isExistById("user_info_layout")){
			return true;
		}
		return false;
	}
	
	//判断是否为弹窗
	public static boolean isPopup(){
		if(ElementManager.isExistById("title")){
			return true;
		}
		return false;
	}
	
	
	// /////////////////////联系人模块//////////////////////

		/**
		 * 去除联系人回收站
		 */
		public static void contactsRecycle() {
			if (isExistenceById("notice_delete")) {
				clickById("notice_delete");
			}
		}

		/**
		 * 根据坐标点，获取头像图片
		 * 
		 * @param point
		 * @return
		 */
		public static BufferedImage getContactHead(Point point) {

			String savePath = "C:/Users/Administrator/workspace_appium/AppiumTest_Appium/test-output/picture/";
			String fileName = "1.jpg";
			File path = null;
			File srcFile = null;

			try {
				// 文件路径
				path = new File(savePath + fileName);

				// 源文件
				srcFile = driver.getScreenshotAs(OutputType.FILE);

				// 复制文件
				FileUtils.copyFile(srcFile, path);

				// 获取文件缓存
				BufferedImage image = getImageFromFile(srcFile);

				// 截图图片大小
				return getSubImage(image, point.x, point.y, 100, 100);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * 选择图片，但不稳定
		 */
		public static void selectImage() {
			// 点击头像
			clickById("contact_detail_header_icon_layout");

			// 点击从图库中选择照片
			clickByName("从图库中选择照片");

			// 选择相册
			clickByName("相册");

			// 再次选择相册
			clickByName("相册");

			int width = driver.manage().window().getSize().width;
			int height = driver.manage().window().getSize().height;

			Point point = new Point(width / 4, height / 2);

			sleepTime(1000);
			touchScreen(point);

			Point point2 = new Point(width / 2, height / 5);
			sleepTime(1000);
			touchScreen(point2);

			sleepTime(2000);
			// 点击完成
			clickByName("完成");
		}

		/**
		 * 获取联系人列表中，联系图标坐标列表，空值返回null
		 * 
		 * @return
		 */
		public static List<Point> getContactsPoint() {
			List<Point> list = new ArrayList<Point>();

			for (WebElement webElement : getAllImageView()) {
				String str = webElement.getAttribute("resourceId");
				String subStr = str.substring(str.indexOf('/') + 1);
				if (subStr.equals("contact_icon")) {
					// System.out.println(webElement.getAttribute("resourceId"));
					// System.out.println(webElement.getLocation());
					list.add(webElement.getLocation());
				}

			}
			// 如果没有联系人
			if (list.size() == 0) {
				// 返回null
				return null;
			} else {
				// 否则返回列表null
				return list;
			}

		}

		/**
		 * 获取联系人列表中的数量，通过获取头像的控件来获取数量，超于一页的联系人数量无法获取。
		 */
		public static int getContactCount() {
			if(isExistenceById("btResolve")){
				System.out.println("联系人数量为0");
				return 0;
			}
			
			// 获取列表个数
			List<Point> list = getContactsPoint();

			// 判断返回列表个数
			if (list == null) {
				System.out.println("getContactCount(): " + 0);
				return 0;
			} else {
				System.out.println("getContactCount(): " + list.size());
				return list.size();
			}
		}

		/**
		 * 清空所有的联系人
		 */
		public static void deleteAllContacts() {
			back("tab_contacts");
			int size = getContactCount();
			if (size > 1) {
				// 点击一个联系人，全选-删除
				if (isExistenceById("contact_name")) {
					clickLongByIdUseJs("contact_name");

					// 点击全选
					clickById("mca_ib_select");

					// 点击删除
					clickById("mca_delete_layout");

					// 点击确认删除
					clickById("dialog_btn_positive");
				}
				contactsRecycle();
			} else if (size == 1) {
				// 只有一个联系人
				clickLongByIdUseJs("contact_name");

				// 点击删除
				clickById("mca_delete_layout");

				// 点击确认删除
				clickById("dialog_btn_positive");

				contactsRecycle();
			} else {
				// 没有联系人
				contactsRecycle();
				return;
			}

		}

		/**
		 * 保存简单的联系人信息核心部分
		 * 
		 * @param username
		 * @param phone
		 */
		public static void saveContact(String username, String phone) {

			// 验证当前页面为新建联系人
			Assert.assertTrue(driver.findElement(By.name("新建联系人")).isDisplayed());

			// 获取界面所有的EditView元素
			// List<WebElement> editText = driver
			// .findElementsByClassName("android.widget.EditText");

			// 第一个元素收入
			// editText.get(0).sendKeys(username);

			intoContentEditTextByName("姓名", username);

			// 点击屏幕，功能缺陷
			// touchWindows();

			// 第四个元素输入
			// editText.get(3).sendKeys(phone);

			// 点击屏幕
			// touchWindows();

			intoContentEditTextByName("电话号码", phone);

			// 点击保存
			clickById("iab_ib_action");
		}

		/**
		 * 在联系人模块，创建联系人，详细信息
		 * 
		 */
		public static void createContacts() {

			String username = "陈测试";
			String phone = "13800138002";

			// 点击联系人
			clickById("tab_contacts");

			// 点击新增按钮
			clickById("iab_ib_action");

			// 验证当前页面为新建联系人
			Assert.assertTrue(driver.findElement(By.name("新建联系人")).isDisplayed());

			// 第一步先添加邮箱选项

			// 点击“添加更多资料”
			clickById("add_more_attribute");

			// 点击邮箱
			contextMenuTitleSelect(2);

			intoContentEditTextByName("姓名", username);

			intoContentEditTextByName("公司", "广州银泰华有限责任公司");

			intoContentEditTextByName("部门", "质量保证");

			intoContentEditTextByName("职位", "高级测试工程师");

			intoContentEditTextByName("电话号码", phone);

			intoContentEditTextByName("邮箱", "helloworld@164.com");

			// 点击保存
			clickById("iab_ib_action");

			// 休眠3秒
			sleepTime(3000);

			// 输入框内，输入搜索内容
			intoContentEditTextById("contact_search_bar", phone);

			// 判断是否通过
			Assert.assertTrue(searchContact(phone, 1));

		}

		/**
		 * 创建详细联系人，添加头像
		 */
		public static void createContactsAddImage() {

			String username = "加头像测试";
			String phone = "13100131000";

			// 点击联系人
			clickById("tab_contacts");

			// 点击新增按钮
			clickById("iab_ib_action");

			// 验证当前页面为新建联系人
			Assert.assertTrue(driver.findElement(By.name("新建联系人")).isDisplayed());

			// 第一步先添加邮箱选项

			// 点击“添加更多资料”
			clickById("add_more_attribute");

			// 点击邮箱
			contextMenuTitleSelect(2);

			intoContentEditTextByName("姓名", username);

			intoContentEditTextByName("公司", "广州银泰华有限责任公司");

			intoContentEditTextByName("部门", "质量保证");

			intoContentEditTextByName("职位", "高级测试工程师");

			intoContentEditTextByName("电话号码", phone);

			intoContentEditTextByName("邮箱", "helloworld@164.com");

			// 点击保存
			clickById("iab_ib_action");

			// 休眠3秒
			sleepTime(3000);

			// 获取默认头像

			//

			// 输入框内，输入搜索内容
			intoContentEditTextById("contact_search_bar", phone);

			// 判断是否通过
			Assert.assertTrue(searchContact(phone, 1));
		}

		/**
		 * 在联系人模块，创建联系人：通过姓名、号码，新建联系人
		 * 
		 * @param username
		 * @param phone
		 */
		public static void createContacts(String username, String phone) {
			// 点击拨号
			clickById("tab_call");

			// 点击联系人
			clickById("tab_contacts");

			// 点击新增按钮
			clickById("iab_ib_action");

			// 保存信息
			saveContact(username, phone);

			// 休眠3秒
			sleepTime(3000);

			// 输入框内，输入搜索内容
			intoContentEditTextById("contact_search_bar", phone);

			// 判断是否通过
			Assert.assertTrue(searchContact(phone, 1));

			clearTextAndNote();
		}

		/**
		 * 联系人模块，删除联系人：通过名称，删除联系人 参数name：手机号码
		 * 
		 * @param name
		 */
		public static void deleteContactsByPhone(String name) {
			// 点击联系人
			clickById("tab_contacts");

			// 输入框内，输入搜索内容
			intoContentEditTextById("contact_search_bar", name);

			// 判断列表是否存在该联系人
			// Assert.assertTrue(searchContact("10086" , 1));

			// 长按联系人
			// clickLongWebElement(getFirstTextView("10086"));
			clickLongByNameUseJs(name);

			// 点击删除
			clickById("mca_delete_icon");

			// 点击确定
			clickById("dialog_btn_positive");
		}

		/**
		 * 联系人模块，删除联系人：通过名称，删除联系人 参数name：手机号码
		 * 
		 * @param name
		 */
		public static void deleteContactsByName(String name) {
			// 点击联系人
			clickById("tab_contacts");

			// 输入框内，输入搜索内容
			intoContentEditTextById("contact_search_bar", name);

			// 判断列表是否存在该联系人
			// Assert.assertTrue(searchContact("10086" , 1));

			// 长按联系人
			// clickLongWebElement(getFirstTextView("10086"));
			clickLongByElementUseJs(searchWebElement(name));

			// 点击删除
			clickById("mca_delete_icon");

			// 点击确定
			clickById("dialog_btn_positive");
		}

		/**
		 * 点击屏幕
		 */
		public static void touchWindows() {
			int x = driver.manage().window().getSize().width;
			int y = driver.manage().window().getSize().height;
			TouchAction ta = new TouchAction(driver);
			ta.press(x - 1, y - 1).waitAction().perform();
			ta.press(x - 1, y - 1).waitAction().perform();
			System.out.println("touch Windows");
		}

	
}
