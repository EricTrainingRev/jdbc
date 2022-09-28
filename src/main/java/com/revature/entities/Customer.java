package com.revature.entities;

/*
    Objects of this class are used to store data from the database, or to pass relevant information into the
    methods that interact with the database
 */

public class Customer {
    public int customerId;
    public String firstName;
    public String lastName;

    public Customer(){}

    public Customer(int customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
