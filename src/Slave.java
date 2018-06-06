
import java.net.ServerSocket;
import java.net.Socket;

public class Slave {

	public static void main(String[] args) {

		// Hardcode in IP and Port here if required
		args = new String[] { "200", "1"};

		if (args.length != 2) {
			System.err.println("Error: Usage: java SalveServer <port number>, <slaveNumber>");
			System.exit(1);
		}

		int slavePortNumber = Integer.parseInt(args[0]);
		int slaveNum = Integer.parseInt(args[1]);

		//
		System.out.println("Slave Started <PORT> " + slavePortNumber);
		try(ServerSocket slaveServerSocket = new ServerSocket(slavePortNumber);) {			
			boolean runSlaveServer = true;
			// spawns a new thread for each task
			while (runSlaveServer) {
				Socket masterSocket = slaveServerSocket.accept();
				new Thread(new SlaveServerThreadProtocol(masterSocket, slaveNum)).start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
