/**
 * 
 */
package ch.zhaw.da.thb;

import ch.zhaw.da.thb.ps.graphic.SimulationGUI;
import ch.zhaw.da.thb.ps.simulation.SimulationConfig;
import ch.zhaw.da.thb.ps.simulation.SimulationServer;
import ch.zhaw.da.thb.ps.simulation.calculation.LocalCalculationHandler;
import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 * 
 *         Class to start and stop a simulation
 */
public class SimulationStarter {

	private SimulationServer simuServer;
	private BaseParticleSystem basePs;
	private SimulationConfig config;	
	
	/**
	 * Starts the simulation with the given config.
	 */
	public SimulationStarter(SimulationConfig aConfig) {
		config = aConfig;
		
		basePs = new BaseParticleSystem(
				config.getParticleCount());

		config.getInitializeAlgorithm().initializeSystem(basePs);

		// Start Simulator
		simuServer = new SimulationServer(config);

		// Register Local-Handler
		try {
			simuServer.setLastParticleSystem(basePs.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		simuServer.registerHandler(new LocalCalculationHandler(config
				.getSimulationAlgorithm(), config));

		// Start GUI
		new SimulationGUI(basePs, simuServer, config);
	}

	/**
	 * Start the Simulation
	 */
	public void start() {
		Thread simuThread = new Thread(simuServer);
		simuThread.start();

	
	}

	/**
	 * Stops the Simulation
	 */
	public void stop() {
		simuServer.stop();
	}
}
