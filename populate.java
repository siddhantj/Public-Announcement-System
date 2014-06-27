

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class populate {

	Connection mainConnection =null;
	Statement mainStatement=null;
	ResultSet mainResultSet = null;
	
	public populate(String[] args)
	{
		connectToDB();
		
		insertStudents(args);
		
		insertAnnouncementSystems(args);
		
		insertBuildings(args);
		
		closeConnection();
		
		
	}
	public static void main(String[] args) 
	{
		populate p=new populate(args);

	}
	
	public void insertStudents(String[] args)
	{
		String line=null;
		String[] splitData=null;
		String studentID=null;
		String xCoor=null,yCoor=null;
		try
		{
			/*Deleting previous tuples */
			mainStatement.executeUpdate("delete from students");
			BufferedReader reader=new BufferedReader(new FileReader(args[1]));
			for(line=reader.readLine();line!=null;line=reader.readLine())
			{
				//System.out.println(line);
				splitData=line.split(",");
				studentID=splitData[0];
				xCoor=splitData[1];
				yCoor=splitData[2];
				mainStatement.executeUpdate("insert into students values('"+studentID +"',sdo_geometry(2001,NULL,SDO_POINT_TYPE("+splitData[1]+","+splitData[2]+",NULL),NULL,NULL))");
			}
			System.out.println("Students inserted");
		}catch(Exception e)
		{
			System.out.println("Error: " +e.toString());
		}
	}
	
	public void insertAnnouncementSystems(String args[])
	{
		String line=null;
		String[] split=null;
		try
		{
			mainStatement.executeUpdate("delete from asystems");
			BufferedReader reader=new BufferedReader(new FileReader(args[2]));	
			for(line=reader.readLine();line!=null;line=reader.readLine())
			{
				//System.out.println(line);
				split=line.split(",");
				mainStatement.executeUpdate("insert into asystems values('"+split[0]+"',SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+split[1]+","+split[2]+",NULL),NULL,NULL),"+split[3]+")");
			}
			System.out.println("Announcement Systems inserted");
		}catch(Exception e)
		{
			System.out.println("Error: "+ e.toString());
		}
	}
	
	public void insertBuildings(String args[])
	{
		String line=null;
		String[] split=null;
		String sqlStatement;
		int length;
		
		try
		{
			/*Deleting previous tuples */
			mainStatement.executeUpdate("delete from buildings");
			BufferedReader reader=new BufferedReader(new FileReader(args[0]));
			for(line=reader.readLine();line!=null;line=reader.readLine())
			{
				//System.out.println(line);
				split=line.split(",");
				sqlStatement="insert into buildings values('"+split[0]+"','"+split[1]+"',"+split[2]+",SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(" ;
				length=split.length;
				for(int i=3;i<length;i=i+2)
				{
					sqlStatement=sqlStatement +split[i]+","+split[i+1];
					if(length-(i+1)==1)
					{
						break;
					}
					else
						sqlStatement=sqlStatement + ",";
				}
				sqlStatement= sqlStatement + ")))";
				//System.out.println(sqlStatement);
				mainStatement.executeUpdate(sqlStatement);
			}
			System.out.println("Buildings inserted");
		}catch(Exception e)
		{
			System.out.println("Error: "+ e.toString());
		}
	}
	
	public void connectToDB()
	{
		
			try{
				System.out.print("Looking for Oracle's jdbc-odbc driver ... ");
			
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				System.out.println("Loaded");
			
				String URL="jdbc:oracle:thin:@localhost:1521:orcl2";
				String userName="sys as sysdba";
				String password="Albert007";
			
				System.out.print("Connecting to DB...");
				mainConnection = DriverManager.getConnection(URL, userName, password);
				System.out.println(", Connected!");

			mainStatement = mainConnection.createStatement();
			}
			catch(Exception e)
			{
				System.out.println( "Error while connecting to DB: "+ e.toString() );
	     		e.printStackTrace();
	     		System.exit(-1);
			}
		}
	
	public void closeConnection()
	{
		
	}
	
}


