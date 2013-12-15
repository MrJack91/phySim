/**
 * 
 */
package ch.zhaw.da.zhb.ps;

import java.io.Serializable;

/**
 * @author Daniel Brun
 *
 * Class which represents a simple particle in a three dimensional area.
 */
public class BaseParticle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4314011216296228401L;

	private int posX;
	private int posY;
	private int posZ;
	
	private double mass;
	private double velocity;
	

}
