/**
 * 
 */
package ch.zhaw.da.thb.ps.simulation;

import ch.zhaw.da.thb.ps.math.init.ParticleInitializeAlgorithm;
import ch.zhaw.da.thb.ps.math.simu.SimulationAlgorithm;

/**
 * @author Daniel Brun
 *
 */
public class SimulationConfig {

	private boolean useMultiCore;
	private int particleCount;
	private int guiSleepTime;
	private int serverSleepTime;
	
	private SimulationAlgorithm simulationAlgorithm;
	private ParticleInitializeAlgorithm initializeAlgorithm;
	
	
	

	
}
