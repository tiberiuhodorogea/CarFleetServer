package SharedClasses.Communication;

/**
 * Created by tiber on 4/14/2016.
 */
public class TypePairContainer {
    private Class requestDataType;
    private Class responseDataType;

    public TypePairContainer(Class requestDataType, Class responseDataType) {
        this.requestDataType = requestDataType;
        this.responseDataType = responseDataType;
    }

    public Class getRequestDataType() {
        return requestDataType;
    }

    public Class getResponseDataType() {
        return responseDataType;
    }
}
