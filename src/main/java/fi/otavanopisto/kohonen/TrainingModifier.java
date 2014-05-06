package fi.otavanopisto.kohonen;

/**
 * Training modifier. 
 * 
 * @author antti.viljakainen
 */
public interface TrainingModifier {

  /**
   * Returns modifier for epoch
   * 
   * @param epoch epoch
   * @return
   */
  double modifier(int epoch);
}
