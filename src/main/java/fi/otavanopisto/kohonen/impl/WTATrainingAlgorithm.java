package fi.otavanopisto.kohonen.impl;

import java.util.Collection;

import fi.otavanopisto.kohonen.Network;
import fi.otavanopisto.kohonen.TrainingAlgorithm;
import fi.otavanopisto.kohonen.TrainingEndClause;
import fi.otavanopisto.kohonen.TrainingModifier;

/**
 * Winner Takes All training algorithm for Kohonen SOM.
 * 
 * In WTA, only the winning neuron is trained, no others are taken into account.
 * 
 * @author antti.viljakainen
 */
public class WTATrainingAlgorithm implements TrainingAlgorithm {

  private TrainingModifier trainingModifier;
  private int maxEpochs;
  private int epoch = -1;
  private final TrainingEndClause endClause;

  public WTATrainingAlgorithm(TrainingModifier trainingModifier, int maxEpochs) {
    this(trainingModifier, null, maxEpochs);
  }
  
  public WTATrainingAlgorithm(TrainingModifier trainingModifier, TrainingEndClause endClause, int maxEpochs) {
    this.trainingModifier = trainingModifier;
    this.endClause = endClause;
    this.maxEpochs = maxEpochs;
  }
  
  public int getEpoch() {
    return epoch;
  }
 
  public void train(Network network, Collection<double[]> data) {
    if (endClause != null)
      trainWithEndClause(network, data);
    else
      trainNormal(network, data);
  }

  private void trainNormal(Network network, Collection<double[]> data) {
    for (epoch = 0; epoch < maxEpochs; epoch++) {
      for (double[] weight : data) {
        int closestNeuron = network.findBMU(weight);
        
        trainNeuron(epoch, network, closestNeuron, weight);
      }
    }
  }

  private void trainWithEndClause(Network network, Collection<double[]> data) {
    for (epoch = 0; epoch < maxEpochs; epoch++) {
      for (double[] weight : data) {
        int closestNeuron = network.findBMU(weight);
        
        trainNeuron(epoch, network, closestNeuron, weight);
      }
      
      if (!endClause.continueTraining(network, epoch, data))
        break;
    }
  }
  
  private void trainNeuron(int epoch, Network network, int neuronIndex, double[] in) {
    double[] neuron = network.getNeuronWeight(neuronIndex);
    
    for (int i = 0; i < neuron.length; i++) {
      neuron[i] += trainingModifier.modifier(epoch) * (in[i] - neuron[i]); 
    }
    
    network.setNeuronWeight(neuronIndex, neuron);
  }

}
