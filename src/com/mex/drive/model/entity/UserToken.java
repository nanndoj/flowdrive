package com.mex.drive.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserToken implements BasicEntity {
  private String userId;
  private String token;

  @Id
  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String user) {
    this.userId = user;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public Object getKey() {
    return this.userId;
  }

  @Override
  public void setKey(Object Key) {
    this.userId = (String) Key;
  }

}
