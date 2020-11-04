package com.capgemini.assignment.addressbook.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.capgemini.assignment.addressbook.model.Contact;
import com.capgemini.assignment.addressbook.service.AddressBook;

public class DBService {
    private static volatile DBService INSTANCE;

    public static DBService getInstance() {
        // double checking lock
        if (null == INSTANCE) {
            // synchronized block
            synchronized (DBService.class) {
                if (null == INSTANCE) {
                    INSTANCE = new DBService();
                }
            }
        }

        return INSTANCE;
    }

    private Connection connection = null;

    private DBService() {
        try {
            this.connection = getConnection();
        } catch (DBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Reads db.properties file and establish connection to database
     * 
     * @return connection to the database
     * @throws DBException
     */
    public static Connection getConnection() throws DBException {

        Connection connection = null;
        try (FileInputStream fStream = new FileInputStream("db.properties")) {

            // load properties file
            Properties properties = new Properties();
            properties.load(fStream);

            // assign db parameters
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            // create a connection from the properties
            connection = DriverManager.getConnection(url, user, password);

        } catch (IOException e1) {
            throw new DBException("Failed to read db.properties");
        } catch (SQLException e2) {
            throw new DBException(e2.getMessage());
        }
        return connection;
    }

    public List<String> getBookNames() {
        List<String> bookNames = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookNames.add(resultSet.getString("book_name"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bookNames;
    }

    public void newBook(String bookName) throws DBException {
        String sql = String.format("INSERT INTO Books(book_name) VALUES('%s')", bookName);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new DBException("Failed to add new address book: " + e.getMessage());
        }

    }

    public void deleteBook(String bookName) throws DBException {
        String sql = String.format("DELETE FROM Books where book_name='%s'", bookName);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new DBException("Failed to delete address book: " + e.getMessage());
        }
    }

    public int addContact(Contact contact, String bookName) throws DBException {
        int bookId = -1;
        // get required book id
        String bookIdSQL = String.format("SELECT * FROM Books WHERE book_name='%s'", bookName);
        try {
            PreparedStatement bookIdStatement = connection.prepareStatement(bookIdSQL);
            ResultSet resultSet = bookIdStatement.executeQuery();
            while (resultSet.next()) {
                bookId = resultSet.getInt("id");
            }
        } catch (Exception e) {
            // TODO: handle exception

            e.printStackTrace();
        }

        try {

            String addContactSQL = "insert into contacts(first_name,last_name,address,city,state,zip,phone_no,email,book_id) values (?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(addContactSQL, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, contact.getfName());
            statement.setString(2, contact.getlName());
            statement.setString(3, contact.getAddress());
            statement.setString(4, contact.getCity());
            statement.setString(5, contact.getState());
            statement.setString(6, contact.getZip());
            statement.setString(7, contact.getPhone());
            statement.setString(8, contact.getEmail());
            statement.setInt(9, bookId);

            int contactId = statement.executeUpdate();
            return contactId;
        } catch (SQLException e) {
            throw new DBException("Failed to add contact: " + e.getMessage());
        }
    }

    public boolean editContact(String name, Contact modified, String bookName) throws DBException {
        try {
            deleteContact(bookName, name);
            addContact(modified, bookName);
        } catch (DBException e) {
            throw new DBException("Failed to edit Contact: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteContact(String bookName, String contactName) throws DBException {
        String sql = "delete c from contacts c join books b on c.book_id=b.id where c.first_name=? and b.book_name=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, contactName);
            statement.setString(2, bookName);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DBException("Failed to delete contact: " + e.getMessage());
        }

    }

    public List<Contact> searchByName(String name) {
        return null;
    }

    public List<Contact> searchByCity(String city) {
        return null;
    }

    public List<Contact> searchByState(String state) {
        return null;
    }

    public Map<String, Integer> countByCity() throws DBException {
        String sql = " select city, count(city) as count from contacts group by city";
        Map<String, Integer> cityMap = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String city = resultSet.getString(1);
                int count = resultSet.getInt(2);
                cityMap.put(city, count);
            }
            return cityMap;
        } catch (SQLException e) {
            throw new DBException("Failed to get citywise count: " + e.getMessage());
        }
    }

    public Map<String, Integer> countByState() throws DBException {
        String sql = "select state,count(state) from contacts group by state;";
        Map<String, Integer> stateMap = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String state = resultSet.getString(1);
                int count = resultSet.getInt(2);
                stateMap.put(state, count);
            }
            return stateMap;
        } catch (SQLException e) {
            throw new DBException("Failed to get Statewise count: " + e.getMessage());
        }

    }

    public AddressBook fetchAddressBook(String bookName) throws DBException {
        String sql = "select * from contacts c,books b where c.book_id=b.id and b.book_name=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bookName);
            ResultSet resultSet = statement.executeQuery();
            return parseAddressBook(bookName, resultSet);
        } catch (SQLException e) {
            throw new DBException("Failed to retrieve: " + e.getMessage());
        }

    }

    private AddressBook parseAddressBook(String bookName, ResultSet resultSet) throws SQLException {
        AddressBook addressBook = new AddressBook(bookName);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String address = resultSet.getString("address");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");
            String zip = resultSet.getString("zip");
            String phone = resultSet.getString("phone_no");
            String email = resultSet.getString("email");
            Contact contact = new Contact(id, firstName, lastName, address, city, state, zip, phone, email);
            addressBook.addContact(contact);
        }
        return addressBook;
    }

    public Map<String, AddressBook> fetchAllAddressBooks() {
        Map<String, AddressBook> bookMap = new HashMap<>();
        List<String> booknames = this.getBookNames();
        booknames.forEach(bookName -> {
            try {
                AddressBook addressBook = this.fetchAddressBook(bookName);
                bookMap.put(bookName, addressBook);
            } catch (DBException e) {

            }
        });
        return bookMap;
    }

    public AddressBook fetchByDateAdded(LocalDate dateAdded) throws DBException {
        String sql = "select * from contacts where date_added=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(dateAdded));
            ResultSet resultSet = statement.executeQuery();
            return parseAddressBook("added in" + dateAdded.toString(), resultSet);
        } catch (SQLException e) {
            throw new DBException("Failed to get contacts by date added: " + e.getMessage());
        }

    }
}
