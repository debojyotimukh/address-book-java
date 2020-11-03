package com.capgemini.assignment.addressbook.model;

import java.util.List;
import java.util.Map;

public interface IAddressLibrary {

    public List<String> getBookNames();

    public void newBook(String bookName);

    public void deleteBook(String bookName);

    public String bookString(String bookName);

    public void closeLibrary();

    public void addContact(String bookName, Contact contact);

    public void editContact(String bookName, String contactName, Contact modified);

    public void deleteContact(String bookName, String contactName);

    public List<Contact> searchByName(String name);

    public List<Contact> searchByCity(String city);

    public List<Contact> searchByState(String state);

    public Map<String, Integer> countByCity();

    public Map<String, Integer> countByState();
}
