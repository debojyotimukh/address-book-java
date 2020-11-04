package com.capgemini.assignment.addressbook.fileservice;

import java.io.FileReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.capgemini.assignment.addressbook.model.Contact;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;

public class JSONService implements IAddressBookIOService{
    public String filepath;
    
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
        this.filepath = filename;
    }

    @Override
    public <K,V> Map<K,V> readContacts(String filepath,Type clazz) {
        try (JsonReader reader = new JsonReader(new FileReader(filepath))) {
            Gson gson = new Gson();
            Map<K,V> contactMap=gson.fromJson(reader, clazz);
            return contactMap;
        } catch (Exception e) {
            throw new JsonIOException("Cannot load contact from " + filepath);
        }
        // try {
		// 	JsonReader reader = new JsonReader(new FileReader(SAMPLE_JSON_PATH));

		// 	Gson gson = new Gson();
		// 	Map<String, AddressBook> ab = gson.fromJson(reader, new TypeToken<Map<String, List<Contact>>>() {
		// 	}.getType());
		// 	return ab;
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		// return addressBookDirectory;
    }

    @Override
    public <K,V> void  writeContacts(Map<K,V> contactMap) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(filepath))) {

            Gson gson = new Gson();
            writer.write(gson.toJson(contactMap));

        } catch (Exception e) {
            throw new JsonIOException("Cannot write contact in " + filepath);
        }

    }


}
