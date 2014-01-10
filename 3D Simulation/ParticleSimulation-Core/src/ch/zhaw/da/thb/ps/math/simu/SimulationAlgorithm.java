/**
 * 
 */
package ch.zhaw.da.thb.ps.math.simu;

import java.util.concurrent.Callable;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 * 
 */
public interface SimulationAlgorithm extends Callable<SimulationAlgorithm> {

	/**
	 * Sets the bounds for calculations.
	 * 
	 * @param aLowerBound
	 *            The lower bounds.
	 * @param anUpperBound
	 *            The upper bounds.
	 */
	public void setCalculationBounds(int aLowerBound, int anUpperBound);

	/**
	 * Sets the last (complete) calculated particle system.
	 * 
	 * @param aSystem
	 *            The last calculated system.
	 */
	public void setLastParticleSystem(BaseParticleSystem aSystem);

	/**
	 * Fetches the resulting system.
	 * 
	 * @return The resulting system.
	 */
	public BaseParticleSystem getResult();

	/**
	 * Clones the underlining object.
	 * 
	 * @return An exact clone of the object.
	 */
	public SimulationAlgorithm copy();

	/**
	 * @return The upper bound.
	 */
	public int getUpperBound();

	/**
	 * @return The lower bound.
	 */
	public int getLowerBound();
}
