import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Slave {

	public static void main(String[] args) {

		// Hardcode in IP and Port here if required
		args = new String[] { "127.0.0.1", "200" };

		if (args.length != 2) {
			System.err.println("Usage: java SalveServer <port number>");
			System.exit(1);
		}

		String hostName = args[0];
		int slavePortNumber = Integer.parseInt(args[1]);
		// testing area

		try (Socket slave = new Socket(hostName, slavePortNumber);
				ObjectOutputStream oos = new ObjectOutputStream(slave.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(slave.getInputStream());) {
			System.out.println("Slave Started");
			Messege messege = (Messege) ois.readObject();
			messege.setMessege("Slave");
			System.out.println("Changed messege " + messege.getMessege());
			// sending back to master
			oos.writeObject(messege);
			System.out.println("Slave send messege to master--Slave done !");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/// oring code V
		// whole job is to accept socket form MasterServerThreadProtocol and
		// SlaveServerThreadProtocol sends it back to MasterServerThreadProtocol
		/*
		 * try (ServerSocket masterServerSocket = new ServerSocket(200);) {
		 * System.out.println("SlaveServer Started with port " + slavePortNumber);
		 * boolean runSlaveServer = true; while (runSlaveServer) { Socket clientSocket =
		 * masterServerSocket.accept(); new Thread(new
		 * SlaveServerThreadProtocol(clientSocket)).start();
		 * System.out.println("SlaveServer Created thread to deal with master reqest");
		 * 
		 * }
		 * 
		 * } catch (Exception e) { // TODO: handle exception }
		 */
	}
}
