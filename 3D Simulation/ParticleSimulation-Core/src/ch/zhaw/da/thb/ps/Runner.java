/**
 * 
 */
package ch.zhaw.da.thb.ps;

import ch.zhaw.da.thb.SimulationStarter;
import ch.zhaw.da.thb.ps.math.init.Grid3DInitAlgImpl;
import ch.zhaw.da.thb.ps.math.simu.NBodyBruteForceAlgorithm;
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

        // select an anlgorithm
        // SimulationAlgorithm simuAlg = new MeetpointAlgorithm();
        // simuAlg.setConfiguration("cube");
		 SimulationAlgorithm simuAlg = new NBodyBruteForceAlgorithm();
		// SimulationAlgorithm simuAlg = new RandomExampleAlgorithm();
		// SimulationAlgorithm simuAlg = new MovingExampleAlgorithm();
       // SimulationAlgorithm simuAlg = new GravityAlgorithm();

		//ParticleInitializeAlgorithm initAlg = new RandomParticleIniAlgImpl();
		// Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(10,500);
		 Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(10,500,50,400);
		// Grid2DInitAlgImpl initAlg = new Grid2DInitAlgImpl(500,5,100);


		SimulationConfig config = new SimulationConfig(true, 1000, 10, 5, simuAlg, initAlg);

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
