import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 00;
	private String jobName;
	private int jobLength;
	private int completedBySlave;

	public Message(String jobName, int jobLength) {
		this.jobName = jobName;
		this.jobLength = jobLength;
	}

	public int getCompletedBySlave() {
		return completedBySlave;
	}

	public void execute(int slaveNum) {
		try {
			System.out.println("Running Task:" + this.toString());
			//Thread.sleep(jobLength * 1000);
			for(int i=jobLength*1000; i>0; i--)
			{
				Thread.sleep(1); // this is just so the thread is using space on the processor, like a real task would
			}
			setCompletedBySlave(slaveNum);

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
