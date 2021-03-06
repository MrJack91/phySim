/**
 * 
 */
package ch.zhaw.da.thb.ps.simulation.calculation;

import ch.zhaw.da.thb.ps.math.simu.SimulationAlgorithm;
import ch.zhaw.da.thb.ps.simulation.SimulationConfig;
import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Daniel Brun
 * 
 *         Local calculation handler
 */
public class LocalCalculationHandler implements CalculationHandler {

	private boolean running;

	public int score;

	private int lowerBounds;
	private int upperBounds;

	private ExecutorService executor;

	private BaseParticleSystem lastPs;
	private BaseParticleSystem resultPs;

	private List<SimulationAlgorithm> simAlgorithms;

	private SimulationConfig config;

	/**
	 * Creates a new instance of this class
	 * 
	 * @param aSimulationAlgorithm
	 *            The algorithm which should be used for simulation.
	 */
	public LocalCalculationHandler(SimulationAlgorithm aSimulationAlgorithm, SimulationConfig aConfig) {

		if (aConfig.isUseMultiCore()) {
            // use a 3er number
            score = Runtime.getRuntime().availableProcessors();
            score += 3 - (score % 3);
		} else {
			score = 1;
		}

		executor = Executors.newFixedThreadPool(score);

		config = aConfig;

		simAlgorithms = new ArrayList<SimulationAlgorithm>();

		for (int i = 0; i < score; i++) {
			simAlgorithms.add(aSimulationAlgorithm.copy());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.da.zhb.ps.core.itf.CalculationHandler#getScore()
	 */
	@Override
	public int getScore() {
		return score;
	}

	@Override
	public CalculationHandler call() throws Exception {

		if (!running && lastPs != null) {
			running = true;

			int loadPerScore = (upperBounds - lowerBounds) / 3 / score;

			int lastIndex = 0;

			for (SimulationAlgorithm simuAlg : simAlgorithms) {
				// Set calculation bounds
				int simUpperBounds = lastIndex + (loadPerScore * 3);

				if (simUpperBounds >= upperBounds) {
					simUpperBounds = upperBounds;
				}

                /*
                System.out.println("lastIndex:\n" + lastIndex);
                System.out.println("simUpperBounds:\n" + simUpperBounds);
                System.out.println();
                */

				simuAlg.setCalculationBounds(lastIndex, simUpperBounds);
				lastIndex = simUpperBounds;
			}

			try {
				// Starting new calculation step
				for (SimulationAlgorithm simuAlg : simAlgorithms) {
					// Set last data
					simuAlg.setLastParticleSystem(lastPs);
				}

				List<Future<SimulationAlgorithm>> results = executor
						.invokeAll(simAlgorithms);

				// Get and merge results
				BaseParticleSystem newParticleSystem = lastPs.clone();
				boolean finished = false;

				while (!finished) {
					finished = true;

					for (int i = 0; i < results.size(); i++) {
						Future<SimulationAlgorithm> future = results.get(i);
						if (future.isDone()) {
							SimulationAlgorithm simuAlg = future.get();
							BaseParticleSystem tmpSystem = simuAlg.getResult();

							newParticleSystem.setCoordinates(mergeArray(
									newParticleSystem.getCoordinates(),
									tmpSystem.getCoordinates(),
									simuAlg.getLowerBound(),
									simuAlg.getUpperBound()));

							newParticleSystem.setColors(mergeArray(
									newParticleSystem.getColors(),
									tmpSystem.getColors(),
									simuAlg.getLowerBound(),
									simuAlg.getUpperBound()));
							
							newParticleSystem.setMass(mergeArray(
									newParticleSystem.getMass(),
									tmpSystem.getMass(),
									simuAlg.getLowerBound(),
									simuAlg.getUpperBound()));
							
							newParticleSystem.setVelocity(mergeArray(
									newParticleSystem.getVelocity(),
									tmpSystem.getVelocity(),
									simuAlg.getLowerBound(),
									simuAlg.getUpperBound()));
							
							newParticleSystem.setAcceleration(mergeArray(
									newParticleSystem.getAcceleration(),
									tmpSystem.getAcceleration(),simuAlg.getLowerBound(),
									simuAlg.getUpperBound()));
							
							results.remove(i);
							i--;
						} else {
							finished = false;
						}
					}
					Thread.sleep(config.getServerSleepTime());
				}

				resultPs = newParticleSystem;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			running = false;
		}

		return this;
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

	@Override
	public void setCalculationBounds(int aLowerBound, int anUpperBound) {
		upperBounds = anUpperBound;
		lowerBounds = aLowerBound;
	}

	@Override
	public void setLastParticleSystem(BaseParticleSystem aSystem) {
		lastPs = aSystem;
	}

	@Override
	public BaseParticleSystem getResult() {
		return resultPs;
	}

	@Override
	public int getUpperBound() {
		return upperBounds;
	}

	@Override
	public int getLowerBound() {
		return lowerBounds;
	}
}
