package fi.otavanopisto.kohonen;

import java.util.Collection;

/**
 * Training algorithm for Kohonen SOM.
 * 
 * @author antti.viljakainen
 */
public interface TrainingAlgorithm {

  /**
   * Trains the given Network with given data.
   * 
   * @param network network
   * @param data data
   */
  void train(Network network, Collection<double[]> data);
}
