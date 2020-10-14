package com.capgemini.training.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;

public class AddressBookService {
    private  List<Contact> contactList = new ArrayList<>();
    private  String bookName;
  
    public void load() throws IOException {
        String CSV_FILE_PATH = "./addressbook/dat/" + bookName + ".csv";
        String JSON_FILE_PATH="./addressbook/dat/" + bookName + ".json";
        contactList=  CSVUtils.loadContactsFromCSV(CSV_FILE_PATH);
        contactList=  JSONUtils.loadContactsFromJSON(JSON_FILE_PATH);
    }

    public void close() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        String CSV_FILE_PATH = "./addressbook/dat/" + bookName + ".csv";
        String JSON_FILE_PATH="./addressbook/dat/" + bookName + ".json";
        CSVUtils.writeContactsInCSV(contactList, CSV_FILE_PATH);
        JSONUtils.writeContactsInJSON(contactList, JSON_FILE_PATH);

    } 

    public String getBookName() {
        return bookName;
    }

    public boolean addContact(Contact contact) {
        List<Contact> filteredByFName = searchByName(contact.getfName());
        for (Contact sameName : filteredByFName)
            if (sameName.equals(contact))
                return false;
        contactList.add(contact);
        return true;
    }

    public List<Contact> searchByName(String name) {
        return contactList.stream().
                filter(person -> person.getfName().equalsIgnoreCase(name)).
                collect(Collectors.toList());
    }

    public List<Contact> searchByCity(String city) {
        return contactList.stream().
                filter(person -> person.getCity().equalsIgnoreCase(city)).
                collect(Collectors.toList());
    }

    public List<Contact> searchByState(String state) {
        return contactList.stream().
                filter(person -> person.getState().equalsIgnoreCase(state)).
                collect(Collectors.toList());
    }

    public Map<String, List<Contact>> cityMap() {
        return contactList.stream().collect(groupingBy(Contact::getCity));
    }

    public Map<String, List<Contact>> stateMap() {
        return contactList.stream().collect(groupingBy(Contact::getState));
    }

    public List<Contact> sortBy(Function<? super Contact, ? extends String> key) {
        return contactList.stream().sorted(Comparator.comparing(key)).collect(Collectors.toList());
    }

    public boolean editContact(Contact current, Contact modified) {
        if (!contactList.contains(current))
            return false;
        contactList.remove(current);
        contactList.add(modified);
        return true;
    }

    public boolean deleteContact(Contact contact) {
        contactList.remove(contact);
        return true;
    }

    @Override
    public String toString() {
        if (contactList.isEmpty())
            return "No contacts found!";

        StringBuilder sBuilder = new StringBuilder();
        contactList.forEach(sBuilder::append);
        return sBuilder.toString();
    }

    public AddressBookService(String bookName) {
        this.bookName = bookName;
    }
}