/**
 * 
 */
package ch.zhaw.da.thb.ps.math.simu;

/**
 * @author Daniel Brun
 * 
 *         Concrete Implementation of a simulation algorithm.
 */
public class MeetpointAlgorithm extends BaseAlgorithm {

    public int stopAt;
    private int speed;


    public void GravityAlgorithm() {
        this.setConfiguration("");
    }

    @Override
    public SimulationAlgorithm copy() {
        MeetpointAlgorithm simuAlg = new MeetpointAlgorithm();

        simuAlg.lastPs = lastPs;
        simuAlg.resultPs = resultPs;
        simuAlg.lowerBound = lowerBound;
        simuAlg.upperBound = upperBound;
        simuAlg.stopAt = stopAt;
        simuAlg.speed = speed;

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

			for (int i = lowerBound; i < upperBound; i ++) {
                    // if(Math.abs(resultPs.getCoordinates()[i]) >= this.stopAt){
                    if(Math.abs(resultPs.getCoordinates()[i]) >= this.stopAt){
                        float currentCor = resultPs.getCoordinates()[i];
                        int sign = (int) Math.signum(currentCor);
                        resultPs.getCoordinates()[i] = currentCor - (sign * (float)this.speed);
                    }

                    //resultPs.getColors()[i] = 0.8f;

                    // this.trackParticle(i, 26664);
			}

			running = false;
		}
		return this;
	}

    /**
     * set special configuration
     * @param mode cube, ...
     */
    public void setConfiguration(String mode) {
        if (mode == "cube") {
            this.stopAt = 2000;
            this.speed = 25;
        } else {
            this.stopAt = 0;
            this.speed = 50;
        }
    }

}
