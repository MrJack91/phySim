/**
 * 
 */
package ch.zhaw.da.thb.ps.simulation.data;

import java.io.Serializable;

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

