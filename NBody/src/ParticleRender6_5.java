import javax.swing.JFrame;

/////// main class
// precalculated, only one 'graviton', no density, limited area of influence.

public class ParticleRender6_5 extends JFrame{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH= 1500; // + 8 + 3 = cinematic?
    public static final int HEIGHT = 1520; // + 28 + 25 = cinematic?
    public static final int SCALE = 1;
    public static final int PARTICLECOUNT = 30;
    public static final int TICK = 50;
    
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("ParticleMaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setUndecorated(true);
        frame.setResizable(true);
        frame.setFocusable(true);
        
        final RenderClass6_5 ren = new RenderClass6_5(WIDTH, HEIGHT);
        ren.spawnParticles(PARTICLECOUNT);
        frame.add(ren);
        
        frame.setVisible(true); // NEEDS TO BE AFTER ADDING ALL OBJECTS TO FRAME
        
        final boolean stop = false; // final and yet needs to not be final to be useful
        
        Thread runThread = new Thread(new Runnable(){
            public void run(){
              if(stop != true){
                  for(int i = 0; i < 0x7fffffff; i++){ //not infinite, stops after 1000000 repaint()'s
                      ren.repaint( );
                      try{Thread.sleep(TICK);}catch(Exception e){System.out.println("Exception e at Thread.sleep");}
                  }
              }
          }
        });
        
        runThread.start();
        
    }
    
}

