package com.capgemini.assignment.addressbook.fileservice;

public class FileIOHandler {
    public enum IO_TYPE {
        JSON, CSV, DAT
    };

    public static IAddressBookIOService<T> getIOHandler(IO_TYPE ioType, String filename) {
        if (ioType.equals(IO_TYPE.JSON))
            return new JSONService<T>(filename);
        if (ioType.equals(IO_TYPE.CSV))
            return new CSVService(filename);
        return null;
    }

}
