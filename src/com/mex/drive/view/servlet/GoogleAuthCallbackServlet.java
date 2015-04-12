package com.mex.drive.view.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mex.drive.auth.GoogleAuthHelper;
import com.mex.drive.business.UserBO;
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

        // Check if the user is already saved on the system
        User foundUser = new UserBO().get(User.class, u.getKey());

        // If the user was found
        if (foundUser == null) {
          resp.sendRedirect("/app/404");
        } else {
          req.setAttribute("token", req.getParameter("code"));
          req.setAttribute("user", foundUser);
          req.getRequestDispatcher("/postlogin.jsp").forward(req, resp);
        }
      }

      resp.getWriter().print(userInfo.toString());
    } catch (JSONException | ServletException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
