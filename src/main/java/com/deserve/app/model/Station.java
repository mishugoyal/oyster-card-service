package com.deserve.app.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class Station {
  private String name;
  private List<Integer> zones;
}
