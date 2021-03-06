/**
 * 
 */
package ch.zhaw.da.thb.ps.handler;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 *
 * Interface for the Simulation-GUI to interact with the Simulation-Server
 */
public interface SimulationHandler {

	/**
	 * Returns the next particle system.
	 * 
	 * @return The updated particle system.
	 */
	public BaseParticleSystem updateParticleSystemWithNextAvailable();
	
	/**
	 * Fired when the mouse is clicked in the canvas.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void mouseClicked(int x, int y);
}
