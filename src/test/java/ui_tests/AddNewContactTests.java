package ui_tests;

import data_providers.ContactDataProvider;
import data_providers.ContactWrongPhoneDataProvider;
import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.HeaderMenuItem;

import java.lang.reflect.Method;

import static pages.BasePage.clickButtonHeader;
import static utils.ContactFactory.*;
import static utils.PropertiesReader.getProperty;


public class AddNewContactTests extends AppManager {
    SoftAssert softAssert = new SoftAssert();
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;
    int countOfContacts;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
//        loginPage.typeLoginRegistrationForm("family@mail.ru", "Family123!");
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
        contactsPage = new ContactsPage(getDriver());
        countOfContacts = contactsPage.getCountOfContacts();
        addPage = clickButtonHeader(HeaderMenuItem.ADD);
    }

    @Test
    public void addNewContactPositiveTest() {
        addPage.typeContactForm(positiveContact());
        int countOfContactsAfterAdd = contactsPage.getCountOfContacts();
        Assert.assertEquals(countOfContactsAfterAdd, countOfContacts + 1);
    }

    @Test(dataProvider = "dataProviderFromFile",
            dataProviderClass = ContactDataProvider.class)
    public void addNewContactPositiveTest_WithDataProvider(Contact contact) {
        addPage.typeContactForm(contact);
        int countOfContactsAfterAdd = contactsPage.getCountOfContacts();
        Assert.assertEquals(countOfContactsAfterAdd, countOfContacts + 1);
    }

    @Test
    public void addNewContactPositiveTest_ClickLastContact() {
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
//        contactsPage.clickLastContact();
        Assert.assertTrue(contactsPage.isContactPresent(contact));
    }

    @Test
    public void addNewContactPositiveTest_ScrollLastContact() {
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        contactsPage.scrollToLastContact();
        contactsPage.clickLastContact();
        String text = contactsPage.getTextInContact();
        softAssert.assertTrue(text.contains(contact.getName()),
                "validate Name in DetailCard");
        softAssert.assertTrue(text.contains(contact.getEmail()),
                "validate Email in DetailCard");
        softAssert.assertTrue(text.contains(contact.getPhone()),
                "validate Phone in DetailCard");
        softAssert.assertAll();
    }

    @Test(dataProvider = "dataProviderFromFile",
            dataProviderClass = ContactWrongPhoneDataProvider.class)
    public void addNewContactNegativeTest_WrongPhones_WithDataProvider(Contact contact) {
        addPage.typeContactForm(contact);
        Assert.assertTrue(addPage.closeAlertReturnText().contains("Phone not valid"));
        tearDown();
    }

    @Test(dataProvider = "dataProviderFromFile_Wrong_EmptyField",
            dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_EmptyFieldWithDP(Contact contact) {
        addPage.typeContactForm(contact);
        Assert.assertTrue(addPage.isButtonSaveDisabled());
    }

    @Test(dataProvider = "dataProviderFromFile_WrongEmail",
            dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_WrongEmail_WithDP(Method method, Contact contact) {
        logger.info("start test " + method.getName() + " with contact " + contact);
        addPage.typeContactForm(contact);
        Assert.assertTrue(addPage.closeAlertReturnText().contains("Email not valid"));
    }
}
