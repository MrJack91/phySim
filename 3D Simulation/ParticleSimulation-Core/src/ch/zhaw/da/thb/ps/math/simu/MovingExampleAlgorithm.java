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
public class MovingExampleAlgorithm implements SimulationAlgorithm {

	private int lowerBound;
	private int upperBound;

	private BaseParticleSystem lastPs;
	private BaseParticleSystem resultPs;

	private boolean running;

	
	private Random rand;
	/**
	 * Creates a new instance of this class.
	 */
	public MovingExampleAlgorithm() {
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
		MovingExampleAlgorithm simuAlg = new MovingExampleAlgorithm();

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

			for (int i = lowerBound; i < upperBound; i ++) {
				resultPs.getCoordinates()[i] = resultPs.getCoordinates()[i] + 10.0f;

				//resultPs.getColors()[i] = 0.8f;
				
				if(resultPs.getCoordinates()[i] > lastPs.getParticleCount()){
					resultPs.getCoordinates()[i] = 0;
				}
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
