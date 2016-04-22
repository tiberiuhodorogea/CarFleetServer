package clase.HandleStrategy;

import SharedClasses.Objects.Credentials;
import SharedClasses.Objects.User;

public class CheckUserStrategy implements HandlingStrategy{

	@Override
	public User handle(Object dataFromClient) {
		Credentials credentials = (Credentials)dataFromClient;
		User ret = db.checkUser(credentials);
	    return ret;
	}

	

}
