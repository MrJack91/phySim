/**
 * 
 */
package ch.zhaw.da.thb.ps.math.init;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 *
 * Interface for initialization alogorithms for particle systems
 */
public interface ParticleInitializeAlgorithm {

	/**
	 * Initializes the particles in the given system.
	 * 
	 * @param aSystem The system to initialize
	 * @return The initialized system
	 */
	public BaseParticleSystem initializeSystem(BaseParticleSystem aSystem);
}
