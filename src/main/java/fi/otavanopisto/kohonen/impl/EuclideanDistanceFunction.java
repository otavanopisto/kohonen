package fi.otavanopisto.kohonen.impl;

import fi.otavanopisto.kohonen.DistanceFunction;

/**
 * Euclidean distance function. Calculates Euclidean distance between two vectors.
 * 
 * @author antti.viljakainen
 */
public class EuclideanDistanceFunction implements DistanceFunction {

  public double getDistance(double[] vector1, double[] vector2) {
    double sum = 0;

    for (int i = 0; i < vector1.length; i++) {
      sum += (vector1[i] - vector2[i]) * (vector1[i] - vector2[i]);
    }
    
    return Math.sqrt(sum);
  }

  public double getDistance(Double[] vector1, double[] vector2) {
    double sum = 0;

    for (int i = 0; i < vector1.length; i++) {
      if (vector1[i] != null)
        sum += (vector1[i] - vector2[i]) * (vector1[i] - vector2[i]);
    }
    
    return Math.sqrt(sum);
  }

}
