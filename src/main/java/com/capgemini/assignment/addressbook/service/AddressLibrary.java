package com.capgemini.assignment.addressbook.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.capgemini.assignment.addressbook.dao.DBException;
import com.capgemini.assignment.addressbook.dao.DBService;
import com.capgemini.assignment.addressbook.fileservice.FileIOHandler;
import com.capgemini.assignment.addressbook.fileservice.IAddressBookIOService;
import com.capgemini.assignment.addressbook.fileservice.FileIOHandler.IO_TYPE;
import com.capgemini.assignment.addressbook.model.Contact;
import com.google.gson.reflect.TypeToken;

public class AddressLibrary implements IAddressLibrary {
    private static final AddressLibrary INSTANCE = new AddressLibrary();

    public static AddressLibrary getInstance() {
        return INSTANCE;
    }

    private Map<String, AddressBook> library;
    private IAddressBookIOService ioService;
    private DBService dbService = null;

    public AddressLibrary() {
        ioService = FileIOHandler.getIOHandler(IO_TYPE.JSON, "AddressBookDB.json");
        dbService = DBService.getInstance();
        this.library = new HashMap<>();
        updateFromDB();
        updateToJson();
        // updateFromJson();
    }

    @Override
    public void newBook(String bookName) {
        AddressBook addressBook = new AddressBook(bookName);
        library.put(bookName, addressBook);
        try {
            dbService.newBook(bookName);
            updateToJson();
        } catch (DBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public AddressBook openBook(String bookName) {
        return library.get(bookName);
    }

    @Override
    public void closeLibrary() {
        updateToJson();
    }

    @Override
    public void deleteBook(String name) {
        library.remove(name);
        try {
            dbService.deleteBook(name);
            updateToJson();
        } catch (DBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public String bookString(String bookName) {
        return library.get(bookName).toString();
    }

    @Override
    public void addContact(String bookName, Contact contact) {
        try {
            synchronized (AddressLibrary.class) {
                int id = dbService.addContact(contact, bookName);
                contact.setId(id);
            }
            library.get(bookName).addContact(contact);
            updateToJson();
        } catch (DBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editContact(String bookName, String contactName, Contact modified) {
        library.get(bookName).searchByName(contactName).stream().findFirst().ifPresent(contact -> contact = modified);
        dbService.editContact(contactName, modified, bookName);
        updateToJson();

    }

    @Override
    public void deleteContact(String bookName, String contactName) {
        library.get(bookName).searchByName(contactName).stream().findFirst().ifPresent(contact -> {
            library.get(bookName).deleteContact(contact);
        });
        try {
            dbService.deleteContact(bookName, contactName);
            updateToJson();
        } catch (DBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private Map<String, List<Contact>> consolidatedCityMap() {
        Map<String, List<Contact>> cityMap = new HashMap<>();
        for (AddressBook book : library.values()) {
            book.cityMap().forEach((key, value) -> cityMap.merge(key, value, (vo, vn) -> {
                vo.addAll(vn);
                return vo;
            }));
        }

        return cityMap;
    }

    private Map<String, List<Contact>> consolidatedStateMap() {
        Map<String, List<Contact>> stateMap = new HashMap<>();
        for (AddressBook book : library.values()) {
            book.stateMap().forEach((key, value) -> stateMap.merge(key, value, (vo, vn) -> {
                vo.addAll(vn);
                return vo;
            }));
        }

        return stateMap;
    }

    @Override
    public List<Contact> searchByName(String name) {
        List<Contact> contacts = new ArrayList<>();
        library.values().forEach(book -> contacts.addAll(book.searchByName(name)));
        return contacts;
    }

    @Override
    public List<Contact> searchByCity(String name) {
        List<Contact> contacts = new ArrayList<>();
        library.values().forEach(book -> contacts.addAll(book.searchByCity(name)));
        return contacts;
    }

    @Override
    public List<Contact> searchByState(String name) {
        List<Contact> contacts = new ArrayList<>();
        library.values().forEach(book -> contacts.addAll(book.searchByState(name)));
        return contacts;
    }

    @Override
    public Map<String, Integer> countByCity() {
        Map<String, Integer> count = new HashMap<>();
        consolidatedCityMap().forEach((k, v) -> count.put(k, v.size()));
        return count;
    }

    @Override
    public Map<String, Integer> countByState() {
        Map<String, Integer> count = new HashMap<>();
        consolidatedStateMap().forEach((k, v) -> count.put(k, v.size()));
        return count;
    }

    @Override
    public List<String> getBookNames() {
        Set<String> names = this.library.keySet();
        return new ArrayList<String>(names);
    }

    public void updateToJson() {

        ioService.writeContacts(this.library.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().getList())));

    }

    public void updateFromJson() {
        Map<String, List<Contact>> contactListMap = ioService.readContacts("AddressBookDB.json",
                new TypeToken<Map<String, List<Contact>>>() {
                }.getType());

        contactListMap.forEach((k, v) -> {
            this.library.put(k, new AddressBook(v));
        });
    }

    public void addContacts(List<Contact> contacts, String bookName) {
        Map<Integer, Boolean> contactAdditionStatus = new HashMap<>();
        contacts.forEach(contact -> {
            Runnable task = () -> {
                contactAdditionStatus.put(contact.hashCode(), false);
                System.out.println("Contact being added " + Thread.currentThread().getName());
                this.addContact(bookName, contact);
                contactAdditionStatus.put(contact.hashCode(), true);
                System.out.println("Contact added " + Thread.currentThread().getName());
            };
            Thread thread = new Thread(task, contact.getfName());
            thread.start();
        });
        while (contactAdditionStatus.containsValue(false)) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("done");
        updateToJson();
    }

    public void updateFromDB(){
        Map<String, AddressBook> fetchdBooks = dbService.fetchAllAddressBooks();
        this.library=fetchdBooks;
    }

    public boolean isSyncWithDB(){
        Map<String, AddressBook> fetchdBooks = dbService.fetchAllAddressBooks();
        if(!fetchdBooks.keySet().equals(this.library.keySet()))
            return false;

        Map<String, Boolean> valueEquals = fetchdBooks.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().equals(this.library.get(e.getKey()))));

        return !valueEquals.containsValue(false);
    }

}
