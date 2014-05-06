package fi.otavanopisto.kohonen.impl;

import fi.otavanopisto.kohonen.TrainingModifier;

/**
 * Linear Training Modifier. Modifier decreases in linear fashion starting from
 * maxModifier down to 0 at maxEpoch.
 * 
 * @author antti.viljakainen
 */
public class LinearTrainingModifier implements TrainingModifier {

  private final int maxEpoch;
  private final double maxModifier;

  public LinearTrainingModifier(double maxModifier, int maxEpoch) {
    this.maxModifier = maxModifier;
    this.maxEpoch = maxEpoch;
  }

  public double modifier(int epoch) {
    return (double) (maxEpoch - epoch) / maxEpoch * maxModifier;
  }

}
