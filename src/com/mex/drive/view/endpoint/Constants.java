package com.mex.drive.view.endpoint;

/**
 * Contains the client IDs and scopes for allowed clients consuming the
 * helloworld API.
 */
public class Constants {
  // Web Applications
  public static final String WEB_CLIENT_ID          = "647668539498-4ipru2kbu7415fhts4hf3l5shvosi68n.apps.googleusercontent.com";
  public static final String SECRET_KEY             = "6Bo0BlJtMnkA02N8C6VnQA8L";

  // Service Account
  public static final String SERVICE_ACCOUNT_ID     = "647668539498-fdlqgjlckqq5lfvfma81rm5j7h255iu2.apps.googleusercontent.com";
  public static final String SERVICE_ACCOUNT_EMAIL  = "647668539498-fdlqgjlckqq5lfvfma81rm5j7h255iu2@developer.gserviceaccount.com";
  public static final String SERVICE_ACCOUNT_USER   = "contato@makerextreme.com.br";

  /** Path to the Service Account's Private Key file */
  public static final String SERVICE_ACCOUNT_PKCS12 = "MexGED-0f8c8f1f95ce.p12";

  // Oauth Scopes
  public static final String SCOPE_EMAIL            = "https://www.googleapis.com/auth/userinfo.profile";
  public static final String SCOPE_PROFILE          = "https://www.googleapis.com/auth/userinfo.email";
  public static final String CALLBACK_URL           = "http://localhost:8888/googleoauth2callback";

}
