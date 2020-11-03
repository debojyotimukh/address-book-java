package com.capgemini.assignment.addressbook.fileservice;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.capgemini.assignment.addressbook.model.AddressBook;
import com.capgemini.assignment.addressbook.model.Contact;

public interface IAddressBookIOService {
    
    public <K,V> Map<K,V> readContacts(String filepath,Type clazz);

    public <K,V> void writeContacts(Map<K,V> contacts);
}
