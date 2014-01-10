/**
 * 
 */
package ch.zhaw.da.thb.ps;

import ch.zhaw.da.thb.ps.graphic.SimulationGUI;
import ch.zhaw.da.thb.ps.math.init.ParticleInitializeAlgorithm;
import ch.zhaw.da.thb.ps.math.init.RandomParticleIniAlgImpl;
import ch.zhaw.da.thb.ps.math.simu.MeetpointAlgorithm;
import ch.zhaw.da.thb.ps.math.simu.SimulationAlgorithm;
import ch.zhaw.da.thb.ps.simulation.SimulationServer;
import ch.zhaw.da.thb.ps.simulation.calculation.LocalCalculationHandler;
import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

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


        SimulationAlgorithm simuAlg = new MeetpointAlgorithm();
        simuAlg.setConfiguration("cube");
        // SimulationAlgorithm simuAlg = new MovingExampleAlgorithm();
        // SimulationAlgorithm simuAlg = new NBodyBruteForceAlgorithm();
        // SimulationAlgorithm simuAlg = new RandomExampleAlgorithm();



		ParticleInitializeAlgorithm initAlg = new RandomParticleIniAlgImpl();
		initAlg.initializeSystem(basePs);
		
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
