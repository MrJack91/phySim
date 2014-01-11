/**
 * 
 */
package ch.zhaw.da.thb.ps.math.simu;

import java.awt.Color;
import java.util.Random;

import javax.vecmath.Color3f;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 * 
 *         Implementation of a N-Body Brute-Force Algorithm.
 */
public class NBodyBruteForceAlgorithm implements SimulationAlgorithm {

	private int lowerBound;
	private int upperBound;

	private BaseParticleSystem lastPs;
	private BaseParticleSystem resultPs;

	private boolean running;

	/**
	 * Creates a new instance of this class.
	 */
	public NBodyBruteForceAlgorithm() {
		running = false;
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

			Color3f color3f = new Color3f();
			
			// Gravitational Constant
			float g = (float) (667384 * Math.pow(10, -11));
			float softeningFactor = (float)Math.pow(100,-10); 

			// FIXME: Calculation goes here
			// Concrete Algorithm & Code:
			// http://http.developer.nvidia.com/GPUGems3/gpugems3_ch31.html
			// Concrete & Simple:
			// http://physics.princeton.edu/~fpretori/Nbody/intro.htm
			// http://catdir.loc.gov/catdir/samples/cam041/2003046028.pdf
			// http://www.cs.hut.fi/~ctl/NBody.pdf
			// Complex:
			// https://spaces.seas.harvard.edu/download/attachments/11043272/ipdps2013_camera.pdf
			// Example:
			// http://www.ids.ias.edu/~piet/act/comp/algorithms/starter/
			// Sample code:
			// http://www.browndeertechnology.com/docs/BDT_OpenCL_Tutorial_NBody-rev3.html

			// http://www.ids.ias.edu/~piet/act/comp/algorithms/starter/nbody_sh1.html

			// CODE!!!: http://physics.princeton.edu/~fpretori/Nbody/code.htm
			// Loop over each particle in the given boundaries

			for (int i = lowerBound; i < upperBound; i += 3) {
				float summ = 0;

				// Loop over the last particle system and calculate each
				// dependency
				for (int x = 0; x < lastPs.getParticleCount() * 3; x += 3) {
					float distance = (float) Math
							.sqrt(Math.pow(
									lastPs.getCoordinates()[i]
											- lastPs.getCoordinates()[x], 2)
									+ Math.pow(lastPs.getCoordinates()[i + 1]
											- lastPs.getCoordinates()[x + 1], 2)
									+ Math.pow(lastPs.getCoordinates()[i + 1]
											- lastPs.getCoordinates()[x + 1], 2));

					float partial = 0;
					float xMass = 1000;

					// Calculate
					partial = ((xMass * distance) / (float) Math.pow(
							Math.pow(distance, 2)
									+ Math.pow(softeningFactor, 2), (3 / 2)));

					summ = summ + partial;
				}

				float acceleration = g * summ;

				// Calculate position

				for (int y = i; y <= (i + 3); y++) {
					int sign = (int) Math.signum(lastPs.getCoordinates()[y]);
					float nextPos = lastPs.getCoordinates()[y] - (sign * (float) acceleration * 60);
					resultPs.getCoordinates()[y] = nextPos;
				}
				
				int red = (int) (17 + 4 * acceleration * 1000) ;
				int green = 209 ;
				
				if(red >= 209){
					red = 209;
					green = (int) (209 - 3 *acceleration * 100);
				}
				
				if(green <= 17){
					green = 17;
				}
				
				Color color = new Color(red, green, 17);
				color3f.set(color);
				
				resultPs.getColors()[i] = color3f.x;
				resultPs.getColors()[i+1] = color3f.y;
				resultPs.getColors()[i+2] = color3f.z;
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

	@Override
	public void setConfiguration(String mode) {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

}
