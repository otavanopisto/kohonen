package fi.otavanopisto.kohonen;

/**
 * Distance function generalization interface.
 * 
 * @author antti.viljakainen
 */
public interface DistanceFunction {

  /**
   * Returns distance between two vectors
   * 
   * @param vector1
   * @param vector2
   * @return Distance between the two vectors
   */
  double getDistance(double[] vector1, double[] vector2);

  /**
   * Returns distance between the two vectors. Some components of the first
   * vector may be null, in which case they should be ignored in distance
   * calculation.
   * 
   * @param vector1
   * @param vector2
   * @return Distance between the two vectors
   */
  double getDistance(Double[] vector1, double[] vector2);
}
