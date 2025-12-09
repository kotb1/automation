package BPM;

import Pages.PageBase;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class MSANLasedProblem extends PageBase {
    public MSANLasedProblem(WebDriver driver) {
        super(driver);
    }

    String problemstatus_id="CLOSE_CODE_CLODO";
    String CloseReason_id="Close_Reason2_CLODO";
    String Submit_id_xpath="//*[@id=\"SUBMIT\"]";
    String SubmitCallTeamleader_xpath="//*[@id=\"Submit\"]";
    String BusinessroleError_xpath="//*[@id=\"alertDiv\"]/p";
    String Call_enterprise_team_xpath="//*[@id=\"lb4786\"]";

    public void FMEsetCloseCode_Reason(String closename, String closereason)
    {
        Select ProblemdropDownList= new Select(driver.findElement(By.id(problemstatus_id)));
        ProblemdropDownList.selectByValue(closename);
        Select closeReasonDropdown = new Select(driver.findElement(By.id(CloseReason_id)));

        closeReasonDropdown.selectByVisibleText(closereason);
    }
    public void  SubmitForm()
    {
        driver.findElement(By.xpath(Submit_id_xpath)).click();

    }
    public String getBusinessRule()
    {
        WebElement error = driver.findElement(By.xpath(BusinessroleError_xpath));
        return error.getText().toString();

    }
    public void  SubmitCallTeamaForm()
    {
        driver.findElement(By.xpath(SubmitCallTeamleader_xpath)).click();

    }
    public String getEnterpriseTeamValidation()
    {
        WebElement enterprise_team_validation = driver.findElement(By.xpath(Call_enterprise_team_xpath));
        return enterprise_team_validation.getText().toString();

    }

}
