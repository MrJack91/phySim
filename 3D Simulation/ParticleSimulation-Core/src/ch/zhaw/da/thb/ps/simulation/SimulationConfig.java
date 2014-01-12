/**
 * 
 */
package ch.zhaw.da.thb.ps.simulation;

import ch.zhaw.da.thb.ps.math.init.ParticleInitializeAlgorithm;
import ch.zhaw.da.thb.ps.math.simu.SimulationAlgorithm;

/**
 * @author Daniel Brun
 * 
 * The Simulation configuration.
 */
public class SimulationConfig {

	private boolean useMultiCore;
	private int particleCount;
	private int guiSleepTime;
	private int serverSleepTime;

	private SimulationAlgorithm simulationAlgorithm;
	private ParticleInitializeAlgorithm initializeAlgorithm;
	
	/**
	 * @param useMultiCore True if multiple cpu should be used.
	 * @param particleCount The particle count.
	 * @param guiSleepTime The gui sleep time.
	 * @param serverSleepTime The server sleep time.
	 * @param simulationAlgorithm The simulation algorithm.
	 * @param initializeAlgorithm The system initialization algorithm.
	 */
	public SimulationConfig(boolean useMultiCore, int particleCount,
			int guiSleepTime, int serverSleepTime,
			SimulationAlgorithm simulationAlgorithm,
			ParticleInitializeAlgorithm initializeAlgorithm) {
		super();
		this.useMultiCore = useMultiCore;
		this.particleCount = particleCount;
		this.guiSleepTime = guiSleepTime;
		this.serverSleepTime = serverSleepTime;
		this.simulationAlgorithm = simulationAlgorithm;
		this.initializeAlgorithm = initializeAlgorithm;
	}
	/**
	 * @return the useMultiCore
	 */
	public boolean isUseMultiCore() {
		return useMultiCore;
	}
	/**
	 * @return the particleCount
	 */
	public int getParticleCount() {
		return particleCount;
	}
	/**
	 * @return the guiSleepTime
	 */
	public int getGuiSleepTime() {
		return guiSleepTime;
	}
	/**
	 * @return the serverSleepTime
	 */
	public int getServerSleepTime() {
		return serverSleepTime;
	}
	/**
	 * @return the simulationAlgorithm
	 */
	public SimulationAlgorithm getSimulationAlgorithm() {
		return simulationAlgorithm;
	}
	/**
	 * @return the initializeAlgorithm
	 */
	public ParticleInitializeAlgorithm getInitializeAlgorithm() {
		return initializeAlgorithm;
	}

}
