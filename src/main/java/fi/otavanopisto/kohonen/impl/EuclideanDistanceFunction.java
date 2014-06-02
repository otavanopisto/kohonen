package fi.otavanopisto.kohonen.impl;

import fi.otavanopisto.kohonen.DistanceFunction;
import fi.otavanopisto.kohonen.KohonenUtils;

/**
 * Euclidean distance function. Calculates Euclidean distance between two vectors.
 * 
 * @author antti.viljakainen
 */
public class EuclideanDistanceFunction implements DistanceFunction {

  public double getDistance(double[] vector1, double[] vector2) {
    return KohonenUtils.euclideanDistance(vector1, vector2);
  }

  public double getDistance(Double[] vector1, double[] vector2) {
    return KohonenUtils.euclideanDistance(vector1, vector2);
  }

}
