package com.capgemini.training.java;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class JSONUtils {
    public static List<Contact> loadContactsFromJSON(final String filepath) {
        List<Contact> contactList = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(filepath))) {
            Gson gson = new Gson();
            Contact[] contacts = gson.fromJson(reader, Contact[].class);
            contactList = Arrays.asList(contacts);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public static void writeContactsInJSON(List<Contact> contactList, final String filepath) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(filepath))) {

            Gson gson = new Gson();
            writer.write(gson.toJson(contactList));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
