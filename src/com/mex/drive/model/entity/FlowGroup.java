package com.mex.drive.model.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class FlowGroup extends BasicEntity {
  private String description;
  private String color;

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
