package com.mex.drive.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ControllerFilter implements Filter {
  private static final String[]    URL_VALUES      = new String[] { "/app", "/googleauth", "/googleoauth2callback" };
  private static final Set<String> EXTERNAL_ACCESS = new HashSet<String>(Arrays.asList(URL_VALUES));

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
    // Get the token from the request
    HttpServletRequest request = (HttpServletRequest) req;
    String token = request.getHeader("token");

    if (EXTERNAL_ACCESS.contains(request.getRequestURI()) || request.getRequestURI().startsWith("/_ah")) {
      filterChain.doFilter(req, resp);
      return;
    }

    if ((token != null) && (token.length() > 0)) {
      filterChain.doFilter(req, resp);
      return;
    }

    request.getRequestDispatcher("/404").forward(req, resp);

  }

  @Override
  public void init(FilterConfig config) throws ServletException {

  }

}
