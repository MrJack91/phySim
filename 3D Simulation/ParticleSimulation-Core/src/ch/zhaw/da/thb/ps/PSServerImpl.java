/**
 * 
 */
package ch.zhaw.da.thb.ps;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Daniel Brun
 *
 */
public class PSServerImpl extends UnicastRemoteObject{

	/**
	 * @throws RemoteException
	 */
	public PSServerImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @throws RemoteException
	 */
	public PSServerImpl(int arg0) throws RemoteException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws RemoteException
	 */
	public PSServerImpl(int arg0, RMIClientSocketFactory arg1,
			RMIServerSocketFactory arg2) throws RemoteException {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

}
