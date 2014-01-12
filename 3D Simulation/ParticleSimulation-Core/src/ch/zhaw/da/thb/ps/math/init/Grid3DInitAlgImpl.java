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
public class Grid3DInitAlgImpl implements ParticleInitializeAlgorithm {

	private Color3f color3f;
	private Color color;

	private int bounds;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param abounds
	 *            The bounds of the grid
	 */
	public Grid3DInitAlgImpl(int aBounds) {
		color3f = new Color3f();
		color = new Color(17, 209, 23);
		color3f.set(color);

		bounds = aBounds;
	}

	@Override
	public BaseParticleSystem initializeSystem(BaseParticleSystem aSystem) {
		int particleCount = aSystem.getParticleCount();

		/*int stepWidth = bounds / particleCount;
		int stepCount = particleCount / ()
		int lastPosX = -1*bounds;
		int lastPosY = -1*bounds;
		int lastPosZ = -1*bounds;

		int sum = 0;
		for(int x = 0;x < steps;x++){
			for(int y = 0;y < steps;y++){
				for(int z = 0;z < steps;z++){
					
					sum++;
				}
				sum++;
			}
			sum++;
		}
		
		System.out.println(sum);
		
		for (int i = 0; i < particleCount * 3; i += 3) {
			for(int x = -1*bounds;x <= bounds;x++){
				for(int y = -1*bounds;y <= bounds;y++){
					for(int z = -1*bounds;z <= bounds;z++){
						
					}
				}
			}
			aSystem.getCoordinates()[i] = lastPos + step;
			aSystem.getCoordinates()[i + 1] = lastPos + step;
			aSystem.getCoordinates()[i + 2] = lastPos + step;

			aSystem.getColors()[i] = color3f.x;
			aSystem.getColors()[i + 1] = color3f.y;
			aSystem.getColors()[i + 2] = color3f.z;
			
			lastPos = lastPos + step;
		}*/

		return aSystem;
	}

}
