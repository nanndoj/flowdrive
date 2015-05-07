package com.mex.drive.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
@MappedSuperclass
public class IdentifiedEntity implements BasicEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdGenerator")
  protected Key  key;
  @Transient
  private String id;

  @Override
  public Object getKey() {
    return this.key;
  }

  public String getId() {
    if ((this.id == null) && (this.key != null)) {
      this.id = this.key.getName();
    }
    return this.id;
  }

  @Override
  public void setKey(Object id) {
    this.key = (Key) id;
  }

  public void setId(String id) {
    this.id = id;
    this.setKey(KeyFactory.createKey(this.getClass().getSimpleName(), id));
  }
}