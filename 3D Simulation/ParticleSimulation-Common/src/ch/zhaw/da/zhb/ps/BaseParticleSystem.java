/**
 * 
 */
package ch.zhaw.da.zhb.ps;

import java.io.Serializable;

/**
 * @author Daniel Brun
 *
 * Holds the base data for a particle system
 */
public class BaseParticleSystem implements Serializable {

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
	}

	/**
	 * Initializes the particle system.
	 */
	public void initializeParticleSystem(){
		coordinates = new float[particleCount * 3];
		colors = new float[particleCount * 3];
		
		for (int i = 0; i < particleCount * 3; i = i + 3) {
			setParticle(i);
		}

	}
	
	//TODO: Implement with algorithms
	public void setParticle(int aPos) {
		coordinates[aPos] = (float) Math.random();
		coordinates[aPos + 1] = (float) Math.random();
		coordinates[aPos + 2] = (float) Math.random();

		colors[aPos] = 0.8f;
		colors[aPos + 1] = 0.8f;
		colors[aPos + 2] = 0.8f;
	}

	//TODO: Implement with algorithms
	public void updateParticle(int aPos){
		//DO some update stuff -> formula
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
	 * @return the colors
	 */
	public float[] getColors() {
		return colors;
	}

	public void invokeUpdate() {
		for (int i = 0; i < particleCount * 3; i = i + 3) {
			setParticle(i);
		}
	}
}
