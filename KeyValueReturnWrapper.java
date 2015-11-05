import java.io.Serializable;


public class KeyValueReturnWrapper implements Serializable {
	public static final long serialVersionUID = 1L;
	private String myKey;
	private String myValue;
	private KeyValueCode myCode;
	public KeyValueReturnWrapper(final String theKey, final String theValue,
			final KeyValueCode theCode) {
		myKey = theKey;
		myValue = theValue;
		myCode = theCode;
	}
	public String getKey() {
		return myKey;
	}
	public String getValue() {
		return myValue;
	}
	public KeyValueCode getCode() {
		return myCode;
	}
	@Override
	public String toString() {
		return "[KeyValueReturnWrapper key=" + myKey + " value=" 
				+ myValue + " code=" + myCode;
	}

}
