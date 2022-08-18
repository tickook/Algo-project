package com.path1.compute;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class PathGenerator {

  //gives list of all paths that would be taken by cabs using dynamic programming.
  public static List<List<Vertex>> generatePaths(Input input) {

    Vertex s = input.getSource();

    if(s == null) {
      throw new IllegalArgumentException("source is null");
    }
    
    return findAllPaths(groupRiders(input), s);

  }

  //gives list of all paths that would be taken by cabs using Hamiltonian cycle(brute force).
  public static List<List<Vertex>> generatePathsUsingHamiltonian(Input input) {

    Vertex s = input.getSource();
    
    List<List<Vertex>> finalPaths = new ArrayList<>();

    List<Vertex> finalPath = new ArrayList<>();
    Stack<Vertex> path = new Stack<>();
    path.push(s);

    //call function to group riders so paths can be formed for each group.
    Map<String, List<Vertex>> group1 = groupRiders(input);

    for(List<Vertex> list : group1.values()) {

      double[][] distance = new double[list.size() + 1][list.size() + 1];
      list.add(0, s);

      for(int i =0; i < list.size() -1; i++) {
        for(int j =i; j < list.size(); j++) {
          distance[i][j] = getDistance(list.get(i).x, list.get(i).y, list.get(j).x, list.get(j).y);
          distance[j][i] = distance[i][j];
        }
      }

      boolean[] visited = new boolean[list.size()];

      List<List<Vertex>> ans = new ArrayList<>();
      getHamiltonianPath(s, s, path, list, distance, visited, ans);
      Double min = Double.MAX_VALUE;
      finalPath = new ArrayList<>();
      for(List<Vertex> current : ans) {
        Double sum =0.0;
        for(int i=0;i<current.size()-1;i++) {
          sum+= getDistance(current.get(i).x, current.get(i).y, current.get(i+1).x, current.get(i+1).y);
        }

        if(min > sum) {
          min = sum;
          finalPath = current;
        }

      }
      finalPaths.add(finalPath);
    }
    return finalPaths;

  }

  private static Map<String, List<Vertex>> cluster(List<Vertex> vertices, int k, List<Vertex> centroids, int perCar) {
    PriorityQueue<Edge> pq = new PriorityQueue<Edge>((a,b)-> Double.compare(a.distance, b.distance));
    Set<String> grouped = new HashSet<>();
    Map<String, List<Vertex>> map = new HashMap<>();
    for(Vertex centroid : centroids) {
      map.put(centroid.name, new ArrayList<>());
      for(Vertex vertex : vertices) {
        pq.add(new Edge(centroid, vertex, getDistance(centroid.x, centroid.y, vertex.x, vertex.y)));
      }
    }

    while(!pq.isEmpty()) {
      if(grouped.size() == vertices.size()) {
        break;
      }
      Edge current = pq.poll();
      if(!grouped.contains(current.destination.name) && map.get(current.source.name).size() < perCar) {
        map.get(current.source.name).add(current.destination);
        grouped.add(current.destination.name);
      }
    }

    return map;

  }

  private static double getDistance(double x1, double y1, double x2, double y2) {
    return Point2D.distance(x1, y1, x2, y2);
  }

  //create centroids based on range of x and y coordinates.
  private static List<Vertex> createCentroids(int clusters, List<Vertex> vertices) {
    List<Vertex> centroids = new ArrayList<>();

    List<Double> xCoords = new ArrayList<>();
    List<Double> yCoords = new ArrayList<>();

    for(Vertex v : vertices) {
      xCoords.add(v.x);
      yCoords.add(v.y);
    }

    Collections.sort(xCoords);
    Collections.sort(yCoords);

    int n = xCoords.size();

    int count = 1;

    int factor = n-1/clusters-1;

    //takes x and y coordinates such that they are evenly spaced.
    while(count <= clusters) {
      int current = 0;
      centroids.add(new Vertex("v"+count++, xCoords.get(current), yCoords.get(current)));
      current += factor;
    }

    return centroids;
  }

  //once we get the first cluster we can reiterate to improve the grouping.
  private static List<Vertex> reiterate(Map<String, List<Vertex>> group, List<Vertex> vertices) {
    List<Vertex> res = new ArrayList<>();
    Set<String> centroids = group.keySet();

    List<String> list = new ArrayList<String>(centroids);

    double sumx = 0;
    double sumy = 0;

    for(int i=0;i<centroids.size();i++) {
      List<Vertex> current = group.get(list.get(i));
      int n = current.size();
      for(int j=0;j<n;j++){
        sumx+=current.get(j).x;
        sumy+=current.get(j).y;
      }
      res.add(new Vertex(list.get(i), sumx/n, sumy/n));
    }

    return res;
  }

  //finds paths using dynamic programming.
  private static List<List<Vertex>> findAllPaths(Map<String, List<Vertex>> group1, Vertex startingPoint) {
    List<Integer> path = new ArrayList<>();
    List<List<Vertex>> allPaths = new ArrayList<>();
    for(List<Vertex> list : group1.values()) {
      double[][] distance = new double[list.size() + 1][list.size() + 1];
      list.add(0, startingPoint);

      for(int i =0; i < list.size() -1; i++) {
        for(int j =i; j < list.size(); j++) {
          distance[i][j] = getDistance(list.get(i).x, list.get(i).y, list.get(j).x, list.get(j).y);
          distance[j][i] = distance[i][j];
        }
      }

      //start
      int n = distance.length;
      int finalState = (int) Math.pow(2, n) - 1;
      double[][] dp = new double[n][(int) Math.pow(2, n)];

      int start = 0;

      for (int end = 0; end < n; end++) {
        if (end == start) {
          continue;
        }
        dp[end][(1 << start) | (1 << end)] = distance[start][end];
      }

      for (int i = 3; i <= n; i++) {
        for (int subset : perumatations(i, n)) {
          if (((1 << start) & subset) == 0) {
            continue;
          }
          for (int next = 0; next < n; next++) {
            if (next == start || ((1 << next) & subset) == 0) {
              continue;
            }
            int subsetWithoutNext = subset ^ (1 << next);
            double min = Double.POSITIVE_INFINITY;
            for (int end = 0; end < n; end++) {
              if (end == start || end == next || ((1 << end) & subset) == 0) {
                continue;
              }
              double dis = dp[end][subsetWithoutNext] + distance[end][next];
              if (dis < min) {
                min = dis;
              }
            }
            dp[next][subset] = min;
          }
        }
      }

      Double min = Double.POSITIVE_INFINITY;
      for (int i = 0; i < n; i++) {
        if (i == start) continue;
        double tourCost = dp[i][finalState] + distance[i][start];
        if (tourCost < min) {
          min = tourCost;
        }
      }

      int lastIndex = start;
      int state = finalState;
      path.add(start);

      for (int i = 1; i < n; i++) {

        int index = -1;
        for (int j = 0; j < n; j++) {
          if (j == start || ((1 << j) & state) == 0) {
            continue;
          }
          if (index == -1) {
            index = j;
          }
          double prevDist = dp[index][state] + distance[index][lastIndex];
          double newDist  = dp[j][state] + distance[j][lastIndex];
          if (newDist < prevDist) {
            index = j;
          }
        }

        path.add(index);
        state = state ^ (1 << index);
        lastIndex = index;
      }

      path.add(start);
      Collections.reverse(path);

      List<Vertex> finalPath = new ArrayList<>();

      for(int i=0; i< path.size(); i++) {
        finalPath.add(list.get(path.get(i)));
      }

      allPaths.add(finalPath);
      path = new ArrayList<>();

    }

    return allPaths;
  }

  private static List<Integer> perumatations(int n1, int n2) {
    List<Integer> subsets = new ArrayList<>();
    combinations(0, 0, n1, n2, subsets);
    return subsets;
  }

  private static void combinations(int set, int current, int r, int n, List<Integer> subsets) {

    int eleLeft = n - current;
    if (eleLeft < r) {
      return;
    }

    if (r != 0) {
      for (int i = current; i < n; i++) {
        set |= 1 << i;
        combinations(set, i + 1, r - 1, n, subsets);
        set &= ~(1 << i);
      }
    } else {
      subsets.add(set);
    }
  }
  
  //groups all riders into groups using k-means clustering.
  public static Map<String, List<Vertex>> groupRiders(Input input) {
    List<Vertex> vertices = input.getVertices();

    if(vertices == null || vertices.isEmpty()) {
      throw new IllegalArgumentException("no riders sent");
    }
    
    int clusters = input.getNumOfCabs();
    
    int perCar = input.getNumPerCar();      
    
    Map<String, List<Vertex>> group = cluster(vertices, clusters, createCentroids(clusters, vertices), perCar);

    Map<String, List<Vertex>> group1 = cluster(vertices, clusters, reiterate(group, vertices), perCar);

    int count = 0;
    while(!group1.equals(group) && count < 1000) {
      group = group1;
      group1 = cluster(vertices, clusters, reiterate(group, vertices), perCar);
      count++;
    }
    return group1;
  }

  //creates all possible hamiltonian pathsand puts them in a list.
  private static void getHamiltonianPath(Vertex vertex, Vertex source, Stack<Vertex> path, List<Vertex> vertices,
      double[][] distance, boolean[] visited, List<List<Vertex>> ans) {
    if (vertex == source && path.size() == vertices.size() + 1) {

      List<Vertex> temp = new ArrayList<>();

      int n = path.size();
      for (int i=0; i<n;i++) {
        temp.add(path.get(n-i-1));
      }

      ans.add(temp);
    }

    for (int i = 0; i < vertices.size(); i++) {
      if(path.size() < vertices.size() && i ==0) {
        continue;
      }
      if (distance[vertices.indexOf(vertex)][i] >= 1 && visited[i] == false) {
        int current = i;
        visited[current] = true;
        path.push(vertices.get(current));

        getHamiltonianPath(vertices.get(current), source, path, vertices, distance, visited, ans);
        visited[current] = false;
        path.pop();
      }
    }

  }



}
