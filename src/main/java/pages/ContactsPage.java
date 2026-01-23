package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ContactsPage extends BasePage {
    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

//    @FindBy(xpath = "//a[@href='/add']")
    @FindBy(xpath = "//*[text()='ADD']")
    WebElement btnAdd;
    @FindBy(xpath = "//a[@href='/contacts']")
    WebElement btnContacts;
    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;
    @FindBy(xpath = "//h1[text()=' No Contacts here!']")
    WebElement contactPageMessage;

//    public boolean isBtnAddDisplayed() {
//        return isElementDisplayed(btnAdd);
//    }
    public boolean isTextInBtnAddPresent(String text) {
        return isTextInElementPresent(btnAdd, text);
    }

//    public boolean isBtnContactsDisplayed() {
//        return isElementDisplayed(btnContacts);
//    }
    public boolean isTextInBtnContactsPresent(String text) {
        return isTextInElementPresent(btnContacts, text);
    }

    public boolean isTextInBtnSignOutPresent(String text) {
        return isTextInElementPresent(btnSignOut, text);
    }

    public boolean isTextInContactPageMessage(String text) {
        return isTextInElementPresent(contactPageMessage, text);
    }
}
