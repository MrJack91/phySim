/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;

/**
 * @author Daniel Brun
 * 
 */
public class Particle extends Shape3D {

	private PointArray pointParticles;
	private ParticleControler partControler;

	private float[] coord;
	private float[] colors;

	private int particleCount;

	/**
	 * 
	 */
	public Particle(int aParticleCount) {
		particleCount = aParticleCount;

		pointParticles = new PointArray(particleCount, PointArray.COORDINATES
				| PointArray.COLOR_3 | PointArray.BY_REFERENCE);

		pointParticles.setCapability(GeometryArray.ALLOW_REF_DATA_WRITE);
		pointParticles.setCapability(GeometryArray.ALLOW_REF_DATA_READ);

		ParticleUpdater updater = new ParticleUpdater(this,particleCount);
		partControler = new ParticleControler(updater,this);

		createGeometry();
		createAppearance();
	}

	private void createAppearance() {
		Appearance app = new Appearance();
		
		PointAttributes pa = new PointAttributes();
		pa.setPointSize(5.0f);
		pa.setPointAntialiasingEnable(true);
		app.setPointAttributes(pa);
		
		setAppearance(app);

	}

	private void createGeometry() {
		coord = new float[particleCount * 3];
		colors = new float[particleCount * 3];

		for (int i = 0; i < particleCount * 3; i = i + 3) {
			setParticle(i);
		}

		pointParticles.setCoordRefFloat(coord);
		pointParticles.setColorRefFloat(colors);

		setGeometry(pointParticles);
	}

	public void setParticle(int aPos) {
		coord[aPos] = (float) Math.random();
		coord[aPos + 1] = (float) Math.random();
		coord[aPos + 2] = (float) Math.random();

		colors[aPos] = 0.8f;
		colors[aPos + 1] = 0.8f;
		colors[aPos + 2] = 0.8f;
	}

	public void updateParticle(int aPos){
		//DO some update stuff -> formula
	}
	
	/**
	 * @return the partControler
	 */
	public ParticleControler getPartControler() {
		return partControler;
	}

	public void update(ParticleUpdater updater) {
		pointParticles.updateData(updater);
		
	}

}
