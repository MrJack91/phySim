/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupOnElapsedTime;

/**
 * @author Daniel Brun
 *
 * Controler-Class for the particle system update.
 */
public class ParticleSystemControler extends Behavior {

	private WakeupCondition wakeUpCondition;
	private ParticleSystemUpdater psUpdater;
	private ParticleSystemGraphic psGraphic;
	

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aDelay The timer delay in miliseconds.
	 * @param aPsUpdater The updater.
	 * @param aPsGraphic The graphic.
	 */
	public ParticleSystemControler(int aDelay,ParticleSystemUpdater aPsUpdater, ParticleSystemGraphic aPsGraphic) {
		wakeUpCondition = new WakeupOnElapsedTime(aDelay);
		
		psUpdater = aPsUpdater;
		psGraphic = aPsGraphic;
	}

	/* (non-Javadoc)
	 * @see javax.media.j3d.Behavior#initialize()
	 */
	@Override
	public void initialize() {
		wakeupOn(wakeUpCondition);

	}

	/* (non-Javadoc)
	 * @see javax.media.j3d.Behavior#processStimulus(java.util.Enumeration)
	 */
	@Override
	public void processStimulus(Enumeration aCriteria) {
		psGraphic.invokeUpdate(psUpdater);
		wakeupOn(wakeUpCondition);
	}
}
