/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import ch.zhaw.da.thb.ps.handler.SimulationHandler;
import ch.zhaw.da.thb.ps.simulation.SimulationConfig;
import ch.zhaw.da.thb.ps.simulation.data.BaseParticleSystem;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author Daniel Brun
 * 
 */
public class SimulationGUI extends JFrame implements MouseListener{

	/**
	 * Generated Serial Version UID.
	 */
	private static final long serialVersionUID = 2535348095491353537L;

	private ParticleSystemGraphic psGraphic;
	private ParticleSystemUpdater psUpdater;
	private ParticleSystemControler psControler;

	private BaseParticleSystem particleSystem;
	private SimulationHandler simuHandler;

	private SimulationConfig config;
	
	private Canvas3D canvas;
	
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
			SimulationHandler aHandler, SimulationConfig aConfig) {

		particleSystem = aParticleSystem;
		simuHandler = aHandler;

		config = aConfig;
		
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
		canvas = new Canvas3D(
				SimpleUniverse.getPreferredConfiguration());
		canvas.addMouseListener(this);
		
		// Create scene
		BranchGroup sceneGraph = createSceneGraph();

		// Create Universe
		SimpleUniverse u = new SimpleUniverse(canvas);

		OrbitBehavior ob = new OrbitBehavior(canvas);
		ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				Double.MAX_VALUE));
		u.getViewingPlatform().setViewPlatformBehavior(ob);

		// Set default View
		u.getViewingPlatform().setNominalViewingTransform();
		u.getViewer().getView().setFrontClipDistance(10);
		u.getViewer().getView()
				.setBackClipDistance(particleSystem.getParticleCount() * 4);
		
		// Move view;
		TransformGroup viewTGroup = u.getViewingPlatform()
				.getMultiTransformGroup().getTransformGroup(0);
		Transform3D viewMove = new Transform3D();

		viewTGroup.getTransform(viewMove);
		viewMove.setTranslation(new Vector3f(0.0f, 0.0f, particleSystem
				.getParticleCount() * 4)); 
		viewTGroup.setTransform(viewMove);

		//Mouse Wheel zoom
		MouseWheelZoom mwz = new MouseWheelZoom(MouseBehavior.INVERT_INPUT);
		mwz.setTransformGroup(u.getViewingPlatform()
				.getViewPlatformTransform());
		mwz.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				Double.MAX_VALUE));
		mwz.setFactor(30.0);
		sceneGraph.addChild(mwz);
		
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
		psControler = new ParticleSystemControler(config.getGuiSleepTime(), psUpdater,
				psGraphic);

		BoundingSphere sphere = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				Double.MAX_VALUE); 
														
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

	@Override
	public void mouseClicked(MouseEvent anEvent) {
		Dimension dim = this.getSize();
		
		int xZeroPos = dim.width / 2;
		int yZeroPos = dim.height / 2;
		
		int xMousePos = (int) canvas.getMousePosition().getX();
		int yMousePos = (int) canvas.getMousePosition().getY();

		int x3dPos = xMousePos - xZeroPos;
		int y3dPos = -1*yMousePos + yZeroPos;
		
		simuHandler.mouseClicked(x3dPos,y3dPos);
	}

	@Override
	public void mouseEntered(MouseEvent anEvent) {
		
	}

	@Override
	public void mouseExited(MouseEvent anEvent) {
		
	}

	@Override
	public void mousePressed(MouseEvent anEvent) {
		
	}

	@Override
	public void mouseReleased(MouseEvent anEvent) {
		
	}
}
