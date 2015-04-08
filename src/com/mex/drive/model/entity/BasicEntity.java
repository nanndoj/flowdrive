package com.mex.drive.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;

@Entity
@MappedSuperclass
public class BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Key id;

  public Key getKey() {
    return this.id;
  }

  public void setKey(Key id) {
    this.id = id;
  }

}