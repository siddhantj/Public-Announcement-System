NAME: SIDDHANT JAWA
USCID: 7769-2282-69
Username- sjawa


Submitted files: 
1. createdb.sql
2. dropdb.sql
3. populate.java
4. hw2.java
5. sdoapi.jar
6.Readme.txt

Resolution of HW: 1100 x 700

How to compile/run:

1) First unzip all the files. All the files should be in the same folder. Otherwise the program won't run.


2. Run the createdb.sql file to create/update necessary tables/views/indexes.


3. To insert fields in the tables, run populate.java. It should run as follows:

	
	javac -cp ojdbc6.jar populate.java
	java -cp ojdbc6.jar;. populate buildings.xy students.xy announcementSystems.xy


4. Run the hw2.java file from command prompt to open the map window. An example is given below:

	javac -cp ".;ojdbc6.jar;sdoapi.jar" hw2.java
	java -cp ".;ojdbc6.jar;sdoapi.jar" hw2


5. Run the dropdb.sql file to delete all the tables/views/indexes created during this setup.


NOTE: Recommended to keep all the files in a single directory.