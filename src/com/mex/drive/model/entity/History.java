package com.mex.drive.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class History extends BasicEntity {
  private Date   date;
  private String text;

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
