package clase;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;

import SharedClasses.Communication.*;
import SharedClasses.Communication.Exceptions.KeyNotMappedException;
import SharedClasses.Objects.*;
import clase.HandleStrategy.ActionStrategyHashMapper;
import clase.HandleStrategy.HandlingStrategy;
import database.DataBaseManager;


public class RequestHandler implements Runnable {

	private Response response = null;
	private Request request = null;
	private String requestJson = null;
	private Socket client = null;
	private HandlingStrategy strategy;
	DataBaseManager db = DataBaseManager.get();
	PrintWriter pr = null;
	
	public RequestHandler(String requestJson,Socket client){
		this.requestJson = requestJson;
		this.client = client;
		try {
			pr = new PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		request = new RequestFactory().create(requestJson);
		
		Handle(request); 	
		
		pr.write(this.response.toJson()+"\n");
		System.out.println("RESPONSE: "+ this.response.toJson()+"\n\n");
		pr.flush();
		
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void Handle(Request request){
		RequestedAction requestedAction = request.getRequestedAction();
		try {
			this.strategy = ActionStrategyHashMapper.getHandlingStrategyFor(requestedAction);
			Object responseData= this.strategy.handle(request.deserializeData());
			this.response = new ResponseFactory().create(requestedAction,responseData);
		} catch (KeyNotMappedException e) {
			e.printStackTrace();
		}
	}
	}

