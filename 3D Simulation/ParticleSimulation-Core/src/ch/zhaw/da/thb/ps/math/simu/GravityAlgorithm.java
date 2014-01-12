/**
 *
 */
package ch.zhaw.da.thb.ps.math.simu;

import javax.vecmath.Color3f;

/**
 * @author Daniel Brun
 *
 *         Concrete Implementation of a simulation algorithm.
 */
public class GravityAlgorithm extends BaseAlgorithm {

    public int stopAt;

    /**
     * the mass of the base before calc
     */
    private int baseCountInit = 10;

    /**
     * the mass of the base while the count -> after
     */
    private int baseCountAdditional = 0;

    private double gravityConst;
    private double frictionConst;

    private Color3f color3f;

    /**
     * construct
     */
    public GravityAlgorithm() {
        this.setConfiguration("");
    }

    @Override
    public SimulationAlgorithm copy() {
        GravityAlgorithm simuAlg = new GravityAlgorithm();

        simuAlg.lastPs = lastPs;
        simuAlg.resultPs = resultPs;
        simuAlg.lowerBound = lowerBound;
        simuAlg.upperBound = upperBound;
        simuAlg.stopAt = stopAt;
        simuAlg.baseCountInit = baseCountInit;
        simuAlg.gravityConst = gravityConst;
        simuAlg.frictionConst = frictionConst;
        simuAlg.color3f= color3f;

        return (BaseAlgorithm) simuAlg;
    }

    @Override
    public SimulationAlgorithm call() throws Exception {
        if (!running) {
            running = true;

            try {
                resultPs = lastPs.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            Color3f color3f = new Color3f();

            /*
            System.out.println("lowerBound\t" + lowerBound);
            System.out.println("upperBound\t" + upperBound);
            */

            // calc every cordinate
            for (int i = lowerBound; i <= upperBound; i = i + 3) {
                /*
                if (i >= upperBound -6 || i <= 6) {
                    System.out.println(i);
                }
                */

                calcNextCord(i);

                //resultPs.getColors()[i] = 0.8f;

                // this.trackParticle(i, 26664);
            }

            // save
            // FIXME: Number 4 should be equal to threads

            this.baseCountInit += 4 * this.baseCountAdditional;
            this.baseCountAdditional = 0;

            running = false;
        }
        return this;
    }

    /**
     * calcs the gravity relative to base (base is growth)
     * @param i
     * @return
     */
    private void calcNextCord(int i) {
        double curSpeed, radiusQuad, radius;

        float corX = resultPs.getCoordinates()[i];
        float corY = resultPs.getCoordinates()[i+1];
        float corZ = resultPs.getCoordinates()[i+2];

        // calc radius to center
        radiusQuad = Math.pow(corX, 2) + Math.pow(corY, 2) + Math.pow(corZ, 2);
        radius = Math.sqrt(radiusQuad);

        // is point not in base, save next position
        if (radius >= this.stopAt) {
            try {
                // mass of center is baseCountInit, selfmass is 1
                curSpeed = (double)this.frictionConst * this.gravityConst * (this.baseCountInit) / radiusQuad;
            } catch (Exception e) {
                curSpeed = 0;
            }
            if (curSpeed == 0) {
                curSpeed = 10;
            }

            // debug
            if (i == 49998) {
                /*
                System.out.println("frictionConst:\t\t" + this.frictionConst);
                System.out.println("gravityConst:\t\t" + this.gravityConst);
                System.out.println("baseCountInit:\t\t" + this.baseCountInit);
                */
                // System.out.println("i:\t\t\t\t\t" + i);

                /*
                System.out.println("corX:\t\t\t\t" + corX);
                System.out.println("corY:\t\t\t\t" + corY);
                System.out.println("corZ:\t\t\t\t" + corZ);
                System.out.println("corX^2:\t\t\t\t" + Math.pow(corX, 2));
                System.out.println("corY^2:\t\t\t\t" + Math.pow(corY, 2));
                System.out.println("corZ^2:\t\t\t\t" + Math.pow(corZ, 2));
                System.out.println("radius^2:\t\t\t" + (Math.pow(corX, 2) + Math.pow(corY, 2) + Math.pow(corZ, 2)));
                System.out.println("radius:\t\t\t\t" + (radius));
                System.out.println("curSpeed:\t\t\t" + curSpeed);
                System.out.println();
                */



            }

            double nextRadius = radius - curSpeed;

            // check the limit of move (at center)
            if (nextRadius < this.stopAt) {
                nextRadius = this.stopAt;
                this.baseCountAdditional++;
            }

            double factor = this.getFactorFromRadius(nextRadius, resultPs.getCoordinates()[i], resultPs.getCoordinates()[i+1], resultPs.getCoordinates()[i+2]);

            // set next point
            for (int n = i; n <= i+2; n++) {
                float corTemp = resultPs.getCoordinates()[n];


                // calc next
                float corNext = (float) (corTemp * factor);

                /*
                float corNext = corTemp - (sign * (float)curSpeed);

                // fixme: stop at good place -> radius must be <= this.stopAt (1000)
                // if the sign is switching, stop the particle at stopAt
                if ((int)Math.signum(corNext) != sign) {
                    // calc random pos..
                    corNext = sign * this.rand.nextInt(this.stopAt);
                }
                */

                // save for base index count calculation
                switch (n-i) {
                    case 0:
                        corX = corNext;
                        break;
                    case 1:
                        corY = corNext;
                        break;
                    case 2:
                        corZ = corNext;
                        break;
                }

                resultPs.getCoordinates()[n] = corNext;


                /*
                int red = (int) (17 + 4 * curSpeed * 10000) ;
                int green = 209;

                if(red >= 209){
                    red = 209;
                    green = (int) (209 - 3 * curSpeed * 1000);
                }

                if(green <= 17){
                    green = 17;
                }


                Color color = new Color(red, green, 17);
                color3f.set(color);

                resultPs.getColors()[i] = color3f.x;
                resultPs.getColors()[i+1] = color3f.y;
                resultPs.getColors()[i+2] = color3f.z;
                */

            }

            /*
            // calc next radius
            radius = Math.sqrt(Math.pow(corX, 2) + Math.pow(corY, 2) + Math.pow(corZ, 2));
            // if last time count it to the base
            if (radius < this.stopAt) {
                // baseCountInit++;
                this.baseCountAdditional++;
            }
            */
        }
    }

    /**
     * calcs the factor to get next coordiantes
     * @param rad wished radius
     * @param x x-val
     * @param y y-val
     * @param z z-val
     * @return
     */
    public double getFactorFromRadius(double rad, double x, double y, double z) {
        double factor = Math.pow(rad, 2) / (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        return factor;
    }


    /**
     * set special configuration
     * @param mode cube, ...
     */
    public void setConfiguration(String mode) {
        if (mode == "mode1") {

        } else {
            this.stopAt = 1000;
            // this.gravityConst = 6.673 * Math.pow(10, -18); // Erd Gravitationskraft
            // this.gravityConst = 6.673 * Math.pow(10, 6);

            this.gravityConst = 6.673 * Math.pow(10, 8);
            this.frictionConst = 0.18;
        }
    }

}
