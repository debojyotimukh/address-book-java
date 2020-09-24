import java.util.ArrayList;
import java.util.Scanner;

class Contacts {
    private String fName;
    private String lName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;

    public Contacts(String fName, String lName, String address, String city, String state, String zip, String phone,
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

}

class AddressBookService {
    private ArrayList<Contacts> contactList = new ArrayList<>();

    public void addContact(Contacts contact) {
        contactList.add(contact);
    }

    @Override
    public String toString(){
        StringBuilder sBuilder=new StringBuilder();
        for(Contacts contacts:contactList)
            sBuilder.append(contacts.toString()+"\n");
        
        return sBuilder.toString();
    }
}

public class AddressBookMain {
    private static Contacts readContact(Scanner sc) {
        System.out.println("FIRST NAME: ");
        String fName = sc.next();
        System.out.println("LAST NAME: ");
        String lName = sc.next();
        System.out.println("ADDRESS: ");
        String address = sc.nextLine();
        System.out.println("CITY: ");
        String city = sc.nextLine();
        System.out.println("STATE: ");
        String state = sc.nextLine();
        System.out.println("ZIP: ");
        String zip = sc.next();
        System.out.println("PHONE NUMBER: ");
        String phone = sc.next();
        System.out.println("EMAIL ADDRESS: ");
        String email = sc.next();

        return new Contacts(fName, lName, address, city, state, zip, phone, email);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Address Book Program");
        Scanner sc = new Scanner(System.in);
        AddressBookService book = new AddressBookService();

        book.addContact(readContact(sc));

        System.out.println(book.toString());
    }
}