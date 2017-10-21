
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ClientMain {
	static Window w = new Window();
	static User me;
	static PrintWriter serverOut;
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		
		int hostPortNumber = Integer.parseInt(args[0]);
		int clientPortNumber = Integer.parseInt(args[1]);
		
		ServerSocket serverSocket = new ServerSocket(hostPortNumber);
		
		while(socket == null) {
			try {
				socket = new Socket("localhost", clientPortNumber);
			}catch(Exception e) {System.out.println("Attempting to reconnect");}
		}
		//10.186.70.180
		Socket clientSocket = serverSocket.accept();
		
		serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
		
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    
	    Scanner textInput = new Scanner(System.in);
	    
	    String timeZone = JOptionPane.showInputDialog(null, "What is your time zone?");
	    
	    String name = JOptionPane.showInputDialog(null, "What is your name?");
	    
	    me = new User(timeZone, name);
	    serverOut.println(me);
	    
	    String otherUserInput = in.readLine();
	    User otherUser = new User(otherUserInput.split("\\|")[0], otherUserInput.split("\\|")[1]);
	    
		w.setVisible(true);
	    
	    /*Thread serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					String input = textInput.nextLine();
					
				}
			}
		});*/
	    
	    Thread clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						String input = in.readLine();
						appendTextOfScreen(otherUser, input);
					} catch (IOException e) {
						System.out.println("Connection was closed by remote host");
						System.exit(0);
					}
				}
				
			}
		});
	    
		//serverThread.start();
		clientThread.start();
	}
	
	
	public static void appendTextOfScreen(User u, String input) {
		w.chatText.append(u.name + ": " + input + "\n");
		w.scrollBox.getVerticalScrollBar().setValue(w.scrollBox.getVerticalScrollBar().getMaximum());
		w.repaint();
	}
	
}
