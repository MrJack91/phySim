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
 */
public class ParticleControler extends Behavior {

	private WakeupCondition timeDelay;
	private ParticleUpdater updater;
	private Particle particle;
	
	/**
	 * 
	 */
	public ParticleControler(ParticleUpdater anUpdater, Particle aParticle) {
		timeDelay = new WakeupOnElapsedTime(2000);
		updater = anUpdater;
		particle = aParticle;
	}

	/* (non-Javadoc)
	 * @see javax.media.j3d.Behavior#initialize()
	 */
	@Override
	public void initialize() {
		wakeupOn(timeDelay);

	}

	/* (non-Javadoc)
	 * @see javax.media.j3d.Behavior#processStimulus(java.util.Enumeration)
	 */
	@Override
	public void processStimulus(Enumeration aCriteria) {
		particle.update(updater);
		wakeupOn(timeDelay);

	}

}
