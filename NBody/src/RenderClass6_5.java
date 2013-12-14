

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderClass6_5 extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Random r = new Random();
    float oldX, oldY;
    
    ArrayList<Particle> particleAL = new ArrayList<Particle>();
    ArrayList<Graviton> gravitonAL = new ArrayList<Graviton>();
    
    private final int WIDTH;
    private final int HEIGHT;
    
    private long lastTime;
    
    private boolean pause = false;
    private boolean emit = false;
    
    private BufferedImage particleImage;
    private int[] particleRaster;
    
    private int[][] densityArray;
    private int[][] lightArray;
    
    private int lightFade = 200;
    private int lightCap = 2048;
    private float lightChange = .5f;
    
    
    public RenderClass6_5(int W, int H){
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true); 
        requestFocusInWindow();
        
        this.WIDTH = W/2; //I use a raster that is half the size of the image drawn to screen
        this.HEIGHT = H/2; // ^
        
        this.setBackground(Color.BLACK);
        
        particleImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB); // this image stores the particles as they are drawn on screen
        particleRaster = ((DataBufferInt)particleImage.getRaster().getDataBuffer()).getData(); // this array stores the raster of the above image
                
        densityArray = new int[WIDTH/32+1][HEIGHT/32+1]; // this array holds the density at each 'block'
        lightArray = new int[WIDTH/32+1][HEIGHT/32+1]; // this array holds the light brigthness at each 'block'
    }
    
    public void update(Graphics g){
        paint(g);
    }
    
    public void spawnParticles(int ParticleCount){
        
        gravitonAL.add(new Graviton());
        
        int width = WIDTH;
        int height = HEIGHT;
        
        for(int i = 0; i < ParticleCount; i++){
            
            Particle p = new Particle();
            
            float xPos = width/4 + r.nextFloat()*width/2;
            float yPos = height/4 + r.nextFloat()*height/2;
            
            p.setParticle(xPos-150, yPos+100, 0, 0);
            
            particleAL.add(p);
            
        }
    }
    
    private final static float InvSqrt(float x){ // returns inverted square root of input
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x); // store floating-point bits in integer
        i = 0x5f3759d5 - (i >> 1); // initial guess for Newton's method
        //i = (i >>1) & 0x1f1ff1f1; // try to do i & 00000000110000000 type thing
        x = Float.intBitsToFloat(i); // convert new bits into float
        x = x*(1.5f - xhalf*x*x); // One round of Newton's method
        return x;
    }
    
    public void glow( int light, int x, int y){ // recursive method that calculates the light at every block in lightArray
        
        if(light > lightCap){
            light = lightCap;
        }
        
        if(lightArray[x][y] <= light){
            lightArray[x][y] = light;
        }
        
        if( light <= lightFade || x <= 0 || x >= (int)(WIDTH/32) || y <= 0 || y >= (int)(HEIGHT/32)/*if light <= 1 || location invalid*/){
            //base case
            //do nothing
            
        } else {
            
            if( light > lightFade + lightArray[x + 1][y    ] )
                glow( (int)(light*lightChange), x + 1, y    );
                
            if( light > lightFade + lightArray[x - 1][y    ] )
                glow( (int)(light*lightChange), x - 1, y    );
            
            if( light > lightFade + lightArray[x    ][y + 1] )
                glow( (int)(light*lightChange), x   , y  + 1);
            
            if( light > lightFade + lightArray[x    ][y - 1] )
                glow( (int)(light*lightChange), x   , y - 1);
            
        }
        
    }
    
    public int additiveColor(int c1, int c2){ // my additive color blender
        
        int red = (c1 & 0x00ff0000) + (c2 & 0x00ff0000);
        int grn = (c1 & 0x0000ff00) + (c2 & 0x0000ff00);
        int blu = (c1 & 0x000000ff) + (c2 & 0x000000ff);
        
        if( red > 0x00ff0000 )
            red = 0x00ff0000;
        
        if( grn > 0x0000ff00 )
            grn = 0x0000ff00;
        
        if( blu > 0x000000ff )
            blu = 0x000000ff;
        
        return 0xff000000 + red + grn + blu;
    }
    
    public void emitParticles(int numberSquare){ // emits particles
        
        for(int x = 0; x <= numberSquare; x++){
            for(int y = 0; y <= numberSquare; y++){
                    
                Particle p = new Particle();
                
                float xPos = (oldX + x - numberSquare/2);
                float yPos = (oldY + y - numberSquare/2);
                
                float xVel = (r.nextFloat() - .5f);
                float yVel = (r.nextFloat() - .5f);
                
                p.setParticle(xPos, yPos, xVel, yVel);
                particleAL.add(p);
                    
            }
        }
    }
    
    public void paint(Graphics g){
        super.paintComponent(g);
        
        float xPos, yPos, xVel, yVel; // I make everything a local variable because accessing local variables is faster than accessing fields
        float ClickToX, ClickToY, InvClickToP; // ^
        int width = WIDTH; // ^
        int height = HEIGHT; // ^
        
        if(emit){
            emitParticles(16);
        }
         
        for(int x_I = 0, lightWidth = (int)(WIDTH/32)+1; x_I < lightWidth; x_I++){  //Draw previous frame's lighting, then clear lightArray
            for(int y_I = 0, lightHeight = (int)(HEIGHT/32)+1; y_I < lightHeight; y_I++){
                
                
                int red = (int)(.15*lightArray[x_I][y_I]);
                //int red = (int)(.15*densityArray[x_I][y_I]);
                if(red > 200){
                    red = 200;
                }
                if(red < 7){
                    red = 0;
                }
                
                g.setColor(new Color(red, red/2, red/4));
                g.fillRect(x_I*64, y_I*64, 64, 64);
                
                densityArray[x_I][y_I] = (int)(.6*densityArray[x_I][y_I]); // 'Clear' previous densityArray
                //densityArray[x_I][y_I] = 0; // alternate clearing
            }
        }
        
        if(!pause){
            
        for(int x_I = 0, lightWidth = (int)(WIDTH/32)+1; x_I < lightWidth; x_I++){  //clear lightArray
            for(int y_I = 0, lightHeight = (int)(HEIGHT/32)+1; y_I < lightHeight; y_I++){
                
                lightArray[x_I][y_I] = 0;
                
            }
        }
        
        for(int I = 0; I < particleRaster.length; I++){ // clear image of particles
            particleRaster[I] = 0;
        }
        
        for(int particle_I = 0, pAL = particleAL.size(); particle_I < pAL; particle_I++){ // for each particle
            Particle p = particleAL.get(particle_I);
            
            xPos = p.xPos;
            yPos = p.yPos;
            xVel = p.xVel;
            yVel = p.yVel;
            
            for(int gi = 0, gAL = gravitonAL.size(); gi < gAL; gi++ ){ // for each 'graviton'
                
                Graviton v = gravitonAL.get(gi);
                
                ClickToX = v.xPos - xPos;
                ClickToY = v.yPos - yPos;
                float xPull = v.xPull;
                float yPull = v.yPull;
                
                InvClickToP = InvSqrt((ClickToX*ClickToX + ClickToY*ClickToY));
                
                xVel += xPull * ClickToX * InvClickToP; // update x/y Vel
                yVel += yPull * ClickToY * InvClickToP; // ^
                
            }
            
            xPos += xVel; // update x/y Pos
            yPos += yVel; // ^
            
            p.setParticle(xPos, yPos, xVel, yVel); // set particle in new locaiton with new velocities
            
            if(xPos <= width-4 && xPos >= 4 && yPos <= height-4 && yPos >= 4){ // if particle on screen
                
                for(int xi = -2; xi < 2; xi++){ // particles aren't just one pixel, so have to iterate
                    for(int yi = -2; yi < 2; yi++){ 
                        
                        particleRaster[(int)(xPos+xi + width*(int)(yPos+yi))] = additiveColor(particleRaster[(int)(xPos+xi + width*(int)(yPos+yi))], 0xff9f1604); // draw particle
                        
                    }
                }
                
            densityArray[ (int)((xPos+2)/32)] [(int)((yPos+2)/32)] += 2;
            }//*/
            
        }
        }
        
        for(int x_I = 0, lightWidth = (int)(WIDTH/32)+1; x_I < lightWidth; x_I++){
            for(int y_I = 0, lightHeight = (int)(HEIGHT/32)+1; y_I < lightHeight; y_I++){
                
                glow(densityArray[x_I][y_I], x_I, y_I);
                
            }
        }
        
        g.drawImage(particleImage, 0,0, 2*WIDTH, 2*HEIGHT, null); // draw image with a scale of two
        
        g.setColor(Color.WHITE);
        g.drawString("Framerate:" + (1000/(System.currentTimeMillis() - lastTime)), 5, 15);
        g.drawString("Particles : " + particleAL.size(), 5, 28);
        g.drawString("Gravity Well : " + gravitonAL.size(), 5, 41);
                
        for(int gi = 0, gAL = gravitonAL.size(); gi < gAL; gi++ ){
               
            Graviton v = gravitonAL.get(gi);
            
            g.fillRect((int)v.xPos*2, 0, 1, 12);
            g.fillRect(0, (int)v.yPos*2, 12, 1);
            g.fillRect((int)v.xPos*2, HEIGHT*2, 1, 12); // 39 = 12 + 27
            g.fillRect(WIDTH*2, (int)v.yPos*2, 12, 1);  // 18 = 12 + 6
           
        }
        lastTime = System.currentTimeMillis();
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if(SwingUtilities.isLeftMouseButton(me)){
            
            float mouseX = me.getX()/2;
            float mouseY = me.getY()/2;
            
            Graviton v = new Graviton();
        
            v.setGraviton(mouseX, mouseY, .5f, .5f);
        
            gravitonAL.add(v);
        }
        
    }

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {
        if(SwingUtilities.isRightMouseButton(me)){
            
            emit = false ;
            
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        requestFocusInWindow();
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        
        float mouseX = me.getX()/2;
        float mouseY = me.getY()/2;
        
        if(SwingUtilities.isRightMouseButton(me)){
            
            emit = true;
            
        }
        
        if(SwingUtilities.isLeftMouseButton(me)){
            
            Graviton v = new Graviton();
        
            v.setGraviton(mouseX, mouseY, .5f, .5f);
        
            gravitonAL.remove(0);
        
            gravitonAL.add(0, v);
            
        }
        
        oldX = mouseX;
        oldY = mouseY;
        
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
        int KeyChar = ke.getKeyChar();
        
        System.out.println(KeyChar);
        
        if(KeyChar == 27 /*ESC*/){
            pause = !pause;
        }
        
        if(KeyChar == 112 /*KeyEvent.VK_P*/){
            gravitonAL.clear();
            gravitonAL.add(new Graviton());
        }
        
        if(KeyChar == 99 /*KeyEvent.VK_C*/){
            particleAL.clear();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {}
}
