package fi.otavanopisto.kohonen;

import java.util.List;

import fi.otavanopisto.kohonen.impl.EuclideanDistanceFunction;

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
  public static double getMapDistance(Network network, List<double[]> data) {
    EuclideanDistanceFunction euc = new EuclideanDistanceFunction();
    
    double dist = 0;
    
    for (int i = 0; i < data.size(); i++) {
      int bmu = network.findBMU(data.get(i));
      double[] bmuWeight = network.getNeuronWeight(bmu);
      
      dist += euc.getDistance(bmuWeight, data.get(i));
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

}
