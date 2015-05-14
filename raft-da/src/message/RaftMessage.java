package message;
import java.io.Serializable;

public abstract class RaftMessage implements Serializable{
	public String name;
	public Object payload;
}
