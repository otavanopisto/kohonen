package fi.otavanopisto.kohonen;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import fi.otavanopisto.kohonen.impl.EuclideanDistanceFunction;
import fi.otavanopisto.kohonen.impl.KohonenNetwork;
import fi.otavanopisto.kohonen.impl.LinearTrainingModifier;
import fi.otavanopisto.kohonen.impl.MatrixTopology;
import fi.otavanopisto.kohonen.impl.WTATrainingAlgorithm;

public class TestWTATraining extends TestCase {

  public TestWTATraining(String string) {
    super(string);
  }
  
//  public void testWTA1x2() throws Exception {
//    double[] maxWeight = new double[] { 1d, 1d };
//    Topology topology = new MatrixTopology(1, 2);
//    Network network = new KohonenNetwork(maxWeight, topology);
//    
//    int maxEpochs = 8000;
//    TrainingModifier trainingModifier = new LinearTrainingModifier(0.8, maxEpochs);
//    
//    WTATrainingAlgorithm algorithm = new WTATrainingAlgorithm(trainingModifier, maxEpochs);
//    
//    double[] in1 = new double[] { 0d, 0d };
//    double[] in2 = new double[] { 1d, 1d };
//    
//    List<double[]> data = new ArrayList<double[]>();
//    data.add(in1);
//    data.add(in2);
//
//    double[] neuronWeight1 = network.getNeuronWeight(0).clone();
//    double[] neuronWeight2 = network.getNeuronWeight(1).clone();
//    
//    algorithm.train(network, data);
//
//    double[] neuronWeight1b = network.getNeuronWeight(0);
//    double[] neuronWeight2b = network.getNeuronWeight(1);
//    
//    int ind1 = network.findBMU(in1);
//    int ind2 = network.findBMU(in2);
//    
//    assertNotSame("Input vectors mapped to same neuron.", ind1, ind2);
//    assertTrue("Neuron 1 didn't learn a thing.", KohonenTestUtils.checkVectorsChanged(neuronWeight1, neuronWeight1b));
//    assertTrue("Neuron 2 didn't learn a thing.", KohonenTestUtils.checkVectorsChanged(neuronWeight2, neuronWeight2b));
//  }
//
//  public void testWTA2x2() throws Exception {
//    double[] maxWeight = new double[] { 1d, 1d };
//    Topology topology = new MatrixTopology(2, 2);
//    Network network = new KohonenNetwork(maxWeight, topology);
//    
//    int maxEpochs = 8000;
//    TrainingModifier trainingModifier = new LinearTrainingModifier(0.8, maxEpochs);
//    WTATrainingAlgorithm algorithm = new WTATrainingAlgorithm(trainingModifier, maxEpochs);
//    
//    List<double[]> data = new ArrayList<double[]>();
//    data.add(new double[] { 0d, 0d });
//    data.add(new double[] { 0d, 1d });
//    data.add(new double[] { 1d, 0d });
//    data.add(new double[] { 1d, 1d });
//
//    List<double[]> startWeights = new ArrayList<double[]>();
//    for (int i = 0; i < network.getNeuronCount(); i++)
//      startWeights.add(network.getNeuronWeight(i).clone());
//    
//    algorithm.train(network, data);
//
//    for (int i = 0; i < network.getNeuronCount(); i++)
//      assertTrue("Neuron " + i + " didn't learn a thing.", KohonenTestUtils.checkVectorsChanged(startWeights.get(i), network.getNeuronWeight(i)));
//    
//    int[] bmus = new int[data.size()];
//    for (int i = 0; i < data.size(); i++) {
//      bmus[i] = network.findBMU(data.get(i));
//    }
//    
//    for (int i = 0; i < network.getNeuronCount(); i++) {
//      int count = 0;
//      for (int j = 0; j < bmus.length; j++) {
//        if (bmus[j] == i)
//          count++;
//      }
//      assertEquals(1, count);
//    }
//  }
  
  /**
   * Test that WTA trains only BMU and none of the other neurons are changed
   * 
   * @throws Exception
   */
  public void testWTATopology() throws Exception {
    double[] maxWeight = new double[] { 1d, 1d };
    Topology topology = new MatrixTopology(2, 2);
    Network network = new KohonenNetwork(maxWeight, topology);
    
    int maxEpochs = 1;
    TrainingModifier trainingModifier = new LinearTrainingModifier(0.8, maxEpochs);
    
    WTATrainingAlgorithm algorithm = new WTATrainingAlgorithm(trainingModifier, maxEpochs);
    EuclideanDistanceFunction edf = new EuclideanDistanceFunction();
    
    double[] in1 = new double[] { 0d, 0d };
    
    List<double[]> data = new ArrayList<double[]>();
    data.add(in1);

    List<double[]> neuronWeights = new ArrayList<double[]>();
    
    for (int i = 0; i < topology.getNeuronCount(); i++) {
      neuronWeights.add(network.getNeuronWeight(i).clone());
    }
    
    int ind1 = network.findBMU(in1);

    algorithm.train(network, data);

    for (int i = 0; i < topology.getNeuronCount(); i++) {
      double[] neuronWeight = network.getNeuronWeight(i);
      double[] oldWeight = neuronWeights.get(i);
      
      if (i == ind1) {
        assertTrue(edf.getDistance(neuronWeight, oldWeight) != 0);
      } else {
        assertVectorSame(neuronWeight, oldWeight);
      }
    }
  }

  private void assertVectorSame(double[] d, double[] d2) {
    for (int i = 0; i < d.length; i++) {
      assertEquals(d[i], d2[i]);
    }
  }
  
  public void testWTACumulative() throws Exception {
    double[] maxWeight = new double[] { 1d, 1d };
    Topology topology = new MatrixTopology(2, 2);
    Network network = new KohonenNetwork(maxWeight, topology);
    
    int maxEpochs = 10;
    TrainingModifier trainingModifier = new LinearTrainingModifier(0.2, maxEpochs);
    WTATrainingAlgorithm algorithm = new WTATrainingAlgorithm(trainingModifier, maxEpochs);
    
    List<double[]> data = new ArrayList<double[]>();
    data.add(new double[] { 0d, 0d });
    data.add(new double[] { 0d, 1d });
    data.add(new double[] { 1d, 0d });
    data.add(new double[] { 1d, 1d });

    double dist = KohonenTestUtils.getMapDistance(network, data);
    
    for (int i = 1; i < 5; i++) {
      algorithm.train(network, data);
      double newDist = KohonenTestUtils.getMapDistance(network, data);
      
      assertTrue("Map isn't learning. i = " + i, newDist < dist);
      dist = newDist;
    }
  }
}
