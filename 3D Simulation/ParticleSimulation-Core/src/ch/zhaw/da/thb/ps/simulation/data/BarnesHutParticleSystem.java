/**
 * 
 */
package ch.zhaw.da.thb.ps.simulation.data;

import ch.zhaw.da.thb.ps.simulation.data.tree.BarnesHutNode;

/**
 * @author Daniel Brun
 * 
 *         Specialised particle system for the barnes hut algorithm.
 */
public class BarnesHutParticleSystem extends BaseParticleSystem {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -403524630082087768L;
	
	private BarnesHutNode rootNode;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aParticleCount The particle count.
	 */
	public BarnesHutParticleSystem(int aParticleCount) {
		super(aParticleCount);

	}

	
	/**
	 * Creates the tree.
	 * 
	 */
	public void createTree(){
		rootNode = new BarnesHutNode(this, -particleCount, particleCount,
				-particleCount, particleCount, -particleCount,
				particleCount);
		
		for(int i = 0;i < particleCount*3;i+=3){
			if(!rootNode.addParticle(i,coordinates[i],coordinates[i+1],coordinates[i+2])){
				//System.err.println("Particle could not be placed in tree " + coordinates[i] + " " + coordinates[i+1] + " " + coordinates[i+2]);
			}
		}
	}
	
	/**
	 * @return the rootNode
	 */
	public BarnesHutNode getRootNode() {
		return rootNode;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public BarnesHutParticleSystem clone() throws CloneNotSupportedException {
		BarnesHutParticleSystem clonePs = (BarnesHutParticleSystem) super.clone();

		clonePs.rootNode = rootNode; //FIMXE: Evtl. clone Method.
		
		return clonePs;
	}
}
