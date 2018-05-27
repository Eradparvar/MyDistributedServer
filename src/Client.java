import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

		try (Socket clientSocket = new Socket(hostName, portNumber);
				ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {
			Messege messege = new Messege("client messege -- Undone");
			oos.writeObject(messege);
			System.out.println("Cleint sent messege to master");

			messege = (Messege) ois.readObject();

			System.out.println(messege.getMessege());
			System.out.println("*****Cleint done ******");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
