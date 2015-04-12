package com.mex.drive.view.endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.mex.drive.business.UserBO;
import com.mex.drive.model.entity.User;

@Api(name = "user", version = "v1")
public class UserREST {

  @ApiMethod(name = "insert", httpMethod = HttpMethod.POST)
  public User insert(User user) throws Exception {
    return (User) new UserBO().insert(user);
  }
}
