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
 *         Initializes the particle system with random coordinates.
 */
public class RandomParticleIniAlgImpl implements ParticleInitializeAlgorithm {

	private Random rand;

	private Color3f color3f;
	private Color color;

	/**
	 * Creates a new instance of this class.
	 */
	public RandomParticleIniAlgImpl() {
		rand = new Random();

		color3f = new Color3f();
		color = new Color(17, 209, 23);
		color3f.set(color);
	}

	@Override
	public BaseParticleSystem initializeSystem(BaseParticleSystem aSystem) {
		int particleCount = aSystem.getParticleCount();

		for (int i = 0; i < particleCount * 3; i += 3) {
			aSystem.getCoordinates()[i] = rand.nextInt(particleCount * 2)
					- particleCount;
			aSystem.getCoordinates()[i + 1] = rand.nextInt(particleCount * 2)
					- particleCount;
			aSystem.getCoordinates()[i + 2] = rand.nextInt(particleCount * 2)
					- particleCount;

			aSystem.getColors()[i] = color3f.x;
			aSystem.getColors()[i + 1] = color3f.y;
			aSystem.getColors()[i + 2] = color3f.z;
			
			int m = rand.nextInt(100);
			aSystem.getMass()[i] = m;
			aSystem.getMass()[i+1] = m;
			aSystem.getMass()[i+2] = m;
			
			aSystem.getVelocity()[i] = 0;
			aSystem.getVelocity()[i+1] = 0;
			aSystem.getVelocity()[i+2] = 0;
		}

		return aSystem;
	}

}
