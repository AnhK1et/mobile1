package com.example.anhkiet3;
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;
    public static class Name {
        public String firstname;
        public String lastname;
        public Name(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }
    }
    public static class Address {
        public String city;
        public String street;
        public int number;
        public String zipcode;
        public Geolocation geolocation;
        public Address(String city, String street, int number, String zipcode, Geolocation geolocation) {
            this.city = city;
            this.street = street;
            this.number = number;
            this.zipcode = zipcode;
            this.geolocation = geolocation;
        }
    }
    public static class Geolocation {
        public String lat;
        public String _long;
        public Geolocation(String lat, String _long) {
            this.lat = lat;
            this._long = _long;
        }
    }
    public RegisterRequest(String email, String username, String password, Name name, Address address, String phone) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
} 