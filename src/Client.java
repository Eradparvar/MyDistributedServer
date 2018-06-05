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
		SocketWrapper socketWrapperClient = null;

		socketWrapperClient = new SocketWrapper(hostName, portNumber);
		Scanner sc = new Scanner(System.in);
		boolean runClient = true;
		while (runClient) {
			System.out.println("Enter Job type and Job lenght");
			String type = "java";// sc.next();
			int jobLenght = 1;// sc.nextInt();
			Messege messege = new Messege(type, jobLenght);
			socketWrapperClient.writeUnshared(messege);
			System.out.println("Sent task to master, waiting for completion");
			messege = (Messege) socketWrapperClient.readObject();
			System.out.println("Recived " + messege);
			System.out.println("*****Cleint done ******");
			sc.nextLine();
		}

		socketWrapperClient.closeAll();

	}

}
