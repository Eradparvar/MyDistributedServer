import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Master {
	static ArrayList<ServerInfo> slaveDir = new ArrayList<>();
	static int slaveNum = 0;
	static int roundRobbinCounter = 0;

	// find the slave with the least num of tasks. If tie, then it will
	// round robbin between the slaves

	public static void main(String[] args) {
		// Hardcode in IP and Port here if required
		// args = new String[] { "100" };

		if (args.length != 1) {
			System.err.println("Error: Usage: java MasterServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		System.out.println("NUMBER of slaves " + setupSlaveDir());

		try (ServerSocket masterServerSocket = new ServerSocket(portNumber);) {
			System.out.println("MasterServer Started with port " + portNumber);
			boolean runMasterServer = true;
			int chossenSlave;
			while (runMasterServer) {
				Socket clientSocket = masterServerSocket.accept();

				new Thread(new MasterServerThreadProtocol(clientSocket, slaveDir)).start();
				System.out.println("Master created thread to deal with client reqest");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static int setupSlaveDir() {
		// creates slave a dir of all the slavs host and port num
		// later can read from file
		// slave 1
		String fileName = "config.txt";
		String line = null;
		try {
			Scanner sc = new Scanner(new FileReader(fileName));
			String hostName;
			int portNum;
			while (sc.hasNextLine()) {
				hostName = sc.next();
				portNum = sc.nextInt();
				ServerInfo serverInfo = new ServerInfo(hostName, portNum);
				slaveDir.add(serverInfo);
				sc.nextLine();
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return slaveDir.size();

		// ServerInfo serverInfo = new ServerInfo("127.0.0.1", 200);
		// slaveDir.add(serverInfo);
		// // slave 2
		// ServerInfo serverInfo2 = new ServerInfo("127.0.0.1", 300);
		// slaveDir.add(serverInfo2);

	}
}
