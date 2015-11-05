import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KeyValueStore extends Remote {
    KeyValueReturnWrapper put(String key, String value) throws RemoteException;
    KeyValueReturnWrapper get(String key) throws RemoteException;
    KeyValueReturnWrapper delete(String key)throws RemoteException;
}
