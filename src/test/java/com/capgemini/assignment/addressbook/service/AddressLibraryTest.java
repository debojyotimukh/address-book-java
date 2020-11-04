package com.capgemini.assignment.addressbook.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.assignment.addressbook.model.Contact;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Unit test for Address Library .
 */
public class AddressLibraryTest {
    public AddressLibrary library;

    @Before
    public void init() {
        library = new AddressLibrary();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;

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
        library.addContact("friend", new Contact("Bina", "Kamal", "sadar natin laane", "Bangalore", "Karnataka",
                "489025", "7277282884", "etgsgshs@gmail.com"));
        boolean result = library.isSyncWithDB();
        Assert.assertTrue(result);
    }

    @Test
    public void givenADatabase_whenAddedMultipleConatact_returnsCountOFContacs() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Bina", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025", "7277282884",
                "etgsgshs@gmail.com"));
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

    private Response addToJSONServer(Contact contact) {
        String contactJson = new Gson().toJson(contact);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(contactJson);
        return request.post("work/");
    }

    @Test
    public void givenNewContact_whenAddedToJSONServer_shouldReturn201Response() {
        Contact contact = new Contact(54, "Test", "Contact", "From rest Assured", "Howrah", "West Bengal", "456784",
                "987645334", "new.contact@json.com");
        Response response = addToJSONServer(contact);
        Assert.assertEquals(201, response.getStatusCode());
        library.updateFromJson();
        int entries = library.openBook("work").getCount();
        Assert.assertEquals(1, entries);
    }

    @Test
    public void givenExistingContact_whenDeleted_shouldReturn200Response() {
        Contact contact = new Contact(54, "Test", "Contact", "From rest Assured", "Howrah", "West Bengal", "456784",
                "987645334", "new.contact@json.com");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        Response response = request.delete("work/" + contact.getId());
        Assert.assertEquals(200, response.getStatusCode());
        library.updateFromJson();
        int entries = library.openBook("work").getCount();
        Assert.assertEquals(0, entries);

    }

    @Test
    public void givenExistingContact_whenUpdated_shouldReturn200Response() {
        
        Contact contact = new Contact("Test", "Contact", "From rest Assured", "Howrah", "West Bengal", "456784",
                "987645334", "new.contact@json.com");

        String contactJson = new Gson().toJson(contact);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(contactJson);

        Response response = request.put("test/" + 45);
        library.updateFromJson();
        Assert.assertEquals(200, response.getStatusCode());
    }

    



}
