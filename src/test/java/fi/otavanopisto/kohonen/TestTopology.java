package fi.otavanopisto.kohonen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fi.otavanopisto.kohonen.impl.GaussNeighborhoodModifier;
import fi.otavanopisto.kohonen.impl.HexagonalTopology;
import fi.otavanopisto.kohonen.impl.KohonenNetwork;
import fi.otavanopisto.kohonen.impl.LinearTrainingModifier;
import fi.otavanopisto.kohonen.impl.MatrixTopology;
import fi.otavanopisto.kohonen.impl.WTMTrainingAlgorithm;
import junit.framework.TestCase;

public class TestTopology extends TestCase {

  public TestTopology(String string) {
    super(string);
  }
  
  public void testMatrixTopology() {
    MatrixTopology t = new MatrixTopology(10, 10);
    
    assertEquals(100, t.getNeuronCount());
    
    double[] max = new double[] { 1d, 1d };
    Network network = new KohonenNetwork(t.getNeuronCount(), max);
    
    Map<Integer, Integer> n = t.getNeighborhood(network, 0);
    
    assertEquals(9, n.get(9).intValue());
    assertEquals(2, n.get(11).intValue());
    assertEquals(9, n.get(90).intValue());
    assertEquals(18, n.get(99).intValue());
    assertEquals(14, n.get(59).intValue());
    assertEquals(10, n.get(73).intValue());
    
    n = t.getNeighborhood(network, 35);
    
    assertEquals(10, n.get(99).intValue());
    assertEquals(6, n.get(59).intValue());
    assertEquals(6, n.get(73).intValue());
  }

  public void testMatrixTopology2() {
    MatrixTopology t = new MatrixTopology(7, 4);
    
    assertEquals(28, t.getNeuronCount());
    
    double[] max = new double[] { 1d, 1d };
    Network network = new KohonenNetwork(t.getNeuronCount(), max);
    
    Map<Integer, Integer> n = t.getNeighborhood(network, 0);
    
    assertEquals(3, n.get(9).intValue());
    assertEquals(9, n.get(27).intValue());
    assertEquals(6, n.get(15).intValue());
  }
  
  public void testHexagonalTopology() {
    HexagonalTopology t = new HexagonalTopology(10, 10);
    
    assertEquals(100, t.getNeuronCount());
    
    double[] max = new double[] { 1d, 1d };
    Network network = new KohonenNetwork(t.getNeuronCount(), max);
    
    Map<Integer, Integer> n = t.getNeighborhood(network, 0);
    
    // Row 0
    assertEquals(9, n.get(9).intValue());
    
    // Row 1
    assertEquals(1, n.get(10).intValue());
    for (int i = 11; i <= 19; i++) {
      assertEquals(i - 9, n.get(i).intValue());
    }
    
    // Row 2
    assertEquals(2, n.get(20).intValue());
    assertEquals(2, n.get(21).intValue());
    for (int i = 22; i <= 29; i++) {
      assertEquals(i - 19, n.get(i).intValue());
    }

    n = t.getNeighborhood(network, 23);
    
    assertEquals(4, n.get(0).intValue());
    assertEquals(2, n.get(2).intValue());
    assertEquals(3, n.get(20).intValue());

    t = new HexagonalTopology(3, 7);
    n = t.getNeighborhood(network, 20);
    
    assertEquals(7, n.get(0).intValue());
    assertEquals(5, n.get(8).intValue());
  }

  /**
   * Test matrix topology training with big neighbourhood, where every 
   * neuron should get moved at least some.
   * 
   * @throws Exception
   */
  public void testMatrixLearning() throws Exception {
    double[] maxWeight = new double[] { 1d, 1d };
    Topology topology = new MatrixTopology(2, 7);
    Network network = new KohonenNetwork(topology.getNeuronCount(), maxWeight);
    
    int maxEpochs = 1;
    TrainingModifier trainingModifier = new LinearTrainingModifier(0.2, maxEpochs);
    NeighborhoodModifier neighborhoodModifier = new GaussNeighborhoodModifier(10);
    WTMTrainingAlgorithm algorithm = new WTMTrainingAlgorithm(topology, trainingModifier, neighborhoodModifier, maxEpochs);
    
    List<double[]> data = new ArrayList<double[]>();
    data.add(new double[] { 0d, 0d });

    List<double[]> startWeights = new ArrayList<double[]>();
    for (int i = 0; i < network.getNeuronCount(); i++)
      startWeights.add(network.getNeuronWeight(i).clone());
    
    algorithm.train(network, data);

    for (int i = 0; i < network.getNeuronCount(); i++)
      assertTrue("Neuron " + i + " didn't learn a thing.", KohonenTestUtils.checkVectorsChanged(startWeights.get(i), network.getNeuronWeight(i)));
  }
  
  /**
   * Test hexagonal topology training with big neighbourhood, where every 
   * neuron should get moved at least some.
   * 
   * @throws Exception
   */
  public void testHexagonalLearning() throws Exception {
    double[] maxWeight = new double[] { 1d, 1d };
    Topology topology = new HexagonalTopology(5, 5);
    Network network = new KohonenNetwork(topology.getNeuronCount(), maxWeight);
    
    int maxEpochs = 1;
    TrainingModifier trainingModifier = new LinearTrainingModifier(0.2, maxEpochs);
    NeighborhoodModifier neighborhoodModifier = new GaussNeighborhoodModifier(10);
    WTMTrainingAlgorithm algorithm = new WTMTrainingAlgorithm(topology, trainingModifier, neighborhoodModifier, maxEpochs);
    
    
    List<double[]> data = new ArrayList<double[]>();
    data.add(new double[] { 0d, 0d });

    List<double[]> startWeights = new ArrayList<double[]>();
    for (int i = 0; i < network.getNeuronCount(); i++)
      startWeights.add(network.getNeuronWeight(i).clone());
    
    algorithm.train(network, data);

    for (int i = 0; i < network.getNeuronCount(); i++)
      assertTrue("Neuron " + i + " didn't learn a thing.", KohonenTestUtils.checkVectorsChanged(startWeights.get(i), network.getNeuronWeight(i)));
  }
  
}
