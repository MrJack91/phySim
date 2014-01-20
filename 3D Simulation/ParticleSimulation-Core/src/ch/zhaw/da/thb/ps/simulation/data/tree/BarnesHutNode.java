/**
 * 
 */
package ch.zhaw.da.thb.ps.simulation.data.tree;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

/**
 * @author Daniel Brun
 * 
 *         Represents a node in the Barnes-Hut-Tree
 */
public class BarnesHutNode {

	private static final float INDICATION_FACTOR = 0.4f;
	private static final float SOFTENING_FACTOR = Math.ulp(1.0f);
	private static final float GRAVITY = (float) (667384 * Math.pow(10, -11));

	private BaseParticleSystem basePS;
	private List<BarnesHutNode> childNodes;

	private boolean particleSet;
	private int particleIndex;

	private float xStartPos;
	private float xEndPos;

	private float yStartPos;
	private float yEndPos;

	private float zStartPos;
	private float zEndPos;

	private float middleXPos;
	private float middleYPos;
	private float middleZPos;

	private float boxWidth;

	private float summedUpMass;

	/**
	 * @param basePS
	 *            The base particle system.
	 * @param xStartPos
	 *            The x start pos.
	 * @param xEndPos
	 *            The x end pos.
	 * @param yStartPs
	 *            The y start pos.
	 * @param yEndPos
	 *            The y end pos.
	 * @param zStartPos
	 *            The z start pos.
	 * @param zEndPos
	 *            The z end pos.
	 */
	public BarnesHutNode(BaseParticleSystem aBasePs, float xStartPos,
			float xEndPos, float yStartPos, float yEndPos, float zStartPos,
			float zEndPos) {
		super();
		this.basePS = aBasePs;
		this.xStartPos = xStartPos;
		this.xEndPos = xEndPos;
		this.yStartPos = yStartPos;
		this.yEndPos = yEndPos;
		this.zStartPos = zStartPos;
		this.zEndPos = zEndPos;

		childNodes = new ArrayList<BarnesHutNode>(8);
		particleSet = false;
		particleIndex = -1;

		boxWidth = xEndPos - xStartPos;

		middleXPos = xStartPos + (xEndPos - xStartPos) / 2;
		middleYPos = yStartPos + (yEndPos - yStartPos) / 2;
		middleZPos = zStartPos + (zEndPos - zStartPos) / 2;

		summedUpMass = 0;
	}

	/**
	 * @param childNodes
	 *            the childNodes to set
	 */
	public void setChildNodes(List<BarnesHutNode> childNodes) {
		this.childNodes = childNodes;
	}

	/**
	 * Adds the particle to the cube.
	 * 
	 * @param anIndex
	 *            The index of the particle
	 * @param anXPos
	 *            The x pos.
	 * @param anYPos
	 *            The y pos.
	 * @param aZPos
	 *            The z pos.
	 * @return true if the particle was added successfully.
	 */
	public boolean addParticle(int anIndex, float anXPos, float anYPos,
			float aZPos) {
		boolean result = false;

		if (anXPos >= xStartPos && anXPos <= xEndPos && anYPos >= yStartPos
				&& anYPos <= yEndPos && aZPos >= zStartPos && aZPos <= zEndPos) {
			if (particleSet) {
				if (childNodes.isEmpty()) {

					// "Front"
					childNodes.add(new BarnesHutNode(basePS, xStartPos,
							middleXPos, yStartPos, middleYPos, zStartPos,
							middleZPos));
					childNodes.add(new BarnesHutNode(basePS, middleXPos,
							xEndPos, yStartPos, middleYPos, zStartPos,
							middleZPos));

					childNodes.add(new BarnesHutNode(basePS, xStartPos,
							middleXPos, middleYPos, yEndPos, zStartPos,
							middleZPos));
					childNodes
							.add(new BarnesHutNode(basePS, middleXPos, xEndPos,
									middleYPos, yEndPos, zStartPos, middleZPos));

					// "Back"
					childNodes.add(new BarnesHutNode(basePS, xStartPos,
							middleXPos, yStartPos, middleYPos, middleZPos,
							zEndPos));
					childNodes
							.add(new BarnesHutNode(basePS, middleXPos, xEndPos,
									yStartPos, middleYPos, middleZPos, zEndPos));

					childNodes.add(new BarnesHutNode(basePS, xStartPos,
							middleXPos, middleYPos, yEndPos, middleZPos,
							zEndPos));
					childNodes.add(new BarnesHutNode(basePS, middleXPos,
							xEndPos, middleYPos, yEndPos, middleZPos, zEndPos));

					for (BarnesHutNode childNode : childNodes) {
						if (childNode.addParticle(particleIndex,
								basePS.getCoordinates()[particleIndex],
								basePS.getCoordinates()[particleIndex + 1],
								basePS.getCoordinates()[particleIndex + 2])) {
							break;
						}
					}

					particleIndex = -1;
				}

				if (anXPos >= (middleXPos - 20) && anXPos <= (middleXPos + 20) && anYPos >= (middleYPos - 20)&& anYPos <= (middleYPos + 20)
						&& aZPos >= (middleZPos - 20)&& aZPos <= (middleZPos + 20)) {
					summedUpMass += basePS.getMass()[anIndex];
					result = true;
				} else {

					for (BarnesHutNode childNode : childNodes) {
						if (childNode.addParticle(anIndex, anXPos,
										anYPos, aZPos)) {
							result = true;
							summedUpMass += basePS.getMass()[anIndex];
							break;
						}
					}
				}

			} else {
				particleSet = true;
				particleIndex = anIndex;
				result = true;
				summedUpMass += basePS.getMass()[particleIndex];
			}
		}

		return result;
	}

	/**
	 * Calculates to force acting on the given particle
	 * 
	 * @param anIndex
	 *            The index of the particle
	 * @param anXPos
	 *            The x pos.
	 * @param anYPos
	 *            The y pos.
	 * @param aZPos
	 *            The z pos.
	 * @return The result.
	 */
	public void calculate(int anIndex, float anXPos, float anYPos, float aZPos,
			BaseParticleSystem aResultPs) {

		float partDistance = (int) Math.sqrt(Math.pow(anXPos - middleXPos, 2)
				+ Math.pow(anYPos - middleYPos, 2)
				+ Math.pow(aZPos - middleZPos, 2));

		float indicator = boxWidth / partDistance;

		if (indicator <= INDICATION_FACTOR) {
			float partial = ((summedUpMass * partDistance) / (float) Math
					.pow(Math.pow(partDistance, 2)
							+ Math.pow(SOFTENING_FACTOR, 2), (3 / 2)));

			// Calculate Summs
			if ((anXPos - middleXPos) >= 0) {
				aResultPs.getVelocity()[anIndex] = basePS.getVelocity()[anIndex]
						+ GRAVITY * partial * -1 * 10;
			} else {
				aResultPs.getVelocity()[anIndex] = basePS.getVelocity()[anIndex]
						+ GRAVITY * partial * 10;
			}

			if ((anYPos - middleYPos) >= 0) {
				aResultPs.getVelocity()[anIndex + 1] = basePS.getVelocity()[anIndex + 1]
						+ GRAVITY * partial * -1 * 10;
			} else {
				aResultPs.getVelocity()[anIndex + 1] = basePS.getVelocity()[anIndex + 1]
						+ GRAVITY * partial * 10;
			}

			if ((aZPos - middleZPos) >= 0) {
				aResultPs.getVelocity()[anIndex + 2] = basePS.getVelocity()[anIndex + 2]
						+ GRAVITY * partial * -1 * 10;
			} else {
				aResultPs.getVelocity()[anIndex + 2] = basePS.getVelocity()[anIndex + 2]
						+ GRAVITY * partial * 10;
			}
		} else {
			for (BarnesHutNode node : childNodes) {
				node.calculate(anIndex, anXPos, anYPos, aZPos, aResultPs);
			}
		}
	}

	/**
	 * @param basePS
	 *            the basePS to set
	 */
	public void setBasePS(BaseParticleSystem basePS) {
		this.basePS = basePS;
	}
}
