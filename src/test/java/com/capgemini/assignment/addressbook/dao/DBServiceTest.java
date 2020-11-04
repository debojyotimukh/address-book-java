package com.capgemini.assignment.addressbook.dao;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.capgemini.assignment.addressbook.model.Contact;
import com.capgemini.assignment.addressbook.service.AddressBook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DBServiceTest {
    DBService dbService;

    @Before
    public void init() {

        dbService = DBService.getInstance();
    }

    @Test
    public void givenDBProperties_when_triedToConnect_shouldConnectSuccessfully() {
        try (Connection conn = DBService.getConnection()) {
            Assert.assertNotNull(conn);
            Assert.assertEquals("addressbook", conn.getCatalog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenBookTable_whenQueriedName_shouldGiveExpectedCount() {
        List<String> bookNames = dbService.getBookNames();
        Assert.assertEquals(bookNames.size(), 3);
    }

    @Test
    public void givenBookTable_whenNewBookAdded_shouldIncreaseCount() throws DBException {
        dbService.newBook("personal");
        List<String> bookNames = dbService.getBookNames();
        Assert.assertEquals(bookNames.size(), 4);
    }

    @Test
    public void givenBookTable_whenBookDeleted_shouldReduceCount() throws DBException {
        dbService.deleteBook("personal");
        List<String> bookNames = dbService.getBookNames();
        Assert.assertEquals(bookNames.size(), 3);
    }

    @Test
    public void givenBookName_whenFetchedContacts_shouldMatchCount() throws DBException {
        AddressBook addressBook = dbService.fetchAddressBook("home");
        Assert.assertEquals(3, addressBook.getCount());

    }

    @Test
    public void givenDB_whenFetchedAddressMap_shouldMatchCounts() {
        Map<String, AddressBook> bookMap = dbService.fetchAllAddressBooks();
        Assert.assertEquals(3, bookMap.get("home").getCount());
        Assert.assertEquals(2, bookMap.get("friend").getCount());
        Assert.assertEquals(0, bookMap.get("work").getCount());
    }

    @Test
    public void givenDB_whenQueriedForCityWiseCount_shouldMatchExpectedCounts() throws DBException {
        Map<String, Integer> cityCount = dbService.countByCity();
        Assert.assertEquals(Integer.valueOf(2), cityCount.get("Howrah"));
        Assert.assertEquals(Integer.valueOf(1), cityCount.get("Chennai"));
    }

    @Test
    public void givenDB_whenQueriedForStateWiseCount_shouldMatchExpectedCounts() throws DBException {
        Map<String, Integer> stateCount = dbService.countByState();
        Assert.assertEquals(Integer.valueOf(3), stateCount.get("West Bengal"));
        Assert.assertEquals(Integer.valueOf(1), stateCount.get("Haryana"));

    }

    @Test
    public void givenDB_whenQueriedDateAdded_shouldMatchCount() throws DBException {
        LocalDate dateAdded = LocalDate.of(2018, 05, 10);
        AddressBook addressBook = dbService.fetchByDateAdded(dateAdded);
        Assert.assertEquals(2, addressBook.getCount());
    }

    @Test
    public void givenNewContact_whenAddedToDatabase_shouldIncreaseCount() throws DBException {
        Contact contact = new Contact("Bina", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
                "7277282884", "etgsgshs@gmail.com");

        dbService.addContact(contact, "work");
        AddressBook addressBook = dbService.fetchAddressBook("work");
        Assert.assertEquals(1, addressBook.getCount());

    }

    @Test
    public void givenExistingContact_whenDeleted_shouldBeRemovedSucessfully() throws DBException {
        Assert.assertTrue(dbService.deleteContact("work", "Bina"));
        AddressBook addressBook = dbService.fetchAddressBook("work");
        Assert.assertEquals(0, addressBook.getCount());

    }

}
