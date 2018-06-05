
public class ServerInfo {

	private String hostName;
	private int portNum;
	private int numOfTasks = 0;

	public ServerInfo(String hostName, int portNum) {
		this.hostName = hostName;
		this.portNum = portNum;

	}

	public synchronized void decrementNumOfTasks() {
		numOfTasks--;
	}

	public String getHostName() {
		return hostName;
	}

	public int getNumOfTaskOnQueue() {
		return numOfTasks;
	}

	public int getPortNum() {
		return portNum;
	}

	public synchronized void incrementNumOfTasks() {
		numOfTasks++;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}

}
