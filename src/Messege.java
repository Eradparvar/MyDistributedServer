import java.io.Serializable;

public class Messege implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messege;

	public Messege(String messege) {
		this.messege = messege;
	}

	public String getMessege() {
		return messege;
	}

	public void setMessege(String messege) {
		this.messege = messege;
	}
}
