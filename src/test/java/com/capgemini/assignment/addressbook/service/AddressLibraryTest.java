package com.capgemini.assignment.addressbook.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.assignment.addressbook.model.Contact;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Address Library .
 */
public class AddressLibraryTest {
    public AddressLibrary library;

    @Before
    public void init() {
        library = new AddressLibrary();
    }

    @Test
    public void givenADatabase_whenRetrievedDataFromDatabase_returnsCountofDataReceived() {
        /*
         * ABD.readDirectory(IOService.DB_IO); int n = ABD.getCountOFEntries();
         * ABD.printDirectory(IOService.CONSOLE_IO); Assert.assertEquals(12, n);
         */
    }

    @Test
    public void givenADatabase_whenUpdatedDataForAContact_returnsIsSyncWithDatabase() {
        /*
         * AddressBookDirectory ABD = new AddressBookDirectory();
         * ABD.readDirectory(IOService.DB_IO); ABD.updateContactInDatabase("Ram",
         * "Khan", "89/1 dharamshala haman road"); boolean b =
         * ABD.isSyncWithDatabase("Ram", "Khan"); Assert.assertTrue(b);
         */
    }

    @Test
    public void givenADatabase_whenAddedAConatact_returnsisSyncWithDatabase() {
        library.addContact("friend", new Contact("Bina", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
        "7277282884", "etgsgshs@gmail.com"));
        boolean result=library.isSyncWithDB();
        Assert.assertTrue(result);
    }

    @Test
    public void givenADatabase_whenAddedMultipleConatact_returnsCountOFContacs() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Bina", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
                "7277282884", "etgsgshs@gmail.com"));
        contacts.add(new Contact("Binayak", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
                "7277282884", "etgsgshs@gmail.com"));
        contacts.add(new Contact("Patil", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
                "7277282884", "etgsgshs@gmail.com"));
        Instant start = Instant.now();
        library.addContacts(contacts, "test");
        Instant end = Instant.now();
        System.out.println("Time required " + Duration.between(start, end));

        Assert.assertEquals(3, library.openBook("test").getCount());

    }

    @Test
    public void givenADatabase_whenRetrievedData_givesADBjsonFile() {
        /*
         * AddressBookDirectory ABD = new AddressBookDirectory();
         * ABD.readDirectory(IOService.DB_IO); ABD.setNewAddressBook(); try { FileWriter
         * writer = new FileWriter("./contactDB.json"); Gson gson = new
         * GsonBuilder().setPrettyPrinting().create(); String str =
         * gson.toJson(ABD.getNewAddressBook()); writer.write(str); writer.close(); }
         * catch (IOException e) { e.printStackTrace(); }
         */
    }

    /*
     * @Before public void setup() { RestAssured.baseURI = "http://localhost";
     * RestAssured.port = 3000; }
     * 
     * public Map<String, List<Contact>> getEmployee() { List<String> booklist =
     * Arrays.asList(new String[] { "Family", "Friend", "Profession" }); Map<String,
     * Boolean> statusCodes = new HashMap<>(); Map<String, List<Contact>> directory
     * = new HashMap<>(); booklist.forEach(nameOfBook -> {
     * statusCodes.put(nameOfBook, false); Response response = RestAssured.get("/" +
     * nameOfBook); System.out.println("Employee Payroll entries in Json Server :" +
     * nameOfBook + " \n" + response.asString()); directory.put(nameOfBook, new
     * Gson().fromJson(response.asString(), new TypeToken<List<Contact>>() {
     * }.getType())); statusCodes.put(nameOfBook, true); }); return directory; }
     */

    /*
     * private Response addEmployeeToJsonServer(Contact contact, String bookname) {
     * String contactJson = new
     * GsonBuilder().setPrettyPrinting().create().toJson(contact);
     * RequestSpecification request = RestAssured.given();
     * request.header("Content-type", "application/json");
     * request.body(contactJson); return request.post("/" + bookname); }
     * 
     * @Test@Ignore public void
     * givenContactDetailsInJsonServer_whenRetrieved_shouldReturnNoOfCounts() {
     * Map<String, List<Contact>> data = getEmployee(); AddressBookDirectory dir =
     * new AddressBookDirectory(); dir.setNewAddressBook(data); int entries =
     * dir.getCountOFEntries(); dir.printDirectory(IOService.CONSOLE_IO);
     * Assert.assertEquals(12, entries); }
     */
    /*
     * @Test@Ignore public void
     * givenContactDetailsInJsonServer_whenAddedAConatct_shouldReturnNoOfCountsAndResponseCode
     * () { Map<String, List<Contact>> data = getEmployee(); AddressBookDirectory
     * dir = new AddressBookDirectory(); dir.setNewAddressBook(data); Contact
     * contact = new Contact("Bina", "Kamal", "sadar natin laane", "Bangalore",
     * "Karnataka", "489025", "7277282884", "etgsgshs@gmail.com", "2020-10-29");
     * Response response = addEmployeeToJsonServer(contact, "Profession"); int
     * statusCode = response.getStatusCode(); Assert.assertEquals(201, statusCode);
     * Contact newAddedContact = new Gson().fromJson(response.asString(),
     * Contact.class);
     * dir.getAddressBookDirectory().get("Profession").getContact().add(
     * newAddedContact);
     * dir.getNewAddressBook().get("Profession").add(newAddedContact);
     * dir.printDirectory(IOService.CONSOLE_IO); int entries =
     * dir.getCountOFEntries(); Assert.assertEquals(13, entries); }
     */
}
