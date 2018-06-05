import java.io.Serializable;

public class Messege implements Serializable {

	private static final long serialVersionUID = 1L;
	private String jobName;
	private int jobLength;
	private int completedBySlave;

	public Messege(String jobName, int jobLength) {
		this.jobName = jobName;
		this.jobLength = jobLength;
	}

	public int getCompletedBySlave() {
		return completedBySlave;
	}

	public void run(int slaveNun) {
		try {
			System.out.println("Running Task:" + this.toString());
			Thread.sleep(jobLength * 1000);
			setCompletedBySlave(slaveNun);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setCompletedBySlave(int completedBySlave) {
		this.completedBySlave = completedBySlave;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Job Name: " + jobName + " Job length: " + jobLength;
	}
}
