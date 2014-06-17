package fi.otavanopisto.kohonen.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import fi.otavanopisto.kohonen.NeighborhoodModifier;
import fi.otavanopisto.kohonen.Network;
import fi.otavanopisto.kohonen.Topology;
import fi.otavanopisto.kohonen.TrainingAlgorithm;
import fi.otavanopisto.kohonen.TrainingEndClause;
import fi.otavanopisto.kohonen.TrainingModifier;

/**
 * Winner Takes Most training algorithm for Kohonen SOM.
 * 
 * In WTM the neighbouring neurons are trained as per topolgy distances.
 * 
 * @author antti.viljakainen
 */
public class WTMTrainingAlgorithm implements TrainingAlgorithm {

  private final TrainingModifier trainingModifier;
  private final int maxEpochs;
  private int epoch = -1;
  private final NeighborhoodModifier neighborhoodModifier;
  private final TrainingEndClause endClause;
  private Topology topology;

  public WTMTrainingAlgorithm(Topology topology, TrainingModifier trainingModifier, NeighborhoodModifier neighborhoodModifier, int maxEpochs) {
    this(topology, trainingModifier, neighborhoodModifier, null, maxEpochs);
  }

  public WTMTrainingAlgorithm(Topology topology, TrainingModifier trainingModifier, NeighborhoodModifier neighborhoodModifier, TrainingEndClause endClause, int maxEpochs) {
    this.topology = topology;
    this.trainingModifier = trainingModifier;
    this.neighborhoodModifier = neighborhoodModifier;
    this.endClause = endClause;
    this.maxEpochs = maxEpochs;
  }
  
  public void train(Network network, Collection<double[]> data) {
    if (endClause != null)
      trainWithEndClause(network, data);
    else
      trainNormal(network, data);
  }

  public int getEpoch() {
    return epoch;
  }
  
  private void trainNormal(Network network, Collection<double[]> data) {
    for (epoch = 0; epoch < maxEpochs; epoch++) {
      for (double[] in : data) {
        int closestNeuron = network.findBMU(in);
        
        trainNeuron(epoch, network, closestNeuron, in);
      }
    }
  }

  private void trainWithEndClause(Network network, Collection<double[]> data) {
    for (epoch = 0; epoch < maxEpochs; epoch++) {
      for (double[] in : data) {
        int closestNeuron = network.findBMU(in);
        
        trainNeuron(epoch, network, closestNeuron, in);
      }
      
      if (!endClause.continueTraining(network, epoch, data))
        break;
    }
  }
  
  private void trainNeuron(int epoch, Network network, int neuronIndex, double[] in) {
    double[] neuron = network.getNeuronWeight(neuronIndex);
    
    for (int i = 0; i < neuron.length; i++) {
      double ch = trainingModifier.modifier(epoch) * (in[i] - neuron[i]);
      neuron[i] += ch; 
    }
    
    // Update BMU
    network.setNeuronWeight(neuronIndex, neuron);
    
    // Update Neighbors
    if (topology != null) {
      Map<Integer, Integer> neighborhood = topology.getNeighborhood(network, neuronIndex);
      
      Set<Integer> neighbors = neighborhood.keySet();
      
      for (Integer neighbor : neighbors) {
        // Prevent double training of bmu
        if (neighbor.intValue() == neuronIndex)
          continue;
        
        double[] neuronWeight = network.getNeuronWeight(neighbor);
        int distance = neighborhood.get(neighbor); 
        
        double nm = neighborhoodModifier.modifier(distance, epoch);
        double tm = trainingModifier.modifier(epoch);
        double totalModifier = nm * tm;

        for (int i = 0; i < neuronWeight.length; i++) {
          double nch = totalModifier * (in[i] - neuronWeight[i]);
          
          neuronWeight[i] += nch; 
        }
        
        network.setNeuronWeight(neighbor, neuronWeight);
      }
    }
  }

}
