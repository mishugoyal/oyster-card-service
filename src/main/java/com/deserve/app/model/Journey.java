package com.deserve.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Journey {
  private Station source;
  private Station destination;
  private JourneyType journeyType;
}
