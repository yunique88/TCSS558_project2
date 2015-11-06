import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author David Kaplan et. al.
 * @version 2015
 */
public class KeyValueServer implements KeyValueStore {
	
	private static FileWriter fw = null;
	
	private Map<String, String> myKeyValueMap;

	/**
	 * A method to get the current time to stamp our log
	 * Given time in ms since January 1, 1970 UDT
	 * @return the time of the event
	 */
	private static String getCurrentTimeStamp() {
        /* Get the current system time stamp */
        return new SimpleDateFormat("[yyyy-MM-dd'T'HH:mm:ss.SSS] ").format(new Date());
    }
	
	/**
	 * Private constructor prevents instantiation
	 * @throws IOException 
	 */
	private KeyValueServer() throws IOException {
		myKeyValueMap = new HashMap<String, String>();
		
		fw = new FileWriter("ServerLog.txt", true);
			
        //Populate the KeyValueMap.
    	myKeyValueMap.put("testingId1", "password123");
    	myKeyValueMap.put("testingId2", "passw023");
    	myKeyValueMap.put("testingId3", "pasee123");
    	myKeyValueMap.put("testingId4", "passwoe3");
    	myKeyValueMap.put("testingId5", "password9595");
    	myKeyValueMap.put("testingId6", "password321");
    	myKeyValueMap.put("testingId7", "pswd123");
	}
	@Override
	public synchronized KeyValueReturnWrapper put(String key, String value)   {
		
		myKeyValueMap.put(key, value);
		try {
			fw.write(getCurrentTimeStamp() + "Receiving data: put, key = " + key + ", value = " + value + "\n");
			fw.flush();
		} catch (Exception e) {
			try {
				fw.write(getCurrentTimeStamp() + "Error receiving data: put, key = " + key + ", value = " + value + "\n");
				fw.flush();
			} catch (IOException e1) {
				System.out.println(getCurrentTimeStamp() + "Error writing to log file");
				e1.printStackTrace();
			}
		}

		return new KeyValueReturnWrapper(key, value, KeyValueCode.PUT);
	}

	
	@Override
	public synchronized KeyValueReturnWrapper get(String key)  {
		KeyValueReturnWrapper kvr;
		if (myKeyValueMap.containsKey(key)) {
			kvr = new KeyValueReturnWrapper(key, myKeyValueMap.get(key),
					KeyValueCode.GET);
		} else {
			kvr = new KeyValueReturnWrapper(key, "",
					KeyValueCode.GET_KEY_MISSING);
		}
		try {
			fw.write(getCurrentTimeStamp() + "Receiving data: get, key = " + key + "\n");
			fw.flush();
		} catch (Exception e) {
			try {
				fw.write(getCurrentTimeStamp() + "Error receiving data: get, key = " + key + "\n");
				fw.flush();
			} catch (IOException e1) {
				System.out.println(getCurrentTimeStamp() + "Error writing to log file");
				e1.printStackTrace();
			}
		}
		return kvr;
	}

	@Override
	public synchronized KeyValueReturnWrapper delete(String key) {
		KeyValueReturnWrapper kvr;
		if (myKeyValueMap.containsKey(key)) {
			kvr = new KeyValueReturnWrapper(key, myKeyValueMap.remove(key),
					KeyValueCode.DELETE);
		} else {
			kvr = new KeyValueReturnWrapper(key, "",
					KeyValueCode.DELETE_KEY_MISSING);
		}
		try {
			fw.write(getCurrentTimeStamp() + "Deleting data: delete, key = " + key + "\n");
			fw.flush();
		} catch (Exception e) {
			try {
				fw.write(getCurrentTimeStamp() + "Error deleting data: delete, key = " + key + "\n");
				fw.flush();
			} catch (IOException e1) {
				System.out.println(getCurrentTimeStamp() + "Error writing to log file");
				e1.printStackTrace();
			}
		}

		return kvr;
	}
	
    public static void main(String args[]) {

        try {
            KeyValueServer kvserver = new KeyValueServer();
            KeyValueStore stub = 
            		(KeyValueStore) UnicastRemoteObject.exportObject(kvserver, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("KeyValueStore", stub);

            System.out.println("KeyValueServer started");
        } catch (Exception e) {
            System.out.println("Error starting server " + e.toString());
            e.printStackTrace();
        }
    }
}
