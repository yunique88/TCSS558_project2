import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * A client for a remote Key Value Server
 * @author Dave K.
 * @version 2015
 */
public class KeyValueClient {
	
	private static FileWriter fw = null;
	
	/**
	 * A method to get the current time to stamp our log
	 * Given time in ms since January 1, 1970 UDT
	 * @return String representation of current time.
	 */
	private static String getCurrentTimeStamp() {
        /* Get the current system time stamp */
        return "[" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date()) + "] ";
    }
	
	public static void main(String[] args) throws IOException {

		//used the line below from Oracle "Hello" example
        String host = (args.length < 1) ? null : args[0];
        try {
        	//if host = null means "localhost"
			Registry registry = LocateRegistry.getRegistry(host);
	        KeyValueStore stub = (KeyValueStore) registry.lookup("KeyValueStore");

	        fw = new FileWriter("ClientLog.txt", true);
	        
	        // 5 puts
	        fw.write(put(stub, "Alpha", "password"));
	        fw.write(put(stub, "Alpha", "password"));
	        fw.write(put(stub, "Beta", "wdfdfdfd"));
	        fw.write(put(stub, "Gamma", "1n2b5h"));
	        fw.write(put(stub, "Omega", "1232322"));
	        fw.write(put(stub, "Trianga", "1ooooooo2"));

	       
	        // 5 gets
	        fw.write(get(stub, "Alpha"));
	        fw.write(get(stub, "Beta"));
	        fw.write(get(stub, "Gamma"));
	        fw.write(get(stub, "Omega"));
	        fw.write(get(stub, "Holymonga"));

	       
	        // 5 deletes
	        fw.write(delete(stub, "Alpha"));
	        fw.write(delete(stub, "Beta"));
	        fw.write(delete(stub, "Gamma"));
	        fw.write(delete(stub, "Omega"));
	        fw.write(delete(stub, "Holymonga"));

	        fw.close();
	        // output these testCases.
//	        for (int i = 0; i < testCases.size(); i++) {
//		        System.out.println(testCases.get(i));
//	        }
		} catch (RemoteException | NotBoundException e) {
			fw.write(getCurrentTimeStamp() + "Error sending data:" + e.getMessage());
			//e.printStackTrace();
		}

	}

	/**
	 * Wrapper for put so you put to a stub, and also returns a log string.
	 * @param stub
	 * @param key
	 * @param value
	 * @return String of the log that it wants to write.
	 * @throws RemoteException
	 */
	public static String put(KeyValueStore stub, String key, String value) throws RemoteException {
		stub.put(key, value);
		String returner = getCurrentTimeStamp() + "Sending data: put, key = " 
					+ key + ", value = " + value + "\n";
		return returner;
	}
	
	/**
	 * Wrapper for get method so gets from a stub, and also returns a log string.
	 * @param stub
	 * @param key
	 * @return String of the log that it wants to write.
	 * @throws RemoteException
	 */
	public static String get(KeyValueStore stub, String key) throws RemoteException {
		stub.get(key);
		String returner = getCurrentTimeStamp() + "Sending data: get, key = " 
					+ key + "\n";
		return returner;
	}
	
	
	/**
	 * Wrapper for delete method so delete from a stub, and also returns a log string.
	 * @param stub
	 * @param key
	 * @return String of the log that it wants to write.
	 * @throws RemoteException
	 */
	public static String delete(KeyValueStore stub, String key) throws RemoteException {
		stub.delete(key);
		String returner = getCurrentTimeStamp() + "Sending data: delete, key = " 
					+ key + "\n";
		return returner;
	}
}