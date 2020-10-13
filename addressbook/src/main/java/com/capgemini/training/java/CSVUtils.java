package com.capgemini.training.java;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CSVUtils {
    public static List<Contact> loadContactsFromCSV(final String filepath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filepath));

        CsvToBean<Object> csvToBean = new CsvToBeanBuilder<>(reader).withType(Contact.class)
                .withIgnoreLeadingWhiteSpace(true).build();
        List<Object> contactList = csvToBean.parse();
        List<Contact> result = new ArrayList<>();
        for (Object contactBean : contactList) {
            Contact contact = (Contact) contactBean;
            result.add(contact);
        }
        return result;
    }

    public static void writeContactsInCSV(final List<Contact> contacts,final String filepath)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        Writer writer = Files.newBufferedWriter(Paths.get(filepath));
        StatefulBeanToCsv<Contact> beanToCsv = new StatefulBeanToCsvBuilder<Contact>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
        
        beanToCsv.write(contacts);
    }
}
