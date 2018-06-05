import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper<T> {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public SocketWrapper(String hostName, int portNum) {
		try {
			socket = new Socket(hostName, portNum);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void closeAll() {
		try {
			socket.close();
			oos.close();
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ObjectInputStream getObjectInputStream() {
		return ois;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return oos;
	}

	public Object readObject() {
		try {
			return ois.readObject();
		} catch (EOFException e) {
			// do nothing
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void writeUnshared(T t) {
		try {
			oos.writeUnshared(t);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
