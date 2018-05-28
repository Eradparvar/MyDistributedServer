import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Master {
	static ArrayList<ObjectOutputStream> oosSlaveList = new ArrayList<>();
	static ArrayList<ObjectInputStream> oisSlaveList = new ArrayList<>();

	public static void main(String[] args) {
		// Hardcode in IP and Port here if required
		args = new String[] { "100" };

		if (args.length != 1) {
			System.err.println("Usage: java MasterServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		/// test area
		setupSlaveStreams();

		// end area
		try (ServerSocket masterServerSocket = new ServerSocket(100);) {
			System.out.println("MasterServer Started with port " + portNumber);
			boolean runServer = true;
			while (runServer) {
				Socket clientSocket = masterServerSocket.accept();
				new Thread(new MasterServerThreadProtocol(clientSocket, oosSlaveList, oisSlaveList)).start();
				System.out.println("Master created thread to deal with client reqest");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void setupSlaveStreams() {
		// creats OOS and OIS for the slave

		/// move to method
		try {
			int portNum = 200;
			ServerSocket serverSocket = new ServerSocket(portNum);
			Socket slaveSocket = serverSocket.accept();
			oosSlaveList.add(new ObjectOutputStream(slaveSocket.getOutputStream()));
			oisSlaveList.add(new ObjectInputStream(slaveSocket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
