package clase.HandleStrategy;

import SharedClasses.Communication.ResponseEnum;
import SharedClasses.Objects.Car;
import database.DataBaseManager;

public class AddCarStrategy implements HandlingStrategy{

	@Override
	public ResponseEnum handle(Object dataFromClient) {
		
		Car car = (Car) dataFromClient;
		ResponseEnum ret = DataBaseManager.get().insertCar(car,car.getManagerId());
		return ret;
	}

}
