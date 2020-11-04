package com.capgemini.assignment.addressbook.fileservice;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.capgemini.assignment.addressbook.model.Contact;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class CSVService implements IAddressBookIOService {

    private String filename;

    public CSVService(String filename) {
        this.filename = filename;
    }

    public static List<Contact> loadContactsFromCSV(final String filepath) throws CsvIOException {
        List<Contact> contactList = null;

        try (Reader reader = Files.newBufferedReader(Paths.get(filepath));) {

            CsvToBean<Contact> csvToBean = new CsvToBeanBuilder<Contact>(reader).withType(Contact.class).build();
            contactList = csvToBean.parse();

        } catch (Exception e) {
            throw new CsvIOException("Failed to read from CSV!");
        }

        return contactList;
    }

    public static void writeContactsInCSV(List<Contact> contacts, final String filepath) throws CsvIOException {

        try (Writer writer = Files.newBufferedWriter(Paths.get(filepath));) {

            StatefulBeanToCsv<Contact> beanToCsv = new StatefulBeanToCsvBuilder<Contact>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            beanToCsv.write(contacts);

        } catch (Exception e) {
            throw new CsvIOException("Failed to write in CSV!");
        }

    }

    @Override
    public <K, V> Map<K, V> readContacts(String filepath, Type clazz) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <K, V> void writeContacts(Map<K, V> contacts) {
        // TODO Auto-generated method stub

    }


}
