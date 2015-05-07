package com.mex.drive.model.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;

import com.google.appengine.api.datastore.KeyFactory;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends IdentifiedEntity {
  private String name;
  private String email;
  private String profileImageURL;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProfileImageURL() {
    return this.profileImageURL;
  }

  public void setProfileImageURL(String profileImageURL) {
    this.profileImageURL = profileImageURL;
  }

  @PrePersist
  public void ensureId() {
    this.key = KeyFactory.createKey("User", this.getId());
  }
}
