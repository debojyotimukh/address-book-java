package com.capgemini.assignment.addressbook.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddressLibrary implements IAddressLibrary {
    private Map<String, AddressBook> library;

    public AddressLibrary() {
        this.library = new HashMap<>();
    }

    @Override
    public void newBook(String bookName) {
        AddressBook addressBook = new AddressBook(bookName);
        library.put(bookName, addressBook);
    }

    public AddressBook openBook(String bookName) {
        return library.get(bookName);
    }

    @Override
    public void deleteBook(String name) {
        library.remove(name);
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

	public List<String> getBookNames() {
        Set<String> names = this.library.keySet();
		return new ArrayList<String>(names);
	}

}
