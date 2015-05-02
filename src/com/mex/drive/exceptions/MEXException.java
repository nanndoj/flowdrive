package com.mex.drive.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MEXException {

  private Exception ex;

  public MEXException(Exception e) {
    this.ex = e;
  }

  public void handle(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: exception handling
    this.ex.printStackTrace();
  }

}
