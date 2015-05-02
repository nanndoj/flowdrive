package com.mex.drive.view.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mex.drive.auth.GoogleAuthHelper;
import com.mex.drive.business.UserBO;
import com.mex.drive.exceptions.MEXException;
import com.mex.drive.model.entity.User;

@SuppressWarnings("serial")
public class GoogleAuthCallbackServlet extends HttpServlet {
  /*
   * The GoogleAuthHelper handles all the heavy lifting, and contains all
   * "secrets" required for constructing a google login url.
   */
  final GoogleAuthHelper helper = new GoogleAuthHelper();

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    try {
      JSONObject userInfo;
      userInfo = this.helper.getUserInfoJson(req.getParameter("code"));

      if (userInfo != null) {
        // Parse the user
        User u = new User();
        u.setId(userInfo.getString("id"));

        UserBO userBO = new UserBO();

        // Check if the user has permission to access this feature
        // by consulting Google Admin Console
        if (userBO.isAuthorized(u)) {
          userBO.authorize(u);

          // The user has the required permission to go on
          req.setAttribute("token", req.getParameter("code"));
          req.setAttribute("user", u);

          // Go ahead
          req.getRequestDispatcher("/postlogin.jsp").forward(req, resp);

        } else {
          resp.sendRedirect("/app/404");
        }
      }
    } catch (Exception e) {
      new MEXException(e).handle(req, resp);
    }

  }
}
