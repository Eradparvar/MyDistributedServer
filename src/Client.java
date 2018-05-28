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
		System.out.println("Client started on <HOST> " + hostName + "<PORT> " + portNumber);
		for (int i = 0; i < 2; i++) {
			SocketWrapper socketWrapperClient = new SocketWrapper(hostName, portNumber);
			System.out.println("Created wrapper");

			Messege messege = new Messege("Client Messege -- Undone");
			socketWrapperClient.writeUnshared(messege);
			System.out.println("Sent messege to master");
			messege = (Messege) socketWrapperClient.readObject();
			System.out.println("Got messege form master");
			socketWrapperClient.closeAll();
			System.out.println(messege.getMessege());
			System.out.println("*****Cleint done ******");
		}
		///
		/*
		 * try (Socket clientSocket = new Socket(hostName, portNumber);
		 * ObjectOutputStream oos = new
		 * ObjectOutputStream(clientSocket.getOutputStream()); ObjectInputStream ois =
		 * new ObjectInputStream(clientSocket.getInputStream())) { for (int i = 0; i <
		 * 3; i++) { Messege messege = new Messege("client messege -- Undone");
		 * oos.writeUnshared(messege);
		 * 
		 * System.out.println("Cleint sent messege to master");
		 * 
		 * messege = (Messege) ois.readObject();
		 * 
		 * System.out.println(messege.getMessege());
		 * System.out.println("*****Cleint done ******" + i); TimeUnit.SECONDS.sleep(2);
		 * System.out.println("Slepted"); } System.out.println("Client problem not 3");
		 * } catch (Exception e) { // TODO: handle exception }
		 */
	}

}
