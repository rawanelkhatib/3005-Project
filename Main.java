import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;


public class Main {

    public static String username_global;
    public static boolean loggedIn = false;
    public static boolean owner = false;
    public static ArrayList<String> cart = new ArrayList<>();
    public static ArrayList<Integer> cartQuantity = new ArrayList<>();
    public static int saleNextId;
    public static int pubNextId;


    public static boolean update(Connection con, String s) { //sql related method
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(s);
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public static ResultSet query(Connection con, String q) { //sql related method
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(q);
        } catch (Exception e){
            System.out.println(e);
            System.exit(0);
            return null;
        }
    }

    public static int size(ResultSet s) { //sql related method
        int counter = 0;
        try {
            while(s.next()) {
                counter++;
            }
        } catch (Exception e){
            System.out.println(e);
            System.exit(0);
        }
        return counter;
    }


    public static void loginMenu(Connection con) {
        //login/signup menu
        while(!loggedIn) {
            System.out.println("Welcome to Look-A-Book BookStore!\nEnter 1 to login.\nEnter 2 to make an account.\nEnter 0 at any time to exit.");

            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();

            switch (choice) {
                case "1" -> login(con);
                case "2" -> signUp(con);
                case "0" -> System.exit(0);
                default -> System.out.println("Incorrect input try again.");
            }
        }

        if (owner) {
            ownerMenu(con);
        } else {
            customerMenu(con);
        }

    }



    public static void login(Connection con) { //login/signup related method
        try {
                System.out.println("Login Page:\nEnter your username or '0' to return to the menu.");
                Scanner in = new Scanner(System.in);
                String username = in.nextLine();
                if (username.equals("0")) {
                    return;
                }

                ResultSet rs = query(con, "select username, password, owner from profile where username = '" + username + "'");
                rs.next();
                if (!rs.getString(1).equals(username)) {
                    System.out.println("There are no users with that username. Try again.");
                    login(con); //never reached as rs is empty when username non-existent
                }



                System.out.println("Enter your password.");
                in = new Scanner(System.in);
                String password = in.nextLine();

                if (!rs.getString(2).equals(password)) {
                    System.out.println("Incorrect password. Try again.");
                    login(con);
                } else {
                    if (rs.getInt(3)==1) {
                        owner = true;
                    }
                    username_global = username;
                    loggedIn = true;
                    System.out.println("Login successful.");
                    return;
                }

        } catch (Exception e){
            System.out.println("There are no users with that username. Try again.");
            login(con);
        }
    }

    public static void logout(Connection con) { //logs user out and returns to login page
        username_global = "";
        loggedIn = false;
        owner = false;
        cart.clear();
        cartQuantity.clear();
        loginMenu(con);
    }

    public static void signUp(Connection con) { //login/signup related method
        try {
                System.out.println("Sign-Up Page:\nEnter your username or '0' to return to the menu.");
                Scanner in = new Scanner(System.in);
                String username = in.nextLine();
                if (username.equals("0")) {
                    return;
                }

                ResultSet rs = query(con, "select username from profile where username = '" + username + "'");

                if (size(rs) != 0) {
                    System.out.println("Username already taken try again.");
                    signUp(con);
                } else {
                    System.out.println("Enter your password. (Min 4 characters)");
                    in = new Scanner(System.in);
                    String password = in.nextLine();

                    if (password.length() < 4) {
                        System.out.println("Error. Your password must be at least 4 characters long for security purposes. Try again.");
                        signUp(con);
                    } else {
                        System.out.println("Enter your full name.");
                        in = new Scanner(System.in);
                        String fullName = in.nextLine();

                        System.out.println("Enter your full shipping address."); //any input is accepted
                        in = new Scanner(System.in);
                        String shipping = in.nextLine();

                        System.out.println("Enter your full banking information."); //any input is accepted
                        in = new Scanner(System.in);
                        String banking = in.nextLine();

                        //user does not have option to be an owner
                        if (update(con, "insert into profile values('" + username + "', '" + fullName + "', '" + password + "', '" +
                                shipping + "', '" + banking + "', 0)")) {
                            System.out.println("Signup successful. Returning to the menu.");
                            return;
                        }
                    }
                }

        } catch (Exception e){ System.out.println(e);}
    }

    public static void ownerMenu(Connection con) {
            System.out.println("Owner Menu:\nEnter 1 to add books to the collection.\nEnter 2 remove books from the collection\n" +
                    "Enter 3 to view sales reports.\nEnter 0 to logout.");

            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();

        switch (choice) {
            case "0" -> logout(con);
            case "1" -> addBook(con);
            case "2" -> removeBook(con);
            case "3" -> browseSales(con);
            default -> System.out.println("Invalid input try again.");
        }
    }

    public static int addPublisher(Connection con){
        int pub_id = pubNextId;
        try {
            System.out.println("Adding publisher Information");
            //getting publisher name
            System.out.println("Enter the name of the publisher");
            Scanner in11 = new Scanner(System.in);
            String pub_name = in11.nextLine();
            //getting publisher address
            System.out.println("Enter the address of the publisher");
            Scanner in12 = new Scanner(System.in);
            String address = in12.nextLine();
            //getting email
            System.out.println("Enter the email of the publisher");
            Scanner in13 = new Scanner(System.in);
            String email = in13.nextLine();
            //getting phone
            System.out.println("Enter the publisher's phone number");
            Scanner in14 = new Scanner(System.in);
            String phone = in14.nextLine();
            //getting banking info
            System.out.println("Enter the banking information of the publisher");
            Scanner in15 = new Scanner(System.in);
            String banking = in15.nextLine();

            update(con, "insert into publisher values ( ' " + pubNextId + " ', ' " + pub_name + " ', ' " + address + " ', ' " + email + " ', ' " + phone + " ', ' " + banking + " ')");
            pubNextId++;
        }catch (Exception e) {
            System.out.println("Error entering wrong information, try again");
            addBook(con);
        }
        return pub_id;
    }
    public static void addBook(Connection con){
        System.out.println("Does the publisher of this book already exist in the records?\nEnter 1 for yes and 0 for no\n");
        try {
            ResultSet rs = query(con, "select pub_id, pub_name from publisher");
            while (rs.next()) {
                System.out.println("Publisher ID " + rs.getString(1) + ". Publisher Name: " + rs.getString(2));
            }
        }catch (Exception e) { System.out.println("Error. No Publishers with your request."); }

        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        int pub_id;

        if(choice.equals("0")) {
            pub_id = addPublisher(con);
        } else {
            System.out.println("Enter the publisher ID of the book");
            Scanner in6 = new Scanner(System.in);
            pub_id = in6.nextInt();
        }
        try {
            try {
                ResultSet rs = query(con, "select ISBN, book_name from book where pub_id = ' " + pub_id + " ' ");
                while (rs.next()) {
                    System.out.println("ISBN " + rs.getString(1) + ". Book Name: " + rs.getString(2));
                }
            }catch (Exception e) { System.out.println("Error. No Books with your request."); }
            //System.out.println("Adding a new book");
            //getting isbn
            System.out.println("Enter the ISBN of the book");
            Scanner in1 = new Scanner(System.in);
            String ISBN = in1.nextLine();
            //getting name
            System.out.println("Enter the name of the book");
            Scanner in2 = new Scanner(System.in);
            String name = in2.nextLine();
            //getting author
            System.out.println("Enter the author of the book");
            Scanner in3 = new Scanner(System.in);
            String author = in3.nextLine();
            //getting genre
            System.out.println("Enter the genre of the book");
            Scanner in4 = new Scanner(System.in);
            String genre = in4.nextLine();
            //getting number of pages
            System.out.println("Enter the number of pages of the book");
            Scanner in5 = new Scanner(System.in);
            int pages = in5.nextInt();
            //getting price
            System.out.println("Enter the price of the book");
            Scanner in7 = new Scanner(System.in);
            double price = in7.nextDouble();
            //getting cost
            System.out.println("Enter the cost of the book");
            Scanner in8 = new Scanner(System.in);
            double cost = in8.nextDouble();
            //getting stock
            System.out.println("Enter the stock of the book");
            Scanner in9 = new Scanner(System.in);
            int stock = in9.nextInt();
            //getting percentage
            System.out.println("Enter the percentage of the book");
            Scanner in10 = new Scanner(System.in);
            int percentage = in10.nextInt();

            update(con, "insert into book values ( ' " + ISBN + " ', ' " + name + " ', ' " + author + " ', ' " + genre + " ', ' " + pages + " ', ' " + pub_id + " ', ' " + price + " ', ' " + cost + " ', ' " + stock + " ', ' " + percentage + " ')");
        }catch(Exception e) {System.out.println("Error entering wrong information, try again");
            addBook(con);
        }
        System.out.println("Book Added Successfully!");
        ownerMenu(con);
    }

    public static void removeBook (Connection con){
        ArrayList<String> bookList = new ArrayList<>();
        try {
            ResultSet rs = query(con, "select ISBN, book_name, author from book");
            while (rs.next()) {
                System.out.println((bookList.size()+1) + ". Title: " + rs.getString(2) + ". Author: " + rs.getString(3) + ". ISBN: " + rs.getString(1));
                bookList.add(rs.getString(1));
            }
        }catch (Exception e) { System.out.println("Error. No Books with your request."); }

        try {
            System.out.println("Enter number of the book that you want to delete or enter 0 to return to the owner menu.");
            Scanner in = new Scanner(System.in);
            int bookNum = parseInt(in.nextLine());

            if (bookNum == 0) {
                ownerMenu(con);
                return;
            } else if (bookNum > 0 && bookNum <= bookList.size()) {
                update(con, "delete from book where isbn = '" + bookList.get(bookNum-1) + "'");
            } else {
                System.out.println("Out of bounds try again.");
                removeBook(con);
                return;
            }
        }catch (Exception e) {
            System.out.println("Error. Invalid input, try again");
            removeBook(con);
        }
        System.out.println("Book Removed Successfully!");
        removeBook(con);
    }

    public static void browseSales (Connection con){
        System.out.println("What kind of sale would you like to view\nEnter 1 to browse sales per genre.\nEnter 2 to browse sales per author.\n" +
                "Enter 3 to browse sales by book name.\nEnter 4 to browse sales by book isbn.\nEnter 5 to browse by sales vs expenditures.\nEnter 0 to return to the owner menu.");

        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();

        switch (choice) {
            case "0" -> ownerMenu(con);
            case "1" -> saleByGenre(con);
            case "2" -> saleByAuthor(con);
            case "3" -> saleByBookName(con);
            case "4" -> saleByISBN(con);
            case "5" -> saleByPriceVSCost(con);
            default -> {
                System.out.println("Incorrect Input try again.");
                browseSales(con);
            }
        }
    }

    public static void saleByGenre(Connection con){ //do one for all
        try{
            ResultSet rs = query(con,"select distinct genre from book natural join book_sales");
            while(rs.next()) {
                ResultSet rs2 = query(con,"select getRevenueGenre('" + rs.getString(1) + "'), getCostGenre('" + rs.getString(1) + "')," +
                        "sum(num_sold) from book natural join book_sales where genre = '" + rs.getString(1) + "'");
                rs2.next();

                System.out.println("Genre: " + rs.getString(1) + ". Amount Sold: " + rs2.getInt(3) + ". Revenue: $" + rs2.getDouble(1) +
                        ". Cost: $" + rs2.getDouble(2) + ". Profit: $" + (rs2.getDouble(1)-rs2.getDouble(2)));
            }

        } catch (Exception e) { System.out.println("Error. No sales with your request."); }
        System.out.println();
        browseSales(con);
    }

    public static void saleByAuthor(Connection con){
        //System.out.println("What author would you like to check the sales for");
        //Scanner in = new Scanner(System.in);
        //String author = in.nextLine();

        try{
            ResultSet rs = query(con,"select distinct author from book natural join book_sales");
            while(rs.next()) {
                ResultSet rs2 = query(con,"select getRevenueAuthor('" + rs.getString(1) + "'), getCostAuthor('" + rs.getString(1) + "')," +
                        "sum(num_sold) from book natural join book_sales where author = '" + rs.getString(1) + "'");
                rs2.next();

                System.out.println("Author: " + rs.getString(1) + ". Amount Sold: " + rs2.getInt(3) + ". Revenue: $" + rs2.getDouble(1) +
                        ". Cost: $" + rs2.getDouble(2) + ". Profit: $" + (rs2.getDouble(1)-rs2.getDouble(2)));
            }

        } catch (Exception e) { System.out.println("Error. No sales with your request."); }
        System.out.println();
        browseSales(con);
    }

    public static void saleByBookName(Connection con){
        System.out.println("What book name would you like to check the sales for");
         Scanner in = new Scanner(System.in);
         String bookName = in.nextLine();

        try{
            ResultSet rs = query(con,"select distinct isbn, book_name, author from book natural join book_sales where upper(book_name) = upper('" + bookName + "')");
            while(rs.next()) {
                ResultSet rs2 = query(con,"select getRevenueBookName('" + bookName + "'), getCostBookName('" + bookName + "')," +
                        "sum(num_sold) from book natural join book_sales where isbn = '" + rs.getString(1) + "'");
                rs2.next();
                System.out.println("ISBN: " + rs.getString(1) + ". Book Name: " + rs.getString(2) + ". Author: " + rs.getString(3) + ".  Amount Sold: " + rs2.getInt(3) + ". Revenue: $" + rs2.getDouble(1) +
                        ". Cost: $" + rs2.getDouble(2) + ". Profit: $" + (rs2.getDouble(1)-rs2.getDouble(2)));
            }

        } catch (Exception e) { System.out.println("Error. No sales with your request."); }
        System.out.println();
        browseSales(con);
    }

    public static void saleByISBN(Connection con){
        System.out.println("What book isbn would you like to check the sales for");
        Scanner in = new Scanner(System.in);
        String isbn = in.nextLine();

        try{
            ResultSet rs = query(con,"select book_name, author from book natural join book_sales where isbn = '" + isbn + "'");
            rs.next();
            ResultSet rs2 = query(con,"select getRevenueISBN('" + isbn + "'), getCostISBN('" + isbn + "')," +
                    "sum(num_sold) from book natural join book_sales where isbn = '" + isbn + "'");
            rs2.next();
            System.out.println("ISBN: " + isbn + ". Book Name: " + rs.getString(1) + ". Author: " + rs.getString(2) + ".  Amount Sold: " + rs2.getInt(3) + ". Revenue: $" + rs2.getDouble(1) +
                    ". Cost: $" + rs2.getDouble(2) + ". Profit: $" + (rs2.getDouble(1)-rs2.getDouble(2)));


        } catch (Exception e) { System.out.println("Error. No sales with your request."); }
        System.out.println();
        browseSales(con);
    }

    public static void saleByPriceVSCost(Connection con){ //include profit margin, net profit, percent given to publishers etc
        try{
            ResultSet rs = query(con,"select sum(total), getTotalCostofSales(), getTotalCostInStock(), getCostToPublishers() from sale");
            rs.next();
            System.out.println("Total Sales vs Expenditures\nRevenue: $" + rs.getDouble(1) + ". Cost from sales: $" + rs.getDouble(2) +
                    ". Cost in Stock: $" + rs.getDouble(3) + ". Cost to Publishers: $" + rs.getDouble(4) + ". Profit: $" + (rs.getDouble(1) - rs.getDouble(2) - rs.getDouble(2) - rs.getDouble(3)));
        } catch (Exception e) { System.out.println("Error. No sales with your request."); }
        System.out.println();
        browseSales(con);
    }

    public static void customerMenu(Connection con) {
            System.out.println("Welcome to Look-A-Book BookStore main menu " + username_global + "!\nEnter 1 to browse/search the book library.\n" +
                    "Enter 2 to view previous purchases.\nEnter 0 to logout.");

            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();

        switch (choice) {
            case "0" -> logout(con);
            case "1" -> browse(con);
            case "2" -> viewPurchases(con);
        }
    }

    public static long difference(Date d1, Date d2) {
        return TimeUnit.DAYS.convert(d2.getTime() - d1.getTime(), TimeUnit.MILLISECONDS);
    }


    public static void viewPurchases(Connection con){
        try{
            ResultSet rs = query(con,"select sale_id, total, date from sale");
            System.out.println("Your Purchases:");
            while(rs.next()) {
                String status;
                long diff = difference(rs.getDate(3), new Date(System.currentTimeMillis()));

                if (diff >=3) {
                    status = "Delivered";
                } else if (diff >=2) {
                    status = "Shipping";
                } else {
                    status = "Processing";
                }

                System.out.println("Sale ID: " + rs.getString(1) + ". Total $" + rs.getString(2) + ". Date: " + rs.getDate(3) + ". Status: " + status);
            }

            System.out.println("Enter the sale id of the purchase to view its details or enter 0 to return to the customer menu");
             try {
                 Scanner in = new Scanner(System.in);
                 int choice = in.nextInt();
                 viewPurchaseInfo(con, choice);
                 customerMenu(con);
             } catch (Exception e) {
                 System.out.print("Incorrect input try again");
                 viewPurchases(con);
             }

        }catch (Exception e) { System.out.println("Error. No purchases with your request."); }

    }


    public static void viewPurchaseInfo(Connection con, int saleId) {
        try {
            ResultSet rs = query(con, "select book_name, author, genre, price, num_sold from book natural join book_sales where sale_id = " + saleId);
            int counter = 1;

            while(rs.next()) {
                System.out.println(counter + ". Name: " + rs.getString(1) + ". Author: " + rs.getString(2) + ". Genre: " + rs.getString(3)
                + ". Price: $" + rs.getDouble(4) + ". Quantity: " + rs.getInt(5));
                counter ++;
            }
        } catch (Exception e){
            System.out.println("Something went wrong.");
        }

    }



    public static void browse(Connection con) {
        System.out.println("Book Library:\nEnter 1 to browse all books in the library.\nEnter 2 to search by book name.\n" +
                "Enter 3 to search by isbn.\nEnter 4 to search by author name.\nEnter 5 to search by book genre.\nEnter 6 to view the cart.\nEnter 0 to return to the customer menu.");

        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();

        switch (choice) {
            case "0":
                customerMenu(con);

            case "1":
                addToCart(con, searchBooks(con));
                break;

            case "2":
                System.out.println("Enter the name of the book you would like to search for.");
                Scanner in2 = new Scanner(System.in);
                String choice2 = in2.nextLine();
                addToCart(con, searchBooks(con, "book_name", choice2));
                break;

            case "3":
                System.out.println("Enter the 13 number isbn of the book you would like to search for.");
                Scanner in3 = new Scanner(System.in);
                String choice3 = in3.nextLine();
                addToCart(con, searchBooks(con, "isbn", choice3));
                break;

            case "4":
                System.out.println("Enter the author name of the book you would like to search for.");
                Scanner in4 = new Scanner(System.in);
                String choice4 = in4.nextLine();
                addToCart(con, searchBooks(con, "author", choice4));
                break;

            case "5":
                System.out.println("Enter the genre of the book you would like to search for.");
                Scanner in5 = new Scanner(System.in);
                String choice5 = in5.nextLine();
                addToCart(con, searchBooks(con, "genre", choice5));
                break;

            case "6":
                viewCart(con);

                System.out.println("\nEnter 1 to remove an item from the cart\nEnter 2 to checkout\nEnter 0 to return to the library.");
                Scanner in6 = new Scanner(System.in);
                String choice6 = in6.nextLine();

                if (choice6.equals("0")) {
                    browse(con);
                } else if (choice6.equals("1")) {
                    removeFromCart(con);
                } else if (choice6.equals("2")) {
                    checkout(con);
                } else {
                    System.out.println("Incorrect input. Returning to library.");
                    browse(con);
                }
                break;

            default:
                System.out.println("Incorrect Input try again.");
                browse(con);
                break;
        }
    }

    public static ArrayList<String> searchBooks(Connection con, String column, String q) { //prints all books in library with given query
        try {
            ResultSet rs = query(con, "select isbn, book_name, author, genre, pages, price, stock from book where upper(" + column + ") like upper('" + q + "')");
            ArrayList<String> isbnList = new ArrayList<String>();
            int counter = 1;

            while(rs.next()) {
                isbnList.add(rs.getString(1));
                System.out.println(counter + ". Title: " + rs.getString(2) + ". Author: " + rs.getString(3) + ". Genre: " +
                        rs.getString(4) + ". Pages: " + rs.getInt(5) + ". Price: $" + rs.getDouble(6) + ". Stock: " + rs.getInt(7));
                counter++;
            }

            return isbnList;
        } catch (Exception e) {System.out.println("Error. No books in stock match your search.");}
            browse(con);
            return null;
    }

    public static ArrayList<String> searchBooks(Connection con) { //prints all books in library
        try {
            ResultSet rs = query(con, "select isbn, book_name, author, genre, pages, price, stock from book");
            ArrayList<String> isbnList = new ArrayList<String>();
            int counter = 1;

            while(rs.next()) {
                isbnList.add(rs.getString(1));
                System.out.println(counter + ". Title: " + rs.getString(2) + ". Author: " + rs.getString(3) + ". Genre: " +
                        rs.getString(4) + ". Pages: " + rs.getInt(5) + ". Price: $" + rs.getDouble(6) + ". Stock: " + rs.getInt(7));
                counter++;
            }

            return isbnList;
        } catch (Exception e) { System.out.println("Error. No books in stock match your search."); }
        browse(con);
            return null;
    }

    public static void addToCart(String isbn, int quantity) { //adds isbn and quantity to cart and cart quantity
        cart.add(isbn);
        cartQuantity.add(quantity);
    }

    public static void addToCart(Connection con, ArrayList<String> books) { //interface to add to the cart, works with view cart
        if (books == null || books.isEmpty()) {
            browse(con);
        } else {
            System.out.println("Enter the number of the book to add it to the cart or enter 0 to return to the library:");
            try {
                Scanner in11 = new Scanner(System.in);
                String choice11 = in11.nextLine();
                int choiceInt = parseInt(choice11) - 1;

                if (choiceInt == -1) {
                    browse(con);
                } else if (choiceInt >= 0 && choiceInt < books.size()) {
                    System.out.println("Enter the number of copies of this book you would like");
                    Scanner in12 = new Scanner(System.in);
                    String choice12 = in12.nextLine();
                    int quantity = parseInt(choice12);

                    ResultSet rs = query(con, "select stock from book where isbn = '" + books.get(choiceInt) + "'");
                    rs.next();

                    if (quantity < 1 || quantity >  rs.getInt(1)) {
                        System.out.println("Invalid quantity try again");
                        addToCart(con, books);
                    } else {
                        addToCart(books.get(choiceInt), quantity);
                        System.out.println("Successfully added!");
                        addToCart(con, books);
                    }
                } else {
                    System.out.println("Out of bounds try again.");
                    addToCart(con, books);
                }

            } catch (Exception e) {
                System.out.println("Invalid input try again.");
                addToCart(con, books);
            }

        }
    }

    public static void viewCart(Connection con) { //prints cart
        for (int i = 0; i < cart.size(); i++) {
            try {
                ResultSet rs = query(con, "select book_name, author, genre, pages, price, stock from book where isbn = '" + cart.get(i) + "'");
                rs.next();
                System.out.println((i+1) + ". Title: " + rs.getString(1) + ". Author: " + rs.getString(2) + ". Genre: " +
                            rs.getString(3) + ". Pages: " + rs.getInt(4) + ". Price: $" + rs.getDouble(5) + ". Quantity: " + cartQuantity.get(i) + ". Stock: " + rs.getInt(6));

            } catch (Exception e) {
                System.out.println("Error in getting book " + (i+1));
                break;
            }
        }
    }

    public static void removeFromCart(Connection con) {
        try {
            System.out.println("Enter the number of the book to remove from the cart or enter 0 to cancel.");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            int choiceInt = parseInt(choice) - 1;

            if (choiceInt == -1) {
                browse(con);
            } else if (choiceInt >= 0 || choiceInt < cart.size()) {
                cart.remove(choiceInt);
                cartQuantity.remove(choiceInt);
                System.out.println("Successfully removed!");
                removeFromCart(con);
            } else {
                System.out.println("Out of bounds try again.");
                removeFromCart(con);
            }

        } catch (Exception e) {
            System.out.println("Invalid input try again.");
            removeFromCart(con);
        }
    }

    public static void checkout(Connection con) {
        double total = 0;

        for (int i = 0; i < cart.size(); i++) {
            try {
                ResultSet rs = query(con, "select price from book where isbn = '" + cart.get(i) + "'");
                rs.next();
                total += rs.getDouble(1)*cartQuantity.get(i);

            } catch (Exception e) {
                System.out.println("Error in getting book " + (i+1));
                break;
            }
        }
        System.out.println("Your total is $" + total + "\nWould you like to proceed with the purchase?\nEnter 1 to proceed\nEnter 0 to return to library");
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();

        if (choice.equals("0")) {
            browse(con);
            return;
        } else if (choice.equals("1")) {
            System.out.println("Enter 1 to use your profile's shipping address\nEnter 2 to use a new one for this purchase\nEnter 0 to cancel the purchase and return to the library");
            Scanner in2 = new Scanner(System.in);
            String choice2 = in2.nextLine();
            String shipping;

            if (choice2.equals("0")) {
                browse(con);
                return;
            } else if (choice2.equals("1")) {
                try {
                    ResultSet rs2 = query(con, "select shipping from profile where username = '" + username_global + "'");
                    rs2.next();
                    shipping = rs2.getString(1);
                } catch (Exception e) {
                    System.out.println("Error in finding profile. Returning to checkout menu.");
                    checkout(con);
                    return;
                }

            } else if (choice2.equals("2")) {
                System.out.println("Enter the shipping address for this purchase");
                Scanner in3 = new Scanner(System.in);
                shipping = in3.nextLine();
            } else {
                System.out.println("Invalid input returning to checkout menu.");
                checkout(con);
                return;
            }


            try {

                update(con, "insert into sale values (" + saleNextId + ", '" + new Timestamp(System.currentTimeMillis()) + "', " + total + ", '" + shipping + "')");
                update(con, "insert into sold_to values (" + saleNextId + ", '" + username_global + "')");

                for (int i = 0; i < cart.size(); i++) {
                    ResultSet rs3 = query(con, "select stock from book where isbn = '" + cart.get(i) + "'");
                    rs3.next();
                    int stock = rs3.getInt(1);
                    update(con, "insert into book_sales values (" + saleNextId + ", '" + cart.get(i) + "', " + cartQuantity.get(i) +")");
                    update(con, "update book set stock = (stock - " + cartQuantity.get(i) + ") where isbn = '" + cart.get(i) + "'");

                    if (stock-cartQuantity.get(i)<10) { //error handing
                        ResultSet rs4 = query(con, "select sum(num_sold) from book_sales natural join book natural join sale where isbn = '" + cart.get(i) + "' " +
                                "and date > (select now() - '1 month'::INTERVAL)");
                        rs4.next();
                        int monthSold = rs4.getInt(1);
                        update(con, "update book set stock = (stock + " + monthSold + ") where isbn = '" + cart.get(i) + "'");
                        System.out.println("Emailing publisher. Requesting " + monthSold + " more books of isbn: " + cart.get(i));
                    }
                }

                cart.clear();
                cartQuantity.clear();
                saleNextId++;
                System.out.println("Successful purchase! Returning to library");
                browse(con);
            } catch (Exception e) {
                System.out.println("Error in checkout please try again later");
            }


        } else {
            System.out.println("Invalid input try again.");
            checkout(con);
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName ("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://localhost:5432/BookStore"; //change to the correct port
            String user = "postgres";
            String pass = "student"; //whatever your password for postgres is
            Connection con = DriverManager.getConnection(dbURL, user, pass);

            ResultSet rs = query(con, "select max(sale_id) as max_id from sale");
            rs.next();
            saleNextId = rs.getInt(1) + 1; //if no sales null + 1 = 1

            rs = query(con, "select max(pub_id) as pub_id from publisher");
            rs.next();
            pubNextId = rs.getInt(1) + 1; //if no sales null + 1 = 1

            loginMenu(con);

        } catch(Exception e){ System.out.println(e);}
    }
}
