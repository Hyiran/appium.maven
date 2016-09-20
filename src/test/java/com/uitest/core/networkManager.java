package com.uitest.core;

import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.AndroidDriver;

public class networkManager {
  /**
   * 检查网络
   * @since 2016.3.3
   * @param driver
   *          AndroidDriver
   * @return boolean
   */
  public static boolean checkNet(AndroidDriver driver) {
    String text = driver.getNetworkConnection().toString();
    if (text.contains("Data: true")) {
      return true;
    } else {
      return false;
    }
  }
  
	/**
	 * 确保联网状态
	 * <p>
	 * 条件：1、有SIM卡，wifi可有可无连接记录。
	 * <p>
	 * 条件：2、无SIM卡，wifi需要连接记录
	 */
	public static void openNetworkConn() {
		if (checkNet()) {
			// 如果为true，什么都不做
		} else {
			// 开启网络连接
			setNetworkConn(6);
		}
	}

	public static void closeNetworkConn() {
		if (checkNet()) {
			// 关闭网络连接
			setNetworkConn(1);
		}
	}

	/**
	 * 输入模式，设置联网（1/2/4/6）
	 * <p>
	 * mode = 1, set airplane
	 * <p>
	 * mode = 2, set wifi only
	 * <p>
	 * mode = 4, set data only
	 * <p>
	 * mode = 6, set wifi and data
	 * 
	 * @param mode
	 */
	public static void setNetworkConn(int mode) {
		switch (mode) {
		// 飞行模式
		case 1:
			driver.setNetworkConnection(new NetworkConnectionSetting(true,
					false, false));
			break;
		// 仅wifi
		case 2:
			driver.setNetworkConnection(new NetworkConnectionSetting(false,
					true, false));
			if (isExistenceByName("确定")) {
				clickByName("确定");
			}
			break;
		// 仅数据连接（没有SIM卡页为true）至少手机接收到的信号
		case 4:
			driver.setNetworkConnection(new NetworkConnectionSetting(false,
					false, true));
			break;
		// WiFi和数据连接（建议使用这个）
		case 6:
			driver.setNetworkConnection(new NetworkConnectionSetting(false,
					true, true));
			if (isExistenceByName("确定")) {
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
		// 获取联网状态注意：没卡时，data也为true，只有在飞行模式下，才显示false
		if (text.contains("Wifi: true") && text.contains("Data: true")) {
			System.out.println("checkNet: true");
			return true;
		} else {
			System.out.println("checkNet: false");
			return false;
		}

	}
  
}
