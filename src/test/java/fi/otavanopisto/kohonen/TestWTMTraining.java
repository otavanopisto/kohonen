package fi.otavanopisto.kohonen;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import fi.otavanopisto.kohonen.impl.GaussNeighborhoodModifier;
import fi.otavanopisto.kohonen.impl.HexagonalTopology;
import fi.otavanopisto.kohonen.impl.KohonenNetwork;
import fi.otavanopisto.kohonen.impl.LinearTrainingModifier;
import fi.otavanopisto.kohonen.impl.MatrixTopology;
import fi.otavanopisto.kohonen.impl.WTMTrainingAlgorithm;

public class TestWTMTraining extends TestCase {

  public TestWTMTraining(String string) {
    super(string);
  }
  
  public void testWTM() throws Exception {
    double[] maxWeight = new double[] { 1d, 1d };
    Topology topology = new MatrixTopology(1, 2);
    Network network = new KohonenNetwork(topology.getNeuronCount(), maxWeight);
    
    int maxEpochs = 50000;
    TrainingModifier trainingModifier = new LinearTrainingModifier(0.8, maxEpochs);
    NeighborhoodModifier neighborhoodModifier = new GaussNeighborhoodModifier(0.1);
    WTMTrainingAlgorithm algorithm = new WTMTrainingAlgorithm(topology, trainingModifier, neighborhoodModifier, maxEpochs);
    
    double[] in1 = new double[] { 0d, 0d };
    double[] in2 = new double[] { 1d, 1d };
    
    List<double[]> data = new ArrayList<double[]>();
    data.add(in1);
    data.add(in2);

    double[] neuronWeight1 = network.getNeuronWeight(0).clone();
    double[] neuronWeight2 = network.getNeuronWeight(1).clone();
    
    algorithm.train(network, data);

    double[] neuronWeight1b = network.getNeuronWeight(0);
    double[] neuronWeight2b = network.getNeuronWeight(1);
    
    int ind1 = network.findBMU(in1);
    int ind2 = network.findBMU(in2);
    
    assertNotSame("Input vectors mapped to same neuron.", ind1, ind2);
    assertTrue("Neuron 1 didn't learn a thing.", KohonenTestUtils.checkVectorsChanged(neuronWeight1, neuronWeight1b));
    assertTrue("Neuron 2 didn't learn a thing.", KohonenTestUtils.checkVectorsChanged(neuronWeight2, neuronWeight2b));
  }

  public void testWTMQuad() throws Exception {
    double[] maxWeight = new double[] { 1d, 1d };
    Topology topology = new MatrixTopology(2, 2);
    Network network = new KohonenNetwork(topology.getNeuronCount(), maxWeight);
    
    int maxEpochs = 50000;
    TrainingModifier trainingModifier = new LinearTrainingModifier(0.8, maxEpochs);
    NeighborhoodModifier neighborhoodModifier = new GaussNeighborhoodModifier(0.2);
    WTMTrainingAlgorithm algorithm = new WTMTrainingAlgorithm(topology, trainingModifier, neighborhoodModifier, maxEpochs);
    
    List<double[]> data = new ArrayList<double[]>();
    data.add(new double[] { 0d, 0d });
    data.add(new double[] { 0d, 1d });
    data.add(new double[] { 1d, 0d });
    data.add(new double[] { 1d, 1d });

    List<double[]> startWeights = new ArrayList<double[]>();
    for (int i = 0; i < network.getNeuronCount(); i++)
      startWeights.add(network.getNeuronWeight(i).clone());
    
    algorithm.train(network, data);

    for (int i = 0; i < network.getNeuronCount(); i++)
      assertTrue("Neuron " + i + " didn't learn a thing.", KohonenTestUtils.checkVectorsChanged(startWeights.get(i), network.getNeuronWeight(i)));
  }

  public void testWTMQuadCumulative() throws Exception {
    double[] maxWeight = new double[] { 1d, 1d };
    Topology topology = new MatrixTopology(2, 2);
    Network network = new KohonenNetwork(topology.getNeuronCount(), maxWeight);
    
    int maxEpochs = 20;
    TrainingModifier trainingModifier = new LinearTrainingModifier(0.2, maxEpochs);
    NeighborhoodModifier neighborhoodModifier = new GaussNeighborhoodModifier(0.2);
    WTMTrainingAlgorithm algorithm = new WTMTrainingAlgorithm(topology, trainingModifier, neighborhoodModifier, maxEpochs);
    
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

  public void testWTMHexQuadCumulative() throws Exception {
    double[] maxWeight = new double[] { 1d, 1d };
    Topology topology = new HexagonalTopology(5, 5);
    Network network = new KohonenNetwork(topology.getNeuronCount(), maxWeight);
    
    int maxEpochs = 20;
    TrainingModifier trainingModifier = new LinearTrainingModifier(0.2, maxEpochs);
    NeighborhoodModifier neighborhoodModifier = new GaussNeighborhoodModifier(0.2);
    WTMTrainingAlgorithm algorithm = new WTMTrainingAlgorithm(topology, trainingModifier, neighborhoodModifier, maxEpochs);
    
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
