/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryUpdater;

import ch.zhaw.da.thb.ps.handler.SimulationHandler;
import ch.zhaw.da.zhb.ps.BaseParticleSystem;

/**
 * @author Daniel Brun
 *
 * Geometry-Updater for a Particle System.
 */
public class ParticleSystemUpdater implements GeometryUpdater {

	private BaseParticleSystem particleSystem;
	private SimulationHandler simuHandler;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aParticleSystem The particle system to update.
	 * @param aSimuHandler The simulation handler.
	 */
	public ParticleSystemUpdater(BaseParticleSystem aParticleSystem, SimulationHandler aSimuHandler) {
		super();
		simuHandler = aSimuHandler;
		particleSystem = aParticleSystem;
	}

	/* (non-Javadoc)
	 * @see javax.media.j3d.GeometryUpdater#updateData(javax.media.j3d.Geometry)
	 */
	@Override
	public void updateData(Geometry aGeometry) {
		particleSystem = simuHandler.updateParticleSystemWithNextAvailable(particleSystem);
	}

}
