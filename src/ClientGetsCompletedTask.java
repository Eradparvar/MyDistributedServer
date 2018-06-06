
public class ClientGetsCompletedTask implements Runnable
{
	private SocketWrapper socketWrapperClient;
	
	public ClientGetsCompletedTask(SocketWrapper socketWrapperClient)
	{
		this.socketWrapperClient = socketWrapperClient;
	}

	@Override
	public void run()
	{
		System.out.println("Sent task to master, waiting for completion");
		
		//Message message = (Message) socketWrapperClient.readObject();
		System.out.println("Received " + socketWrapperClient.readObject());
		System.out.println("*****Client done ******");
	}	
}
