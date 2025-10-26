package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ClosedWO extends PageBase{
    public ClosedWO(WebDriver driver) {
        super(driver);
    }

    String close_reason_xpath="//*[contains(@id, 'pt:soc12::content')]";
    String closeWO_TabXpath="//*[contains(@id, 'pt:tab2::disAcr')]";
    String closed_WO_xpath="//*[contains(@id, 'l1::text')]";
    String SearchClosedWO="//*[contains(@id, 'it19::content')]";
    String search_link="Search";
    public void navigateToClosedWO()
    {
        WebElement close_tab = driver.findElement(By.xpath(closeWO_TabXpath));
        close_tab.click();
    }
    public void navigateTOclosedWODetails()
    {
        WebElement closedWO= driver.findElement(By.xpath(closed_WO_xpath));
        closedWO.click();
    }

    public String returnClose_reason(){
        WebElement closereason= driver.findElement(By.xpath(close_reason_xpath));

      return  closereason.getText().toString();
    }

    public void SeacrhWithClosedWO(String WO_id)
    {
        //   OpenedWorkOrderLink.click();
       // WebElement searchButton=;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement workorder = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(SearchClosedWO))
        );

        workorder.clear();
        workorder.sendKeys(WO_id);

        driver.findElement(By.linkText(search_link)).click();
    }
}
