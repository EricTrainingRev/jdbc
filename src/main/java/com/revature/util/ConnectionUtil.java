package com.revature.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
    This ConnectionUtil class is used to handle creating a connection object for us to interact with our
    database. It includes a method that utilizes a properties file and one that uses environment variables
    to abstract database credentials away from the developer. Make sure you don't accidentally upload any
    sensitive data to your central repository, especially if said repository is public.
 */

public class ConnectionUtil {

    public static Connection createConnectionUsingPropertiesFile(){
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/creds.properties"))) {
            Properties creds = new Properties();
            creds.load(input);
            return DriverManager.getConnection(
                    creds.getProperty("url"),
                    creds.getProperty("user"),
                    creds.getProperty("password")
            );
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection createConnectionUsingEnvironmentVariables(){
        try {
            return DriverManager.getConnection(String.format(
                    "jdbc:postgresql://%s:%s/%s?user=%s&password=%s",
                    System.getenv("HOST"),
                    System.getenv("PORT"),
                    System.getenv("NAME"),
                    System.getenv("USER"),
                    System.getenv("PASSWORD")
            ));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(createConnectionUsingPropertiesFile());
        System.out.println(createConnectionUsingEnvironmentVariables());
    }

}
