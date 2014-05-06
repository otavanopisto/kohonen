package fi.otavanopisto.kohonen;

import java.util.Map;

/**
 * Topology functionality of Kohonen SOM.
 * 
 * @author antti.viljakainen
 */
public interface Topology {

  /**
   * Returns neighborhood for Neuron.
   * 
   * @param network Network
   * @param neuronIndex Neuron index to get neighborhood for 
   * @return Neuron index, neighbor distance set for neuron
   */
  Map<Integer, Integer> getNeighborhood(Network network, int neuronIndex);

  /**
   * Returns neuron count for this topology
   * 
   * @return neuron count for the topology
   */
  int getNeuronCount();
}
