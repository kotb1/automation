package Pages;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OpenedWorkOrderPage extends PageBase{
    public OpenedWorkOrderPage(WebDriver driver) {
        super(driver);
    }

    //Search criteria
    // @FindBy(linkText = "Opened Work Orders")
    //WebElement OpenedWorkOrderLink;
    String WOTXT_id="pt:mr:0:pt:it5::content";

    WebElement OrgTxtbox=driver.findElement(By.name("pt:mr:0:pt:it2"));
    @FindBy(linkText = "Search")
    WebElement SearchButton;
    String sortByButtonLinkTxt="Sort By";
    String SortByDatexpath="/html/body/div[1]/form/div[2]/div[2]/div/div/div/table/tbody/tr/td/table/tbody/tr[2]/td/div/table/tbody/tr[1]/td[2]";
    String SortDecsDatexpath="/html/body/div[1]/form/div[2]/div[2]/div[2]/div/div/table/tbody/tr/td/table/tbody/tr[2]/td/div/table/tbody/tr[2]";
    String RequestTypeDropDownID="pt:mr:0:pt:smc1::drop";
    String RequestTypesList="x1b8";
    String OperationsLinkTxt="operations";
    String BulkCloseButtonxpath="//*[@id='pt:mr:0:pt:b345456554765']/td[2]";
    String BulkCloseReqtypeList="pt:mr:0:pt:soc156576::content";
    String BulkCloseSearchButton="pt:mr:0:pt:b77";
    String BulkcloseTaskTypeList="/html/body/div[1]/form/div[2]/div[2]/div[1]/table/tbody/tr/td/div/div/table/tbody/tr[2]/td[2]/div/div/div[1]/div/div/div/div[2]/div/span/div/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/select";
    String reqtype_xpath="//label[normalize-space()='Request Type']/parent::td/following-sibling::td//span\n";
    public void SearchforWorkOrder(String Work_order_id)
    {
        //   OpenedWorkOrderLink.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement workorder = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'it5::content')]"))
        );

        workorder.clear();
        workorder.sendKeys(Work_order_id);

        SearchButton.click();
    }
    public void SortingForWO()
    {
        SearchButton.click();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        WebElement sortingByIcon=driver.findElement(By.linkText(sortByButtonLinkTxt));
        sortingByIcon.click();
        WebElement sortingBy_InitiateDate=driver.findElement(By.xpath(SortByDatexpath));
        sortingBy_InitiateDate.click();
        WebElement sortDesc= driver.findElement(By.xpath(SortDecsDatexpath));
        sortDesc.click();
    }

    public void filterwithReqType(String requestType)
    {

        // Click the dropdown to show options
        WebElement requestTypeDropdown = driver.findElement(By.id(RequestTypeDropDownID));
        requestTypeDropdown.click();

        // Wait for the dropdown to expand and options to become visible
        // Thread.sleep(1000); // replace with explicit wait for stability

        // Get all checkbox labels (assuming checkboxes are rendered with <label> tags)
        List<WebElement> requestTypes = driver.findElements(By.className(RequestTypesList));
        for (WebElement element : requestTypes) {
            String text = element.getText().toString();
            System.out.println("Found option: " + text);

            if (text.equalsIgnoreCase(requestType)) {
                System.out.println("Match found: " + text + " — selecting it.");
                element.click(); // Click on the matched element
                break;
            }
        }
        SearchButton.click();
        // boolean found = false;
    }

    public void bulkclose(String Req, String CloseResone ,String ORG, String WorkORderID)
    {
        WebElement operationsbutton= driver.findElement(By.linkText(OperationsLinkTxt));
        operationsbutton.click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bulkclose = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(BulkCloseButtonxpath)));

        bulkclose.click();
        WebElement dropdownReqType = wait.until(ExpectedConditions.elementToBeClickable(By.id(BulkCloseReqtypeList)));
        Select selectReqtype = new Select(dropdownReqType);
        selectReqtype.selectByVisibleText(Req);
        WebElement searchButton = driver.findElement(By.id(BulkCloseSearchButton));
        searchButton.click();
        WebElement dropDownTasktype = driver.findElement(By.xpath(BulkcloseTaskTypeList));
        System.out.println(dropDownTasktype.getText().toString());
        Select selectTaskType = new Select(dropDownTasktype);
        selectTaskType.selectByVisibleText("Team Leader");
        searchButton.click();
        // WebElement Orgtxt =driver.findElement(By.id("pt:mr:2:pt:it72233::content"));
        //Orgtxt.sendKeys(ORG);
        List<WebElement> ORDERs= driver.findElements(By.className("x14o p_AFSelected"));
        for(WebElement ordersId:ORDERs )
        {
            System.out.println(ordersId.getText().toString());
        }




    }
    public String returnRequestType()
    {
        WebElement ReqType = driver.findElement(By.xpath(reqtype_xpath));
        return ReqType.getAttribute("title");
    }

}


