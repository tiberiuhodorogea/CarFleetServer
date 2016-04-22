package clase.HandleStrategy;

import java.util.Hashtable;

import SharedClasses.Communication.RequestedAction;
import SharedClasses.Communication.Exceptions.KeyNotMappedException;

public class ActionStrategyHashMapper {

	private static Hashtable<RequestedAction,HandlingStrategy> actionStrategyMapper =
            new Hashtable<RequestedAction,HandlingStrategy>();

	static {
        // maps the requested action
        // with the strategy object responsible with handling the associated request
    	actionStrategyMapper.put(RequestedAction.CHECK_ACCESS,new CheckUserStrategy());
    	actionStrategyMapper.put(RequestedAction.ADD_CAR, new AddCarStrategy());
    	actionStrategyMapper.put(RequestedAction.GET_FLEET_LIST,new GetFleetListStrategy() );
    	
        //////ADD more when implement new request - response
    }
	
	
	public static HandlingStrategy getHandlingStrategyFor(RequestedAction key) throws KeyNotMappedException{
		HandlingStrategy strategy = null;
		strategy = actionStrategyMapper.get(key);
		if(strategy == null)
			throw new KeyNotMappedException("Key : " + key.toString()+ " not mapped to any strategy");
		return actionStrategyMapper.get(key);
	}

    
}
