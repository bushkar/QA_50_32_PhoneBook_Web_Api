package ui_tests;

import dto.Contact;
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
import static utils.ContactFactory.positiveContact;
import static utils.PropertiesReader.getProperty;

@Listeners(TestNGListener.class)

public class EditContactTests extends AppManager {
    SoftAssert softAssert = new SoftAssert();
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
        contactsPage = clickButtonHeader(HeaderMenuItem.CONTACTS);
    }

    @Test
    public void editContactPositiveTest() {
        int contactIndex = 2;
        softAssert.assertTrue(contactsPage.openContact(contactIndex), "failed to open contact before edit");
        contactsPage.editContact();
        Contact contact = positiveContact();
        contactsPage.typeContactForm(contact);
        contactsPage.scrollToLastContact();
        softAssert.assertTrue(contactsPage.openContact(contactIndex), "failed to open contact after edit");
        String text = contactsPage.getTextInContact();
        softAssert.assertTrue(text.contains(contact.getName()), "validate Name in DetailCard");
        softAssert.assertTrue(text.contains(contact.getLastName()), "validate LastName in DetailCard");
        softAssert.assertTrue(text.contains(contact.getPhone()), "validate Phone in DetailCard");
        softAssert.assertTrue(text.contains(contact.getEmail()), "validate Email in DetailCard");
        softAssert.assertTrue(text.contains(contact.getAddress()), "validate Address in DetailCard");
        softAssert.assertTrue(text.contains(contact.getDescription()), "validate Description in DetailCard");
        softAssert.assertAll();
    }
}
