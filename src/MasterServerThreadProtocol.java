
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class MasterServerThreadProtocol implements Runnable {
	Socket clientSocket;
	ArrayList<ServerInfo> slaveDir;
	ObjectOutputStream clientOOS;
	ObjectInputStream clientOIS;
	int chossenSlave;

	public MasterServerThreadProtocol(Socket clientSocket, int chossenSlave, ArrayList<ServerInfo> slaveDir) {
		this.clientSocket = clientSocket;
		this.slaveDir = slaveDir;
		this.chossenSlave = chossenSlave;
		try {
			this.clientOOS = new ObjectOutputStream(clientSocket.getOutputStream());
			this.clientOIS = new ObjectInputStream(clientSocket.getInputStream());
		} catch (EOFException e) {
			e.getStackTrace();// remove later-----------
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ------------whole purpose to send object to slave and get it back then send
	// to client
	@Override
	public void run() {

		try {
			boolean runThread = true;
			while (runThread) {
				Messege clientMessege = (Messege) clientOIS.readObject(); // get message form client
				System.out.println("Got messge from client");
				// send cleintMessege to slave
				System.out.println("chossenSlave " + chossenSlave);

				// sening and revieicg from slave--
				ServerInfo slaveInfo = slaveDir.get(chossenSlave);
				String slaveHost = slaveInfo.getHostName();
				int slavePort = slaveInfo.getPortNum();

				SocketWrapper slaveSocketWrapper = new SocketWrapper(slaveHost, slavePort);
				System.out.println("created slavWrapper");
				slaveSocketWrapper.writeUnshared(clientMessege);
				System.out.println("wrote to slave");
				// get messege from slave here
				Messege completedMessege = (Messege) slaveSocketWrapper.readObject();
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
		}

	}

	private void decrementNumOfTasks(int chossenSlave) {
		slaveDir.get(chossenSlave).decrementNumOfTasks();
		
	}
}
