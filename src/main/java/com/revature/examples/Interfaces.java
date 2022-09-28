package com.revature.examples;

import com.revature.entities.Customer;
import com.revature.util.ConnectionUtil;

import java.sql.*;

public class Interfaces {
    // Note: I am creating static methods here for the sake of easy examples: the methods don't need to be static to work

    public static void getTime(){
        // this format will work with any static query: just change the ResultSet method call to match the data
        try (Connection conn = ConnectionUtil.createConnectionUsingPropertiesFile()){
            Statement statement = conn.createStatement();
            String sql = "select now()";
            ResultSet rs = statement.executeQuery(sql);
            rs.next(); // this moves the ResultSet cursor to the first row of data
            System.out.println(rs.getTimestamp("now")); //could also return as a string
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void getCustomerById(int id) {
        // this method uses the PreparedStatement interface, which lets us pass data into the query we send
        try(Connection conn = ConnectionUtil.createConnectionUsingPropertiesFile()){
            String sql = "select * from customer where customer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery(); // this returns a result set, so we assign the value to a variable
            rs.next();
            /*
                The value below is being printed to the console, you could just as easily assign the data to an
                object, transform the data and pass it into another method, etc. Also, printf() is a convenient
                way of creating a formatted string and passing the result to the console
             */
            System.out.printf(
                    "%s, %s, %s%n", // %n at the end makes the terminal go to a new line
                    rs.getInt(1), // postgres column indexing starts at 1
                    rs.getString(2),
                    rs.getString("last_name")
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllCustomers(){
        try(Connection conn = ConnectionUtil.createConnectionUsingPropertiesFile()){
            String sql = "select * from customer";
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()){
                System.out.printf(
                        "%s, %s, %s%n",
                        rs.getInt(1), // postgres column indexing starts at 1
                        rs.getString("first_name"),
                        rs.getString(3)
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateCustomerById(Customer customer){
        try(Connection conn = ConnectionUtil.createConnectionUsingPropertiesFile()){
            String sql = "update customer set first_name = ?, last_name = ? where customer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.firstName);
            ps.setString(2, customer.lastName);
            ps.setInt(3, customer.customerId);
            System.out.println(ps.executeUpdate()); // returns the number of rows affected by the statement
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void createCustomer(Customer customer){
        try(Connection conn = ConnectionUtil.createConnectionUsingPropertiesFile()){
            String sql = "insert into customer values (default, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.firstName);
            ps.setString(2, customer.lastName);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            System.out.println(rs.getInt(1));
            /*
                A trick for validating to a user their account has been created is returning their newly created
                id for them to see: this can be done by sending it directly, adding it to the entity used to pass
                their data into the database, or in other ways
             */
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteCustomerById(int id){
        try(Connection conn = ConnectionUtil.createConnectionUsingPropertiesFile()){
            String sql = "delete from customer where customer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            System.out.println(ps.executeUpdate());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        deleteCustomerById(5);
    }

}
