package pages;

import dto.Contact;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

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
    @FindBy(className = "contact-item_card__2SOIM")
    List<WebElement> contactsList;
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM'][last()]")
    WebElement lastContact;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']/div")
    WebElement divListContacts;

    public boolean isContactPresent(Contact contact) {
        for (WebElement element : contactsList) {
            if (element.getText().contains(contact.getName())
                    && element.getText().contains(contact.getPhone())) {
                System.out.println(element.getText());
                return true;
            }
        }
        return false;
    }

    public void scrollToLastContact() {
        Actions actions = new Actions(driver);
//        actions.scrollToElement(lastContact).perform();
//        int deltaY = driver.findElement(By
//                        .xpath("//div[@class='contact-page_leftdiv__yhyke']/div"))
//                .getSize().getHeight();
        int deltaY = divListContacts.getSize().getHeight();
        System.out.println("Height --> " + deltaY);
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin
                .fromElement(contactsList.get(0));
        pause(3);
        actions.scrollFromOrigin(scrollOrigin, 0, deltaY).perform();
    }

    public void clickLastContact() {
        lastContact.click();
    }

    public int getCountOfContacts() {
        return contactsList.size();
    }

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
