package fi.otavanopisto.kohonen.impl;

import java.util.HashMap;
import java.util.Map;

import fi.otavanopisto.kohonen.Network;
import fi.otavanopisto.kohonen.Topology;

/**
 * Matrix topology for Kohonen SOM. Neurons are ordered into a grid of rows x
 * columns. Distance of neighbor neurons is 1 for each neuron immediately to the
 * left, right, up or down from given neuron.
 * 
 * @author antti.viljakainen
 */
public class MatrixTopology implements Topology {

  private final int rows;
  private final int columns;

  public MatrixTopology(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
  }
  
  public Map<Integer, Integer> getNeighborhood(Network network, int neuronIndex) {
    int row = neuronIndex / columns;
    int col = neuronIndex - (row * columns);
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    
    for (int neuron = 0; neuron < network.getNeuronCount(); neuron++) {
      if (neuron == neuronIndex)
        continue;
      
      int nrow = neuron / columns;
      int ncol = neuron - (nrow * columns);
      
      int rdistance = Math.abs(nrow - row);
      int cdistance = Math.abs(ncol - col);
      
      int distance = rdistance + cdistance;
      
      map.put(neuron, distance);
    }
    
    return map;
  }

  public int getNeuronCount() {
    return rows * columns;
  }

}
