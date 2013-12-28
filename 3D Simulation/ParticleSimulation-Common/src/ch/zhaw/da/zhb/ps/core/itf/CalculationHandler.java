package ch.zhaw.da.zhb.ps.core.itf;

import java.util.concurrent.Callable;

import ch.zhaw.da.zhb.ps.BaseParticleSystem;


/**
 * @author Daniel Brun
 *
 * Interface for the abstraction of the simulation calculations
 */
public interface CalculationHandler extends Callable<CalculationHandler> {

	/**
	 * Calculates the "power-score" of the handler.
	 * 
	 * @return The score
	 */
	public int getScore();
	
	/**
	 * Sets the bounds for calculations.
	 * 
	 * @param aLowerBound The lower bounds.
	 * @param anUpperBound The upper bounds.
	 */
	public void setCalculationBounds(int aLowerBound, int anUpperBound);
	
	/**
	 * Sets the last (complete) calculated particle system.
	 * 
	 * @param aSystem The last calculated system.
	 */
	public void setLastParticleSystem(BaseParticleSystem aSystem);
	
	/**
	 * Fetches the resulting system.
	 * 
	 * @return The resulting system.
	 */
	public BaseParticleSystem getResult();
	
	/**
	 * @return The upper bound.
	 */
	public int getUpperBound();
	
	/**
	 * @return The lower bound.
	 */
	public int getLowerBound();
}
