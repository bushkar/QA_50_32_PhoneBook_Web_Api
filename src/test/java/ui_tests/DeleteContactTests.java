package ui_tests;

import manager.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static pages.BasePage.clickButtonHeader;
import static utils.PropertiesReader.getProperty;

@Listeners(TestNGListener.class)

public class DeleteContactTests extends AppManager {
    SoftAssert softAssert = new SoftAssert();
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    int countOfContacts;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
//        contactsPage = clickButtonHeader(HeaderMenuItem.CONTACTS);
        contactsPage = new ContactsPage(getDriver());
        countOfContacts = contactsPage.getCountOfContacts();
    }

    @Test
    public void deleteContactPositiveTest_DeleteFirstContact() {
        softAssert.assertTrue(contactsPage.openContact(0), "failed to open contact");
        contactsPage.deleteContact();
        contactsPage.scrollToLastContact();
        int countOfContactsAfterDelete = contactsPage.getCountOfContacts();
        softAssert.assertEquals(countOfContactsAfterDelete, countOfContacts - 1, "сhecking contact deletion");
        softAssert.assertAll();
    }
}
