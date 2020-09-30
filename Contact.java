public class Contact {
    private final String fName;
    private final String lName;
    private final String address;
    private final String city;
    private final String state;
    private final String zip;
    private final String phone;
    private final String email;

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

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Contact))
            return false;

        Contact other = (Contact) obj;
        if (!(fName.equalsIgnoreCase(other.getfName())))
            return false;
        if (!(lName.equalsIgnoreCase(other.getlName())))
            return false;
        if (!(address.equalsIgnoreCase(other.getAddress())))
            return false;
        if (!(city.equalsIgnoreCase(other.getCity())))
            return false;
        if (!state.equalsIgnoreCase(other.getState()))
            return false;
        if (!zip.equals(other.getZip()))
            return false;
        if (!(phone.equals(other.getPhone())))
            return false;
        return email.equals(other.getEmail());
    }

    @Override
    public String toString() {
        return fName + "\t" + lName + "\t" + address + "\t" + city + "\t" + state + "\t" + zip + "\t" + phone + "\t"
                + email;
    }

}
