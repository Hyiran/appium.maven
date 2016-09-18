package com.appium.utilcode;

import java.util.HashMap;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 日志记录：
 * 版本     日期     修改者  更新内容
 * 1.0    2016-3-3  程文健 创建项目
 * 1.1    2016-3-8  程文健 添加函数"checkandClick"
 * 2.0    2016-3-15 程文健 添加函数"getViewbyUidesc","getViewbyUitext",
 *                        "getViewbyXathwithcontentdesc","getViewbyXathwithtext", 用于查找相关元素
 * 2.1    2016-3-17 程文健 添加函数"getXpathString",
 *                        更新"getViewbyXathwithcontentdesc","getViewbyXathwithtext"
 * 2.1.1  2016-3-18 程文健 更新函数"waitForElement"增加返回值
 * 2.2    2016-3-22 程文健 函数"clickByCoordinate","clickScreenWithPercentage"移至driverUtil类库中
 * 2.3    2016-4-21 程文健 新增：函数findText()， 用于查找页面是否存在文本，返回该元素
 * 
 */

/**
 * 用于页面元素管理，等待页面元素，判断页面元素是否存在、是否被显示等。
 * 
 * @author 陈柏辉
 * @version 1.0 2016-6-16
 */

public class elementManager {
	/**
	 * 判断页面是否含有某个元素
	 * 
	 * @param driver
	 *            WebElement
	 * @param selector
	 *            The element you want to search for.( Type:By,
	 *            example:By.id("ElementId") )
	 * @return boolean
	 */
	public static boolean doesWebElementExist(WebDriver driver, By selector) {
		try {
			driver.findElement(selector);
			return true;
		} catch (NoSuchElementException e) { // 没找到该元素，返回 false
			return false;
		}
	}

	/**
	 * 判断页面是否含有某元素
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param selector
	 *            The element you want to search for.( Type:By,
	 *            example:By.id("ElementId") )
	 * @return boolean
	 */
	public static boolean doesWebElementExist(AndroidDriver driver, By selector) {
		try {
			driver.findElement(selector);
			return true;
		} catch (NoSuchElementException e) { // 没找到该元素，返回 false
			return false;
		}
	}

	/**
	 * 根据UIautomator底层方法得到对应desc的view，使用时请确保此元素存在
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param contentDesc
	 *            view的内容(content-desc)
	 * @return View
	 */
	public static WebElement getViewbyUidesc(AndroidDriver driver,
			String contentDesc) {
		return driver
				.findElementByAndroidUIAutomator("new UiSelector().descriptionContains(\""
						+ contentDesc + "\")");
	}

	/**
	 * 根据UIautomator底层方法得到对应text的view，使用时请确保此元素存在
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param textContent
	 *            view的内容(text)
	 * @return View
	 */
	public static WebElement getViewbyUitext(AndroidDriver driver,
			String textContent) {
		return driver
				.findElementByAndroidUIAutomator("new UiSelector().textContains(\""
						+ textContent + "\")");
	}

	/**
	 * 根据类名(className)、属性(attribute)、属性数据(attributeData)，获取Xpath字符串
	 * 
	 * @param className
	 *            元素类名
	 * @param attribute
	 *            view的属性，如：content-desc/text/index等
	 * @param attributeData
	 *            view的属性对应的数据
	 * @return String
	 */
	public static String getXpathString(String className, String attribute,
			String attributeData) {
		return "//" + className + "[contains(@" + attribute + ",'"
				+ attributeData + "')]";
	}

	/**
	 * xpath根据content-desc查找元素，使用时请确保此元素存在
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param className
	 *            view的类型/class，如："android.widget.TextView"
	 * @param contentDesc
	 *            view的内容(content-desc)
	 * @return View
	 */
	public static WebElement getViewbyXpathwithcontentdesc(
			AndroidDriver driver, String className, String contentDesc) {
		return driver.findElementByXPath(getXpathString(className,
				"content-desc", contentDesc));
	}

	/**
	 * xpath根据text查找元素，使用时请确保此元素存在
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param className
	 *            view的类型/class，如："android.widget.TextView"
	 * @param textContent
	 *            view的内容(text)
	 * @return View
	 */
	public static WebElement getViewbyXpathwithtext(AndroidDriver driver,
			String className, String textContent) {
		return driver.findElementByXPath(getXpathString(className, "text",
				textContent));
	}

	/**
	 * 判断页面中某元素是否被显示出来，注意如果元素不存在也会返回 false
	 * 
	 * @param driver
	 *            WebDriver
	 * @param selector
	 *            The element you want to search for.( Type:By,
	 *            example:By.id("ElementId") )
	 * @return boolean
	 */
	public static boolean doesElementDisplay(WebDriver driver, By selector) {
		try {
			return driver.findElement(selector).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * 判断页面中某元素是否被显示出来，注意如果元素不存在也会返回 false
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param selector
	 *            The element you want to search for.( Type:By,
	 *            example:By.id("ElementId") )
	 * @return boolean
	 */
	public static boolean doesElementDisplay(AndroidDriver driver, By selector) {
		try {
			return driver.findElement(selector).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * 设置元素等待超时时间
	 * 
	 * @param driver
	 *            WebDriver
	 * @param by
	 *            The element you wait for.( Type:By, example:By.id("ElementId")
	 *            )
	 * @param timeOut
	 *            time out, timeunit:seconds
	 * @return WebElement
	 */
	public static WebElement waitForElement(WebDriver driver, final By by,
			int timeOut) {
		try {
			return new WebDriverWait(driver, timeOut)
					.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver wd) {
							return wd.findElement(by);
						}
					});
		} catch (Exception e) {
			System.out.println("wart for \" " + by + " \" error!");
			return null;
		}
	}

	/**
	 * 设置元素等待超时时间
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param by
	 *            The element you wait for.( Type:By, example:By.id("ElementId")
	 *            )
	 * @param timeOut
	 *            time out, timeunit:seconds
	 * @return WebElement
	 */
	public static WebElement waitForElement(AndroidDriver driver, final By by,
			int timeOut) {
		try {
			return new WebDriverWait(driver, timeOut)
					.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver wd) {
							return wd.findElement(by);
						}
					});
		} catch (Exception e) {
			System.out.println("wart for \" " + by + " \" error!");
			return null;
		}
	}

	/**
	 * 判断文本是否出现
	 * 
	 * @param driver
	 *            WebDriver
	 * @param text
	 *            需要查找的文本
	 * @param timeOut
	 *            查找超时
	 * @return WebElement
	 */
	public static WebElement findText(WebDriver driver, String text, int timeOut) {
		By xpt = By.xpath("//*[contains(.,'" + text + "')]");
		return waitForElement(driver, xpt, timeOut);
	}

	/**
	 * 判断文本是否出现
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param text
	 *            需要查找的文本
	 * @param timeOut
	 *            查找超时
	 * @return WebElement
	 */
	public static WebElement findText(AndroidDriver driver, String text,
			int timeOut) {
		By xpt = By.xpath("//*[contains(.,'" + text + "')]");
		return waitForElement(driver, xpt, timeOut);
	}

	/**
	 * 检查元素是否存在，若不存在则返回 false，若存在则点击该元素并返回 true
	 * 
	 * @param driver
	 *            AndroidDriver
	 * @param element
	 *            The element you want to click.( Type:By,
	 *            example:By.id("ElementId") )
	 * @return boolean
	 */
	public static boolean checkandClick(AndroidDriver driver, By element) {
		if (doesWebElementExist(driver, element)) {
			driver.findElement(element).click();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查元素是否存在，若不存在则返回 false，若存在则点击该元素并返回 true
	 * 
	 * @param driver
	 *            WebDriver
	 * @param element
	 *            The element you want to click.( Type:By,
	 *            example:By.id("ElementId") )
	 * @return boolean
	 */
	public static boolean checkandClick(WebDriver driver, By element) {
		if (doesWebElementExist(driver, element)) {
			driver.findElement(element).click();
			return true;
		} else {
			return false;
		}
	}

	// /////////////////////////////////////////////////////////////

	/**
	 * 判断某个元素是否存在,true存在；false不存在。
	 */
	public static boolean isExistenceById(AndroidDriver<WebElement> driver,
			String id) {
		System.out.println("isExistenceById: " + id);
		try {
			driver.findElementById(id);
			System.out.println("isExistenceById: true");
			return true;
		} catch (Exception ex) {
			// 找不到元素
		}
		System.out.println("isExistenceById: false");
		return false;
	}

	/**
	 * 在指定时间内，判断元素是否存在，true存在；false不存在。
	 */
	public static boolean isExistenceById(AndroidDriver<WebElement> driver,
			String id, int timeout) {
		try {
			driver.findElementById(id);
			new WebDriverWait(driver, timeout).until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.id(id)));
			System.out.println("isExistenceById: true");
			return true;
		} catch (Exception ex) {
			// 找不到元素
		}
		System.out.println("isExistenceById: false");
		return false;
	}

	/**
	 * 判断某个元素是否存在,true存在；false不存在。
	 */
	public static boolean isExistenceByName(AndroidDriver<WebElement> driver,
			String name) {
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
	 * 实现按钮长按，传参为name。（该类方法不建议使用，容易报错）
	 * 
	 * @param name
	 */
	public static void clickLongByName(AndroidDriver<WebElement> driver,
			String name) {
		try {
			TouchAction ta = new TouchAction(driver);
			WebElement el = driver.findElementByName("name");
			// WebElement el = driver.findElementById(packageName + ":id/" +
			// name);
			ta.longPress(el).waitAction(5000).release().perform();
		} catch (Exception e) {
			System.out.println("clickLongTime error.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 实现按钮长按，传参为WebElement（该类方法不建议使用，容易报错）
	 * 
	 * @param el
	 */
	public static void clickLongByWebElement(AndroidDriver<WebElement> driver,
			WebElement el) {
		try {
			TouchAction ta = new TouchAction(driver);
			ta.longPress(el).waitAction(2000).release().perform();
		} catch (Exception e) {
			System.out.println("clickLongTime error.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 调用JS脚本实现长按，参数是WebElement元素
	 * 
	 * @param element
	 */
	public static void clickLongByElementUseJs(
			AndroidDriver<WebElement> driver, WebElement element) {
		System.out.println("[start] clickLongByElementUseJs");
		JavascriptExecutor js = driver;
		HashMap<String, String> tapObject = new HashMap<String, String>();
		tapObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: longClick", tapObject);
		System.out.println("[ end ] clickLongByElementUseJs");

	}

	/**
	 * 调用JS脚本实现长按，参数是txt
	 * 
	 * @param TextViewName
	 */
	public static void clickLongByNameUseJs(AndroidDriver<WebElement> driver,
			String TextViewName) {
		System.out.println("[start] clickLongByNameUseJs " + TextViewName);
		// 这里容易出问题，查找控件经常出错
		WebElement element = getFirstTextView(TextViewName, 1);
		if (element == null) {
			System.out.println("对象为空");
			return;
		}
		JavascriptExecutor js = driver;
		HashMap<String, String> tapObject = new HashMap<String, String>();
		tapObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: longClick", tapObject);
		System.out.println("[ end ] clickLongByNameUseJs");

	}

	/**
	 * 调用JS脚本实现长按，参数是txt
	 * 
	 * @param TextView
	 *            id
	 */
	public static void clickLongByIdUseJs(AndroidDriver<WebElement> driver,
			String id) {
		System.out.println("[start] clickLongByIdUseJs");
		WebElement element = driver.findElementById(id);
		JavascriptExecutor js = driver;
		HashMap<String, String> tapObject = new HashMap<String, String>();
		tapObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: longClick", tapObject);
		System.out.println("[ end ] clickLongByIdUseJs");

	}

	/**
	 * 通过控件的id，点击控件
	 * 
	 * @param id
	 */
	public static void clickById(AndroidDriver<WebElement> driver, String id) {
		try {
			System.out.println("[start] click: " + id);
			driver.findElement(By.id(id)).click();
			System.out.println("[ end ] " + id + ".click");
		} catch (Exception ex) {
			System.out.println("Can not find " + id);
			// ex.printStackTrace();
		}
	}

	/**
	 * 通过控件的txt，点击控件
	 * 
	 * @param name
	 */
	public static void clickByName(AndroidDriver<WebElement> driver, String name) {
		try {
			System.out.println("[start] click: " + name);
			driver.findElement(By.name(name)).click();
			System.out.println("[ end ] " + name + ".click");
		} catch (Exception ex) {
			System.out.println("Can not find " + name);
			// ex.printStackTrace();
		}
	}

}
