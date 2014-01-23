/**
 * 
 */
package ch.zhaw.da.thb.ps;

import ch.zhaw.da.thb.SimulationStarter;
import ch.zhaw.da.thb.ps.math.init.Grid2DInitAlgImpl;
import ch.zhaw.da.thb.ps.math.init.Grid3DInitAlgImpl;
import ch.zhaw.da.thb.ps.math.init.ParticleInitializeAlgorithm;
import ch.zhaw.da.thb.ps.math.init.RandomParticleIniAlgImpl;
import ch.zhaw.da.thb.ps.math.simu.BarnesHutAlgorithm;
import ch.zhaw.da.thb.ps.math.simu.GravityAlgorithm;
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
	public Runner(int aProgrammCode) {
		// SimulationAlgorithm simuAlg = new RandomExampleAlgorithm();
		// SimulationAlgorithm simuAlg = new MovingExampleAlgorithm();
		// SimulationAlgorithm simuAlg = new MeetpointAlgorithm();
		// simuAlg.setConfiguration("cube");

		SimulationAlgorithm simuAlg = null;
		ParticleInitializeAlgorithm initAlg = null;

		int particleCount = 2000;
		int sliceValue = 10;

		/* START - Parts for GravityAlgorithm grid definitions */
		// ParticleInitializeAlgorithm initAlg = new RandomParticleIniAlgImpl();

		/*
		 * sliceValue = 100; particleCount = 2000;
		 */

		/*
		 * sliceValue = 50; particleCount = 5000;
		 */

		sliceValue = 100;

		// Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(sliceValue,
		// particleCount);
		/* END - Parts for GravityAlgorithm grid definitions */

		// select an particle system base
		// ParticleInitializeAlgorithm initAlg = new RandomParticleIniAlgImpl();
		// Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(50, particleCount);
		// Grid3DInitAlgImpl initAlg = new Grid3DInitAlgImpl(10,500,50,400);
		// Grid2DInitAlgImpl initAlg = new Grid2DInitAlgImpl(500,5,100);

        // aProgrammCode = 1;

		switch (aProgrammCode) {
		// Gravity
		case 1:
			particleCount = 5000;
			simuAlg = new GravityAlgorithm();
			initAlg = new RandomParticleIniAlgImpl();
			break;
		case 2:
			particleCount = 5000;
			simuAlg = new GravityAlgorithm();
			initAlg = new Grid2DInitAlgImpl(particleCount, 1, 1);
			break;
		case 3:
			particleCount = 5000;
            sliceValue = 50;
			simuAlg = new GravityAlgorithm();
			initAlg = new Grid3DInitAlgImpl(sliceValue, particleCount);
			break;
		// NBody
		case 101:
			particleCount = 650;
			simuAlg = new NBodyBruteForceAlgorithm();
			initAlg = new RandomParticleIniAlgImpl();
			break;
		case 102:
			particleCount = 650;
			simuAlg = new NBodyBruteForceAlgorithm();
			initAlg = new Grid2DInitAlgImpl(particleCount, 50, 100);
			break;
		case 103:
			particleCount = 650;
			sliceValue = 10;
			simuAlg = new NBodyBruteForceAlgorithm();
			initAlg = new Grid3DInitAlgImpl(sliceValue, particleCount, 50, 400);
			break;

		// Barnes Hut
		case 201:
			particleCount = 1000;
			simuAlg = new BarnesHutAlgorithm();
			initAlg = new RandomParticleIniAlgImpl();
			break;
		case 202:
			particleCount = 1000;
			simuAlg = new BarnesHutAlgorithm();
			initAlg = new Grid2DInitAlgImpl(particleCount, 50, 100);
			break;
		case 203:
			particleCount = 1000;
			sliceValue = 10;
			simuAlg = new BarnesHutAlgorithm();
			initAlg = new Grid3DInitAlgImpl(sliceValue, particleCount, 50, 400);
			break;
		default: {
			simuAlg = new NBodyBruteForceAlgorithm();
			initAlg = new RandomParticleIniAlgImpl();
		}
		}

		SimulationConfig config = new SimulationConfig(true, particleCount, 10, 2, simuAlg, initAlg);

		SimulationStarter starter = new SimulationStarter(config);
		starter.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer progCount = Integer.valueOf(-1);

		if (args.length == 1) {
			progCount = Integer.parseInt(args[0]);
		}

		new Runner(203);

	}

}
