# 3005-Project
Authors:
Brandon Atkins #101121680
Rawan Elkhatib #101157299

Setup Instructions:

Open pgAdmin (or any postgresSql interface) and create a new database called BookStore
In the BookStore database open the query tool
In the query tool, copy and paste the DDL.sql file and run it to create the tables
To initialize the tables, copy and paste the Insert.sql file into the qurey tool and run it
This should add some base values into the database 

After setting up the database you should download the Main.java and postgresql-42.3.1.jar files
You need Java version 13 or above to be able to use the code
Create a new project in your IDE
In your project create a new class in your src file called Main
Copy and paste the Main.java file into the new class created 
In the project structure, opnen modules and add the postgresql-42.3.1.jar file as one of the dependencies 
Click apply and the code should not be connected to your database 

Before running the code, in the main function (line 765), check that the port in the dbURl is the same port used for your database 

Files:
Main.java -> runs the program
DDL.sql -> creates tables from the database
Insert.sql -> initializes database with values 
postgresql-42.3.1.jar -> connect postgres to the java program 


