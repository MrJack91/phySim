/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryUpdater;

/**
 * @author Daniel Brun
 *
 */
public class ParticleUpdater implements GeometryUpdater {

	private int particleCount;
	private Particle particle;
	
	public ParticleUpdater(Particle aParticle, int aParticleCount) {
		particleCount = aParticleCount;
		particle = aParticle;
	}
	
	/* (non-Javadoc)
	 * @see javax.media.j3d.GeometryUpdater#updateData(javax.media.j3d.Geometry)
	 */
	@Override
	public void updateData(Geometry aGeometry) {
		
		for(int i = 0;i < particleCount*3;i = i+3){
			particle.setParticle(i);
		}

	}

}
