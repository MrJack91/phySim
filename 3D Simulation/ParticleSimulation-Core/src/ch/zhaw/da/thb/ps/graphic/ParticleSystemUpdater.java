/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryUpdater;

import ch.zhaw.da.zhb.ps.BaseParticleSystem;

/**
 * @author Daniel Brun
 *
 * Geometry-Updater for a Particle System.
 */
public class ParticleSystemUpdater implements GeometryUpdater {

	private BaseParticleSystem particleSystem;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aParticleSystem The particle system to update.
	 */
	public ParticleSystemUpdater(BaseParticleSystem aParticleSystem) {
		super();
		particleSystem = aParticleSystem;
	}

	/* (non-Javadoc)
	 * @see javax.media.j3d.GeometryUpdater#updateData(javax.media.j3d.Geometry)
	 */
	@Override
	public void updateData(Geometry aGeometry) {
		particleSystem.invokeUpdate();
	}

}
