package com.capgemini.assignment.addressbook.fileservice;

import java.lang.reflect.Type;
import java.util.Map;


public interface IAddressBookIOService {
    
    public <K,V> Map<K,V> readContacts(String filepath,Type clazz);

    public <K,V> void writeContacts(Map<K,V> contacts);
}
