package message;
import java.io.Serializable;

public abstract class RaftMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6823779747499853247L;
	public String name;
	public Object payload;
}
