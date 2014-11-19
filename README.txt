This directory contains a simple example illustrating how to implement 
an Object Layer module (subsystem) and an associated Persistence
module.

There are 2 subdirectories:

  src
         include the sources, placed in the following packages:

     edu/uga/dawgtrades/model             -- the interfaces of the Object Model module's API

     edu/uga/dawgtrades/model/impl        -- the implementation of the Object Model layer

     edu/uga/dawgtrades/persistence       -- the interfaces of the Persistence module's API

     edu/uga/dawgtrades/persistence/impl  -- the interfaces of the Persistence module's API

  classes
         includes the compiled Java class files


An ant build file is also included (build.xml).

In order to create your own copy of the clubs example you need to:

0.  Copy the entire Clubs directory to your system

1.  Install the MySQL database system (may be already pre-installed on Linux)

2.  Create a clubs database using mysqladmin, or from within the mysql
    client, while logged in as root, using the create database
    command, as below:

      CREATE DATABASE dawgtrades;
     
    -- you will need to consult the MySQL documentation, if needed

3.  Create a database user 'demo' with password 'demo' and access to
    the clubs database (from within the mysql client, logged in as
    root). For example:

      GRANT ALL PRIVILEGES ON demo.* TO demo@localhost IDENTIFIED BY 'demo';

4.  Load the clubs schema into the database:

      mysql -u demo -p clubs < db/dawgtrades-mysql.sql

    You may want to verify that all tables have been created properly:
    Run:

      mysql -u demo -p clubs

    and then execute:

      show tables;

    You should see the all of the tables defined.

5.  Compile the clubs Object Model module and the test programs:

      ant compile

    (you must have the ant utility installed)
    It possible to compile the example "by hand", using javac
    directly.

    You should see no compilation errors.

6.  Run the test programs:

      java -classpath classes:jar/classes/mysql-connector-java-5.1.34-bin.jar  edu.uga.dawgtrades.test.ObjectModelCreate

      java -classpath classes:jar/classes/mysql-connector-java-5.1.34-bin.jar  edu.uga.dawgtrades.test.ObjectModelRead

      java -classpath classes:jar/classes/mysql-connector-java-5.1.34-bin.jar  edu.uga.dawgtrades.test.ObjectModelUpdate

      java -classpath classes:jar/classes/mysql-connector-java-5.1.34-bin.jar  edu.uga.dawgtrades.test.ObjectModelDelete

    in this sequence.  

    After each of the above command, you may use the mysql client to list the
    content of all of the tables to see how the above program affect
    the database tables.


