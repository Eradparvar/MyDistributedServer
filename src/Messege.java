import java.io.Serializable;

public class Messege implements Serializable {

	private static final long serialVersionUID = 1L;
	private String messege;
	private int jobLength;
	private int completedBySlave;

	public Messege(String messege, int jobLength) {
		this.messege = messege;
		this.jobLength = jobLength * 1000;
	}

	public String getMessege() {
		return messege.toString();
	}

	public void setMessege(String messege) {
		this.messege = messege;
	}

	public void run(int slaveNun) {
		try {
			System.out.println("Running Task:$$$$ " + messege.toString());
			Thread.sleep(jobLength);
			setCompletedBySlave(slaveNun);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getCompletedBySlave() {
		return completedBySlave;
	}

	public void setCompletedBySlave(int completedBySlave) {
		this.completedBySlave = completedBySlave;
	}
}
