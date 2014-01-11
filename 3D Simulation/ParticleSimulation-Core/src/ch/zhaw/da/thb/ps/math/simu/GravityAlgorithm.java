/**
 *
 */
package ch.zhaw.da.thb.ps.math.simu;

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

            // calc every cordinate
            for (int i = lowerBound; i < upperBound; i = i + 3) {
                calcNextCord(i);

                //resultPs.getColors()[i] = 0.8f;

                // this.trackParticle(i, 26664);
            }

            // System.out.println(this.baseCountAdditional);

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

        float currentCor = resultPs.getCoordinates()[i];

        float corX = resultPs.getCoordinates()[i];
        float corY = resultPs.getCoordinates()[i+1];
        float corZ = resultPs.getCoordinates()[i+2];

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

            // my lovely 8888
            if (i == 3 * 8888) {
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
                System.out.println("r^2:\t\t\t\t" + (Math.pow(corX, 2) + Math.pow(corY, 2) + Math.pow(corZ, 2)));
                System.out.println("curSpeed:\t\t\t" + curSpeed);
                System.out.println();
                */
            }

            // set next point
            for (int n = i; n <= i+2; n++) {
                float corTemp = resultPs.getCoordinates()[n];


                // calc next
                int sign = (int) Math.signum(corTemp);
                float corNext = corTemp - (sign * (float)curSpeed);

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
            }

            // calc next radius
            radius = Math.sqrt(Math.pow(corX, 2) + Math.pow(corY, 2) + Math.pow(corZ, 2));
            // if last time count it to the base
            if (radius < this.stopAt) {
                // baseCountInit++;
                this.baseCountAdditional++;
            }
        }
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
            this.gravityConst = 6.673 * Math.pow(10, 8);
            this.frictionConst = 0.18;
        }
    }

}