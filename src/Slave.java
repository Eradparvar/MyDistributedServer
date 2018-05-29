import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Slave {

	public static void main(String[] args) {

		// Hardcode in IP and Port here if required
		// args = new String[] { "200",<slaveNumHere> };

		if (args.length != 2) {
			System.err.println("Error: Usage: java SalveServer <port number>, <slaveNumber>");
			System.exit(1);
		}

		int slavePortNumber = Integer.parseInt(args[0]);
		int slaveNum = Integer.parseInt(args[1]);

		//
		try {
			System.out.println("Slave Started <PORT> " + slavePortNumber);
			ServerSocket slaveServerSocket = new ServerSocket(slavePortNumber);
			boolean runSlaveServer = true;
			while (runSlaveServer) {
				Socket masterSocket = slaveServerSocket.accept();
				new Thread(new SlaveServerThreadProtocol(masterSocket, slaveNum)).start();
				System.out.println("Slave created thread to deal with master reqest");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
