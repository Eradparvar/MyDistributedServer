
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MasterServerThreadProtocol implements Runnable {
	Socket clientSocket;
	private ArrayList<ObjectOutputStream> oosSlaveList;
	private ArrayList<ObjectInputStream> oisSlaveList;
	ObjectOutputStream clientOOS;
	ObjectInputStream clientOIS;

	public MasterServerThreadProtocol(Socket clientSocket, ArrayList<ObjectOutputStream> oosList,
			ArrayList<ObjectInputStream> oisList) {
		this.clientSocket = clientSocket;
		this.oosSlaveList = oosList;
		this.oisSlaveList = oisList;
		try {
			this.clientOOS = new ObjectOutputStream(clientSocket.getOutputStream());
			this.clientOIS = new ObjectInputStream(clientSocket.getInputStream());
		} catch (EOFException e) {
			e.getStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	String hostName = "127.0.0.1";

	// ------------whole purplse to send object to slave
	@Override
	public void run() {

		try {
			boolean runThread = true;
			while (runThread) {
				// System.out.println(clientOIS.readUTF());
				Messege clientMessege = (Messege) clientOIS.readObject(); // get messege form client
				/// next method
				System.out.println("Got messge from client");
				clientMessege.setMessege("Master " + clientMessege);
				System.out.println("Changed messege to master");
				oosSlaveList.get(0).writeUnshared(clientMessege); // send messege to slave
				System.out.println("Sent messege to slave- see slave");
				// come back here
				// TODO get finished messege from slave
				Messege messegeFromSlave = (Messege) oisSlaveList.get(0).readObject();

				System.out.println("Got messege from slave");
				clientOOS.writeUnshared(messegeFromSlave);
				clientOOS.flush();
				System.out.println("Sent messege back to cleint !!");
			}
		} catch (EOFException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
