package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    int serverHasStopped() throws RemoteException;
}
