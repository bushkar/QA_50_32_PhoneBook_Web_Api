package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.RetryAnalyser;
import utils.TestNGListener;

import static utils.PropertiesReader.*;
@Listeners(TestNGListener.class)

public class LoginTests extends AppManager {

    @Test(retryAnalyzer = RetryAnalyser.class)
    public void loginPositiveTest() {
//        System.out.println("first test");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
//        loginPage.typeLoginRegistrationForm("family@mail.ru", "Family123!");
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();

//        ContactsPage contactsPage = new ContactsPage(getDriver());
//        Assert.assertTrue(contactsPage.isBtnAddDisplayed());
        Assert.assertTrue(new ContactsPage(getDriver()).isTextInBtnAddPresent("ADD"));
    }

    @Test
    public void loginPositiveTestWithUser() {
//        User user = new User("family@mail.ru", "Family123!");
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();

//        ContactsPage contactsPage = new ContactsPage(getDriver());
//        Assert.assertTrue(contactsPage.isBtnContactsDisplayed());
        Assert.assertTrue(new ContactsPage(getDriver()).isTextInBtnContactsPresent("CONTACTS"));
    }

    @Test
    public void loginNegativeTest_WrongEmail() {
        User user = new User("familymail.ru", "Family123!");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(), "Wrong email or password");
    }

    @Test
    public void loginNegativeTest_WrongPassword() {
        User user = new User("family@mail.ru", "Famil123!");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(), "Wrong email or password");
    }
}
