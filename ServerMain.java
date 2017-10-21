import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerMain {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		int portNumber = 420;
		
		ServerSocket serverSocket = new ServerSocket(portNumber);
		Socket clientSocket = serverSocket.accept();
		
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		long startTime = System.currentTimeMillis();
		while(true) {
			
			out.println(System.currentTimeMillis() - startTime);
			out.flush();
			Thread.sleep(1);
		}
	}
}
