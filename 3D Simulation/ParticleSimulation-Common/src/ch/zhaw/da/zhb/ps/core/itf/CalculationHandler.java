package ch.zhaw.da.zhb.ps.core.itf;

/**
 * @author Daniel Brun
 *
 * Interface for the abstraction of the simulation calculations
 */
public interface CalculationHandler extends Runnable {

	/**
	 * Calculates the "power-score" of the handler.
	 * 
	 * @return The score
	 */
	public int getScore();
}
