package com.mex.drive.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class WorkFlow extends IdentifiedEntity {
  private String name;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<FlowStep> getSteps() {
    return this.steps;
  }

  public void setSteps(List<FlowStep> steps) {
    this.steps = steps;
  }

  private List<FlowStep> steps;
}
