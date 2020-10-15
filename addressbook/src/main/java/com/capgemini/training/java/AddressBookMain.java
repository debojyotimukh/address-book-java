package com.capgemini.training.java;

import java.util.*;

public class AddressBookMain {
    private List<AddressBookService> library = new ArrayList<>();
    private Scanner sc = null;

    private Map<String, List<Contact>> consolidatedCityMap() {
        Map<String, List<Contact>> cityMap = new HashMap<>();
        for (AddressBookService book : library) {
            book.cityMap().forEach((key, value) -> cityMap.merge(key, value, (vo, vn) -> {
                vo.addAll(vn);
                return vo;
            }));
        }

        return cityMap;
    }

    private Map<String, List<Contact>> consolidatedStateMap() {
        Map<String, List<Contact>> stateMap = new HashMap<>();
        for (AddressBookService book : library) {
            book.stateMap().forEach((key, value) -> stateMap.merge(key, value, (vo, vn) -> {
                vo.addAll(vn);
                return vo;
            }));
        }

        return stateMap;
    }

    private int locateIndex(String name) {
        for (int i = 0; i < library.size(); i++)
            if (library.get(i).getBookName().equals(name))
                return i;
        return -1;
    }

    private void addBook(String bookName) {
        AddressBookService addressBookService = new AddressBookService(bookName);
        library.add(addressBookService);
        AddressBookCLI.addressPrompt(addressBookService, sc);
    }

    private void openBook(String bookName) throws CsvIOException {
        System.out.println("Current: " + bookName);
        AddressBookService addressBookService = library.get(locateIndex(bookName));
        addressBookService.load();
        AddressBookCLI.addressPrompt(addressBookService, sc);
    }

    private void deleteBook(String name) {
        library.remove(locateIndex(name));
    }

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
                library.forEach(book -> book.searchByName(name).forEach(System.out::println));
                break;
            case 2: // by city
                System.out.println("Enter city: ");
                String city = sc.nextLine();
                library.forEach(book -> book.searchByCity(city).forEach(System.out::println));
                break;
            case 3: // by state
                System.out.println("Enter state: ");
                String state = sc.nextLine();
                library.forEach(book -> book.searchByState(state).forEach(System.out::println));
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
                consolidatedCityMap().forEach((k, v) -> System.out.println(k + "\t" + v.size()));
                break;
            case 2: // by state
                consolidatedStateMap().forEach((k, v) -> System.out.println(k + "\t" + v.size()));
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
                    addBook(bookName);
                    break;
                case 2: // select
                    System.out.println("Available books are: ");
                    library.forEach(book -> System.out.println(book.getBookName() + " ,"));
                    System.out.println("Open Book: ");
                    try {
                        openBook(sc.nextLine());
                        break;
                    } catch (CsvIOException e) {
                        e.getMessage();
                    }
                    break;
                case 3: // delete
                    System.out.println("Enter name to delete: ");
                    deleteBook(sc.nextLine());
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