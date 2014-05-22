package fi.otavanopisto.kohonen.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fi.otavanopisto.kohonen.DistanceFunction;
import fi.otavanopisto.kohonen.Network;
import fi.otavanopisto.kohonen.Topology;

/**
 * Default implementation of Kohonen Network. Introduces input/output methods
 * to save/load a network.
 * 
 * @author antti.viljakainen
 */
public class KohonenNetwork implements Network {

  public KohonenNetwork(double[] maxWeight, Topology topology) {
    this.topology = topology;

    initNeurons(topology.getNeuronCount(), maxWeight);
  }

  public KohonenNetwork(InputStream in, Topology topology) throws IOException {
    this.topology = topology;
    neurons = new ArrayList<double[]>();
    
    InputStreamReader reader = new InputStreamReader(in);
    try {
      BufferedReader input = new BufferedReader(reader);
      String line;

      while ((line = input.readLine()) != null) {
        String[] temp = line.split("\t");
        double[] neuronWeight = new double[temp.length];

        for (int i = 0; i < temp.length; i++) {
          neuronWeight[i] = Double.valueOf(temp[i]);
        }

        neurons.add(neuronWeight);
      }
    } finally {
      reader.close();
    }
  }
  
  private void initNeurons(int neuronCount, double[] maxWeight) {
    Random rand = new Random();
    neurons = new ArrayList<double[]>(neuronCount);
    
    for (int i = 0; i < neuronCount; i++) {
      double[] neuron = new double[maxWeight.length];
      
      for (int j = 0; j < maxWeight.length; j++) {
        neuron[j] = rand.nextDouble() * maxWeight[j];
      }
      
      neurons.add(neuron);
    }
  }

  public int getNeuronCount() {
    return neurons.size();
  }

  public double[] getNeuronWeight(int index) {
    return neurons.get(index);
  }

  public void setNeuronWeight(int index, double[] weight) {
    neurons.set(index, weight);
  }

  public int findBMU(double[] in) {
    if (getNeuronCount() > 0) {
      double bestDistance = distanceFunction.getDistance(in, getNeuronWeight(0));
      int bmu = 0;
      
      for (int i = 1; i < getNeuronCount(); i++) {
        double distance = distanceFunction.getDistance(in, getNeuronWeight(i));
        
        if (distance < bestDistance) {
          bestDistance = distance;
          bmu = i;
        }
      }
      
      return bmu;
    } else
      return -1;
  }

  public int findBMU(Double[] in) {
    if (getNeuronCount() > 0) {
      double bestDistance = distanceFunction.getDistance(in, getNeuronWeight(0));
      int bmu = 0;
      
      for (int i = 1; i < getNeuronCount(); i++) {
        double distance = distanceFunction.getDistance(in, getNeuronWeight(i));
        
        if (distance < bestDistance) {
          bestDistance = distance;
          bmu = i;
        }
      }
      
      return bmu;
    } else
      return -1;
  }

  public List<Integer> findBMUs(Double[] in, double maxDiff) {
    int networkSize = getNeuronCount();
    double bestDistance = -1;
    
    double[] distances = new double[networkSize];
    
    if (networkSize > 0) {
      distances[0] = distanceFunction.getDistance(in, getNeuronWeight(0));
      bestDistance = distances[0];
      
      for (int i = 1; i < networkSize; i++){
        distances[i] = distanceFunction.getDistance(in, getNeuronWeight(i));

        if (distances[i] < bestDistance) {
          bestDistance = distances[i];
        }
      }
    }
    
    maxDiff = maxDiff + bestDistance;
    
    List<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < networkSize; i++) {
      if (distances[i] < maxDiff)
        list.add(i);
    }
    
    return list;
  }
  
  public Topology getTopology() {
    return topology;
  }

  public void networkToStream(OutputStream out) throws IOException {
    String weightList;
    double[] weight;
  
    OutputStreamWriter osw = new OutputStreamWriter(out);
    try {
      PrintWriter pw = new PrintWriter(osw);

      for (int i = 0; i < getNeuronCount(); i++) {
        weightList = "";
        weight = getNeuronWeight(i);

        for (int j = 0; j < weight.length; j++) {
          weightList += weight[j];
          if (j < weight.length - 1) {
            weightList += "\t";
          }
        }
        pw.println(weightList);
      }
    } finally {
      osw.close();
    }
  }

  public int getInputCount() {
    return neurons.get(0).length;
  }
  
  private List<double[]> neurons;
  private final Topology topology;
  private DistanceFunction distanceFunction = new EuclideanDistanceFunction();
}
