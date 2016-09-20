package com.uitest.contact.Activity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;


public class MainActivity_mms{
/**
 * 
 *  拨号盘页面：
	 属性：
		常用固定号码如：10086、10010、10000
		常用暗码：*#06#、*#*#4636#*#*
		组件ID: 页面各个组件ID按功能命名
		页面对象：页面各个UI对象
	 方法：
		输入号码
		清除号码、删除一个号码
		拨号
		其他设置（添加联系人、暂停、等待、发送短信）
		选择号码
 * 
 * 
 * 
 */
	
	// //////////////////短信模块/////////////////////

	public static void hasContact(){
		
		back("tab_contacts");
		
		String tmpphone = "10000";
		String tmpname  = "tmpdata";
		
		if(isExistenceById("btResolve"))
		{
			createContacts(tmpname, tmpphone);
		}
		
	}
	
	
	/**
	 * 清除所有不相干的分组
	 */
	public static void clearGroup() {

		//创建临时联系人
		hasContact();
		
		intoMyGroupPage();

		List<WebElement> list = getLisWebElementById("txt_group_name");
		String name;
		int num = 0;
		int m = 0;
		// 循环删除
		for ( ; m<=20 ; m++) {
			num = list.size();
			// System.out.println("********************first: " + num);
			// Log.info("cbh", "********************first: " + num);

			for (int i = num - 1; i >= 0; i--) {
				name = list.get(i).getText();
				// System.out.println("********************name: " +name +
				// "; i " + i);
				// Log.info("cbh", "********************name: " +name + "; i " +
				// i);
				if (!isContains(name)) {
					// 长按分组名
					clickLongByNameUseJs(name);
					// 点击解散分组
					clickByName("解散分组");
					// 点击解散
					clickByName("解散");

					sleepTime(1000);
				}
			}

			// 再获取列表
			list = getLisWebElementById("txt_group_name");
			// System.out.println("********************second: " + list.size());
			// Log.info("cbh", "********************second: " + list.size());
			// 退出标准
			if ((list.size() <= 5)) {
				if (num == list.size()) {
					break;
				}
			}
		}

		
		back("tab_contacts");
		deleteAllContacts();
		if(m>17){
			Myassert("clear group error." + getTestCaseName(), false);
		}
		
	}

	/**
	 * 判断字符含有字符
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isContains(String name) {
		if (name.contains("未分组") || name.contains("紧急联系人")
				|| name.contains("家人") || name.contains("好友") || name.contains("同事")) {
			return true;
		}
		return false;
	}

	/**
	 * 准备未读短信,创建含多条的短信
	 */
	public static void prepareUnreadMMS(String num1, String num2) {
		// 创建未读短信数量
		openAppByName("SMSToolDemo");
		setSmsToolDemo(num1, num2);
		openAppByName("和通讯录");

	}

	/**
	 * 准备未读短信
	 */
	public static int prepareUnreadMMS() {
		// 确保存在未读短信
		List<WebElement> list;
		do {
			deleteAllMMs();

			// 创建未读短信数量
			openAppByName("SMSToolDemo");
			setSmsToolDemo("7", "1");
			openAppByName("和通讯录");
			Assert.assertTrue("创建的短信数量有异常", getMMsCount() == 7);

			clickById("tab_mms");
			list = getLisWebElementById("unread");

			System.out.println("未读短信有：" + list.size());

		} while (list.size() <= 1);

		return list.size();
	}

	/**
	 * 取消接收新消息弹窗：true为开启；false为关闭
	 */
	public static void setMessagePop(boolean bl) {
		// 进入短信提醒设置
		clickById("tab_mms");

		clickMenuAndSelect(7);

		Assert.assertTrue("没有进入信息设置页", isExistenceByName("信息设置"));

		clickById("setting_message_remind");

		Assert.assertTrue("没有进入短信提醒设置", isExistenceByName("短信提醒设置"));

		// setting_message_pop_cb
		String result = driver.findElementById("setting_message_pop_cb")
				.getAttribute("checked");
		String state = bl + "";
		if (result.equals(state)) {
			// 什么都不做
		} else {
			clickById("setting_message_pop_cb");
			sleepTime(1000);
			//确认关闭
			clickById("dialog_btn_positive");
		}

		Assert.assertTrue(
				"设置失败",
				driver.findElementById("setting_message_pop_cb")
						.getAttribute("checked").equals(state));

		back("tab_mms");
	}

	/**
	 * 在精选短信列表
	 * 
	 * @param num
	 */
	public static void selectFeaturemms(int num) {
		String UiSelector = ""
				+ "new UiSelector().resourceIdMatches(\".+id/listview\").childSelector("
				+ "new UiSelector().className(\"android.widget.RelativeLayout\").index("
				+ num + "))";
		driver.findElementByAndroidUIAutomator(UiSelector).click();

	}

	/**
	 * 选择选择第几个表情（0-19），20时删除
	 * 
	 * @param id
	 * @param num
	 */
	public static void selectEmoticon(int num) {
		String UiSelector = ""
				+ "new UiSelector().resourceIdMatches(\".+id/emoticon_viewpager\").childSelector("
				+ "new UiSelector().className(\"android.widget.GridView\").childSelector("
				+ "new UiSelector().className(\"android.widget.ImageView\").index("
				+ num + ")))";
		driver.findElementByAndroidUIAutomator(UiSelector).click();

	}

	/**
	 * 输入时、分，设置时间。注意时间的取值。
	 * 
	 * @param hour
	 * @param minute
	 */
	public static void setDate(String year, String month, String date) {
		// 点击时间
		clickById("date_layout");
		sleepTime(2000);

		// 设置 年
		Assert.assertTrue(isExistenceById("picker_year"));
		setValue("picker_year", year);

		// 设置 月
		Assert.assertTrue(isExistenceById("picker_month"));
		setValue("picker_month", month);

		// 设置 日
		Assert.assertTrue(isExistenceById("picker_date"));
		setValue("picker_date", date);
	}

	/**
	 * 输入时、分，设置时间。注意时间的取值。
	 * 
	 * @param hour
	 * @param minute
	 */
	public static void setTime(String hour, String minute) {
		// 点击时间
		clickById("time_layout");
		sleepTime(2000);

		// 设置 时
		Assert.assertTrue(isExistenceById("picker_hour"));
		setValue("picker_hour", hour);

		// 设置 分
		Assert.assertTrue(isExistenceById("picker_minute"));
		setValue("picker_minute", minute);
	}

	/**
	 * 通过控件id，判断是设置什么类型的时间。
	 * <p>
	 * 设置时间（1-24）注意12或24时间。
	 * <p>
	 * 设置分钟（0-59）
	 * <p>
	 * 注意：没有设置异常机制，自行判断。
	 * 
	 * @param time
	 */
	public static void setValue(String id, String time) {
		WebElement mine;
		int i = 0;
		for (; i < 63; i++) {
			// 获取当前时间
			mine = getWebElementByAndroidUIAutomator(id, 3);

			if (mine.getText().equals(time)) {
				return;
			} else {
				// 点击下一个
				getWebElementByAndroidUIAutomator(id, 4).click();
				sleepTime(1000);
			}

		}
		if(i>61){
			Myassert("getvalue error" + getTestCaseName(), false);
		}
		
	}

	/**
	 * 通过控件ID,输入第几个，获取元素对象。
	 * <p>
	 * （用于定时发送短信，时间控件内选择时间或日期）
	 * 
	 * @param id
	 * @param num
	 * @return
	 */
	public static WebElement getWebElementByAndroidUIAutomator(String id, int num) {
		String UiSelector = ""
				+ "new UiSelector().resourceIdMatches(\".+id/"
				+ id
				+ "\").childSelector("
				+ "new UiSelector().className(\"android.widget.TextView\").index("
				+ num + "))";
		WebElement we = driver.findElementByAndroidUIAutomator(UiSelector);
		return we;
	}

	/**
	 * 点击新建，选择联系人列表中，默认是第一个联系人,输入短信内容(短信内容不支持空格)
	 * 
	 * @param num
	 * @param content
	 */
	public static void createMMs(String content) {
		clickById("tab_mms");

		// 点击新建短信
		clickById("iab_ib_action");

		// 点击添加收件人
		clickById("add_recipients");

		// 验证
		Assert.assertTrue(isExistenceByName("选择收件人"));

		// 添加1个联系人
		addMMsContactMembers(1);

		// 输入短信内容
		intoContentEditTextById("embedded_text_editor", content);

		// 点击确定
		sendMMS();

		sleepTime(2000);
		back("tab_mms");

		// 去除信息回收站
		if (isExistenceById("tv_title")) {
			clickById("notice_delete");
		}

		// 向输入框输入内容
		intoContentEditTextById("contact_search_bar", content);

		// 判断是否通过
		Assert.assertTrue(searchContactInMMs(content));
	}

	/**
	 * 在短信选择联系人页，选择联系人的个数（num<7）
	 * 
	 * @param num
	 */
	public static void addMMsContactMembers(int num) {

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
	 * 在短信页，根据搜索内容获取结果
	 */
	public static boolean searchContactInMMs(String contact) {
		clearTextAndNote();
		// 如果没有短信
		if (isExistenceById("notcontent")) {
			return false;
		}

		if (getWebElementInList(getAllTextView(), contact) != null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 短信新建，输入号码，短信内容，点击发送。（下拉新建短信）
	 * 
	 * @param phone
	 * @param content
	 */
	public static void createMMs(String phone, String content) {
		clickById("tab_mms");

		// 下拉
		swipeToDown();
		Assert.assertTrue(isExistenceByName("新信息"));

		// 向输入框收入内容
		intoContentEditTextByName("收件人:", phone);

		// 输入短信内容
		intoContentEditTextById("embedded_text_editor", content);

		// 点击确定
		sendMMS();
		
		back("tab_mms");

		// 向输入框输入内容
		intoContentEditTextById("contact_search_bar", phone);

		// 判断是否通过
		Assert.assertTrue(searchContactInMMs(phone));

	}

	/**
	 * 获取短信列表中，联系人图标坐标列表，空值返回null
	 * 
	 * @return
	 */
	public static List<Point> getMMsPoint() {

		List<Point> list = new ArrayList<Point>();

		for (WebElement webElement : getAllImageView()) {
			String str = webElement.getAttribute("resourceId");
			String subStr = str.substring(str.indexOf('/') + 1);
			if (subStr.equals("avatar")) {
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
	 * 获取短信列表中的数量，通过获取头像的控件来获取数量，超于一页的联系人数量无法获取。
	 */
	public static int getMMsCount() {
		if(isExistenceById("btResolve")){
			System.out.println("短信数量为0");
			return 0;
		}
		
		// 获取列表个数
		List<Point> list = getMMsPoint();

		// 判断返回列表个数
		if (list == null) {
			System.out.println("getMMsCount(): " + 0);
			return 0;
		} else {
			System.out.println("getMMsCount(): " + list.size());
			return list.size();
		}
	}

	/**
	 * 清除联系人列表中所有的短信
	 */
	public static void deleteAllMMs() {
		System.out.println("[start] deleteAllMMs");
		back("tab_mms");

		// 如果发现无短信，马上退出
		if (isExistenceById("btResolve")) {
			System.out.println("[ end ] deleteAllMMs");
			return;
		}

		for (int i = 0; i < 3; i++) {
			sleepTime(2000);
			// 第一次获取，清除列表中异常短信
			clearSpecialMMs(driver.findElementsById("from"));

			// 如果发现无短信，马上退出
			if (isExistenceById("btResolve")) {
				System.out.println("[ end ] deleteAllMMs");
				return;
			}

			// 再次获取列表
			List<WebElement> list = driver.findElementsById("from");

			// 获取列表长度
			int size = list.size();

			if (size > 1) {
				// 点击一个联系人，全选-删除
				clickLongByElementUseJs(list.get(0));

				// 点击全选
				clickById("mca_ib_select");

				// 点击删除
				clickById("mca_del");

				// 点击确认删除
				clickById("dialog_btn_positive");

				// 去除信息回收站
				if (isExistenceById("tv_title")) {
					clickById("notice_delete");
				}
				sleepTime(2000);
			} else if (size == 1) {
				// 只有条短信
				clickLongByIdUseJs("from");

				// 点击删除
				clickById("mca_del");

				// 点击确认删除
				clickById("dialog_btn_positive");

				// 去除信息回收站
				if (isExistenceById("tv_title")) {
					clickById("notice_delete");
				}
				sleepTime(2000);
			} else {
				// 没有联系人
				System.out.println("[ end ] deleteAllMMs");

				sleepTime(2000);
				return;
			}
		}
		System.out.println("[ end ] deleteAllMMs");
		sleepTime(2000);
	}

	/**
	 * 清除特殊类型的短信
	 */
	public static void clearSpecialMMs(List<WebElement> list) {
		System.out.println("[start] clearSpecialMMs");

		WebElement we = getWebElementInList(list, "139邮件提醒");
		if (we != null) {
			// 点击元素
			we.click();

			Assert.assertTrue(isExistenceByName("139邮件提醒"));

			// 清空记录
			clickMenuAndSelect(1);

			// 点击清空
			clickById("dialog_btn_positive");
			sleepTime(15000);

			back("tab_mms");
		}

		WebElement we1 = getWebElementInList(list, "通知短信归档");

		if (we1 != null) {
			// 点击元素
			we1.click();

			Assert.assertTrue(isExistenceByName("通知短信归档"));

			// 批量删除
			clickMenuAndSelect(1);

			// 点击更多
			clickById("mca_ib_select");

			// 点击删除
			clickById("mca_sure");

			// 确认删除
			clickById("dialog_btn_positive");

			sleepTime(30000);

			back("tab_mms");
		}

		System.out.println("[ end ] clearSpecialMMs");
	}

	
	
	
}
