import java.util.*;

public class AddressBookMain {
    private static int welcomePrompt(Scanner sc) {
        System.out.println("\n\nWelcome to Address Book Program");
        System.out.println("1. New Address Book");
        System.out.println("2. Select Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Quit");
        System.out.print("Your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Dictionary<String, AddressBookService> library = new Hashtable<>();
        while (true) {
            switch (welcomePrompt(sc)) {
                case 1:
                    System.out.println("Name of new address book: ");
                    String bookName = sc.next();
                    sc.nextLine();
                    library.put(bookName, new AddressBookService());
                    AddressBookCLI.run(library.get(bookName), sc);
                    break;
                case 2:
                    System.out.println("Available books are: ");
                    for (Enumeration<String> i = library.keys(); i.hasMoreElements(); ) {
                        System.out.println(i.nextElement() + ",");
                    }
                    System.out.println("Open Book: ");
                    String name = sc.nextLine();
                    System.out.println("Current: " + name);
                    AddressBookCLI.run(library.get(name), sc);
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