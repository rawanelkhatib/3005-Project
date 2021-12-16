# 3005-Project
Authors:
Brandon Atkins #101121680
Rawan Elkhatib #101157299

Setup Instructions:

Open pgAdmin (or any postgresSql interface) and create a new database called BookStore. And
set the owner to postgres. In the BookStore database open the query tool. 
In the query tool, copy and paste the DDL.sql file and run it to create the tables. 
To initialize the tables, copy and paste the Insert.sql file into the qurey tool and run it. 
This should add some base values into the database. 

After setting up the database you should download the Main.java and postgresql-42.3.1.jar files. 
You need Java version 13 or above to be able to use the code. 
Create a new project in your IDE (we used intelliJ, so some instructions may differ based on your IDE). 
In your project create a new class in your src file called Main. 
Copy and paste the Main.java file into the new class created.
In the project structure, open modules and add the postgresql-42.3.1.jar file as one of the dependencies into the external libraries (where the jdk is stored). 
Click apply and the code should now be connected to your database. Next set up the configurartions to run the Main file.

Before running the code, in the main function (line 765), check that the port in the dbURl is the same port used for your database. 
The one in the code is the default for pgAdmin.

Run the Main.java file in your IDE and enter your postgres password. If you get an error it is likely the password entered is incorrect. Howvever if it is
certain the password is correct, there is an error with the port or database initialization.

Files:
Main.java -> runs the program. 
DDL.sql -> creates tables from the database. 
Insert.sql -> initializes database with values. 
postgresql-42.3.1.jar -> connect postgres to the java program. 


