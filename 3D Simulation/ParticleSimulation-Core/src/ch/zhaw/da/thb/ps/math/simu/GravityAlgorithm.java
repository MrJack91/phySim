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

    @Override
    public SimulationAlgorithm copy() {
        BaseAlgorithm simuAlg = new GravityAlgorithm();

        simuAlg.lastPs = lastPs;
        simuAlg.resultPs = resultPs;
        simuAlg.lowerBound = lowerBound;
        simuAlg.upperBound = upperBound;

        return simuAlg;
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


                if ((i >= 26664 && i <= 26666) && 0 == 1) {
                    this.trackParticle(i, 26664);
                } else {
                    resultPs.getCoordinates()[i] = resultPs.getCoordinates()[i] + 50.0f;

                    this.trackParticle(i, 26664);

                    //resultPs.getColors()[i] = 0.8f;

                    if(resultPs.getCoordinates()[i] > lastPs.getParticleCount()){
                        resultPs.getCoordinates()[i] = 0;
                    }
                }
			}
			running = false;
		}
		return this;
	}

}
