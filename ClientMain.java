import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {

	public static void main(String[] args) throws IOException {
		Socket socket = null;
		
		int hostPortNumber = Integer.parseInt(args[0]);
		int clientPortNumber = Integer.parseInt(args[1]);
		
		ServerSocket serverSocket = new ServerSocket(hostPortNumber);
		
		while(socket == null) {
			try {
				socket = new Socket("127.0.0.1", clientPortNumber);
			}catch(Exception e) {System.out.println("Attempting to reconnect");}
		}
		
		Socket clientSocket = serverSocket.accept();
		
		PrintWriter serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    
	    Scanner textInput = new Scanner(System.in);
	    
	    System.out.print("What is your time zone? ");
	    String timeZone = textInput.nextLine();
	    
	    System.out.print("What is your name? ");
	    String name = textInput.nextLine();
	    
	    User me = new User(timeZone, name);
	    serverOut.println(me);
	    
	    
	    String otherUserInput = in.readLine();
	    User otherUser = new User(otherUserInput.split("\\|")[0], otherUserInput.split("\\|")[1]);
	    
	    Thread serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					serverOut.println(textInput.nextLine());
					serverOut.flush();
				}
			}
		});
	    
	    Thread clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						System.out.println(otherUser + ": " + in.readLine());
					} catch (IOException e) {
						System.out.println("Connection was closed by remote host");
						System.exit(0);
					}
				}
				
			}
		});
	    
		serverThread.start();
		clientThread.start();
		
	}

}
