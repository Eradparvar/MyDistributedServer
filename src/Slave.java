import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Slave {

	public static void main(String[] args) {

		// Hardcode in IP and Port here if required
		args = new String[] { "127.0.0.1", "200" };

		if (args.length != 2) {
			System.err.println("Usage: java SalveServer <port number>");
			System.exit(1);
		}

		String slaveHostName = args[0];
		int slavePortNumber = Integer.parseInt(args[1]);

		try {
			Socket slave = new Socket(slaveHostName, slavePortNumber);
			ObjectOutputStream oos = new ObjectOutputStream(slave.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(slave.getInputStream());
			System.out.println("Slave Started  <HOSTNAME> " + slaveHostName + "<PORT> " + slavePortNumber);
			boolean runSlaveServer = true;
			Messege messege = null;
			while (runSlaveServer) {
				
				try {
					
					messege = (Messege) ois.readObject();
					messege.setMessege("Slave");
					System.out.println("Changed messege" + messege.getMessege());
					// sending back to master
					oos.writeUnshared(messege);
					System.out.println("Slave sent messege to master--Slave done !");
				} catch (EOFException e) {
					
				}

			}

		} catch (EOFException end) {

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
