import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SlaveServerThreadProtocol implements Runnable {

	private Socket clientSocket;

	public SlaveServerThreadProtocol(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	// ---whole [purplse so send socket back to MasterServerThreadProtocol
	@Override
	public void run() {

		try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

			Messege clientMessege = (Messege) ois.readObject();
			System.out.println(clientMessege.getMessege());
			clientMessege.setMessege("#### client messege -- Done ###");

			oos.writeObject(clientMessege);
			System.out.println("SlaveServerThreadProtocol/Slave finished");

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
