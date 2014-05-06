package fi.otavanopisto.kohonen.impl;

import fi.otavanopisto.kohonen.TrainingModifier;

/**
 * Constant Training Modifier. Modifier stays constant independent of epoch.
 * 
 * @author antti.viljakainen
 */
public class ConstantTrainingModifier implements TrainingModifier {

  private final double modifier;

  public ConstantTrainingModifier(double modifier) {
    this.modifier = modifier;
  }
  
  public double modifier(int epoch) {
    return modifier;
  }
  
}
