package fi.otavanopisto.kohonen;

import fi.otavanopisto.kohonen.impl.GaussNeighborhoodModifier;
import fi.otavanopisto.kohonen.impl.LinearTrainingModifier;
import junit.framework.TestCase;

public class MiscTests extends TestCase {

  public MiscTests(String string) {
    super(string);
  }
  
  public void testGaussNeighbourhoodModifier() {
    GaussNeighborhoodModifier gn = new GaussNeighborhoodModifier(2);
    
    for (int i = 0; i < 20; i++) {
      assertTrue(gn.modifier(i, 0) <= 1d);
    }
  }
  
  public void testLinearTrainingModifier() {
    LinearTrainingModifier lt = new LinearTrainingModifier(1d, 100);
    
    assertEquals(1d, lt.modifier(0));
    assertEquals(0.5d, lt.modifier(50));
    assertEquals(0d, lt.modifier(100));

    for (int i = 1; i < 100; i++) {
      double m1 = lt.modifier(i - 1);
      double m2 = lt.modifier(i);
      assertTrue("Modifier not linear at " + i, m1 > m2);
    }
  }
}
