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

	// ---whole [purplse so send socket back to MasterServerThreadProtocol
	// 1- how many connection do you have
	// 2- add task to queeu
	@Override
	public void run() {

		try (ObjectInputStream ois = new ObjectInputStream(masterSocket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(masterSocket.getOutputStream())) {

			Messege masterMessege = (Messege) ois.readObject();
			masterMessege.run(slaveNum);

			oos.writeUnshared(masterMessege);
			System.out.println("Slave sent back " + masterMessege);

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
