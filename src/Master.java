import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.print.attribute.standard.RequestingUserName;

public class Master {

	public static void main(String[] args) {

		// Hardcode in IP and Port here if required
		args = new String[] { "100" };

		if (args.length != 1) {
			System.err.println("Usage: java MasterServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		try (ServerSocket masterServerSocket = new ServerSocket(100);) {
			System.out.println("MasterServer Started with port " + portNumber);
			boolean runServer = true;
			while (runServer) {
				Socket clientSocket = masterServerSocket.accept();
				new Thread(new MasterServerThreadProtocol(clientSocket)).start();
				System.out.println("Master created thread to deal with client reqest");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
