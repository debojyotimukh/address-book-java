import java.util.*;

public class AddressBookMain {
    private static final List<AddressBookService> library = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    private static Map<String, List<Contact>> consolidatedCityMap() {
        Map<String, List<Contact>> cityMap = new HashMap<>();
        for (AddressBookService book : library) {
            book.cityMap().forEach((key, value) -> cityMap.merge(key, value, (vo, vn) -> {
                vo.addAll(vn);
                return vo;
            }));
        }

        return cityMap;
    }

    private static Map<String, List<Contact>> consolidatedStateMap() {
        Map<String, List<Contact>> stateMap = new HashMap<>();
        for (AddressBookService book : library) {
            book.stateMap().forEach((key, value) -> stateMap.merge(key, value, (vo, vn) -> {
                vo.addAll(vn);
                return vo;
            }));
        }

        return stateMap;
    }

    private static int locateIndex(String name) {
        for (int i = 0; i < library.size(); i++)
            if (library.get(i).getBookName().equals(name))
                return i;
        return -1;
    }

    private static int welcomePrompt() {
        System.out.println("\n\nWelcome to Address Book Program");
        System.out.println("1. New Address Book");
        System.out.println("2. Select Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Search");
        System.out.println("5. Count");
        System.out.println("6. Quit");
        System.out.print("Your choice: ");
        int choice = AddressBookMain.sc.nextInt();
        AddressBookMain.sc.nextLine();
        return choice;
    }

    private static void searchByPrompt() {
        System.out.println("1. By name");
        System.out.println("2. By city");
        System.out.println("3. By state");
        System.out.println("4. Back");
        System.out.println("Your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter name: ");
                String name = sc.nextLine();
                library.forEach(book -> book.searchByName(name).forEach(System.out::println));
                break;
            case 2:
                System.out.println("Enter city: ");
                String city = sc.nextLine();
                library.forEach(book -> book.searchByCity(city).forEach(System.out::println));
                break;
            case 3:
                System.out.println("Enter state: ");
                String state = sc.nextLine();
                library.forEach(book -> book.searchByState(state).forEach(System.out::println));
                break;
            case 4:
                return;
            default:
                System.out.println("INVALID CHOICE!");
        }

    }

    private static void countPrompt() {
        System.out.println("1. By city");
        System.out.println("2. By state");
        System.out.println("3. Back");
        System.out.println("Your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                consolidatedCityMap().forEach((k, v) -> System.out.println(k + "\t" + v.size()));
                break;
            case 2:
                consolidatedStateMap().forEach((k, v) -> System.out.println(k + "\t" + v.size()));
                break;
            case 3:
                return;
            default:
                System.out.println("INVALID CHOICE!");
        }
    }

    private static void addBook(String bookName) {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.setBookName(bookName);
        library.add(addressBookService);
        AddressBookCLI.run(addressBookService, sc);
    }

    public static void main(String[] args) {
        while (true) {
            switch (welcomePrompt()) {
                case 1: //add
                    System.out.println("Name of new address book: ");
                    String bookName = sc.next();
                    sc.nextLine();
                    addBook(bookName);
                    break;
                case 2: //select
                    System.out.println("Available books are: ");
                    library.forEach(book -> System.out.println(book.getBookName() + " ,"));
                    System.out.println("Open Book: ");
                    String name = sc.nextLine();
                    System.out.println("Current: " + name);
                    AddressBookCLI.run(library.get(locateIndex(name)), sc);
                    break;
                case 3: //delete
                    System.out.println("Enter name to delete: ");
                    name = sc.nextLine();
                    library.remove(locateIndex(name));
                    break;
                case 4:
                    searchByPrompt();
                    break;

                case 5: //count by city/state
                    countPrompt();
                    break;
                case 6: //quit
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }
    }


}