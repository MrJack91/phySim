/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 * 
 * Class which represents a graphical representation of a particle system.
 */
public class ParticleSystemGraphic extends Shape3D {

	private static final float POINT_SIZE = 3.0f;
	
	private PointArray pointArray;

	private BaseParticleSystem particleSystem;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aParticleSystem The particle system.
	 */
	public ParticleSystemGraphic(BaseParticleSystem aParticleSystem) {
		particleSystem = aParticleSystem;
		
		//Init Point-Array
		pointArray = new PointArray(particleSystem.getParticleCount(), PointArray.COORDINATES
				| PointArray.COLOR_3 | PointArray.BY_REFERENCE);

		//Set "Permission" for change
		pointArray.setCapability(GeometryArray.ALLOW_REF_DATA_WRITE);
		pointArray.setCapability(GeometryArray.ALLOW_REF_DATA_READ);
		
		createGeometry();
		createAppearance();
	}

	/**
	 * Create the appearance of the particles.
	 */
	private void createAppearance() {
		Appearance app = new Appearance();
		
		PointAttributes pa = new PointAttributes();
		pa.setPointSize(POINT_SIZE);
		pa.setPointAntialiasingEnable(true);
		app.setPointAttributes(pa);
		
		setAppearance(app);
	}

	/**
	 * Create the geometry of the particle system.
	 */
	private void createGeometry() {
		
		pointArray.setCoordRefFloat(particleSystem.getCoordinates());
		pointArray.setColorRefFloat(particleSystem.getColors());
		
		
		setGeometry(pointArray);
	}
	
	/**
	 * Invokes the update of the particle system.
	 * 
	 * @param anUpdater The ParticleSystemUpdater
	 */
	public void invokeUpdate(ParticleSystemUpdater anUpdater) {
		pointArray.updateData(anUpdater);
	}
}
