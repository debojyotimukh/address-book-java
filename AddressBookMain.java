import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMain {
    private static Contact readContact(Scanner sc) {
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

    private static void addressBookOps(AddressBookService book, Scanner sc) {
        while (true) {
            System.out.println("\n\nWelcome to Address Book Program");
            System.out.println("1. Add Contact");
            System.out.println("2. Edit Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Print Address Book");
            System.out.println("5. Back");
            System.out.print("Your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    if (book.addContact(readContact(sc)))
                        System.out.println("Contact Added!");
                    else System.out.println("Contact already exists");
                    break;
                case 2:
                    System.out.println("Enter name to edit: ");
                    String name = sc.nextLine();
                    if (book.searchByName(name) == -1)
                        System.out.println("NOT FOUND!");
                    else
                        book.editContact(name, readContact(sc));
                    break;
                case 3:
                    System.out.println("Enter name to delete: ");
                    name = sc.nextLine();
                    if (book.searchByName(name) == -1)
                        System.out.println("NOT FOUND!");
                    else
                        book.deleteContact(name);
                    break;
                case 4:
                    System.out.println(book.toString());
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Dictionary<String, AddressBookService> library = new Hashtable<>();
        while (true) {
            System.out.println("\n\nWelcome to Address Book Program");
            System.out.println("1. New Address Book");
            System.out.println("2. Select Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Quit");
            System.out.print("Your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Name of new address book: ");
                    String bookName = sc.next();
                    sc.nextLine();
                    library.put(bookName, new AddressBookService());
                    addressBookOps(library.get(bookName), sc);
                    break;
                case 2:
                    System.out.println("Availble books are: ");
                    for (Enumeration<String> i = library.keys(); i.hasMoreElements(); ) {
                        System.out.println(i.nextElement() + ",");
                    }
                    System.out.println("Open Book: ");
                    String name = sc.nextLine();
                    System.out.println("Current: " + name);
                    addressBookOps(library.get(name), sc);
                    break;
                case 3:
                    System.out.println("Enter name to delete: ");
                    name = sc.nextLine();
                    library.remove(name);
                    break;
                case 4:
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }
    }
}