package com.mex.drive.view.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mex.drive.auth.GoogleAuthHelper;

@SuppressWarnings("serial")
public class GoogleAuthServlet extends HttpServlet {
  /*
   * The GoogleAuthHelper handles all the heavy lifting, and contains all
   * "secrets" required for constructing a google login url.
   */
  final GoogleAuthHelper helper = new GoogleAuthHelper();

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.sendRedirect(this.helper.buildLoginUrl());
  }
}
