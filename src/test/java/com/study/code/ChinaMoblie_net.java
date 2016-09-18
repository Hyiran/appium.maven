package com.study.code;

import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.AndroidDriver;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ChinaMoblie_net {


	static AndroidDriver<WebElement> driver;
	private String packageName = "com.chinamobile.contacts.im";
	private String appActivity = ".Main";
	private static String imagePath ="";
	private static String myport = "";
	
	@BeforeSuite(alwaysRun=true)
	@Parameters({"port","udid","ver"})
	public void setUp(String port,String udid, String ver) throws Exception {

		System.out.println("current use port: "+port+", devices udid: "+udid + " android verstion: " + ver);
		//String udid = "0bd08bcc";
		// 设置自动化相关参数
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("udid", udid);

		capabilities.setCapability("deviceName", "");

		// 设置安卓系统版本
		capabilities.setCapability("platformVersion", ver);

		// 设置app的主包名和主类名
		capabilities.setCapability("appPackage", packageName);
		capabilities.setCapability("appActivity", appActivity);

		// 支持中文输入，会自动安装Unicode 输入法。默认值为 false
		capabilities.setCapability("unicodeKeyboard", "True");

		// 在设定了 unicodeKeyboard 关键字的 Unicode 测试结束后，重置输入法到原有状态
		capabilities.setCapability("resetKeyboard", "True");

		// 初始化
		URL url = new URL("http://127.0.0.1:"+port+"/wd/hub");
		driver = new AndroidDriver<WebElement>(url, capabilities);
		System.out.println("setup running");
		imagePath =  "\\test-output\\pic\\" + port+"\\";
		System.out.println("errorImage path: " + imagePath);
		myport = port;
	}

	@Test(groups={"webview"})
	public void webview_001(){
		openNetworkConn();
		
		
	}
	
	
	@AfterSuite(alwaysRun=true)
	public void tearDown() throws Exception {
		driver.quit();
	}

	
	/**
	 * 通过控件的txt，点击控件
	 * @param name
	 */
	public void clickByName(String name) {
		try {
			sleepTime(1000);
			System.out.println("[start] click: " + name);
			driver.findElement(By.name(name)).click();
			System.out.println("[ end ] " + name + ".click");
		} catch (Exception ex) {
			System.out.println("Can not find " + name);
			// ex.printStackTrace();
		}
	}
	
	/**
	 * 判断某个元素是否存在,true存在；false不存在。
	 */
	public boolean isExistenceByName(String name){
		System.out.println("isExistenceByName: " + name);
		try {
			driver.findElementByName(name);
			System.out.println("isExistenceByName: true");
			return true;
		} catch (Exception ex) {
			// 找不到元素
		}
		System.out.println("isExistenceByName: false");
		return false;
	}
	
	/**
	 * 确保联网状态
	 * <p>条件：1、有SIM卡，wifi可有可无连接记录。
	 * <p>条件：2、无SIM卡，wifi需要连接记录
	 */
	public void openNetworkConn(){
		if(checkNet())
		{
			//如果为true，什么都不做
		}
		else{
			//开启网络连接
			setNetworkConn(6);
		}
	}
	
	public void closeNetworkConn(){
		if(checkNet())
		{
			//关闭网络连接
			setNetworkConn(1);
		}
	}
	
	
	/**
	 * 输入模式，设置联网（1/2/4/6）
	 * <p>mode = 1, set airplane
	 * <p>mode = 2, set wifi only
	 * <p>mode = 4, set data only
	 * <p>mode = 6, set wifi and data
	 * @param mode
	 */
	public void setNetworkConn(int mode){
		switch(mode){
		//飞行模式
		case 1:
			driver.setNetworkConnection(new NetworkConnectionSetting(true, false, false));
			break;
		//仅wifi
		case 2:
			driver.setNetworkConnection(new NetworkConnectionSetting(false, true, false));
			if(isExistenceByName("确定"))
			{
				clickByName("确定");
			}
			break;
		//仅数据连接（没有SIM卡页为true）至少手机接收到的信号	
		case 4:
			driver.setNetworkConnection(new NetworkConnectionSetting(false, false, true));
			break;
		//WiFi和数据连接（建议使用这个）
		case 6:
			driver.setNetworkConnection(new NetworkConnectionSetting(false, true, true));
			if(isExistenceByName("确定"))
			{
				clickByName("确定");
			}
			break;
			default:
				System.out.println("mode is error,  :" + mode);
		}
		
		 
	}
	
	/***
	 * 检查网络
	 * 
	 * @return 是否正常
	 */
	public static boolean checkNet() {
		String text = driver.getNetworkConnection().toString();
		System.out.println("text: " + text);
		//获取联网状态注意：没卡时，data也为true，只有在飞行模式下，才显示false
		if (text.contains("Wifi: true") && text.contains("Data: true"))
		{
			System.out.println("checkNet: true");
			return true;
		}
		else
		{
			System.out.println("checkNet: false");
			return false;
		}
			
	}
	
	/**
	 * 休眠 1000 = 1秒
	 * @param num
	 */
	public void sleepTime(int num) {
		try {
			System.out.println("Wait time: " + num / 1000 + "s, doing something...");
			Thread.sleep(num);
		} catch (Exception e) {
			System.out.println("[ error ] Time out");

		}
	}
	
}
