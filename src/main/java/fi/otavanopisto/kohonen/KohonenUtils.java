package fi.otavanopisto.kohonen;

import java.util.List;

/**
 * Utility class for Kohonen SOM.
 * 
 * @author antti.viljakainen
 */
public class KohonenUtils {

  /**
   * Calculates the sum of distances of network neurons to given data.
   * 
   * @param network Network
   * @param data Data
   * @return Sum of neuron distances to data vectors.
   */
  public static double mapDistance(Network network, List<double[]> data) {
    double dist = 0;
    
    for (int i = 0; i < data.size(); i++) {
      int bmu = network.findBMU(data.get(i));
      double[] bmuWeight = network.getNeuronWeight(bmu);
      
      dist += euclideanDistance(bmuWeight, data.get(i));
    }
    
    return dist;
  }

  /**
   * Calculates average value from vector of values
   * 
   * @param vals values
   * @return average of the values, Double.NaN if no values were specified
   */
  public static double avg(double[] vals) {
    double sum = 0;
    
    if (vals.length > 0) {
      for (int i = 0; i < vals.length; i++)
        sum += vals[i];
      
      return sum / vals.length;
    }
    else
      return Double.NaN;
  }
  
  /**
   * Calculates average value from list of values
   * 
   * @param vals values
   * @return average of the values, Double.NaN if no values were specified
   */
  public static double avg(List<Double> vals) {
    double sum = 0;
    
    if (vals.size() > 0) {
      for (Double d : vals)
        sum += d;
      
      return sum / vals.size();
    }
    else
      return Double.NaN;
  }

  /**
   * Calculates average value from vector of values
   * 
   * @param vals values
   * @return average of the values, Double.NaN if no values were specified
   */
  public static double avg(int[] vals) {
    double sum = 0;
    
    if (vals.length > 0) {
      for (int i = 0; i < vals.length; i++)
        sum += vals[i];
      
      return sum / vals.length;
    }
    else
      return Double.NaN;
  }

  /**
   * Returns Euclidean distance between the two vectors
   * 
   * @param vector1
   * @param vector2
   * @return
   */
  public static double euclideanDistance(double[] vector1, double[] vector2) {
    double sum = 0;

    for (int i = 0; i < vector1.length; i++) {
      sum += (vector1[i] - vector2[i]) * (vector1[i] - vector2[i]);
    }
    
    return Math.sqrt(sum);
  }

  /**
   * Returns Euclidean distance between the two vectors ignoring null elements of vector1.
   * 
   * @param vector1
   * @param vector2 
   * @return
   */
  public static double euclideanDistance(Double[] vector1, double[] vector2) {
    double sum = 0;

    for (int i = 0; i < vector1.length; i++) {
      if (vector1[i] != null)
        sum += (vector1[i] - vector2[i]) * (vector1[i] - vector2[i]);
    }
    
    return Math.sqrt(sum);
  }
  
}
