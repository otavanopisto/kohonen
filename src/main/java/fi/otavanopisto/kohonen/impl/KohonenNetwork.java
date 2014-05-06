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
 * for network streaming.
 * 
 * @author antti.viljakainen
 */
public class KohonenNetwork implements Network {

  public KohonenNetwork(double[] maxWeight, Topology topology) {
    this.topology = topology;
    initNeurons(topology.getNeuronCount(), maxWeight);
  }

  public KohonenNetwork(InputStream in, Topology topology) {
    this.topology = topology;
    neurons = new ArrayList<double[]>();
    
    String[] tempTable;
    double[] tempList;
    int rows = 0;
    try {
      InputStreamReader fr = new InputStreamReader(in);
      BufferedReader input = new BufferedReader(fr);
      String line;

      while ((line = input.readLine()) != null) {
        tempTable = line.split("\t");
        int tableLength = tempTable.length;
        tempList = new double[tableLength];
        for (int i = 0; i < tableLength; i++) {
          tempList[i] = Double.valueOf(tempTable[i]);
        }
        neurons.add(tempList);
        rows++;
      }
      
      fr.close();
      System.out.println(rows + " rows was imported");
    } catch (IOException e) {
      System.out.println("File can not be read!. Error: " + e);
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

  public void networkToStream(OutputStream out) {
    String weightList;
    double[] weight;
    try {
      OutputStreamWriter fw = new OutputStreamWriter(out);
      PrintWriter pw = new PrintWriter(fw);
      int networkSize = getNeuronCount();
      for (int i = 0; i < networkSize; i++) {
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
      fw.close();
    } catch (IOException e) {
      System.out.println("File can not be read!. Error: " + e);
    }
  }

  public int getInputCount() {
    return neurons.get(0).length;
  }
  
  private List<double[]> neurons;
  private final Topology topology;
  private DistanceFunction distanceFunction = new EuclideanDistanceFunction();
}
