package fi.otavanopisto.kohonen.impl;

import java.util.List;

import fi.otavanopisto.kohonen.KohonenUtils;
import fi.otavanopisto.kohonen.Network;
import fi.otavanopisto.kohonen.TrainingEndClause;

/**
 * Minimum distance training end clause. Stops training when the change between
 * two epochs is less than the given minimum distance. Calculates sum of neuron
 * distances to all input data vectors and uses that as comparison distance
 * between epochs.
 * 
 * @author antti.viljakainen
 */
public class MinDistanceTrainingEndClause implements TrainingEndClause {

  private double distance = -1;
  private final double[] distances;
  private final boolean printDistances;
  private int round = 0;
  private boolean initialized = false;
  private final double minDistance;

  public MinDistanceTrainingEndClause(int roundsAveraged, double minDistance) {
    this(roundsAveraged, minDistance, false);
  }

  public MinDistanceTrainingEndClause(int roundsAveraged, double minDistance, boolean printDistances) {
    this.minDistance = minDistance;
    this.distances = new double[roundsAveraged];
    this.printDistances = printDistances;
  }

  public boolean continueTraining(Network network, int epoch,
      List<double[]> data) {
    double previousEpochDistance = distance;
    distances[round] = KohonenUtils.getMapDistance(network, data);
    
    round++;
    
    if (round >= distances.length) {
      round = 0;
      initialized = true;
      
    }

    if (initialized) {
      distance = KohonenUtils.avg(distances);
      
      if (printDistances)
        System.out.println("Epoch " + epoch + " prevDistance " + previousEpochDistance
            + " newDistance " + distance + " diff "
            + (Math.abs(previousEpochDistance - distance)));

      if (previousEpochDistance == -1)
        return true;
      
      if (Math.abs(previousEpochDistance - distance) < minDistance) {
        if (printDistances)
          System.out.println("Minimum distance reached, epoch " + epoch);
        return false;
      } else
        return true;
    }
    else
      return true;
  }

}
