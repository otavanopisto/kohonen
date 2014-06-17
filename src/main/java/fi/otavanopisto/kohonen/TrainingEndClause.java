package fi.otavanopisto.kohonen;

import java.util.Collection;

/**
 * Training End Clause for Kohonen Training Algorithms. Implementations may be
 * used to define special end conditions for network training.
 * 
 * @author antti.viljakainen
 */
public interface TrainingEndClause {
  
  /**
   * Returns true, if training should be carried on and false if training end conditions are met. 
   * 
   * @param network Network under training
   * @param epoch epoch
   * @param data training data
   * @return true, if training should continue, false otherwise
   */
  boolean continueTraining(Network network, int epoch, Collection<double[]> data);
}
