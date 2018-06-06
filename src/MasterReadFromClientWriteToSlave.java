
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class MasterReadFromClientWriteToSlave implements Runnable {

	private ArrayList<ServerInfo> slaveDir;
	private ObjectOutputStream clientOOS;
	private ObjectInputStream clientOIS;
	private Socket slaveSocket;

	public MasterReadFromClientWriteToSlave(Socket clientSocket, ArrayList<ServerInfo> slaveDir) {
		this.slaveDir = slaveDir;
		try {
			this.clientOOS = new ObjectOutputStream(clientSocket.getOutputStream());
			this.clientOIS = new ObjectInputStream(clientSocket.getInputStream());
		} catch (EOFException e) {
			e.printStackTrace();// remove later-----------
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized int findSlaveLeastCon() throws Exception {
		// find the slave with the least num of tasks. If tie, use slave with the lower number
		if (slaveDir.size() == 0)
			throw new Exception("Slave list is empty. Can't find least connection.");
		
		int lowest = slaveDir.get(0).getNumOfTaskOnQueue();
		int nextNum;
		int chosenSlave = 0;
		System.out.println("lowest " + lowest);
		for (int i = 1; i < slaveDir.size(); i++) {
			nextNum = slaveDir.get(i).getNumOfTaskOnQueue();
			System.out.println("nextNum " + nextNum);
			if (nextNum < lowest) {
				lowest = nextNum;
				chosenSlave = i;
				System.out.println("new lowest " + lowest);
			}
		}

		return chosenSlave;
	}

	// ------------whole purpose to send object to slave and get it back then send
	// to client
	@Override
	public void run() {
		ObjectOutputStream slaveOOS;
		ObjectInputStream slaveOIS;
		try {
			boolean runThread = true;
			int chosenSlave;
			while (runThread) {
				Message clientMessage = (Message) clientOIS.readObject(); // get message form client
				System.out.println("Got message from client");
				// send clientMessage to slave

				chosenSlave = findSlaveLeastCon();
				slaveDir.get(chosenSlave).incrementNumOfTasks();

				// sending and receiving from slave--
				ServerInfo slaveInfo = slaveDir.get(chosenSlave);
				String slaveHost = slaveInfo.getHostName();
				int slavePort = slaveInfo.getPortNum();
				System.out.println(slaveHost + " ////---" + slavePort);

				slaveSocket = new Socket(slaveHost, slavePort);
				slaveOOS = new ObjectOutputStream(slaveSocket.getOutputStream());
				slaveOIS = new ObjectInputStream(slaveSocket.getInputStream());
				slaveOOS.writeObject(clientMessage);
				
				
				
				Message completedMessage = (Message) slaveOIS.readObject();
				slaveDir.get(chosenSlave).decrementNumOfTasks();
				System.out.println("Got message from slave");
				clientOOS.writeUnshared(completedMessage);
				clientOOS.flush();
				System.out.println("Sent message back to client !!");
				//new Thread(new MasterReadFromSlaveWriteToClient(chosenSlave, clientOOS, slaveOIS, slaveDir)).start();
			}
		} catch (EOFException eof) {
		} catch (SocketException se) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
