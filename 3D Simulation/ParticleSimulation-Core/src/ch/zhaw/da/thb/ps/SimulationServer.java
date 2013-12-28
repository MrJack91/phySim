/**
 * 
 */
package ch.zhaw.da.thb.ps;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.zhaw.da.thb.ps.handler.SimulationHandler;
import ch.zhaw.da.zhb.ps.BaseParticleSystem;
import ch.zhaw.da.zhb.ps.core.itf.CalculationHandler;

/**
 * @author Daniel Brun
 * 
 */
public class SimulationServer implements SimulationHandler, Runnable {

	private Queue<BaseParticleSystem> systemQueue;
	private List<CalculationHandler> calcHandlers;

	private boolean running;

	private int totalScore;

	private ExecutorService executor;

	private BaseParticleSystem lastParticleSystem;

	private BaseParticleSystem currentDisplayedPs;
	/**
	 * Creates a new instance of this class.
	 */
	public SimulationServer() {
		systemQueue = new ConcurrentLinkedQueue<BaseParticleSystem>();
		calcHandlers = new ArrayList<CalculationHandler>();

		totalScore = 0;
		running = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.da.thb.ps.handler.SimulationHandler#
	 * updateParticleSystemWithNextAvailable
	 * (ch.zhaw.da.zhb.ps.BaseParticleSystem)
	 */
	@Override
	public BaseParticleSystem updateParticleSystemWithNextAvailable() {

		if (!systemQueue.isEmpty()) {
			currentDisplayedPs = systemQueue.remove();
//			for(int i = 0;i < aParticleSystem.getParticleCount()*3;i++){
//				aParticleSystem.getCoordinates()[i] = aParticleSystem.getCoordinates()[i]+ 0.1f;
//				//colors[i] = 0.8f;
//			}
			
//			mergeArray(aParticleSystem.getCoordinates(),newParticleSystem.getCoordinates(),0,aParticleSystem.getParticleCount()*3);
//			mergeArray(aParticleSystem.getColors(),newParticleSystem.getColors(),0,aParticleSystem.getParticleCount()*3);
		}

		return currentDisplayedPs;
	}

	public void registerHandler(CalculationHandler aHandler) {
		if (!calcHandlers.contains(aHandler)) {
			calcHandlers.add(aHandler);

			totalScore += aHandler.getScore();
		}
	}

	@Override
	public void run() {
		if (!running && lastParticleSystem != null) {
			running = true;

			executor = Executors.newFixedThreadPool(calcHandlers.size());

			int loadPerScore = lastParticleSystem.getParticleCount()
					/ totalScore;
			int lastIndex = 0;

			for (CalculationHandler handler : calcHandlers) {
				// Set calculation bounds
				int range = handler.getScore() * loadPerScore * 3;
				int upperBounds = lastIndex + range;

				if (upperBounds >= (lastParticleSystem.getParticleCount() * 3)) {
					upperBounds = lastParticleSystem.getParticleCount() * 3;
				}

				handler.setCalculationBounds(lastIndex, upperBounds);
				lastIndex = upperBounds;
			}

			try {
				while (running) {

					// Starting new calculation step
					for (CalculationHandler handler : calcHandlers) {
						// Set last data
						handler.setLastParticleSystem(lastParticleSystem);

					}
					
					List<Future<CalculationHandler>> results = executor.invokeAll(calcHandlers);
					
					// Get and merge results
					BaseParticleSystem newParticleSystem = lastParticleSystem
							.clone();
					boolean finished = false;
					
					while(!finished){
						finished = true;
						
						for(int i = 0;i <results.size();i++){
							Future<CalculationHandler> future = results.get(i);
							
							if(future.isDone()){
								CalculationHandler handler = future.get();
								BaseParticleSystem tmpSystem = handler.getResult();

								newParticleSystem.setCoordinates(mergeArray(
										newParticleSystem.getCoordinates(),
										tmpSystem.getCoordinates(),handler.getLowerBound(),
										handler.getUpperBound()));
								
								newParticleSystem.setColors(mergeArray(
										newParticleSystem.getColors(),
										tmpSystem.getColors(),handler.getLowerBound(),
										handler.getUpperBound()));
								
								results.remove(future);
								i--;
							}else{
								finished = false;
							}
						}
						Thread.sleep(20);
					}
					
					lastParticleSystem = newParticleSystem;
					systemQueue.add(newParticleSystem);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Merges the given data into the given data array.
	 * 
	 * @param someBaseData
	 *            The base data.
	 * @param someMergeData
	 *            The data to merge into the base data.
	 * @param aLowerBounds
	 *            The lower bounds.
	 * @param anUpperBounds
	 *            The upper bounds.
	 * @return The array with the base and the merged data.
	 */
	private float[] mergeArray(float[] someBaseData, float[] someMergeData,
			int aLowerBounds, int anUpperBounds) {
		float[] mergedData = someBaseData;

		for (int i = aLowerBounds; i < anUpperBounds; i++) {
			mergedData[i] = someMergeData[i];
		}

		return mergedData;
	}

	/**
	 * @param aLastParticleSystem
	 *            the lastParticleSystem to set
	 */
	public void setLastParticleSystem(BaseParticleSystem aLastParticleSystem) {
		lastParticleSystem = aLastParticleSystem;
		currentDisplayedPs = lastParticleSystem;
	}
}
