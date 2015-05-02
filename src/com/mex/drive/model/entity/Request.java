package com.mex.drive.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 * Transitient object representing an group of files. this processunit will flow
 * through the workflow steps
 *
 * @author nanndoj
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Request extends BasicEntity {
  protected String        protocolNumber;
  protected String        title;
  protected String        description;
  protected User          owner;
  protected FlowStep      currentStep;
  protected State         currentState;
  protected float         progressCompleted;
  protected long          elapsedTime;

  @OneToMany(cascade = CascadeType.PERSIST)
  protected List<History> history;

  public String getProcessNumber() {
    return this.protocolNumber;
  }

  public void setProtocolNumber(String processNumber) {
    this.protocolNumber = processNumber;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getOwner() {
    return this.owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public FlowStep getCurrentStep() {
    return this.currentStep;
  }

  public void setCurrentStep(FlowStep currentStep) {
    this.currentStep = currentStep;
  }

  public State getCurrentState() {
    return this.currentState;
  }

  public void setCurrentState(State currentState) {
    this.currentState = currentState;
  }

  public float getProgressCompleted() {
    return this.progressCompleted;
  }

  public void setProgressCompleted(float progressCompleted) {
    this.progressCompleted = progressCompleted;
  }

  public long getElapsedTime() {
    return this.elapsedTime;
  }

  public void setElapsedTime(long elapsedTime) {
    this.elapsedTime = elapsedTime;
  }

  public List<History> getHistory() {
    return this.history;
  }

  public void setHistory(List<History> history) {
    this.history = history;
  }
}
