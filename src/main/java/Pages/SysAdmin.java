package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SysAdmin extends  PageBase
{
    public SysAdmin(WebDriver driver) {
        super(driver);
    }
    public void navigate_to_roles()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("pt:homeMenu:3:CfgGovernrateGtd")
                )
        );
        element.click();
    }
    public void navigate_to_users()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("pt:homeMenu:2:CfgGovernrateGtd")
                )
        );
        element.click();
    }
    public void search_for_user(String username)
    {
        boolean iterate = true;
        try
        {
            while(iterate)
            {
                WebElement element = driver.findElement(By.xpath("//*[normalize-space(text())='Fetching Data...']"));
                String display = element.getCssValue("display");

                if (display.equals("none")) {
                    iterate=false;
                }
            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[normalize-space(text())='User Name']/preceding-sibling::input")
                )
        );
        element.sendKeys(username);
        driver.findElement(By.xpath("//span[normalize-space(text())='Search']")).click();
    }
    public void edit_user() throws InterruptedException {
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//img[contains(@src, '/HiveAdministration/jheadstart/images/editRow.png')]")
                )
        );
        element.click();
    }
    public ArrayList<String> get_roles_user()
    {
        ArrayList<String> roles = new ArrayList<>();
        List<WebElement> spans = driver.findElements(
                By.xpath("//table[contains(@class, 'x14q') and contains(@class, 'x15f')]//span[contains(@id, '::content')]")
        );
        System.out.println("Total spans found: " + spans.size());
        for (WebElement span : spans) {
            //System.out.println(span.getAttribute("id") + " → " + span.getText());
            roles.add(span.getText());
        }
        return roles;
    }
    public void navigate_to_roles_through_menu()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='pt:MenuITem']//a[contains(@class,'x166')]")
        ));
        menu.click();

        driver.findElement(By.xpath("//tr[contains(@id,'Items1')]//td[text()='Roles']")).click();
    }
    public boolean clickSpanParentByText(String searchText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            // Wait for and locate the parent div with dynamic ID part
            WebElement parentDiv = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@id, 'SuperRoleTreeTree::db')]")
            ));

            // Find all spans inside this div
            List<WebElement> spans = parentDiv.findElements(By.tagName("span"));

            for (WebElement span : spans) {
                String spanText = span.getText().trim();
                System.out.println(spanText);
                if (spanText.equalsIgnoreCase(searchText)) {
                    try {
                        // Find the parent <a> tag of the span

                        //WebElement parentLink = span.findElement(By.xpath("./ancestor::a[1]"));
                        String xpath = String.format("//span[normalize-space(text())='%s']/ancestor::a[1]", spanText);
                        WebElement parentLink = driver.findElement(By.xpath(xpath));

                        // Click the link
                        parentLink.click();
//                        System.out.println("Clicked parent <a> of span with text: " + spanText);
                        return true;
                    } catch (NoSuchElementException e) {
//                        System.out.println("No <a> parent found for span with text: " + spanText);
                        return false;
                    }
                }
            }

            System.out.println("No span found with text: " + searchText);
            return false;

        } catch (TimeoutException e) {
            System.out.println("Parent div not found within timeout.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void logout()
    {
        WebElement link = driver.findElement(
                By.xpath("//td[img[contains(@src, '/HiveAdministration/jheadstart/images/profile-icon.png')]]/following-sibling::td//a")
        );
        link.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement parentElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[normalize-space(text())='Log Off']/parent::tr")
        ));
        parentElement.click();
    }
    public void navigate_to_role_Modules()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space(text())='Role Modules']")
        ));
        menu.click();
    }
    public boolean navigate_to_WO_Permissions()
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[normalize-space(text())='Work Order Manager متابعه الطلبات']")
            ));
            menu.click();
            Thread.sleep(3000);
            return true;
        }catch(Exception e1)
        {
            return false;
        }
    }
    public void add_remove_permissions_button()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@alt='Add or Remove Permissions']/parent::a")
        ));
        menu.click();
    }
    public void remove_permission_by_name(String permission) throws InterruptedException {
        Thread.sleep(50000);
        //driver.switchTo().frame(driver.findElement(By.className("AFMaskingFrame")));
        WebElement checkbox = driver.findElement(By.xpath(
                //"(//span[normalize-space(text())='" + permission + "']/ancestor::tr//input[@type='checkbox'])[1]"
        "//span[normalize-space(text())='View Work Order']/ancestor::tr[1]//input[@type='checkbox']"
        ));

// Check if it's already checked
        if (checkbox.isSelected()) {
            // If checked, uncheck it
            checkbox.click();
            System.out.println(permission + " checkbox was checked — now unchecked.");
        } else {
            // If unchecked, keep it as is
            System.out.println(permission + " checkbox was already unchecked — no action taken.");
        }
    }

}
