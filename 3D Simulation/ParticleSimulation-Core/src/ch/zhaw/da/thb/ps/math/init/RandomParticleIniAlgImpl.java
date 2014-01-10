/**
 * 
 */
package ch.zhaw.da.thb.ps.math.init;

import java.awt.Color;
import java.util.Random;

import javax.vecmath.Color3f;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 *
 * Initializes the particle system with random coordinates.
 */
public class RandomParticleIniAlgImpl implements ParticleInitializeAlgorithm {

	private Random rand;
	
	/**
	 * Creates a new instance of this class.
	 */
	public RandomParticleIniAlgImpl() {
		rand = new Random();
	}
	
	@Override
	public BaseParticleSystem initializeSystem(BaseParticleSystem aSystem) {
		int particleCount = aSystem.getParticleCount();
		
		Color3f col = new Color3f();
		Color myColor = new Color(17, 209, 23);
		col.set(myColor);
		
		for (int i = 0; i < particleCount*3; i+=3) {
			aSystem.getCoordinates()[i] = rand.nextInt(particleCount*2) - particleCount;
			aSystem.getCoordinates()[i+1] = rand.nextInt(particleCount*2) - particleCount;
			aSystem.getCoordinates()[i+2] = rand.nextInt(particleCount*2) - particleCount;
			
			aSystem.getColors()[i] = col.x;
			aSystem.getColors()[i + 1] = col.y;
			aSystem.getColors()[i + 2] = col.z;
		}
		
		return aSystem;
	}

}
