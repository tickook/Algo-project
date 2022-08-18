package com.path1.compute;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vertex {

  @JsonProperty("name")
  @NotBlank
  String name;
  
  @JsonProperty("x")
  @NotNull
  double x;
  
  @JsonProperty("y")
  @NotNull
  double y;
  
   /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the x
   */
  public double getX() {
    return x;
  }

  /**
   * @return the y
   */
  public double getY() {
    return y;
  }

  public Vertex() {
    
  }
  
  public Vertex(String name, double x, double y) {
    this.name = name;
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if(o == this) {
      return true;
    }
    if(!(o instanceof Vertex)) {
      return false;
    }
    Vertex v = (Vertex) o;

    return Double.compare(x, v.x) == 0 && Double.compare(y, v.y)== 0 && name.equalsIgnoreCase(v.name); 
  }

}
