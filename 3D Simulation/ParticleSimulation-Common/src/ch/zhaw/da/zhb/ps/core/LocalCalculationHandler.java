/**
 * 
 */
package ch.zhaw.da.zhb.ps.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.zhaw.da.zhb.ps.core.itf.CalculationHandler;

/**
 * @author Daniel Brun
 *
 */
public class LocalCalculationHandler implements CalculationHandler {

	private int score;
	
	private ExecutorService executor;
	
	/**
	 * 
	 */
	public LocalCalculationHandler() {
		
		//Calculate score
		//TODO: Count of Cores + Threads (max mögliche, guter Wert)
		score = 1;
		
		executor = Executors.newFixedThreadPool(score);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.da.zhb.ps.core.itf.CalculationHandler#getScore()
	 */
	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void run() {
		//TODO: Implement
		
		//Set calculation boundaries
		
		//Set data
		
		//Start calculation
		
		//get and merge result
		
	}

}
