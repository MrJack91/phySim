/**
 * 
 */
package ch.zhaw.da.thb.ps.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.zhaw.da.thb.ps.handler.SimulationHandler;
import ch.zhaw.da.thb.ps.math.simu.BarnesHutAlgorithm;
import ch.zhaw.da.thb.ps.math.simu.GravityAlgorithm;
import ch.zhaw.da.thb.ps.simulation.calculation.CalculationHandler;
import ch.zhaw.da.thb.ps.simulation.data.BarnesHutParticleSystem;
import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

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
	
	private SimulationConfig config;
	
	/**
	 * Creates a new instance of this class.
	 */
	public SimulationServer(SimulationConfig aConfig) {
		systemQueue = new ConcurrentLinkedQueue<BaseParticleSystem>();
		calcHandlers = new ArrayList<CalculationHandler>();

		totalScore = 0;
		running = false;
		
		config = aConfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.da.thb.ps.handler.SimulationHandler#
	 * updateParticleSystemWithNextAvailable * (ch.zhaw.da.zhb.ps.BaseParticleSystem)
	 */
	@Override
	public BaseParticleSystem updateParticleSystemWithNextAvailable() {

		if (!systemQueue.isEmpty()) {
			currentDisplayedPs = systemQueue.remove();
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

			int loadPerScore = lastParticleSystem.getParticleCount() / totalScore;
			int lastIndex = 0;

            //
			for (CalculationHandler handler : calcHandlers) {
				// Set calculation bounds
				int range = handler.getScore() * loadPerScore;
				int upperBounds = lastIndex + (range*3);

				if (upperBounds > (lastParticleSystem.getParticleCount() * 3 - 1)) {
					upperBounds = lastParticleSystem.getParticleCount() * 3 - 1;
				}

				handler.setCalculationBounds(lastIndex, upperBounds);

				lastIndex = upperBounds;
			}

			try {
				while (running) {

					//Some special treatments
					if(config.getSimulationAlgorithm() instanceof BarnesHutAlgorithm){
						((BarnesHutParticleSystem)lastParticleSystem).createTree();
					}
					
					// Starting new calculation step
					for (CalculationHandler handler : calcHandlers) {
						// Set last data
						handler.setLastParticleSystem(lastParticleSystem);
					}
					
					List<Future<CalculationHandler>> results = executor.invokeAll(calcHandlers);
					
					// Get and merge results
					BaseParticleSystem newParticleSystem = lastParticleSystem.clone();
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
								
								newParticleSystem.setMass(mergeArray(
										newParticleSystem.getMass(),
										tmpSystem.getMass(),handler.getLowerBound(),
										handler.getUpperBound()));
								
								newParticleSystem.setVelocity(mergeArray(
										newParticleSystem.getVelocity(),
										tmpSystem.getVelocity(),handler.getLowerBound(),
										handler.getUpperBound()));
								
								newParticleSystem.setAcceleration(mergeArray(
										newParticleSystem.getAcceleration(),
										tmpSystem.getAcceleration(),handler.getLowerBound(),
										handler.getUpperBound()));
								
								results.remove(future);
								i--;
							}else{
								finished = false;
							}
						}
						Thread.sleep(config.getServerSleepTime());
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

	/**
	 * Stops the simulation.
	 */
	public void stop() {
		running  = false;
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(config.getSimulationAlgorithm() instanceof GravityAlgorithm){
			//((GravityAlgorithm)config.getSimulationAlgorithm()).
		}
		
	}
}
