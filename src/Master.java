import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Master {
	static ArrayList<ServerInfo> slaveDir = new ArrayList<>();
	static int slaveNum = 0;
	static int roundRobbinCounter = 0;

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
			int chossenSlave;
			while (runMasterServer) {
				Socket clientSocket = masterServerSocket.accept();
				chossenSlave = findSlaveLeastCon();
				incrementNumOfTasks(chossenSlave);
				new Thread(new MasterServerThreadProtocol(clientSocket, chossenSlave, slaveDir)).start();
				System.out.println("Master created thread to deal with client reqest");
				increment();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// find the slave with the least num of tasks. If tie, then it will
	// round robbin between the slaves
	public static synchronized int findSlaveLeastCon() throws Exception {
		if (slaveDir.size() == 0)
			throw new Exception("Slave list is empty. Cant find least connection");

		else if (slaveDir.size() == 1) {
			System.out.println("---------size 1");
			return 0;

		}

		int lowest = slaveDir.get(1).getNumOfTaskOnQueue();
		int nextNum;
		boolean tie = false;
		System.out.println("lowest " + lowest);
		for (int i = 1; i < slaveDir.size(); i++) {
			nextNum = slaveDir.get(i).getNumOfTaskOnQueue();
			System.out.println("nextNum " + nextNum);
			if (nextNum < lowest) {
				lowest = nextNum;
				System.out.println("new lowest " + lowest);
			} else if (nextNum == lowest) {
				tie = true;
				System.out.println("tie " + tie);
				System.out.println("RR " + roundRobbinCounter);
				roundRobbinCounter = ++roundRobbinCounter % slaveDir.size();
				System.out.println("RR " + roundRobbinCounter);
				break;
			}
		}

		return (tie == false) ? lowest : roundRobbinCounter;
	}

	public static void incrementNumOfTasks(int slaveNum) {
		// the method addTaskToQueue() is synchronized. See ServerInfo class
		slaveDir.get(slaveNum).incrementNumOfTasks();

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
