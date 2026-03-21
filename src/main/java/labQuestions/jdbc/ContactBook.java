package labQuestions.jdbc;

import java.sql.*;
import java.util.Scanner;

public class ContactBook {
    static final String URL = "";
    static final String USER = "";
    static final String PASS = "";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true){
            System.out.println("\n1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.println("Choose: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch){
                case 1: addContact(); break;
                case 2: viewContacts(); break;
                case 3: updateContact(); break;
                case 4: deleteContact(); break;
                case 5: System.exit(0);
            }
        }
    }

    static void addContact(){
        try(Connection con = DriverManager.getConnection(URL, USER, PASS)){
            System.out.println("Enter Name: ");
            String name = sc.nextLine();

            System.out.println("Enter your email: ");
            String email = sc.nextLine();

            System.out.println("Enter your mobile number: ");
            String mobile = sc.nextLine();

            String sql = "INSERT INTO contacts(name, email, mobile) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);

            ps.executeUpdate();

            System.out.println("Contact Added Successfully");
        }catch(SQLIntegrityConstraintViolationException e){
            System.out.println("The email or mobile number you added already exists");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    static void viewContacts(){
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contacts");

            System.out.println("\nID\tName\tEmail\tMobile");

            while(rs.next()){
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getString("email") + "\t"
                        + rs.getString("mobile"));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    static void updateContact(){
        try(Connection con = DriverManager.getConnection(URL, USER, PASS)){
            System.out.println("Enter Contact ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter the new name: ");
            String name = sc.nextLine();

            System.out.println("Enter the new email address: ");
            String email = sc.nextLine();

            System.out.println("Enter the new mobile number: ");
            String mobile = sc.nextLine();

            String sql = "UPDATE contacts SET name=?, eamil=?, mobile=?, WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setInt(4, id);

            ps.executeUpdate();

            System.out.println("Contact Updated");
        }catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Duplicate Email ot Mobile not allowed");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    static void deleteContact(){
        try(Connection con = DriverManager.getConnection(URL, USER, PASS)){
            System.out.println("Enter the contact ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM contacts WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Contact Deleted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
