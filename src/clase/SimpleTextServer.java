package clase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;




public class SimpleTextServer {

	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static InputStreamReader inputStreamReader;
	private static BufferedReader bufferedReader;
	private static String message;
	private static final int PORT = 6843;

	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(PORT); // Server socket

		} catch (IOException e) {
			System.out.println("Could not listen on port: "+PORT);
		}

		System.out.println("Server started. Listening to the port "+PORT);

		while (true) {
			try {
				clientSocket = serverSocket.accept(); // accept the client connection
				
				
				inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
				bufferedReader = new BufferedReader(inputStreamReader); 
				message = bufferedReader.readLine(); // get the client REQUEST
				System.out.println("REQUESTUL : " + message);
				
				new Thread(new RequestHandler(message,clientSocket)).start();
			

			} catch (IOException ex) {
				System.out.println("Problem in message reading");
			}
		}

	}
	
	

}