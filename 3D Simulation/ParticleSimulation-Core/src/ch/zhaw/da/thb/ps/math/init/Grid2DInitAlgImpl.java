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
public class Grid2DInitAlgImpl implements ParticleInitializeAlgorithm {

	private Color3f color3f;
	private Color color;

	private int bounds;

	private boolean randomMass;
	private int lowerMassRange;
	private int upperMassRange;

	private Random rand;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aBounds
	 *            The bounds of the system.
	 */
	public Grid2DInitAlgImpl(int aBounds) {
		this(aBounds, 10, 10);

		randomMass = false;
	}

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param bounds
	 *            The generation bounds
	 * @param lowerMassRange
	 *            The lower mass bound
	 * @param upperMassRange
	 *            The upper mass bound.
	 */
	public Grid2DInitAlgImpl(int bounds, int lowerMassRange, int upperMassRange) {
		this.bounds = bounds;
		this.lowerMassRange = lowerMassRange;
		this.upperMassRange = upperMassRange;

		color3f = new Color3f();
		color = new Color(17, 209, 23);
		color3f.set(color);

		randomMass = true;
		rand = new Random();
	}

	@Override
	public BaseParticleSystem initializeSystem(BaseParticleSystem aSystem) {
		int particleCount = aSystem.getParticleCount();

		int particlePerLine = (int) Math.sqrt(particleCount);
		int coordXStep = 2 * bounds / particlePerLine;

		int xPos = 0;
		int yPos = 0;
		int zPos = 0;

		int sum = 0;
		for (int x = -bounds + coordXStep; x < bounds; x += coordXStep) {
			for (int y = -bounds + coordXStep; y < bounds; y += coordXStep) {
				xPos = sum++;
				yPos = sum++;
				zPos = sum++;

				aSystem.getCoordinates()[xPos] = x;
				aSystem.getCoordinates()[yPos] = y;
				aSystem.getCoordinates()[zPos] = 0;

				aSystem.getColors()[xPos] = color3f.x;
				aSystem.getColors()[yPos] = color3f.y;
				aSystem.getColors()[zPos] = color3f.z;

				int m = 10;
				if (randomMass) {
					m = rand.nextInt(lowerMassRange + upperMassRange)
							- lowerMassRange;
				}

				aSystem.getMass()[xPos] = m;
				aSystem.getMass()[yPos] = m;
				aSystem.getMass()[zPos] = m;

				aSystem.getVelocity()[xPos] = 0;
				aSystem.getVelocity()[yPos] = 0;
				aSystem.getVelocity()[zPos] = 0;
			}
		}

		return aSystem;
	}

}
