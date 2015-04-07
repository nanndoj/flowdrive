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
public class ProcessUnit extends BasicEntity {
  protected long          number;
  protected String        subject;

  @OneToMany(cascade = CascadeType.PERSIST)
  protected List<History> history;

  public long getNumber() {
    return this.number;
  }

  public void setNumber(long number) {
    this.number = number;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public List<History> getHistory() {
    return this.history;
  }

  public void setHistory(List<History> history) {
    this.history = history;
  }

}
