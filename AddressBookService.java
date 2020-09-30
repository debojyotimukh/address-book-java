import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class AddressBookService {
    private final ArrayList<Contact> contactList = new ArrayList<>();

    public boolean addContact(Contact contact) {
        String key = contact.getfName();

        List<Contact> filteredByFName = contactList.stream().
                filter(person -> person.getfName().equalsIgnoreCase(key)).
                collect(Collectors.toList());

        for (Contact sameName : filteredByFName)
            if (sameName.equals(contact))
                return false;
        contactList.add(contact);
        return true;
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