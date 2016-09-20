package com.uitest.device;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.WebElement;

public class SingletonClass {

	 private static AndroidDriver<WebElement> driver = null;
	 
	    public static AndroidDriver<WebElement> getInstance(){
	        if(driver==null){
	            synchronized(SingletonClass.class){
	                if(driver==null){
	                	driver=new AndroidDriver<WebElement>(url, capabilities);
	                }
	            }
	        }
	        return driver;
	    }
}
