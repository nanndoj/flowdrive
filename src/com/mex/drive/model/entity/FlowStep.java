package com.mex.drive.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class FlowStep extends BasicEntity {
  private String   name;
  @OneToMany(cascade = CascadeType.PERSIST)
  private WorkFlow flow;

  public WorkFlow getFlow() {
    return this.flow;
  }

  public void setFlow(WorkFlow flow) {
    this.flow = flow;
  }

  public FlowGroup getGroup() {
    return this.group;
  }

  public void setGroup(FlowGroup group) {
    this.group = group;
  }

  private FlowGroup group;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
