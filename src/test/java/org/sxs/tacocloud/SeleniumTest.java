package org.sxs.tacocloud;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author sxs
 * @Date 2023/8/3 0:27
 */
public class SeleniumTest {


    @Test
    void testSelenium() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        assertEquals("Web form", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        assertEquals("Received!", value);

//        driver.quit();
    }

    @Test
    void testHtmlUtil() throws IOException {
        // 初始化 WebClient
        WebClient webClient = new WebClient();
        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");
        webClient.addRequestHeader("Host", "weibo.com");
        // 获取目标网址的页面内容
        HtmlPage page = webClient.getPage("http://www.jingwei.com");
    }

    @Test
    void htmlUtilTest() throws MalformedURLException {
        WebClient webClient = new WebClient();
        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");
        webClient.addRequestHeader("Host", "weibo.com");

        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX);

        driver.getWebClient().addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");
        driver.getWebClient().addRequestHeader("Host", "weibo.com");
        driver.getWebClient().addCookie("WBPSESS=BP4XMQoD7Z31Vf3tBPaOoW0zr9JxZy8EBo9TkppCFIz9NgMiSg9YPiQ91Xp838mF8xGaHNZej3FI5Y9858AGfdCDMxe-ZjklLWVvtj6YmzLtURf_XB18mv8BxO8Z-B5Ijaf5nuRfsoBeQ9ZBF0wxfHNOh5aa_Q_QKBHMKZtDVzM=; path=/; max-age=86400; expires=Thu, 03 Aug 2023 18:06:12 GMT; secure; httponly",
                new URL("https://weibo.com/newlogin?url=https://weibo.com/"),
                "https://weibo.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://weibo.com/newlogin?url=https://weibo.com/");
        System.out.println(driver.getCurrentUrl());

        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies);
        String title = driver.getTitle();
        System.out.println(title);
    }

    @Test
    void testXinlang() {
        WebDriver driver = new EdgeDriver();
        driver.get("http://www.weibo.com/newlogin");
        driver.manage().addCookie(new Cookie("SUBP", "0033WrSXqPxfM72-Ws9jqgMF55529P9D9WhfEhY6qsllDXN9O3xDCZHL",
                "https://weibo.com/newlogin?url=https://weibo.com/"));
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies);
        driver.manage().addCookie(new Cookie("SUB", "_2AkMTlgNCf8NxqwFRmPwdymPnb4t3wwrEieKlyvKZJRMxHRl-yj9kqnwLtRB6OBYtrTCZZHGCtHasNOz0m9-EVK1FIVMm"));
        driver.manage().addCookie(new Cookie("WBPSESS", "BP4XMQoD7Z31Vf3tBPaOoW0zr9JxZy8EBo9TkppCFIz9NgMiSg9YPiQ91Xp838mF8xGaHNZej3FI5Y9858AGfd0OOVJQ7vAX1gr-NwcUmxGywBm0wPSl5hA3RaPk6lwSSC4WtN5BD2rzIQm8iK4ej7L52rTHE_jtKXz4qFMv_Fw="));
        driver.manage().addCookie(new Cookie("XSRF-TOKEN", "O2Haw_HVv5Ny6bTO39aztX2F"));
        System.out.println(cookies);
        System.out.println("---------------");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String title = driver.getTitle();
        System.out.println(title);
    }
}
