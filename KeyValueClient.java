import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class KeyValueClient {

	public static void main(String[] args) {

		//below line lifted from the Oracle example
        String host = (args.length < 1) ? null : args[0];
        try {
        	// and this one
			Registry registry = LocateRegistry.getRegistry(host);
	        KeyValueStore stub = (KeyValueStore) registry.lookup("KeyValueStore");
	        //String s = stub.put("Alpha", "password");
	        //System.out.println("Put on 'Alpha', 'password' returned :" + s);
	        //s = stub.get("Alpha");
			//System.out.println("Get on 'Alpha', returned :" + s);
	        
	        List<KeyValueReturnWrapper> testCases = new ArrayList<KeyValueReturnWrapper>(); 
	        
	        // 5 puts
	        testCases.add(stub.put("Alpha", "password"));
	        testCases.add(stub.put("Beta", "wdfdfdfd"));
	        testCases.add(stub.put("Gamma", "1n2b5h"));
	        testCases.add(stub.put("Omega", "1232322"));
	        testCases.add(stub.put("Trianga", "1ooooooo2"));
	        
	        // 5 gets
	        testCases.add(stub.get("Alpha"));
	        testCases.add(stub.get("Beta"));
	        testCases.add(stub.get("Gamma"));
	        testCases.add(stub.get("Omega"));
	        testCases.add(stub.get("Holymonga"));
	        
	        // 5 deletes
	        testCases.add(stub.delete("Alpha"));
	        testCases.add(stub.delete("Beta"));
	        testCases.add(stub.delete("Gamma"));
	        testCases.add(stub.delete("Omega"));
	        testCases.add(stub.delete("Holymonga"));
	        
	        // output these testCases.
	        for (int i = 0; i < testCases.size(); i++) {
		        System.out.println(testCases.get(i));
	        }
	        
		} catch (RemoteException | NotBoundException e) {
			System.out.println("Error :" + e.getMessage());
			//e.printStackTrace();
		}

	}

}