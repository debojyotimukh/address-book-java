package com.capgemini.assignment.addressbook.fileservice;

import java.util.List;

public interface IAddressBookIOService<T extends Object> {
    
    public List<T> readContacts(String filepath,Class<T[]> clazz);

    public void writeContacts(List<T> contacts, final String filepath);
}
