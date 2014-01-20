/**
 * 
 */
package ch.zhaw.da.thb.ps.math.simu;

import java.awt.Color;

import javax.vecmath.Color3f;

import ch.zhaw.da.thb.ps.simulation.data.BarnesHutParticleSystem;
import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 * 
 *         Implementation of a N-Body Brute-Force Algorithm.
 */
public class BarnesHutAlgorithm implements SimulationAlgorithm {

	private int lowerBound;
	private int upperBound;

	private BarnesHutParticleSystem lastPs;
	private BarnesHutParticleSystem resultPs;

	private boolean running;

	/**
	 * Creates a new instance of this class.
	 */
	public BarnesHutAlgorithm() {
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
		lastPs = (BarnesHutParticleSystem) aSystem;
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
		BarnesHutAlgorithm simuAlg = new BarnesHutAlgorithm();

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

			// Loop over each particle in the given boundaries
			for (int i = lowerBound; i < upperBound; i += 3) {
				float lastX = lastPs.getCoordinates()[i];
				float lastY = lastPs.getCoordinates()[i + 1];
				float lastZ = lastPs.getCoordinates()[i + 2];
				
				lastPs.getRootNode().calculate(i, lastX, lastY, lastZ, resultPs);
				
				//s = v * t + s0
				resultPs.getCoordinates()[i] = lastPs.getCoordinates()[i]  + resultPs.getVelocity()[i] * 200;
				resultPs.getCoordinates()[i+1] = lastPs.getCoordinates()[i+1] + resultPs.getVelocity()[i+1] *200;
				resultPs.getCoordinates()[i+2] = lastPs.getCoordinates()[i+2] + resultPs.getVelocity()[i+2] * 200;
				
				float unsignedAcc = Math.abs(resultPs.getVelocity()[i]) * 100;
				
				int red = (int) (17 + 4 * unsignedAcc );
				int green = 209;

				if (red >= 209) {
					red = 209;
					green = (int) (209 - 3 * unsignedAcc);
				}

				if (green <= 17) {
					green = 17;
				}

				Color color = new Color(red, green, 17);
				color3f.set(color);

				resultPs.getColors()[i] = color3f.x;
				resultPs.getColors()[i + 1] = color3f.y;
				resultPs.getColors()[i + 2] = color3f.z;
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