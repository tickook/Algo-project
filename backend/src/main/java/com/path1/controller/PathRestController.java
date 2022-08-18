package com.path1.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.path1.compute.Input;
import com.path1.compute.PathGenerator;
import com.path1.compute.Vertex;

@RestController
public class PathRestController {
  
  @PostMapping("/getPaths")
  public List<List<Vertex>> generatePaths(@Valid @RequestBody Input input) {
    return PathGenerator.generatePaths(input);
  }
  
  @PostMapping("/getPathsUsingHamiltonian")
  public List<List<Vertex>> generatePathsUsingHamiltonian(@Valid @RequestBody Input input) {
     return PathGenerator.generatePathsUsingHamiltonian(input);
  }
  
  @PostMapping("/getGroups")
  public Map<String, List<Vertex>> groupRiders(@Valid @RequestBody Input input) {
    return PathGenerator.groupRiders(input);
  }

}
