import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class User {
    private final Address address;
    private final int id;
    private final String email;
    private final String username;
    private final String password;
    private final String phone;
    private final String firstname;
    private final String lastname;
    private final int __v;

    public User(int id, String firstname, String lastname, String email, String username, String password, String city, String street, int number, String zipcode, double lat, double lng, String phone, int __v) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = new Address(city, street, number, zipcode, lat, lng);
        this.phone = phone;
        this.__v = __v;
    }

    public Address getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }
    public String getFullName() {
        return firstname.replace("\"", "") + " " + lastname.replace("\"", "");
    }

}
