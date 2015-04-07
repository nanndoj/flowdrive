package com.mex.drive;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mex.drive.model.EMF;

@SuppressWarnings("serial")
public class MEXDriveServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/plain");

    EntityManager em = EMF.get().createEntityManager();

    try {
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      em.close();
    }

    resp.getWriter().println("Hello, world");
  }
}
