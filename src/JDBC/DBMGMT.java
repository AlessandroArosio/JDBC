package JDBC;

// import from Maven "postgres9.x"
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DBMGMT {

    private static Connection dbConnection(){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/java",
                            "postgres", "insert_password");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    public static void createTable(){
        Connection connection = dbConnection();
        Statement statement;
        try {
            statement = connection.createStatement();

            String sql = "CREATE TABLE People " +
                    "(ID SERIAL PRIMARY KEY NOT NULL, " +
                    "NAME TEXT NOT NULL, " +
                    "AGE INT NOT NULL, " +
                    "ADDRESS char(50))";
            statement.executeUpdate(sql);
            statement.close();

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static void insert(){
        Connection connection = dbConnection();
        Statement statement;
        try{
            statement = connection.createStatement();
            String sql = "INSERT INTO people (name, age, address)" +
                    "VALUES ('Mike', 31, 'Leeds');";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void fetchFromDB(){
        Connection connection = dbConnection();
        Statement statement;
        try{
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("Select * from people;");
            while (result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                String address = result.getString("address");
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Age: " + age);
                System.out.println("Address: " + address);
                System.out.println();
            }
            result.close();
            statement.close();
            connection.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        System.out.println("Operation successful");
    }

    public static void update(){
        Connection connection = dbConnection();
        Statement statement;

        try{
            statement = connection.createStatement();
            String sql = "UPDATE people SET name='John' WHERE id = 2;";
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Record updated");
    }

    public static void deleteRow(){
        Connection connection = dbConnection();
        Statement statement;

        try{
            statement = connection.createStatement();
            String sql = "DELETE from People WHERE id=4;";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Record deleted");
    }

    public static void sendStatement(){
        Connection connection = dbConnection();
        Statement statement;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please insert a SQL statement:\n");
        String sql = scan.nextLine();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("The statement: " + sql + "\nhas been executed");
    }
}
