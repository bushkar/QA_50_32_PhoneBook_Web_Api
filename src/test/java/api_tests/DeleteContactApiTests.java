package api_tests;

import dto.*;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

import static utils.ContactFactory.positiveContact;

public class DeleteContactApiTests implements BaseApi, ILogin {
    TokenDto token;
    SoftAssert softAssert = new SoftAssert();
    String idContact;

    @BeforeClass
    public void login() {
        token = login_get_token();
    }

    @BeforeMethod
    public void get_all_contacts() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH, token.getToken())
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            ContactsDto contactsDto = GSON.fromJson(response.body().string(), ContactsDto.class);
            idContact = contactsDto.getContacts().get(0).getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void deleteContactPositiveApiTest() {
        Contact contact = positiveContact();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request requestNewContact = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH, token.getToken())
                .post(requestBody)
                .build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(requestNewContact).execute();
            softAssert.assertEquals(response.code(), 200
                    , "validate status code (new contact)");
            ResponseMessageDto responseMessageDto = GSON.fromJson(response.body().string(), ResponseMessageDto.class);
            String id = responseMessageDto.getMessage().substring(23);
            Request requestDeleteContact = new Request.Builder()
                    .url(BASE_URL + DELETE_CONTACT_URL + id)
                    .addHeader(AUTH, token.getToken())
                    .delete()
                    .build();
            Response responseDeleteContact = OK_HTTP_CLIENT.newCall(requestDeleteContact).execute();
            softAssert.assertEquals(responseDeleteContact.code(), 200
                    , "validate status code (delete contact)");
            softAssert.assertAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteContactByIdPositiveApiTest() {
        Request request = new Request.Builder()
                .url(BASE_URL + DELETE_CONTACT_URL + idContact)
                .addHeader(AUTH, token.getToken())
                .delete()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            Assert.assertEquals(response.code(), 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
