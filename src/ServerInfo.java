
public class ServerInfo {

	private String hostName;
	private int portNum;
	private int numOfTasks = 0;

	public ServerInfo(String hostName, int portNum) {
		this.hostName = hostName;
		this.portNum = portNum;

	}

	public String getHostName() {
		return hostName;
	}

	public int getPortNum() {
		return portNum;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}

	public int getNumOfTaskOnQueue() {
		return numOfTasks;
	}

	public synchronized void incrementNumOfTasks() {
		numOfTasks++;
	}

	public synchronized void decrementNumOfTasks() {
		numOfTasks--;
	}

}
