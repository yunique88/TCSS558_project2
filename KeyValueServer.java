import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


public class KeyValueServer implements KeyValueStore {
	
	private Map<String, String> myKeyValueMap;

	private KeyValueServer() {
		myKeyValueMap = new HashMap<String, String>();
			
	// Prepopulate the KeyValueMap.
	    	myKeyValueMap.put("testingId1", "password123");
	    	myKeyValueMap.put("testingId2", "passw023");
	    	myKeyValueMap.put("testingId3", "pasee123");
	    	myKeyValueMap.put("testingId4", "passwoe3");
	    	myKeyValueMap.put("testingId5", "password9595");
	    	myKeyValueMap.put("testingId6", "password321");
	    	myKeyValueMap.put("testingId7", "pswd123");
		}
	
	@Override
	public KeyValueReturnWrapper put(String key, String value) throws RemoteException {
		myKeyValueMap.put(key, value);
		return new KeyValueReturnWrapper(key, value, KeyValueCode.PUT);
	}

	@Override
	public KeyValueReturnWrapper get(String key) throws RemoteException {
		KeyValueReturnWrapper kvr;
		if (myKeyValueMap.containsKey(key)) {
			kvr = new KeyValueReturnWrapper(key, myKeyValueMap.get(key),
					KeyValueCode.GET);
		} else {
			kvr = new KeyValueReturnWrapper(key, "",
					KeyValueCode.GET_KEY_MISSING);
		}
		return kvr;
	}

	@Override
	public KeyValueReturnWrapper delete(String key) {
		KeyValueReturnWrapper kvr;
		if (myKeyValueMap.containsKey(key)) {
			kvr = new KeyValueReturnWrapper(key, myKeyValueMap.remove(key),
					KeyValueCode.DELETE);
		} else {
			kvr = new KeyValueReturnWrapper(key, "",
					KeyValueCode.DELETE_KEY_MISSING);
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
