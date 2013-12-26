/**
 * 
 */
package ch.zhaw.da.thb.ps;

import ch.zhaw.da.thb.ps.graphic.SimulationGUI;
import ch.zhaw.da.zhb.ps.BaseParticleSystem;

/**
 * @author Daniel Brun
 * 
 * Runs the Particle-Simulation-Core
 */
public class Runner {

	/**
	 * 
	 */
	public Runner() {
		//PSServerInterface server = new PSServerImpl();
		
		//Start Server
		
		//Start Simulator (Separate Thread)
		SimulationServer simuServer = new SimulationServer();
		//Register Local-Handler
		
		
		//Start GUI
		
		//TODO: Simulation GUI should be started from Simulation-Control-GUI
		new SimulationGUI(new BaseParticleSystem(1000),simuServer,200);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Runner();

	}

}
