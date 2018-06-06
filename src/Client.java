
import java.util.Random;
import java.util.Scanner;

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
		SocketWrapper socketWrapperClient = new SocketWrapper(hostName, portNumber);
		
		Random rand = new Random();
		
		Scanner sc = new Scanner(System.in);
		boolean runClient = true;
		while (runClient) {
			System.out.println("Enter Job type");
			String type = "java";// sc.next();
			System.out.println("Enter Job length");
			int jobLength = rand.nextInt(5)+1;// sc.nextInt();
			Message message = new Message(type, jobLength);
			socketWrapperClient.writeUnshared(message);
			
			//new Thread(new ClientGetsCompletedTask(socketWrapperClient)).start();
			
			System.out.println("Sent task to master, waiting for completion");
			
			message = (Message) socketWrapperClient.readObject();
			System.out.println("Received " + message);
			System.out.println("*****Client done ******");
			
			sc.nextLine();
		}

		socketWrapperClient.closeAll();
		sc.close();
	}

}
