package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import java.util.Random;

import static utils.UserFactory.positiveUser;

public class RegistrationTests extends AppManager {
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000);
        User user = new User("muyitr" + i + "@gmail.com", "Password124!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(new ContactsPage(getDriver()).isTextInContactPageMessage("No Contacts here!"));
    }

    @Test
    public void registrationPositiveTest_WithFaker() {
        User user = positiveUser();
        System.out.println(user);
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(new ContactsPage(getDriver()).isTextInContactPageMessage("No Contacts here!"));
    }

    @Test
    public void registrationNegativeTest_WrongEmail() {
        int i = new Random().nextInt(1000);
        User user = new User("muyitr" + i + "gmail.com", "Password124!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_WithFaker_EmptyPassword() {
        User user = positiveUser();
        user.setPassword("");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

}
