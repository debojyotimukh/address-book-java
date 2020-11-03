package com.capgemini.training.java;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Utility {

    public static Contact readContact(Scanner sc) {
        System.out.println("FIRST NAME: ");
        String fName = sc.nextLine();
        System.out.println("LAST NAME: ");
        String lName = sc.nextLine();
        System.out.println("ADDRESS: ");
        String address = sc.nextLine();
        System.out.println("CITY: ");
        String city = sc.nextLine();
        System.out.println("STATE: ");
        String state = sc.nextLine();
        System.out.println("ZIP: ");
        String zip = sc.nextLine();
        System.out.println("PHONE NUMBER: ");
        String phone = sc.nextLine();
        System.out.println("EMAIL ADDRESS: ");
        String email = sc.nextLine();

        return new Contact(fName, lName, address, city, state, zip, phone, email);
    }

    public static void printConditionally(List<Contact> contacts, Predicate<Contact> predicate) {
        //Header
        System.out.println("Index\tFirst Name\tLast Name\tAddress\tCity\tState\tZip\tPhone\tEmail");
        System.out.println("====================================================================================");
        for (Contact contact : contacts)
            if (predicate.test(contact))
                System.out.println(contacts.indexOf(contact) + "\t" + contact.toString());

        System.out.println("====================================================================================");

    }

}
