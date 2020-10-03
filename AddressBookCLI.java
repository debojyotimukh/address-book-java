import java.util.List;
import java.util.Scanner;

public class AddressBookCLI {
    private static int addressPrompt(Scanner sc) {
        System.out.println("\n\nWelcome to Address Book Program");
        System.out.println("1. Add Contact");
        System.out.println("2. Edit Contact");
        System.out.println("3. Delete Contact");
        System.out.println("4. Print Address Book");
        System.out.println("5. Sort Address Book");
        System.out.println("6. Back");
        System.out.print("Your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    private static void sortByPrompt(Scanner sc, AddressBookService addressBookService) {
        System.out.println("1. By first name");
        System.out.println("2. By last name");
        System.out.println("3. By city");
        System.out.println("4. By state");
        System.out.println("5. By zip");
        System.out.println("6. Back");
        System.out.print("Your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                addressBookService.sortBy(Contact::getfName).forEach(System.out::println);
                break;
            case 2:
                addressBookService.sortBy(Contact::getlName).forEach(System.out::println);
                break;
            case 3:
                addressBookService.sortBy(Contact::getCity).forEach(System.out::println);
                break;
            case 4:
                addressBookService.sortBy(Contact::getState).forEach(System.out::println);
                break;
            case 5:
                addressBookService.sortBy(Contact::getZip).forEach(System.out::println);
                break;
            case 6:
                return;
            default:
                System.out.println("INVALID CHOICE!");
        }
    }

    private static void deleteContact(AddressBookService book, Scanner sc, String name) {
        List<Contact> sameName = book.searchByName(name);

        if (sameName.isEmpty())
            System.out.println("NOT FOUND!");
        else if (sameName.size() == 1) {
            book.deleteContact(sameName.get(0));
            System.out.println("Contact Deleted!");
        } else {
            sameName.forEach(x -> System.out.println(sameName.indexOf(x) + "  " + x.toString()));
            System.out.println("Enter an index to delete: ");
            book.deleteContact(sameName.get(sc.nextInt()));
            System.out.println("Contact Deleted!");
        }
    }

    private static void editContact(AddressBookService book, Scanner sc, String name) {
        List<Contact> sameName = book.searchByName(name);
        if (sameName.isEmpty())
            System.out.println("NOT FOUND!");
        else if (sameName.size() == 1) {
            book.editContact(sameName.get(0), Utility.readContact(sc));
            System.out.println("Contact Modified!");
        } else {
            sameName.forEach(x -> System.out.println(sameName.indexOf(x) + "  " + x.toString()));
            System.out.println("Enter an index to edit: ");
            int index = sc.nextInt();
            sc.nextLine();
            book.editContact(sameName.get(index), Utility.readContact(sc));
            System.out.println("Contact Modified!");
        }
    }

    public static void run(AddressBookService book, Scanner sc) {
        while (true) {
            switch (addressPrompt(sc)) {
                case 1: //Add
                    if (book.addContact(Utility.readContact(sc)))
                        System.out.println("Contact Added!");
                    else System.out.println("Contact already exists");
                    break;

                case 2: //Edit
                    System.out.println("Enter name to edit: ");
                    editContact(book, sc, sc.nextLine());
                    break;

                case 3: //Delete
                    System.out.println("Enter name to delete: ");
                    deleteContact(book, sc, sc.nextLine());
                    break;

                case 4: //Print
                    System.out.println(book.toString());
                    break;
                case 5:
                    sortByPrompt(sc, book);
                    break;
                case 6: //Quit
                    return;

                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }

    }
}
