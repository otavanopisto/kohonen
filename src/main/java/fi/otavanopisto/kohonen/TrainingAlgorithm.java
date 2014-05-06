package fi.otavanopisto.kohonen;

import java.util.List;

/**
 * Training algorithm for Kohonen SOM.
 * 
 * @author antti.viljakainen
 */
public interface TrainingAlgorithm {

  void train(Network network, List<double[]> data);
}
