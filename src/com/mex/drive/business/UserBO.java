package com.mex.drive.business;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.Directory.Users.Get;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.mex.drive.model.entity.User;
import com.mex.drive.view.endpoint.Constants;

public class UserBO extends BusinessObject {

  public boolean isAuthorized(User user) throws GeneralSecurityException, IOException, URISyntaxException {
    // Connect with admin directory
    Directory adminService = this.getAdminDirectoryService();

    // Create a user request
    Directory.Users usersService = adminService.users();
    Get request = usersService.get(user.getId());

    // Execute the service
    com.google.api.services.admin.directory.model.User foundUser;
    try {
      foundUser = request.execute();
    } catch (GoogleJsonResponseException ex) {
      foundUser = null;
    }

    // Check if the request has found an user
    if ((foundUser != null) && foundUser.getId().equals(foundUser.getId())) {
      user.setEmail(foundUser.getPrimaryEmail());
      user.setName(foundUser.getName().getFullName());
      user.setProfileImageURL(foundUser.getThumbnailPhotoUrl());
      return true;
    }

    // User not found
    return false;
  }

  /**
   * Connect with Google APIs and return an Directory Service
   *
   * @return
   * @throws GeneralSecurityException.
   * @throws IOException
   * @throws URISyntaxException
   */
  private Directory getAdminDirectoryService() throws GeneralSecurityException, IOException, URISyntaxException {
    // Path to key file
    URL pkcs12path = this.getClass().getClassLoader().getResource("META-INF/" + Constants.SERVICE_ACCOUNT_PKCS12);

    // Create a new credential
    JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(Constants.SERVICE_ACCOUNT_EMAIL).setServiceAccountPrivateKeyFromP12File(new File(pkcs12path.toURI()))
        .setServiceAccountScopes(Collections.singleton(DirectoryScopes.ADMIN_DIRECTORY_USER_READONLY)).setServiceAccountUser(Constants.SERVICE_ACCOUNT_USER)
        .build();

    // Get the service
    Directory service = new Directory.Builder(httpTransport, JSON_FACTORY, credential).setHttpRequestInitializer(credential).build();

    return service;

  }

  /**
   * Authorize the user to access the system if the user is not registered in
   * the system before, this method will insert it
   *
   * @param user
   * @throws Exception
   */
  public void authorize(User user) throws Exception {
    if ((user == null) || (user.getId() == null)) {
      return;
    }
    // Check if the user is already registered in the system
    User foundUser = this.get(User.class, user.getKey());

    if (foundUser == null) {
      this.insert(user);
    }
  }
}
