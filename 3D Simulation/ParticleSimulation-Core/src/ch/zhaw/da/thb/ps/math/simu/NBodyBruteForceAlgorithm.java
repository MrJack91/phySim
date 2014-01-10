/**
 * 
 */
package ch.zhaw.da.thb.ps.math.simu;

import java.util.Random;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 * 
 * Implementation of a N-Body Brute-Force Algorithm.
 */
public class NBodyBruteForceAlgorithm implements SimulationAlgorithm {

	private int lowerBound;
	private int upperBound;

	private BaseParticleSystem lastPs;
	private BaseParticleSystem resultPs;

	private boolean running;

	
	private Random rand;
	/**
	 * Creates a new instance of this class.
	 */
	public NBodyBruteForceAlgorithm() {
		running = false;
		
		rand = new Random();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.zhaw.da.zhb.ps.core.itf.SimulationAlgorithm#setCalculationBounds(int,
	 * int)
	 */
	@Override
	public void setCalculationBounds(int aLowerBound, int anUpperBound) {
		lowerBound = aLowerBound;
		upperBound = anUpperBound;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.zhaw.da.zhb.ps.core.itf.SimulationAlgorithm#setLastParticleSystem(
	 * ch.zhaw.da.zhb.ps.BaseParticleSystem)
	 */
	@Override
	public void setLastParticleSystem(BaseParticleSystem aSystem) {
		lastPs = aSystem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.da.zhb.ps.core.itf.SimulationAlgorithm#getResult()
	 */
	@Override
	public BaseParticleSystem getResult() {
		return resultPs;
	}

	@Override
	public SimulationAlgorithm copy() {
		NBodyBruteForceAlgorithm simuAlg = new NBodyBruteForceAlgorithm();

		simuAlg.lastPs = lastPs;
		simuAlg.resultPs = resultPs;
		simuAlg.lowerBound = lowerBound;
		simuAlg.upperBound = upperBound;

		return simuAlg;
	}

	@Override
	public SimulationAlgorithm call() throws Exception {
		if (!running) {
			running = true;

			try {
				resultPs = lastPs.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}

			//Gravitational Constant
			float g = 667384 * 10^-11;
			float softeningFactor = 1; //Wert???
			
			//FIXME: Calculation goes here
			//Concrete Algorithm & Code: http://http.developer.nvidia.com/GPUGems3/gpugems3_ch31.html
			//Concrete & Simple: http://physics.princeton.edu/~fpretori/Nbody/intro.htm
			//http://catdir.loc.gov/catdir/samples/cam041/2003046028.pdf
			//http://www.cs.hut.fi/~ctl/NBody.pdf
			//Complex: https://spaces.seas.harvard.edu/download/attachments/11043272/ipdps2013_camera.pdf
			//Example: http://www.ids.ias.edu/~piet/act/comp/algorithms/starter/
			//Sample code: http://www.browndeertechnology.com/docs/BDT_OpenCL_Tutorial_NBody-rev3.html
			
			
			//http://www.ids.ias.edu/~piet/act/comp/algorithms/starter/nbody_sh1.html
			
			//CODE!!!: http://physics.princeton.edu/~fpretori/Nbody/code.htm
			//Loop over each particle in the given boundaries

			for (int i = lowerBound,iRealCounter = 0; i < upperBound; i +=3, iRealCounter++) {
				float summ = 0;
				float yMass = 1;
				
				//Loop over the last particle system and calculate each dependency
				for (int x = 0, xRealCounter = 0; x < lastPs.getParticleCount()*3; x +=3,xRealCounter++) {
					float partial = 0;
					float xMass = 1;
					float r = 0; //???? r ij = x j - x i is the vector from body i to body j;
					float vec = 0; //????
					
					//Calculate
					partial =  ((xMass * r) / (float) Math.pow(vec + Math.pow(softeningFactor, 2),(3/2)));
					
					summ = summ + partial;
				}
				
				float acceleration = g * summ;
				
				//Calculate position
				
				//resultPs.getCoordinates()[i] = 0;
			}
			
			running = false;
		}
		return this;
	}

	@Override
	public int getUpperBound() {
		return upperBound;
	}

	@Override
	public int getLowerBound() {
		return lowerBound;
	}

}
