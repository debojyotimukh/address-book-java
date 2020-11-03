package com.capgemini.assignment.addressbook.fileservice;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.capgemini.assignment.addressbook.model.Contact;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class JSONService<T extends Object> implements IAddressBookIOService<T>{
    public String filename;
    
    public static List<Contact> loadContactsFromJSON(final String filepath) {
        List<Contact> contactList = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(filepath))) {
            Gson gson = new Gson();
            Contact[] contacts = gson.fromJson(reader, Contact[].class);
            contactList = Arrays.asList(contacts);

        } catch (Exception e) {
            throw new JsonIOException("Cannot load contact from " + filepath);
        }
        return contactList;
    }

    public static void writeContactsInJSON(List<Contact> contactList, final String filepath) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(filepath))) {

            Gson gson = new Gson();
            writer.write(gson.toJson(contactList));

        } catch (Exception e) {
            throw new JsonIOException("Cannot write contact in " + filepath);
        }
    }

    public JSONService(String filename) {
        this.filename = filename;
    }

    @Override
    public List<T> readContacts(String filepath,Class<T[]> clazz) {
        List<T> list = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(filepath))) {
            Gson gson = new Gson();
            T[] datas = gson.fromJson(reader, clazz);
            list = Arrays.asList(datas);

        } catch (Exception e) {
            throw new JsonIOException("Cannot load contact from " + filepath);
        }
        return list;
    }

    @Override
    public void writeContacts(List<T> contacts, String filepath) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(filepath))) {

            Gson gson = new Gson();
            writer.write(gson.toJson(contacts));

        } catch (Exception e) {
            throw new JsonIOException("Cannot write contact in " + filepath);
        }

    }

}
