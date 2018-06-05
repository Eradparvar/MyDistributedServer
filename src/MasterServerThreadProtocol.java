
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class MasterServerThreadProtocol implements Runnable {
	Socket clientSocket;
	ArrayList<ServerInfo> slaveDir;
	ObjectOutputStream clientOOS;
	ObjectInputStream clientOIS;

	public MasterServerThreadProtocol(Socket clientSocket, ArrayList<ServerInfo> slaveDir) {
		this.clientSocket = clientSocket;
		this.slaveDir = slaveDir;
		try {
			this.clientOOS = new ObjectOutputStream(clientSocket.getOutputStream());
			this.clientOIS = new ObjectInputStream(clientSocket.getInputStream());
		} catch (EOFException e) {
			e.getStackTrace();// remove later-----------
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void decrementNumOfTasks(int chossenSlave) {
		slaveDir.get(chossenSlave).decrementNumOfTasks();

	}

	public synchronized int findSlaveLeastCon() throws Exception {
		if (slaveDir.size() == 0)
			throw new Exception("Slave list is empty. Cant find least connection");

		else if (slaveDir.size() == 1) {
			System.out.println("Only 1 slave");
			return 0;

		}

		int lowest = slaveDir.get(0).getNumOfTaskOnQueue();
		int nextNum;
		boolean tie = false;
		int chossenSlave = 0;
		System.out.println("lowest " + lowest);
		for (int i = 1; i < slaveDir.size(); i++) {
			nextNum = slaveDir.get(i).getNumOfTaskOnQueue();
			System.out.println("nextNum " + nextNum);
			if (nextNum < lowest) {
				lowest = nextNum;
				chossenSlave = i;
				System.out.println("new lowest " + lowest);
			}
		}

		return chossenSlave;
	}

	public void incrementNumOfTasks(int slaveNum) {
		// the method addTaskToQueue() is synchronized. See ServerInfo class
		slaveDir.get(slaveNum).incrementNumOfTasks();

	}

	// ------------whole purpose to send object to slave and get it back then send
	// to client
	@Override
	public void run() {
		ObjectOutputStream slaveOOS;
		ObjectInputStream slaveOIS;
		try {
			boolean runThread = true;
			int chossenSlave;
			while (runThread) {
				Messege clientMessege = (Messege) clientOIS.readObject(); // get message form client
				System.out.println("Got messge from client");
				// send cleintMessege to slave

				chossenSlave = findSlaveLeastCon();
				incrementNumOfTasks(chossenSlave);

				// sending and revieicg from slave--
				ServerInfo slaveInfo = slaveDir.get(chossenSlave);
				String slaveHost = slaveInfo.getHostName();
				int slavePort = slaveInfo.getPortNum();
				System.out.println(slaveHost + " ////---" + slavePort);

				Socket slaveSocket = new Socket(slaveHost, slavePort);
				slaveOOS = new ObjectOutputStream(slaveSocket.getOutputStream());
				slaveOIS = new ObjectInputStream(slaveSocket.getInputStream());
				slaveOOS.writeObject(clientMessege);
				Messege completedMessege = (Messege) slaveOIS.readObject();

				decrementNumOfTasks(chossenSlave);

				System.out.println("Got messege from slave");
				clientOOS.writeUnshared(completedMessege);
				clientOOS.flush();
				System.out.println("Sent messege back to cleint !!");

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
