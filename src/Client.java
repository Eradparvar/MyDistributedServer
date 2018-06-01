import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {

	public static void main(String[] args) {

		// Hardcode in IP and Port here if required
		args = new String[] { "127.0.0.1", "100" };

		if (args.length != 2) {
			System.err.println("Usage: java ClientServer <host name> <port number>");
			System.exit(1);
		}
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		System.out.println("Client started on <HOST> " + hostName + " <PORT> " + portNumber);
		SocketWrapper socketWrapperClient = null;
		for (int i = 0; i < 2; i++) {
			socketWrapperClient = new SocketWrapper(hostName, portNumber);
			System.out.println("Created wrapper");

			Messege messege = new Messege("Image", 1);
			socketWrapperClient.writeUnshared(messege);
			System.out.println("Sent messege to master");
			messege = (Messege) socketWrapperClient.readObject();
			System.out.println("Got messege form master");

			System.out.println("Recived " + messege.getMessege());
			System.out.println("*****Cleint done ******");
		}

		socketWrapperClient.closeAll();

	}

}
