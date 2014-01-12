/**
 * 
 */
package ch.zhaw.da.thb.ps;

import ch.zhaw.da.thb.SimulationStarter;
import ch.zhaw.da.thb.ps.math.init.ParticleInitializeAlgorithm;
import ch.zhaw.da.thb.ps.math.init.RandomParticleIniAlgImpl;
import ch.zhaw.da.thb.ps.math.simu.GravityAlgorithm;
import ch.zhaw.da.thb.ps.math.simu.SimulationAlgorithm;
import ch.zhaw.da.thb.ps.simulation.SimulationConfig;

/**
 * @author Daniel Brun
 * 
 *         Runs the Particle-Simulation-Core
 */
public class Runner {

	/**
	 * 
	 */
	public Runner() {
		//PSServerInterface server = new PSServerImpl();
		
		
		
		
		//TODO: Should be started from Simulation-Control-GUI
		// BaseParticleSystem basePs = new BaseParticleSystem(100000);

		// SimulationAlgorithm simuAlg = new MeetpointAlgorithm();
		// simuAlg.setConfiguration("cube");

		// SimulationAlgorithm simuAlg = new GravityAlgorithm();

		// SimulationAlgorithm simuAlg = new NBodyBruteForceAlgorithm();
		// SimulationAlgorithm simuAlg = new RandomExampleAlgorithm();
		// SimulationAlgorithm simuAlg = new MovingExampleAlgorithm();
		// SimulationAlgorithm simuAlg = new RandomExampleAlgorithm();

		ParticleInitializeAlgorithm initAlg = new RandomParticleIniAlgImpl();
		// Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(1000);
		SimulationAlgorithm simuAlg = new GravityAlgorithm();

		SimulationConfig config = new SimulationConfig(true, 100000, 50, 5, simuAlg, initAlg);

		SimulationStarter starter = new SimulationStarter(config);
		starter.start();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Runner();

	}

}
