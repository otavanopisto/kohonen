package fi.otavanopisto.kohonen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.otavanopisto.kohonen.Network;
import fi.otavanopisto.kohonen.Topology;

/**
 * Hexagonal topology for Kohonen SOM.
 * 
 * Odd rows are indented, meaning a node on even row will link to (r, c) and (r, c + 1) on rows r 
 * below and above. For odd rows, neighbours are (r, c) and (r, c - 1). Row indexing starts at 0
 * so first row is non-indented. 
 * 
 * @author antti.viljakainen
 */
public class HexagonalTopology implements Topology {

  private final int rows;
  private final int columns;

  public HexagonalTopology(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
  }

  public Map<Integer, Integer> getNeighborhood(Network network, int neuronIndex) {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    map.put(neuronIndex, 0);

    List<Integer> list = new ArrayList<Integer>();

    list.add(neuronIndex);

    for (int i = 0; i < list.size(); i++) {
      int neuron = list.get(i);

      List<Integer> neighbours = getImmediateNeighbours(neuron);
      int dist = map.get(neuron) + 1;

      for (Integer n : neighbours) {
        if (!list.contains(n))
          list.add(n);

        if (!map.containsKey(n))
          map.put(n, dist);
      }
    }

    map.remove(neuronIndex);

    return map;
  }

  private List<Integer> getImmediateNeighbours(int neuronIndex) {
    int row = neuronIndex / columns;
    int col = neuronIndex - (row * columns);

    List<Integer> neighbours = new ArrayList<Integer>();

    /**
     * Same row
     */

    // Left
    if (col - 1 >= 0)
      neighbours.add(neuronIndex(row, col - 1));
    // Right
    if (col + 1 < columns)
      neighbours.add(neuronIndex(row, col + 1));

    if (row % 2 == 0) {
      /**
       * Row above
       */
      if (row - 1 >= 0) {
        // Left
        if (col - 1 >= 0)
          neighbours.add(neuronIndex(row - 1, col - 1));

        // Right
        neighbours.add(neuronIndex(row - 1, col));
      }

      /**
       * Row below
       */
      if (row + 1 < rows) {
        // Left
        if (col - 1 >= 0)
          neighbours.add(neuronIndex(row + 1, col - 1));

        // Right
        neighbours.add(neuronIndex(row + 1, col));
      }

    } else {
      /**
       * Row above
       */
      if (row - 1 >= 0) {
        // Left
        neighbours.add(neuronIndex(row - 1, col));

        // Right
        if (col + 1 < columns)
          neighbours.add(neuronIndex(row - 1, col + 1));
      }

      /**
       * Row below
       */
      if (row + 1 < rows) {
        // Left
        neighbours.add(neuronIndex(row + 1, col));

        // Right
        if (col + 1 < columns)
          neighbours.add(neuronIndex(row + 1, col + 1));
      }
    }

    return neighbours;
  }

  private int neuronIndex(int row, int col) {
    return row * columns + col;
  }

  public int getNeuronCount() {
    return rows * columns;
  }

}
