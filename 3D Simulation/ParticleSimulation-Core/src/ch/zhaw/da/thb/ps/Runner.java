/**
 * 
 */
package ch.zhaw.da.thb.ps;

import ch.zhaw.da.thb.ps.graphic.SimulationGUI;
import ch.zhaw.da.zhb.ps.BaseParticleSystem;
import ch.zhaw.da.zhb.ps.core.LocalCalculationHandler;
import ch.zhaw.da.zhb.ps.core.alg.MovingExampleAlgorithm;
import ch.zhaw.da.zhb.ps.core.alg.RandomExampleAlgorithm;
import ch.zhaw.da.zhb.ps.core.itf.SimulationAlgorithm;

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
		
		
		
		
		//TODO: Should be started from Simulation-Control-GUI
		BaseParticleSystem basePs = new BaseParticleSystem(10000);
		SimulationAlgorithm simuAlg = new MovingExampleAlgorithm();
		
		//Start Simulator 
		SimulationServer simuServer = new SimulationServer();
		
		//Register Local-Handler
		try {
			simuServer.setLastParticleSystem(basePs.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		simuServer.registerHandler(new LocalCalculationHandler(simuAlg));
		
		Thread simuThread = new Thread(simuServer);
		simuThread.start();
		
		//Start GUI
		new SimulationGUI(basePs,simuServer,50);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Runner();

	}

}
