package data_providers;

import dto.Contact;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.ContactFactory.positiveContact;

public class ContactWrongPhoneDataProvider {
    @DataProvider
    public Iterator<Contact> dataProviderFromFile() {
        Contact contact = positiveContact();
        List<Contact> list = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data_csv/data_wrong_phones.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(Contact.builder()
                        .name(contact.getName())
                        .lastName(contact.getLastName())
                        .email(contact.getEmail())
                        .phone(line)
                        .address(contact.getAddress())
                        .description(contact.getDescription())
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }
}
