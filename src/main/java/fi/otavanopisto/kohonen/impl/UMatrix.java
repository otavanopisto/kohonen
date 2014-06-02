package fi.otavanopisto.kohonen.impl;

import java.util.Map;

import fi.otavanopisto.kohonen.KohonenUtils;
import fi.otavanopisto.kohonen.Network;
import fi.otavanopisto.kohonen.Topology;

public class UMatrix {

  public static UMatrix createUMatrix(Network network, Topology topology) {
    UMatrix umatrix = new UMatrix();
    umatrix.neurons = new double[network.getNeuronCount()];
    
    for (int i = 0; i < network.getNeuronCount(); i++) {
      Map<Integer, Integer> neighborhood = topology.getNeighborhood(network, i);
      double[] neuronWeight = network.getNeuronWeight(i);
      double neuronTotalDistance = 0;
      int neighborCount = 0;
      
      for (Integer neighbor : neighborhood.keySet()) {
        Integer neighborDist = neighborhood.get(neighbor);
        
        if (neighborDist == 1) {
          double[] neighborWeight = network.getNeuronWeight(neighbor);
          
          neuronTotalDistance += KohonenUtils.euclideanDistance(neuronWeight, neighborWeight);
          neighborCount++;
        }
      }
      
      if (neighborCount > 0)
        umatrix.neurons[i] = neuronTotalDistance / neighborCount;
      else
        umatrix.neurons[i] = 0;
    }
    
    return umatrix;
  }
  
  public double getMaxDistance() {
    double max = 0;
    
    for (int i = 0; i < getNeuronCount(); i++) {
      double d = getNeuronDistance(i); 
      if (d > max)
        max = d;
    }
    
    return max;
  }
  
  public double getNeuronDistance(int index) {
    return neurons[index];
  }

  public Topology getTopology() {
    return topology;
  }

  public int getNeuronCount() {
    return neurons.length;
  }

  private double[] neurons;
  private Topology topology;
}
