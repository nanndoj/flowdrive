package com.mex.drive.view.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
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
      UserBO userBO = new UserBO();

      GoogleTokenResponse tokenResponse;
      // Exchange the auth_token by an access_token and a refresh_token
      tokenResponse = this.helper.getUserToken(req.getParameter("code"));
      JSONObject userInfo = this.helper.getUserInfoJson(tokenResponse);

      if (userInfo != null) {
        // Parse the user
        User u = new User();
        u.setId(userInfo.getString("id"));

        // Store the refresh token in the system
        // Get the machine address allowed by the token
        if (tokenResponse.getRefreshToken() != null) {
          userBO.saveRefreshToken(u, tokenResponse.getRefreshToken());
        }

        // Check if the user has permission to access this feature
        // by consulting Google Admin Console
        if (userBO.isAuthorized(u)) {
          userBO.authorize(u);

          // The user has the required permission to go on
          req.setAttribute("token", tokenResponse.getAccessToken());
          req.setAttribute("expires", tokenResponse.getExpiresInSeconds());
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
