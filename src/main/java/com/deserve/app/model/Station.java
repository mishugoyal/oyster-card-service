package com.deserve.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Station {
  private String name;
  private List<Integer> zones;
}
