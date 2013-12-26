/**
 * 
 */
package ch.zhaw.da.thb.ps.handler;

import ch.zhaw.da.zhb.ps.BaseParticleSystem;

/**
 * @author Daniel Brun
 *
 * Interface for the Simulation-GUI to interact with the Simulation-Server
 */
public interface SimulationHandler {

	/**
	 * Updates the given particle system with the next available data.
	 * 
	 * @param aParticleSystem The particle system to update.
	 * @return The updated particle system.
	 */
	public BaseParticleSystem updateParticleSystemWithNextAvailable(BaseParticleSystem aParticleSystem);
}
