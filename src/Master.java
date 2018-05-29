import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Master {
	static ArrayList<ServerInfo> slaveDir = new ArrayList<>();

	static int slaveNum = 0;

	public static void main(String[] args) {
		// Hardcode in IP and Port here if required
		// args = new String[] { "100" };

		if (args.length != 1) {
			System.err.println("Error: Usage: java MasterServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		setupSlaveDir();

		try (ServerSocket masterServerSocket = new ServerSocket(portNumber);) {
			System.out.println("MasterServer Started with port " + portNumber);
			boolean runMasterServer = true;
			while (runMasterServer) {
				Socket clientSocket = masterServerSocket.accept();
				new Thread(new MasterServerThreadProtocol(clientSocket, slaveNum, slaveDir)).start();
				System.out.println("Master created thread to deal with client reqest");
				increment();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private synchronized static void increment() {
		if (slaveNum == 0) {
			slaveNum++;
			System.out.println("changes slavenum " + slaveNum);
		} else {
			slaveNum = 0;
			System.out.println("changes slavenum " + slaveNum);
		}
	}

	private static void setupSlaveDir() {
		// creates slave a dir of all the slavs host and port num
		// later can read from file
		// slave 1
		ServerInfo serverInfo = new ServerInfo("127.0.0.1", 200);
		slaveDir.add(serverInfo);
		// slave 2
		ServerInfo serverInfo2 = new ServerInfo("127.0.0.1", 300);
		slaveDir.add(serverInfo2);

	}
}
