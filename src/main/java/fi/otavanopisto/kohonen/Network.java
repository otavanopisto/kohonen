package fi.otavanopisto.kohonen;

/**
 * Kohonen SOM network.
 * 
 * @author antti.viljakainen
 */
public interface Network {

  /**
   * Returns neuron count for this network.
   * 
   * @return number of neurons in the network
   */
  int getNeuronCount();

  /**
   * Returns weight vector of neuron at index
   * 
   * @param index
   *          index of neuron
   * @return weight vector of neuron at index
   */
  double[] getNeuronWeight(int index);

  /**
   * Sets neuron weight for neuron at index
   * 
   * @param index index of neuron to set
   * @param weight new weight for neuron 
   */
  void setNeuronWeight(int index, double[] weight);
  
  /**
   * Returns index of best matching unit in respect to input
   * 
   * @param in
   *          input vector
   * @return Index of closest neuron
   */
  int findBMU(double[] in);

  /**
   * Returns index of best matching unit in respect to input. Some of the input
   * vector weights may be null and are then excluded from the distance
   * calculation.
   * 
   * @param in
   *          input vector
   * @return Index of closest neuron
   */
  int findBMU(Double[] in);
  
  /**
   * Return count of inputs to this network
   * 
   * @return number of inputs
   */
  int getInputCount();
}
