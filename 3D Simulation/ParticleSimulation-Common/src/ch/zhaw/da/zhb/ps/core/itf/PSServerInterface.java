/**
 * 
 */
package ch.zhaw.da.zhb.ps.core.itf;

import java.rmi.Remote;

/**
 * @author Daniel Brun
 *
 * RMI-Interface for the Server-Core.
 */
public interface PSServerInterface extends Remote {

	public boolean registerClient();
}
