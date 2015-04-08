package com.mex.drive.view.endpoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mex.drive.business.RequestBO;
import com.mex.drive.model.entity.FlowStep;
import com.mex.drive.model.entity.Request;
import com.mex.drive.model.entity.State;
import com.mex.drive.model.entity.User;

@Api(name = "request", version = "v1")
public class RequestREST {

  @ApiMethod(name = "list", path = "list", httpMethod = HttpMethod.GET)
  public List<Request> getAll() {
    RequestBO requestBO = new RequestBO();
    List<Request> list = new ArrayList<Request>();

    Request req = new Request();
    req.setProtocolNumber("180/2015");
    User owner = new User();
    owner.setName("Fernando Santos");
    owner.setEmail("nanndoj@gmail.com");
    owner.setProfileImageURL("http://uhuuul.com");
    req.setOwner(owner);
    req.setCurrentStep(new FlowStep());
    req.setTitle("Title to test");
    req.setDescription("This is a long description");
    State state = new State();
    state.setStateName("Aguardando análise");
    req.setCurrentState(state);
    req.setProgressCompleted(50.2512F);
    req.setElapsedTime(new Date().getTime());

    list.add(req);

    return list;
  }

  @ApiMethod(name = "get", path = "get", httpMethod = HttpMethod.GET)
  public Request get(@Named(value = "id") long id) {
    RequestBO requestBO = new RequestBO();
    Key key = KeyFactory.createKey(Request.class.getName(), id);
    return requestBO.get(Request.class, key);
  }

  @ApiMethod(name = "insert", httpMethod = HttpMethod.POST)
  public Request insert(Request request) throws Exception {
    RequestBO requestBO = new RequestBO();
    return (Request) requestBO.insert(request);
  }
}
