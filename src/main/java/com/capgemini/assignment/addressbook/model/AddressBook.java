package com.capgemini.assignment.addressbook.model;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AddressBook {
    private List<Contact> contactList;
    private String bookName;

    public AddressBook(String bookName) {
        this.contactList = new ArrayList<>();
        this.bookName = bookName;
    }

    public AddressBook(List<Contact> contactList){
        this.contactList=contactList;
    }

    public String getBookName() {
        return bookName;
    }
    public List<Contact> getList(){
        return this.contactList;
    }

    /**
     * Search address book by name
     * 
     * @param name
     * @return
     */
    public List<Contact> searchByName(String name) {
        return contactList.stream().filter(person -> person.getfName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    /**
     * Search address book by city
     * 
     * @param city
     * @return
     */
    public List<Contact> searchByCity(String city) {
        return contactList.stream().filter(person -> person.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    /**
     * Search address book by state
     * 
     * @param state
     * @return
     */
    public List<Contact> searchByState(String state) {
        return contactList.stream().filter(person -> person.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }

    /**
     * Returns a map of city-contactList
     * 
     * @return
     */
    public Map<String, List<Contact>> cityMap() {
        return contactList.stream().collect(groupingBy(Contact::getCity));
    }

    /**
     * Returns a map of state-contactList
     * 
     * @return
     */
    public Map<String, List<Contact>> stateMap() {
        return contactList.stream().collect(groupingBy(Contact::getState));
    }

    public List<Contact> sortBy(Function<? super Contact, ? extends String> key) {
        return contactList.stream().sorted(Comparator.comparing(key)).collect(Collectors.toList());
    }

    /**
     * Add new contact in address book
     * 
     * @param contact
     * @return
     */
    public boolean addContact(Contact contact) {
        List<Contact> filteredByFName = searchByName(contact.getfName());
        for (Contact sameName : filteredByFName)
            if (sameName.equals(contact))
                return false;
        contactList.add(contact);
        return true;
    }

    /**
     * edit new contact in address book
     * 
     * @param current  current contact
     * @param modified contact
     * @return
     */
    public boolean editContact(Contact current, Contact modified) {
        if (!contactList.contains(current))
            return false;
        contactList.remove(current);
        contactList.add(modified);
        return true;
    }

    /**
     * delete contact from address book
     * 
     * @param contact
     * @return
     */
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

}
