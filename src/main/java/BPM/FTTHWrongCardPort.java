package BPM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class FTTHWrongCardPort
{
    public WebDriver driver;
    public FTTHWrongCardPort(WebDriver driver)
    {
        this.driver=driver;
    }
    String Problem_Exist_id= "ProblemSolved";
    String now_passive_cabinet_id="FTTH_CABINET_procattribs";
    String now_passive_box_id= "FTTH_BOX_procattribs";
    String change_passive_data_id="change_passive_data_procattribs";
    String change_cabinet_id="ChangePassiveCabinet_procattribs";
    String change_box_id="Change_Box_procattribs";
    String select_cabinet_id="FTTHPassiveCabinet_procattribs";
    String Submit_id="Submit";
    String Form_Label_id= "lb8685";
    String Business_Role_Validation_Error_Label_id= "lb_AlertTitle";
    String Response_get_boxes_label_id="FTTHSuccess_procattribs";
    String change_retry_action="Change_and_Retry_procattribs";
    String select_box_id="FTTHBox_procattribs";
    public List<String> get_all_boxes()
    {
        WebElement dropdown = driver.findElement(By.id(select_box_id));
        Select select = new Select(dropdown);
        List<String> optionTexts = new ArrayList<>();
        for (WebElement option : select.getOptions()) {
            optionTexts.add(option.getText());
        }
        return optionTexts;
    }
    public List<String> get_all_Cabinets()
    {
        WebElement dropdown = driver.findElement(By.id(select_cabinet_id));
        Select select = new Select(dropdown);
        List<String> optionTexts = new ArrayList<>();
        for (WebElement option : select.getOptions()) {
            optionTexts.add(option.getText());
        }
        return optionTexts;
    }
    public void retry_action()
    {
        Select select = new Select(driver.findElement(By.id(change_retry_action)));
        select.selectByValue("retry");
    }
    public void change_cabinet_action()
    {
        Select select = new Select(driver.findElement(By.id(change_retry_action)));
        select.selectByValue("changeCabinet");
    }
    public void select_box(String box_number)
    {
        Select select = new Select(driver.findElement(By.id(select_box_id)));
        select.selectByValue(box_number);
    }
    public void select_cabinet(String Cabinet_name)
    {
        Select select = new Select(driver.findElement(By.id(select_cabinet_id)));
        select.selectByValue(Cabinet_name);
    }
    public void select_yes_change_box()
    {
        Select select = new Select(driver.findElement(By.id(change_box_id)));
        select.selectByValue("yes");
    }
    public void select_no_change_box()
    {
        Select select = new Select(driver.findElement(By.id(change_box_id)));
        select.selectByValue("no");
    }
    public void select_yes_change_cabinet()
    {
        Select select = new Select(driver.findElement(By.id(change_cabinet_id)));
        select.selectByValue("yes");
    }
    public void select_no_change_cabinet()
    {
        Select select = new Select(driver.findElement(By.id(change_cabinet_id)));
        select.selectByValue("no");
    }
    public void select_yes_problem_Exist()
    {
        Select select = new Select(driver.findElement(By.id(Problem_Exist_id)));
        select.selectByValue("yes");
    }
    public void select_no_problem_Exist()
    {
        Select select = new Select(driver.findElement(By.id(Problem_Exist_id)));
        select.selectByValue("no");
    }
    public void select_no_change_passive_data()
    {
        Select select = new Select(driver.findElement(By.id(change_passive_data_id)));
        select.selectByValue("NO");
    }
    public void select_yes_change_passive_data()
    {
        Select select = new Select(driver.findElement(By.id(change_passive_data_id)));
        select.selectByValue("YES");
    }
    public void submit_form()
    {
        driver.findElement(By.id(Submit_id)).click();
    }
    public String get_form_label()
    {
        WebElement label = driver.findElement(By.id(Form_Label_id));
        String labelText = label.getText();
        return labelText;
    }
    public String get_validation_error()
    {
        WebElement label = driver.findElement(By.id(Business_Role_Validation_Error_Label_id));
        String labelText = label.getText();
        return labelText;
    }
    public String get_now_passive_Cabinet()
    {
        WebElement input = driver.findElement(By.id(now_passive_cabinet_id));
        String value = input.getAttribute("value");
        return value;
    }
    public String get_now_passive_box()
    {
        WebElement input = driver.findElement(By.id(now_passive_box_id));
        String value = input.getAttribute("value");
        return value;
    }
    public String get_response_get_boxes()
    {
        WebElement input = driver.findElement(By.id(Response_get_boxes_label_id));
        String value = input.getAttribute("value");
        return value;
    }
}
