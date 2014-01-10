/**
 * 
 */
package ch.zhaw.da.thb.ps.math.simu;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

import java.util.Random;

/**
 * @author Daniel Brun
 * 
 *         Concrete Implementation of a simulation algorithm.
 */
public class RandomExampleAlgorithm implements SimulationAlgorithm {

	private int lowerBound;
	private int upperBound;

	private BaseParticleSystem lastPs;
	private BaseParticleSystem resultPs;

	private boolean running;

	
	private Random rand;
	/**
	 * Creates a new instance of this class.
	 */
	public RandomExampleAlgorithm() {
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
		RandomExampleAlgorithm simuAlg = new RandomExampleAlgorithm();

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

			for (int i = lowerBound; i < upperBound; i += 3) {
				resultPs.getCoordinates()[i] = rand.nextInt(1000) - 500;
				resultPs.getCoordinates()[i + 1] = rand.nextInt(1000) - 500;
				resultPs.getCoordinates()[i + 2] = rand.nextInt(1000) - 500;

				resultPs.getColors()[i] = 0.8f;
				resultPs.getColors()[i + 1] = 0.8f;
				resultPs.getColors()[i + 2] = 0.8f;
			}
			
//			for (int i = lowerBound; i < upperBound; i += 3) {
//				resultPs.getCoordinates()[i] = resultPs.getCoordinates()[i] + 0.1f;
//				resultPs.getCoordinates()[i + 1] = resultPs.getCoordinates()[i+ 1] + 0.1f;
//				resultPs.getCoordinates()[i + 2] = resultPs.getCoordinates()[i + 2] + 0.1f;
//
//				resultPs.getColors()[i] = 0.8f;
//				resultPs.getColors()[i + 1] = 0.8f;
//				resultPs.getColors()[i + 2] = 0.8f;
//			}
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
