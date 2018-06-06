import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Master {
	static ArrayList<ServerInfo> slaveDir = new ArrayList<>();
	static int slaveNum = 0;

	public static void main(String[] args) {
		// Hardcode in IP and Port here if required
		args = new String[] { "100" };

		if (args.length != 1) {
			System.err.println("Error: Usage: java MasterServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		System.out.println("NUMBER of slaves " + setUpSlaveDir());

		try (ServerSocket masterServerSocket = new ServerSocket(portNumber);) {
			System.out.println("MasterServer started with port " + portNumber);
			boolean runMasterServer = true;
			while (runMasterServer) {
				Socket clientSocket = masterServerSocket.accept();

				new Thread(new MasterReadFromClientWriteToSlave(clientSocket, slaveDir)).start();
				System.out.println("Master created thread to deal with client request");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static int setUpSlaveDir() {
		// creates slave a dir of all the slaves host and port num
		// later can read from file
		// slave 1
		String fileName = "config.txt";
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
