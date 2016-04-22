package clase.HandleStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import SharedClasses.Objects.Car;
import SharedClasses.Objects.Location;
import SharedClasses.Objects.User;
import SharedClasses.Utils.CarBuilder;
import SharedClasses.Utils.CarStatus;
import SharedClasses.Utils.MyDate;
import database.DataBaseManager;

public class GetFleetListStrategy implements HandlingStrategy{

	@Override
	public ArrayList<Car> handle(Object dataFromClient) {
		return selectAllCarsForUser((User)dataFromClient);
	}
	
	
	private int carId;
	private int managerId;
	private String licenceNumber;
	private long mileage;
	private Location lastKnownLocation = null;
	private MyDate lastUpdateFromFieldDate = null;
	private MyDate lastRevisionDate = null;

	public ArrayList<Car> selectAllCarsForUser(User user){
		ArrayList<Car> carList = new ArrayList<Car>();
		try {
			Statement statement = db.getConnection().createStatement();
			String select = "SELECT * FROM CARS WHERE MANAGER_ID = "+ user.getManagerId();
			ResultSet resultSetCars = statement.executeQuery(select);
			while(resultSetCars.next()){
				Car car;
				CarBuilder carBuilder = new CarBuilder();
				
				//NOT NULLS
				carId = resultSetCars.getInt("ID");
				managerId = resultSetCars.getInt("MANAGER_ID");
				licenceNumber = resultSetCars.getString("LICENCE_NUMBER");
				mileage =resultSetCars.getLong("MILEAGE");
				String lastRevisionDateString = resultSetCars.getString("LAST_REVISION_DATE");
				lastRevisionDate = MyDate.fromString(lastRevisionDateString);
				//END NOT NULLS
				
				int lastKnownLocationId = resultSetCars.getInt("LAST_KNOWN_LOCATION_ID"); // MAY BE NULL
				if(lastKnownLocationId != 0)
					lastKnownLocation = DataBaseManager.get().selectLocation(lastKnownLocationId);
					
				
				String lastUpdateFromFieldDateString = resultSetCars
									.getString("LAST_UPDATE_FROM_FIELD_DATE"); //MAY BE NULL
				if(lastUpdateFromFieldDateString != null)
					lastUpdateFromFieldDate = MyDate.fromString(lastUpdateFromFieldDateString);
					
				car = carBuilder.setId(carId)
						.setManagerId(managerId)
						.setLicenceNo(licenceNumber)
						.setMileage(mileage)
						.setLastRevisionDate(lastRevisionDate)
						.setLastKnownLocation(lastKnownLocation)
						.setLastUpdateFromFieldDate(lastUpdateFromFieldDate)
						.setStatus(CarStatus.OFFLINE) //setting offline although not from db
						.build();
				
				carList.add(car);
				
				carBuilder.flush();
				
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return carList;
	}
}
