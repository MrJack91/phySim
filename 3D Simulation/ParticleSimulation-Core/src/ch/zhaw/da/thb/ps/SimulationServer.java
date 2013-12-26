/**
 * 
 */
package ch.zhaw.da.thb.ps;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	
	/**
	 * Creates a new instance of this class.
	 */
	public SimulationServer() {
		systemQueue = new LinkedList<BaseParticleSystem>();
		calcHandlers = new ArrayList<CalculationHandler>();
		
		totalScore = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.da.thb.ps.handler.SimulationHandler#
	 * updateParticleSystemWithNextAvailable
	 * (ch.zhaw.da.zhb.ps.BaseParticleSystem)
	 */
	@Override
	public BaseParticleSystem updateParticleSystemWithNextAvailable(
			BaseParticleSystem aParticleSystem) {
		
		//TODO: Copy elements from queue
		aParticleSystem.invokeUpdate();
		
		if (!systemQueue.isEmpty()) {
			
		}
		
		return aParticleSystem;
	}

	public void registerHandler(CalculationHandler aHandler){
		if(!calcHandlers.contains(aHandler)){
			calcHandlers.add(aHandler);
			
			totalScore += aHandler.getScore();
		}
	}
	
	@Override
	public void run() {
		if(!running && lastParticleSystem != null){
			running = true;
			
			executor = Executors.newFixedThreadPool(calcHandlers.size());
					
			for(CalculationHandler handler : calcHandlers){
				//Set calculation bounds
				//TODO:
			}
			
			while(running){
				
				//Starting new calculation step
				for(CalculationHandler handler : calcHandlers){
					//Set last data
					//TODO:
					
					//Start calculation
					executor.execute(handler);
				}
				
				//Get and merge results
				//TODO:
			}
		}
	}

	/**
	 * @param lastParticleSystem the lastParticleSystem to set
	 */
	public void setLastParticleSystem(BaseParticleSystem lastParticleSystem) {
		this.lastParticleSystem = lastParticleSystem;
	}
}
