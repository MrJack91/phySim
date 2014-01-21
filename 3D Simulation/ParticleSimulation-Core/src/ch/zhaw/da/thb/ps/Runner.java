/**
 * 
 */
package ch.zhaw.da.thb.ps;

import ch.zhaw.da.thb.SimulationStarter;
import ch.zhaw.da.thb.ps.math.init.Grid3DInitAlgImpl;
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

        // select an algorithm
        // SimulationAlgorithm simuAlg = new MeetpointAlgorithm();
        // simuAlg.setConfiguration("cube");
        // SimulationAlgorithm simuAlg = new NBodyBruteForceAlgorithm();
        // SimulationAlgorithm simuAlg = new RandomExampleAlgorithm();
        // SimulationAlgorithm simuAlg = new MovingExampleAlgorithm();
        SimulationAlgorithm simuAlg = new GravityAlgorithm();
        // BarnesHutAlgorithm simuAlg = new BarnesHutAlgorithm();


        int sliceValue = 10;
        int particleCount = 5000;
        /* START - Parts for GravityAlgorithm grid definitions */
        sliceValue = 100;
        particleCount = 2000;






        Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(sliceValue, particleCount);
        /* END - Parts for GravityAlgorithm grid definitions */


        // select an particle system base
        // ParticleInitializeAlgorithm initAlg = new RandomParticleIniAlgImpl();
		// Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(50, particleCount);
		// Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(10,2000,50,400);
		// Grid2DInitAlgImpl initAlg = new Grid2DInitAlgImpl(500,5,100);


		SimulationConfig config = new SimulationConfig(true, particleCount, 10, 5, simuAlg, initAlg);

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
