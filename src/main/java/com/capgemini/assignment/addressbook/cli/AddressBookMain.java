package com.capgemini.assignment.addressbook.cli;

import java.util.*;

import com.capgemini.assignment.addressbook.model.AddressLibrary;
import com.capgemini.assignment.addressbook.utils.Utility;

public class AddressBookMain {
    private AddressLibrary library = new AddressLibrary();
    private Scanner sc = null;

    private void searchByPrompt() {
        System.out.println("1. By name");
        System.out.println("2. By city");
        System.out.println("3. By state");
        System.out.println("4. Back");
        System.out.println("Your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1: // by name
                System.out.println("Enter name: ");
                String name = sc.nextLine();
                Utility.printList(library.searchByName(name));
                break;
            case 2: // by city
                System.out.println("Enter city: ");
                String city = sc.nextLine();
                Utility.printList(library.searchByCity(city));
                break;
            case 3: // by state
                System.out.println("Enter state: ");
                String state = sc.nextLine();
                Utility.printList(library.searchByState(state));
                break;
            case 4: // back
                return;
            default:
                System.out.println("INVALID CHOICE!");
        }

    }

    private void countPrompt() {
        System.out.println("1. By city");
        System.out.println("2. By state");
        System.out.println("3. Back");
        System.out.println("Your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1: // by city
                Utility.printMap(library.countByCity());
                break;
            case 2: // by state
                Utility.printMap(library.countByState());
                break;
            case 3: // back
                return;
            default:
                System.out.println("INVALID CHOICE!");
        }
    }

    public void welcomePrompt(Scanner scanner) {

        while (true) {
            System.out.println("\n\nWelcome to Address Book Program");
            System.out.println("1. New Address Book");
            System.out.println("2. Select Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Search");
            System.out.println("5. Count");
            System.out.println("6. Quit");
            System.out.print("Your choice: ");

            this.sc = scanner;
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: // add
                    System.out.println("Name of new address book: ");
                    String bookName = sc.next();
                    sc.nextLine();
                    library.newBook(bookName);
                    break;
                case 2: // select
                    System.out.println("Available books are: ");
                    Utility.printList(library.getBookNames());
                    System.out.println("Open Book: ");
                    bookName = sc.nextLine();
                    AddressBookCLI.addressPrompt(library.openBook(bookName), sc);
                    break;
                case 3: // delete
                    System.out.println("Enter name to delete: ");
                    library.deleteBook(sc.nextLine());
                    break;
                case 4: // search
                    searchByPrompt();
                    break;
                case 5: // count by city/state
                    countPrompt();
                    break;
                case 6: // quit
                    return;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }

    }

    public static void main(String[] args) {
        AddressBookMain addressBookMain = new AddressBookMain();
        Scanner sc = new Scanner(System.in);
        addressBookMain.welcomePrompt(sc);
        sc.close();
    }

}