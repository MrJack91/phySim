/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Point3d;

import ch.zhaw.da.zhb.ps.BaseParticleSystem;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author Daniel Brun
 * 
 */
public class SimulationGUI extends JFrame {

	/**
	 * Generated Serial Version UID.
	 */
	private static final long serialVersionUID = 2535348095491353537L;
	
	private ParticleSystemGraphic psGraphic;
	private ParticleSystemUpdater psUpdater;
	private ParticleSystemControler psControler;
	
	private BaseParticleSystem particleSystem;
	private int timerDelay;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aParticleSystem The particle system to simulate.
	 * @param aTimerDelay The timer delay.
	 */
	public SimulationGUI(BaseParticleSystem aParticleSystem, int aTimerDelay) {

		particleSystem = aParticleSystem;
		timerDelay = aTimerDelay;
		
		initComponents();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(500, 500));
		// setUndecorated(true);
		// setResizable(false);
		// setFocusable(true);
		setVisible(true);
	}

	/**
	 * Initializes the basic components.
	 */
	private void initComponents() {
		// Set default layout
		setLayout(new BorderLayout());

		// Create 3D-Canvas
		Canvas3D canvas = new Canvas3D(
				SimpleUniverse.getPreferredConfiguration());

		// Create scene
		BranchGroup sceneGraph = createSceneGraph();

		// Create Universe
		SimpleUniverse u = new SimpleUniverse(canvas);

		// Set default View
		u.getViewingPlatform().setNominalViewingTransform();

		// Add graph to universe
		u.addBranchGraph(sceneGraph);

		add(BorderLayout.CENTER, canvas);
	}

	/**
	 * Creates the scene graph.
	 * 
	 * @return The BranchGroup
	 */
	public BranchGroup createSceneGraph() {
		BranchGroup rootObj = new BranchGroup();
		
		psGraphic = new ParticleSystemGraphic(particleSystem);
		psUpdater = new ParticleSystemUpdater(particleSystem);
		psControler = new ParticleSystemControler(timerDelay, psUpdater, psGraphic);
				
		BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0),100);
		psControler.setSchedulingBounds(sphere);
		
		TransformGroup transGrp = new TransformGroup();
		Transform3D trans = new Transform3D();
		
		transGrp.setTransform(trans);
		transGrp.addChild(psGraphic);
		
		TransformGroup objRotate = new TransformGroup();
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate.addChild(transGrp);
		rootObj.addChild(objRotate);
		
		MouseRotate f1 = new MouseRotate();
		f1.setSchedulingBounds(new BoundingSphere());
		f1.setTransformGroup(objRotate);
		rootObj.addChild(f1);
		
		
		rootObj.addChild(psControler);
		
		return rootObj;
	}
}
