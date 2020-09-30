import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

class Contact {
    private String fName;
    private String lName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;

    public Contact(String fName, String lName, String address, String city, String state, String zip, String phone,
                   String email) {
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return fName + "\t" + lName + "\t" + address + "\t" + city + "\t" + state + "\t" + zip + "\t" + phone + "\t"
                + email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

}

class AddressBookService {
    private ArrayList<Contact> contactList = new ArrayList<>();

    public void addContact(Contact contact) {
        contactList.add(contact);
    }

    public int searchByName(String name) {
        for (Contact contact : contactList)
            if (contact.getfName().equalsIgnoreCase(name))
                return contactList.indexOf(contact);
        return -1;
    }

    public boolean editContact(String name, Contact modified) {
        int index = searchByName(name);
        if (index == -1)
            return false;
        contactList.set(index, modified);
        return true;
    }

    public boolean deleteContact(String name) {
        int index = searchByName(name);
        if (index == -1)
            return false;
        contactList.remove(index);
        return true;
    }

    @Override
    public String toString() {
        if (contactList.isEmpty())
            return "No contacts found!";

        StringBuilder sBuilder = new StringBuilder();
        for (Contact contacts : contactList)
            sBuilder.append(contacts.toString()).append("\n");

        return sBuilder.toString();
    }
}

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
                    book.addContact(readContact(sc));
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
                    for (Enumeration<String> i = library.keys(); i.hasMoreElements();) {
                        System.out.println(i.nextElement() + ",");
                    }
                    System.out.println("Open Book: ");
                    String name = sc.nextLine();
                    System.out.println("Current: "+name);
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