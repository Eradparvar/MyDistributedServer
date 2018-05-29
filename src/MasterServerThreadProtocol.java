
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
	int slaveNum;

	public MasterServerThreadProtocol(Socket clientSocket, int slaveNum, ArrayList<ServerInfo> slaveDir) {
		this.clientSocket = clientSocket;
		this.slaveDir = slaveDir;
		this.slaveNum = slaveNum;
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
				System.out.println("Changed client messege to master");
				// send cleintMessege to slave
				// 1- send cleint task to slave and revie input
				// 2- how many connections do you have salve
				// will have a method that returns the slave socket info and passes it into the
				// masterSocket
				System.out.println(slaveNum);
				// sening and revieicg from slave--
				ServerInfo slaveInfo = slaveDir.get(slaveNum);
				String slaveHost = slaveInfo.getHostName();
				int slavePort = slaveInfo.getPortNum();
				SocketWrapper slaveSocketWrapper = new SocketWrapper(slaveHost, slavePort);
				System.out.println("created slavWrapper");
				slaveSocketWrapper.writeUnshared(clientMessege);
				System.out.println("wrote to slave");
				// get messege from slave here
				Messege completedMessege = (Messege) slaveSocketWrapper.readObject();
				System.out.println("Got messege from slave");
				// send completedMessege to client
				// end send and recive from slave--

				// Socket masterSocket = new Socket("127.0.0.1", 200);
				// ObjectOutputStream oosSlave = new
				// ObjectOutputStream(masterSocket.getOutputStream());
				// ObjectInputStream oisSlave = new
				// ObjectInputStream(masterSocket.getInputStream());
				// oosSlave.writeUnshared(clientMessege);
				// // come back here
				// Messege completedMessege = (Messege) oisSlave.readObject(); // get
				// completedMessege from slave

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
}
