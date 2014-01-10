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
public abstract class BaseAlgorithm implements SimulationAlgorithm {

	protected int lowerBound;
	protected int upperBound;

	protected BaseParticleSystem lastPs;
	protected BaseParticleSystem resultPs;

	protected boolean running;


	protected Random rand;
	/**
	 * Creates a new instance of this class.
	 */
	public BaseAlgorithm() {
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
	public abstract SimulationAlgorithm call() throws Exception;

	@Override
	public int getUpperBound() {
		return upperBound;
	}

	@Override
	public int getLowerBound() {
		return lowerBound;
	}

    /**
     * Get the coordinate of a particle
     * @param i current particle
     * @param trackIndex
     */
    protected void trackParticle(int i, int trackIndex) {
        if (i == trackIndex) {
            System.out.println(
                    "(" + resultPs.getCoordinates()[i] + ", " + resultPs.getCoordinates()[i+1] + ", " + resultPs.getCoordinates()[i+2] + ")"
            );
        }
    }

}
