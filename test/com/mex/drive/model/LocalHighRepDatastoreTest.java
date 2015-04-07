package com.mex.drive.model;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class LocalHighRepDatastoreTest {

  // maximum eventual consistency
  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
                                                  new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

  @Before
  public void setUp() {
    this.helper.setUp();
  }

  @After
  public void tearDown() {
    this.helper.tearDown();
  }

  @Test
  public void testEventuallyConsistentGlobalQueryResult() {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    Key ancestor = KeyFactory.createKey("foo", 3);
    ds.put(new Entity("yam", ancestor));
    ds.put(new Entity("yam", ancestor));
    // global query doesn't see the data
    assertEquals(0, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
    // ancestor query does see the data
    assertEquals(2, ds.prepare(new Query("yam", ancestor)).countEntities(withLimit(10)));
  }
}