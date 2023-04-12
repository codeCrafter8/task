class Address {
    private final Geo geolocation;
    private final String city;
    private final String street;
    private final int number;
    private final String zipcode;
    Address(String city, String street, int number, String zipcode, double lat, double lng){
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.geolocation = new Geo(lat, lng);
    }
    public Geo getGeo() {
        return geolocation;
    }
}