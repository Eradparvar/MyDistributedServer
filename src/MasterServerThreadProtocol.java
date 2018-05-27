
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServerThreadProtocol implements Runnable {
	Socket clientSocket;

	public MasterServerThreadProtocol(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	String hostName = "127.0.0.1";
	static int slavePortNumber = 200;

	// ------------whole purplse to send object to slave
	@Override
	public void run() {
		// strat test

		try (ObjectOutputStream oosc = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream oisc = new ObjectInputStream(clientSocket.getInputStream());
				ServerSocket serverSocket = new ServerSocket(slavePortNumber);
				Socket slaveSocket = serverSocket.accept();
				ObjectOutputStream ooss = new ObjectOutputStream(slaveSocket.getOutputStream());
				ObjectInputStream oiss = new ObjectInputStream(slaveSocket.getInputStream())) {

			Messege clientMessege = (Messege) oisc.readObject(); // get messege for client
			/// next method

			clientMessege.setMessege("Master" + clientMessege);
			ooss.writeObject(clientMessege); // send messege to slave
			// come back here
			Messege messegeFromSlave = (Messege) oiss.readObject(); // get finished messege from slave

			oosc.writeObject(messegeFromSlave);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
