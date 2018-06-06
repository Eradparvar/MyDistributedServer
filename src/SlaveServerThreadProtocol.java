
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SlaveServerThreadProtocol implements Runnable {

	private Socket masterSocket;
	private int slaveNum;

	public SlaveServerThreadProtocol(Socket masterSocket, int slaveNum) {
		this.masterSocket = masterSocket;
		this.slaveNum = slaveNum;
	}

	// runs the task and sends it back to MasterServerThreadProtocol
	@Override
	public void run() {

		try (ObjectInputStream ois = new ObjectInputStream(masterSocket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(masterSocket.getOutputStream())) {

			Message masterMessege = (Message) ois.readObject();
			masterMessege.execute(slaveNum);

			oos.writeUnshared(masterMessege);
			System.out.println("Slave sent back " + masterMessege);

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
