package SharedClasses.Communication;

import SharedClasses.Communication.Exceptions.KeyNotMappedException;
import SharedClasses.Objects.Car;
import SharedClasses.Objects.Credentials;
import SharedClasses.Objects.User;
import SharedClasses.Utils.ObjectUserIdContainer;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tiber on 4/14/2016.
 */
public class ActionTypesHashMapper {

    private static Hashtable<RequestedAction,TypePairContainer> messageActionTypesMapper =
            new Hashtable<RequestedAction,TypePairContainer>();

    static {
        // maps the requested action
        // with the type of  the encapsulated data on the request
        // and with the type of encapsulated data on response
        messageActionTypesMapper.put(RequestedAction.CHECK_ACCESS,new TypePairContainer(Credentials.class, User.class));
        messageActionTypesMapper.put(RequestedAction.ADD_CAR,new TypePairContainer(Car.class,ResponseEnum.class));
        messageActionTypesMapper.put(RequestedAction.GET_FLEET_LIST,new TypePairContainer(User.class, new ArrayList<Car>().getClass()));
        
        //////ADD more when implement new request - response
    }

    public static Class getRequestDataClass(RequestedAction key) throws KeyNotMappedException {
        TypePairContainer typePairContainer = messageActionTypesMapper.get(key);
        if(null == typePairContainer)
            throw new KeyNotMappedException("Key :" + key.toString() + "not mapped to any types combination.");
        Class ret =typePairContainer.getRequestDataType();
        return ret;
    }


    public static Class getResponseDataClass(RequestedAction key) throws KeyNotMappedException {
        TypePairContainer typePairContainer = messageActionTypesMapper.get(key);
        if(null == typePairContainer)
            throw new KeyNotMappedException("Key :" + key.toString() + "not mapped to any types combination.");
        Class ret =  typePairContainer.getResponseDataType();
        return ret;
    }

    
}
