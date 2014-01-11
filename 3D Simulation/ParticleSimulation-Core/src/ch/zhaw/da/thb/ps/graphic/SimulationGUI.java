/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import ch.zhaw.da.thb.ps.handler.SimulationHandler;
import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.*;

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
	private SimulationHandler simuHandler;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param aParticleSystem
	 *            The particle system to simulate.
	 * @param aHandler
	 *            The simulation handler.
	 * @param aTimerDelay
	 *            The timer delay.
	 */
	public SimulationGUI(BaseParticleSystem aParticleSystem,
			SimulationHandler aHandler, int aTimerDelay) {

		particleSystem = aParticleSystem;
		timerDelay = aTimerDelay;
		simuHandler = aHandler;

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

		OrbitBehavior ob = new OrbitBehavior(canvas);
		ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
		u.getViewingPlatform().setViewPlatformBehavior(ob);

		// Set default View
		u.getViewingPlatform().setNominalViewingTransform();
		u.getViewer().getView().setFrontClipDistance(0.1);
		u.getViewer().getView().setBackClipDistance(particleSystem.getParticleCount()*4);//TODO: Dep. from init-size, 25000.0
		
		//Move view;
		TransformGroup viewTGroup = u.getViewingPlatform().getMultiTransformGroup().getTransformGroup(0);
		Transform3D viewMove = new Transform3D();
		
		viewTGroup.getTransform(viewMove);
		viewMove.setTranslation(new Vector3f(0.0f,0.0f,particleSystem.getParticleCount()*4)); //TODO: Dep. from init-size, 25000.0
		viewTGroup.setTransform(viewMove);
		
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
		psUpdater = new ParticleSystemUpdater(particleSystem, simuHandler);
		psControler = new ParticleSystemControler(timerDelay, psUpdater,
				psGraphic);

		// TODO: According to init size
		BoundingSphere sphere = new BoundingSphere(new Point3d(0, 0, 0), particleSystem.getParticleCount()*4); //TODO: Dep. from init-size, 25.000
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
