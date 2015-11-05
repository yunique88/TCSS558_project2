package compute;

import java.rmi.Remote;
import java.rmi.RemoteException;


// THIS IS A STUB FOR THE SERVER SIDE

public interface Compute extends Remote {
    <T> T executeTask(Task<T> t) throws RemoteException;
	
	public String put(String a, String b) throws RemoteException;
//	<T> T get(Task<T> t) throws RemoteException;
//	<T> T delete(Task<T> t) throws RemoteException;
}