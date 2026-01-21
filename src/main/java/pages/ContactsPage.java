package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ContactsPage extends BasePage{
    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[@href='/add']")
    WebElement btnAdd;
    @FindBy(xpath = "//a[@href='/contacts']")
    WebElement btnContacts;

    public boolean isBtnAddDisplayed() {
        return isElementDisplayed(btnAdd);
    }

    public boolean isBtnContactsDisplayed() {
        return isElementDisplayed(btnContacts);
    }
}
