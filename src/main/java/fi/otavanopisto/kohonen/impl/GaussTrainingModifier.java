package fi.otavanopisto.kohonen.impl;

import fi.otavanopisto.kohonen.TrainingModifier;

/**
 * Gauss Training Modifier. Modifier follows gauss function with given width.
 * Maximum modifier may be given to restrict maximum values output.
 * 
 * @author antti.viljakainen
 */
public class GaussTrainingModifier implements TrainingModifier {

  private final double width;
  private final double maxModifier;

  public GaussTrainingModifier(double width) {
    this(width, 1);
  }

  public GaussTrainingModifier(double width, double maxModifier) {
    this.width = width;
    this.maxModifier = maxModifier;
  }

  public double modifier(int epoch) {
    return maxModifier * java.lang.Math.exp(-(java.lang.Math.pow(epoch, 2)) / (2 * width * width));
  }

}
