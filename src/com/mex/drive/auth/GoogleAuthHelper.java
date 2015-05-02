package com.mex.drive.auth;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashSet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mex.drive.view.endpoint.Constants;

/**
 * A helper class for Google's OAuth2 authentication API.
 *
 * @version 20130224
 * @author Matyas Danter
 */
public final class GoogleAuthHelper {

  /**
   * Callback URI that google will redirect to after successful authentication
   */
  // start google authentication constants
  private Collection<String>                scope;
  private static final String               USER_INFO_URL  = "https://www.googleapis.com/oauth2/v1/userinfo";
  private static final JsonFactory          JSON_FACTORY   = new JacksonFactory();
  private static final HttpTransport        HTTP_TRANSPORT = new NetHttpTransport();
  // end google authentication constants

  private String                            stateToken;

  private final GoogleAuthorizationCodeFlow flow;

  /**
   * Constructor initializes the Google Authorization Code Flow with CLIENT ID,
   * SECRET, and SCOPE
   */
  public GoogleAuthHelper() {
    this.scope = new HashSet<String>();
    this.scope.add(Constants.SCOPE_EMAIL);
    this.scope.add(Constants.SCOPE_PROFILE);

    this.flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, Constants.WEB_CLIENT_ID, Constants.SECRET_KEY, this.scope).build();

    this.generateStateToken();
  }

  /**
   * Builds a login URL based on client ID, secret, callback URI, and scope
   */
  public String buildLoginUrl() {

    final GoogleAuthorizationCodeRequestUrl url = this.flow.newAuthorizationUrl();

    return url.setRedirectUri(Constants.CALLBACK_URL).setState(this.stateToken).build();
  }

  /**
   * Generates a secure state token
   */
  private void generateStateToken() {

    SecureRandom sr1 = new SecureRandom();

    this.stateToken = "google;" + sr1.nextInt();

  }

  /**
   * Accessor for state token
   */
  public String getStateToken() {
    return this.stateToken;
  }

  /**
   * Expects an Authentication Code, and makes an authenticated request for the
   * user's profile information
   *
   * @return JSON formatted user profile information
   * @param authCode
   *          authentication code provided by google
   * @throws JSONException
   */
  public JSONObject getUserInfoJson(final String authCode) throws IOException, JSONException {

    final GoogleTokenResponse response = this.flow.newTokenRequest(authCode).setRedirectUri(Constants.CALLBACK_URL).execute();
    final Credential credential = this.flow.createAndStoreCredential(response, null);
    final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
    // Make an authenticated request
    final GenericUrl url = new GenericUrl(USER_INFO_URL);
    final HttpRequest request = requestFactory.buildGetRequest(url);
    request.getHeaders().setContentType("application/json");
    final String jsonIdentity = request.execute().parseAsString();

    return new JSONObject(jsonIdentity);

  }

}