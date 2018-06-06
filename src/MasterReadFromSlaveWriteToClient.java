
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MasterReadFromSlaveWriteToClient implements Runnable
{
	private ArrayList<ServerInfo> slaveDir;
	private ObjectOutputStream clientOOS;
	private ObjectInputStream slaveOIS;
	private int chosenSlave;
	
	public MasterReadFromSlaveWriteToClient(int chosenSlave, ObjectOutputStream clientOOS, ObjectInputStream slaveOIS, ArrayList<ServerInfo> slaveDir)
	{
		this.slaveDir = slaveDir;
		this.clientOOS = clientOOS;
		this.slaveOIS = slaveOIS;
		this.chosenSlave = chosenSlave;
	}

	@Override
	public void run()
	{
		try
		{
			Message completedMessage = (Message) slaveOIS.readObject();
			
			slaveDir.get(chosenSlave).decrementNumOfTasks();
			
			System.out.println("Got message from slave");
			clientOOS.writeUnshared(completedMessage);
			clientOOS.flush();
			System.out.println("Sent message back to client !!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
