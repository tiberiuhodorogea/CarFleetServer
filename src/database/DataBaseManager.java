package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import SharedClasses.Communication.ResponseEnum;
import SharedClasses.Objects.Car;
import SharedClasses.Objects.Credentials;
import SharedClasses.Objects.Location;
import SharedClasses.Objects.User;
import SharedClasses.Utils.MyDate;
import SharedClasses.Utils.UserRole;


public class DataBaseManager {
///singleton
	
	private static DataBaseManager instance = null;
	private Connection connection=null;
	
	public static synchronized DataBaseManager get(){
		if(instance == null)
			instance = new DataBaseManager();
		return instance;
	}
	
	private DataBaseManager(){
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:fleet_project_database.db");
	    	} catch ( Exception e ) {
	    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    		System.exit(0);
	    	}
	    	System.out.println("Opened database successfully");
		}
	public Connection getConnection(){
		return this.connection;
	}

	
	public User checkUser(Credentials credentials) {
		User user = new User();
		user.setRole(UserRole.NO_USER_ROLE);
		Statement statement = null;
		try {
		  statement  =	connection.createStatement();
		  ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE MAIL = \""+credentials.getUserMail()+
				  										"\" AND PASSWORD = \""+ credentials.getPassword()+"\"");
		  
		  if(!resultSet.next()){//empty
			  user = new User();
			  user.setRole(UserRole.NO_USER_ROLE);
			 
		  }
		  else{
			  int id = resultSet.getInt(1);
			  String mail = resultSet.getString(2);
			  String password = resultSet.getString(3);
			  UserRole userRole;
			  int role = resultSet.getInt(4);
			  int managerId = resultSet.getInt(5);
			  
			  if(role == 0)//manager
				  userRole = UserRole.MANAGER;
			  else
				  userRole = UserRole.ADMINISTATOR;
			  
			  user = new User(id,mail,password,userRole,managerId);
				  
		  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
	}

	public ResponseEnum insertCar(Car car, int managerId) {
		if(isDateInFuture(car.getLastRevisionDate()))
			return ResponseEnum.LAST_REVISION_IS_IN_FUTURE;
		
		Statement statement = null;
		ResultSet resultSet = null;
		
		  
		  try {
			statement = connection.createStatement();
			//check for existing licence plate
			resultSet = statement.executeQuery("SELECT * FROM CARS WHERE LICENCE_NUMBER = \"" 
												+ car.getLicenceNo()+"\"");
			if(resultSet.next())//not empty
				return ResponseEnum.DUPLICATE_LICENCE_NUMBER;
			
			//good for insert
			String sql = "";
			sql+="INSERT INTO CARS (MANAGER_ID,LICENCE_NUMBER,MILEAGE,LAST_REVISION_DATE)";
			sql+="VALUES("+managerId+",";
			sql+="\""+car.getLicenceNo()+"\",";
			sql+=car.getMileage()+",";
			sql+="\""+car.getLastRevisionDate()+"\")";
			statement.execute(sql);
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEnum.SQL_ERROR;
		}
		return ResponseEnum.CONFIRM;
	}
	
	boolean isDateInFuture(MyDate date){
		if( date.getYear() > Calendar.getInstance().get(Calendar.YEAR) )
			return true;
		if(date.getYear() == Calendar.getInstance().get(Calendar.YEAR)) // current year
		{
			if(date.getMonth()-1 > Calendar.getInstance().get(Calendar.MONTH))
				return true;
			
				if(date.getMonth()-1 == Calendar.getInstance().get(Calendar.MONTH)){{
					//same year same month
					if(date.getDay() > Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
						return true;
				}
					
			}
		}
		
		return false;
	}
	
	public Location selectLocation(int locationId){
		Location ret = new Location();
		double latitude = 0,longitude=0;
		Statement statement;
		try {
			statement = connection.createStatement();
			String sql = "SELECT LATITUDE,LONGITUTDE FROM LOCATIONS WHERE ID = "+ locationId;
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next())
			{
				latitude = resultSet.getDouble("LATITUDE");
				longitude = resultSet.getDouble("LONGITUDE");
				ret.setLatitude(latitude);
				ret.setLongitude(longitude);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}
}
