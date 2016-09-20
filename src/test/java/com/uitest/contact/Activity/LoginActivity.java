package com.uitest.contact.Activity;

import io.appium.java_client.android.AndroidKeyCode;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

public class LoginActivity {

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

	
	
}
