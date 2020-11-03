package com.capgemini.assignment.addressbook.model;

import java.util.List;
import java.util.Map;

public interface IAddressLibrary {

    public void newBook(String bookName);

    public void deleteBook(String bookName);

    public List<Contact> searchByName(String name);

    public List<Contact> searchByCity(String city);

    public List<Contact> searchByState(String state);

    public Map<String, Integer> countByCity();

    public Map<String, Integer> countByState();
}
