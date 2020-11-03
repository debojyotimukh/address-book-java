package com.capgemini.assignment.addressbook.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.capgemini.assignment.addressbook.fileservice.FileIOHandler;
import com.capgemini.assignment.addressbook.fileservice.IAddressBookIOService;
import com.capgemini.assignment.addressbook.fileservice.FileIOHandler.IO_TYPE;
import com.google.gson.reflect.TypeToken;

public class AddressLibrary implements IAddressLibrary {
    private Map<String, AddressBook> library;
    private IAddressBookIOService ioService;

    public AddressLibrary() {
        ioService = FileIOHandler.getIOHandler(IO_TYPE.JSON, "AddressBookDB.json");
        this.library = new HashMap<>();
        updateList();
    }

    @Override
    public void newBook(String bookName) {
        AddressBook addressBook = new AddressBook(bookName);
        library.put(bookName, addressBook);
        updateJson();
    }

    public AddressBook openBook(String bookName) {
        return library.get(bookName);
    }

    @Override
    public void closeLibrary() {
        updateJson();
    }

    @Override
    public void deleteBook(String name) {
        library.remove(name);
        updateJson();
    }

    @Override
    public String bookString(String bookName) {
        return library.get(bookName).toString();
    }

    @Override
    public void addContact(String bookName, Contact contact) {
        library.get(bookName).addContact(contact);
        updateJson();
    }

    @Override
    public void editContact(String bookName, String contactName, Contact modified) {
        library.get(bookName).searchByName(contactName).stream().findFirst().ifPresent(contact -> contact = modified);
        updateJson();

    }

    @Override
    public void deleteContact(String bookName, String contactName) {
        library.get(bookName).searchByName(contactName).stream().findFirst().ifPresent(contact -> {
            library.get(bookName).deleteContact(contact);
        });
        updateJson();

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

    public void updateJson() {
        ioService.writeContacts(this.library.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().getList())));
    }

    public void updateList() {
        Map<String, List<Contact>> contactListMap = ioService.readContacts("AddressBookDB.json",
                new TypeToken<Map<String, List<Contact>>>() {
                }.getType());

        contactListMap.forEach((k, v) -> {
            this.library.put(k, new AddressBook(v));
        });
    }

}
