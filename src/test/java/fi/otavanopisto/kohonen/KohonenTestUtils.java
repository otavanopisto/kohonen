package fi.otavanopisto.kohonen;

import java.util.List;

public class KohonenTestUtils {

  public static double getMapDistance(Network network, List<double[]> data) {
    return KohonenUtils.getMapDistance(network, data);
  }
  
  public static boolean checkVectorsChanged(double[] v1, double[] v2) {
    for (int i = 0; i < v1.length; i++) {
      if (v1[i] != v2[i])
        return true;
    }
      
    return false;
  }

}
