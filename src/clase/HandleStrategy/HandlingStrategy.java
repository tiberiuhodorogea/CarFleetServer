package clase.HandleStrategy;


import database.DataBaseManager;

public interface HandlingStrategy {
	DataBaseManager db = DataBaseManager.get();
	public Object handle(Object dataFromClient);
}
