/**
 * 
 */
package ch.zhaw.da.thb.ps.graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author Daniel Brun
 * 
 */
public class SimulationGUI extends JFrame {

	/**
	 * Creates a new instance of this class.
	 */
	public SimulationGUI() {

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
	/*
	 * public BranchGroup createSceneGraph() { // Root graph BranchGroup objRoot
	 * = new BranchGroup();
	 * 
	 * // Create a Transformgroup to scale all objects so they // appear in the
	 * scene. TransformGroup objScale = new TransformGroup(); Transform3D t3d =
	 * new Transform3D(); t3d.setScale(0.5); objScale.setTransform(t3d);
	 * objRoot.addChild(objScale);
	 * 
	 * // Create the transform group node and initialize it to the // identity.
	 * Enable the TRANSFORM_WRITE capability so that // our behavior code can
	 * modify it at runtime. Add it to the // root of the subgraph.
	 * TransformGroup objTrans = new TransformGroup();
	 * objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	 * objScale.addChild(objTrans);
	 * 
	 * // Create a simple shape leaf node, add it to the scene graph.
	 * objTrans.addChild(new ColorCube()); //objTrans.addChild(new
	 * Sphere(0.25f));
	 * 
	 * // Create a new Behavior object that will perform the desired //
	 * operation on the specified transform object and add it into // the scene
	 * graph. Transform3D yAxis = new Transform3D(); Alpha rotationAlpha = new
	 * Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 4000, 0, 0, 0, 0, 0);
	 * 
	 * RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
	 * objTrans, yAxis, 0.0f, (float) Math.PI * 2.0f); BoundingSphere bounds =
	 * new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
	 * rotator.setSchedulingBounds(bounds); objTrans.addChild(rotator);
	 * 
	 * // Lightning the object // Create a red light that shines for 100m from
	 * the origin
	 * 
	 * Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
	 * 
	 * BoundingSphere bounds2 = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
	 * 100.0);
	 * 
	 * Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
	 * 
	 * DirectionalLight light1
	 * 
	 * = new DirectionalLight(light1Color, light1Direction);
	 * 
	 * light1.setInfluencingBounds(bounds2);
	 * 
	 * objRoot.addChild(light1);
	 * 
	 * // Set Light Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
	 * 
	 * AmbientLight ambientLightNode = new AmbientLight(ambientColor);
	 * 
	 * ambientLightNode.setInfluencingBounds(bounds);
	 * 
	 * objRoot.addChild(ambientLightNode); // Have Java 3D perform optimizations
	 * on this scene graph. objRoot.compile();
	 * 
	 * return objRoot; }
	 */

	 public BranchGroup createSceneGraph() {
		 	int count = 0;
	        BranchGroup lineGroup = new BranchGroup();
	        Appearance app = new Appearance();
	        ColoringAttributes ca = new ColoringAttributes(new Color3f(204.0f, 204.0f,204.0f), ColoringAttributes.SHADE_FLAT);
	        app.setColoringAttributes(ca);

	        Point3f[] plaPts = new Point3f[10*10*10];
	        Color3f[] colPts=new Color3f[10*10*10]; //parallel to coordinates, colors.
	        
	        for(int x = 0; x < 10;x++){
	        	for(int y = 0;y < 10;y++){
	        		for(int z = 0;z < 10; z++){
	        			 plaPts[count] = new Point3f(x/10.0f,y/10.0f,z/10.0f);
	        			 colPts[count]=new Color3f(x/3.0f,y/3.0f,(float) ((x+y)/3.0));//my arbitrary color set :)
	 	                //Look up line, i and j are divided by 10.0f to be able to
	 	                //see the points inside the view screen
	 	                count++;
	        		}
	        	}
	        }
	        PointArray pla = new PointArray(10*10*10, GeometryArray.COORDINATES|GeometryArray.COLOR_3);
	        pla.setCoordinates(0, plaPts);
	        pla.setColors(0,colPts);
	        
	        PointAttributes a_point_just_bigger=new PointAttributes();
	        a_point_just_bigger.setPointSize(2.0f);//10 pixel-wide point
	        a_point_just_bigger.setPointAntialiasingEnable(true);//now points are sphere-like(not a cube)
	        app.setPointAttributes(a_point_just_bigger);
	        
	        Shape3D plShape = new Shape3D(pla, app);
	        TransformGroup objRotate = new TransformGroup();
	        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        objRotate.addChild(plShape);
	        
	        MouseRotate f1=new MouseRotate();
	        f1.setSchedulingBounds(new BoundingSphere());
	        f1.setTransformGroup(objRotate);
	        lineGroup.addChild(f1);
	        
	        lineGroup.addChild(objRotate);
	        return lineGroup;
	    }

	public static void main(String[] args) {
		new SimulationGUI();
	}
}
