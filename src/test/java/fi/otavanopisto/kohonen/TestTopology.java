package fi.otavanopisto.kohonen;

import java.util.Map;

import fi.otavanopisto.kohonen.impl.KohonenNetwork;
import fi.otavanopisto.kohonen.impl.MatrixTopology;
import junit.framework.TestCase;

public class TestTopology extends TestCase {

  public TestTopology(String string) {
    super(string);
  }
  
  public void testMatrixTopology() {
    MatrixTopology t = new MatrixTopology(10, 10);
    
    assertEquals(100, t.getNeuronCount());
    
    double[] max = new double[] { 1d, 1d };
    Network network = new KohonenNetwork(max , t);
    
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
    Network network = new KohonenNetwork(max , t);
    
    Map<Integer, Integer> n = t.getNeighborhood(network, 0);
    
    assertEquals(3, n.get(9).intValue());
    assertEquals(9, n.get(27).intValue());
    assertEquals(6, n.get(15).intValue());
  }
}
