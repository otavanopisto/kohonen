package fi.otavanopisto.kohonen.impl;

import fi.otavanopisto.kohonen.NeighborhoodModifier;
import fi.otavanopisto.kohonen.TrainingModifier;

/**
 * Gauss Neighborhood Modifier uses gauss function to value neighbor by
 * distance. Training modifier may be used to alter the width of the function
 * while training progresses.
 * 
 * @author antti.viljakainen
 */
public class GaussNeighborhoodModifier implements NeighborhoodModifier {

  private final double width;
  private final TrainingModifier modifier;

  public GaussNeighborhoodModifier(double width, TrainingModifier modifier) {
    this.width = width;
    this.modifier = modifier;
  }
  
  public GaussNeighborhoodModifier(double width) {
    this(width, null);
  }
  
  public double modifier(int distance, int epoch) {
    if (modifier == null)
      return java.lang.Math.exp(-(java.lang.Math.pow(distance, 2)) / (2 * width * width));
    else {
      double w = modifier.modifier(epoch) * width;
      return java.lang.Math.exp(-(java.lang.Math.pow(distance, 2)) / (2 * w * w));
    }
  }

}
