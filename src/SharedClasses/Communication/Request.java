package SharedClasses.Communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import SharedClasses.Communication.Exceptions.KeyNotMappedException;

import java.lang.reflect.Modifier;

/**
 * Created by tiber on 4/14/2016.
 */
public class Request extends AbstractMessage {

    protected Request(RequestedAction requestedAction, Object data) throws KeyNotMappedException {
            this.requestedAction = requestedAction;
            Class typeOfData = ActionTypesHashMapper.getRequestDataClass(requestedAction);//get REQUEST data class
            this.jsonData = new Gson().toJson(data,data.getClass());
    }

    @Override
    public Object deserializeData() throws KeyNotMappedException {
        Class typeOfData = ActionTypesHashMapper.getRequestDataClass(requestedAction);//get REQUEST data class
        return new Gson().fromJson(this.jsonData,typeOfData);
    }

    @Override
    public String toJson() {
            return new Gson().toJson(this,this.getClass());
    }
}
