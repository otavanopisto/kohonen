package fi.otavanopisto.kohonen;

/**
 * Interface for implementations of Neighborhood functions.
 * 
 * @author antti.viljakainen
 */
public interface NeighborhoodModifier {

  /**
   * Returns neighborhood modifier for a given distance given by topology.
   * 
   * @param distance topology distance of two neurons
   * @param epoch epoch
   * @return training modifier for neighborhood distance
   */
  double modifier(int distance, int epoch);
}
