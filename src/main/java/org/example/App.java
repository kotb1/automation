package org.example;

import Pages.Creation;
import Pages.WorkList;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws XPathExpressionException, ParserConfigurationException, IOException, TransformerException, SAXException {
        //System.setProperty("webdriver.chrome.driver", "D:\\Eclipse\\Eclipse Workspace\\kotb\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://10.19.35.91:8003/WorkOrder/faces/security/pages/Login.jsf");
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.id("pt:username::content")).sendKeys("root");
        driver.findElement(By.id("pt:password::content")).sendKeys("root1234");
        driver.findElement(By.className("xfv")).click();
        driver.findElement(By.id("pt:homeMenu:4:CfgGovernrateGtd")).click();
        WorkList x = new WorkList(driver);
        x.navigate_to_mytasks();
        x.Search_my_tasks("9948967");
        x.Start_order();
        WebElement iframe = driver.findElement(By.xpath("//iframe[@title='BPM_INTEGRATION_API']"));
        String iframeUrl = iframe.getAttribute("src");
        System.out.println("Iframe source URL: " + iframeUrl);
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", iframeUrl);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        List<WebElement> elements = driver.findElements(By.xpath("//*"));
        WebElement dropdown =driver.findElement(By.id("ProblemSolved"));
        Select select = new Select(dropdown);
        select.selectByVisibleText("Yes");
        WebElement dropdown2 = driver.findElement(By.id("change_passive_data_procattribs"));
        Select select2 = new Select(dropdown2);
        select2.selectByVisibleText("NO");
        driver.findElement(By.id("Submit")).click();

        driver.get(iframeUrl);

        int maxAttempts = 5;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxAttempts) {
            try {
                WebElement body = driver.findElement(By.tagName("body"));
                String bodyText = body.getText().trim();

                if (bodyText.equalsIgnoreCase("Please Wait , There is work in Progress")) {
                    System.out.println("Still in waiting page. Retrying...");
                    Thread.sleep(2000); // Wait 2 seconds before retry
                    attempt++;
                } else {
                    System.out.println("Page loaded successfully.");
                    success = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error reading body. Retrying...");
                attempt++;
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            }
        }

        if (!success) {
            System.out.println("Still on waiting page. Navigating to Next Task...");
            driver.get(iframeUrl);
        }

        // driver.get(iframeUrl);
        // Print tag name and any visible text
        /*for (WebElement element : elements) {
            String tag = element.getTagName();
            String text = element.getText().trim();

            if (!text.isEmpty()) {
                System.out.println("Tag: " + tag + " | Text: " + text);
            }
        }*/


        //System.out.println(driver.getPageSource().contains("iframe"));
       /* List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        for (int i = 0; i < iframes.size(); i++) {
            System.out.println("Iframe " + i + " ID: " + iframes.get(i).getAttribute("id"));
            System.out.println("Iframe " + i + " Name: " + iframes.get(i).getAttribute("name"));
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("pt:mr:0:pt:lv2:0:if1")));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Now you're inside the iframe context
        List<WebElement> selects = driver.findElements(By.tagName("select"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        for (WebElement select : selects) {
            System.out.println("Select name/id: " + select.getAttribute("name") + " | Text: " + select.getText());
        }
        System.out.println(selects.size());

      //WebElement dropdown = driver.findElement(By.xpath("//select[@id='ProblemSolved' and not(@hidden)]"));
        WebElement dropdown =driver.findElement(By.id("ProblemSolved"));

        Select select = new Select(dropdown);
        List<WebElement> options = select.getOptions();

        for (WebElement option : options) {
            String text = option.getText();
            String id = option.getAttribute("id");
            String name = option.getAttribute("name");

            System.out.println("Option Text: " + text + " | ID: " + id + " | Name: " + name);
        }
        //Select select = new Select(dropdown);
        select.selectByVisibleText("Yes");

        select.selectByVisibleText("Yes");
       /* driver.findElement(By.className("xfv")).click();
        WorkList x = new WorkList(driver);
        driver.findElement(By.id("pt:homeMenu:4:CfgGovernrateGtd")).click();
        //x.navigate_to_mytasks();
        //x.Search_my_tasks("9948953");
        //x.Accept_Assigned_order("9948953");
       x.Search_zone_tasks("9949000");
       x.Accept_Scheduled_order("9949000");
       // x.Search_zone_tasks("9948618");
        x.navigate_to_mytasks();
        x.Search_my_tasks("9949000");
        x.Start_order();
        //x.Accept_Assigned_order("9948618");
        //driver.findElement(By.linkText("Start")).click();
       // WebElement iframe = driver.findElement(By.id("pt:mr:0:pt:lv2:1:if1"));pt:mr:0:pt:lv2:2:if1
        //WebElement iframe = driver.findElement(By.className("xsz xt4 p_AFFlow"));
        WebElement iframe = driver.findElement(By.xpath("//iframe[@title='BPM_I0NTEGRATION_API']"));
        String iframeUrl = iframe.getAttribute("src");
        System.out.println("Iframe source URL: " + iframeUrl);
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", iframeUrl);
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
       // WebElement dropdown = driver.findElement(By.xpath("//select[@id='ProblemSolved' and not(@hidden)]"));
        //WebElement dropdown = driver.findElement(By.id("ProblemSolved"));
        //WebElement dropdown = driver.findElement(By.cssSelector("select[name='ProblemSolved']:not([hidden])"));
        //WebElement dropdown = driver.findElement(By.xpath("//select[@id='ProblemSolved' and not(@hidden)]"));
        //  WebElement dropdown = driver.findElement(
        //        By.xpath("//select[@lookupid='8483' and not(@hidden)]")
        //);
       // Select select = new Select(dropdown);
        //select.selectByVisibleText("Yes");
        //WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
            //    .until(ExpectedConditions.presenceOfElementLocated(By.id("ProblemSolved")));
        //System.out.println("Found: " + el.isDisplayed());
        System.out.println(driver.getPageSource().contains("iframe"));
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        System.out.println("Total iframes on the page: " + iframes.size());

        for (int i = 0; i < iframes.size(); i++) {
            System.out.println("Iframe " + i + " ID: " + iframes.get(i).getAttribute("id"));
            System.out.println("Iframe " + i + " Name: " + iframes.get(i).getAttribute("name"));
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ProcessAttributesDivTab")));
        List<WebElement> dropdowns = driver.findElements(By.id("ProblemSolved"));

        for (WebElement el : dropdowns) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
            if (el.isDisplayed()) {
                System.out.println("la2eto ebn el sarma");
                Select dropdown = new Select(el);
                dropdown.selectByValue("yes");
                break;
            }
        }


        WebElement dropdown2 = driver.findElement(By.id("change_passive_data_procattribs"));
        Select select2 = new Select(dropdown2);
        select2.selectByVisibleText("NO");
        driver.findElement(By.id("Submit")).click();*/
    }
}
