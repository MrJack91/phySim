/**
 * 
 */
package ch.zhaw.da.thb.ps.simulation.data;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Daniel Brun
 *
 * Holds the base data for a particle system
 */
public class BaseParticleSystem implements Serializable, Cloneable {

	/**
	 * Generated Serial Version UID.
	 */
	private static final long serialVersionUID = -8867574492639089834L;

	private int particleCount;
	
	private float[] coordinates;
	private float[] colors;
	
	private int[] mass;
	private int[] velocity;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aParticleCount The particle count.
	 */
	public BaseParticleSystem(int aParticleCount) {
		super();
		particleCount = aParticleCount;
		
		coordinates = new float[aParticleCount * 3];
		colors = new float[aParticleCount * 3];
		
		Random rand = new Random();
		//TODO: Init Algorithm
		for (int i = 0; i < particleCount*3; i += 3) {
			coordinates[i] = rand.nextInt(particleCount*2) - particleCount;
			coordinates[i + 1] = rand.nextInt(particleCount*2) - particleCount;
			coordinates[i + 2] = rand.nextInt(particleCount*2) - particleCount;

			colors[i] = 0.8f;
			colors[i + 1] = 0.8f;
			colors[i + 2] = 0.8f;
		}
	}

	/**
	 * @return the particleCount
	 */
	public int getParticleCount() {
		return particleCount;
	}

	/**
	 * @return the coordinates
	 */
	public float[] getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @param colors the colors to set
	 */
	public void setColors(float[] colors) {
		this.colors = colors;
	}

	/**
	 * @return the colors
	 */
	public float[] getColors() {
		return colors;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public BaseParticleSystem clone() throws CloneNotSupportedException {
		BaseParticleSystem clonePs = (BaseParticleSystem) super.clone();

		clonePs.particleCount = particleCount;
		clonePs.coordinates = coordinates.clone();
		clonePs.colors = colors.clone();
		
		return clonePs;
	}
}

