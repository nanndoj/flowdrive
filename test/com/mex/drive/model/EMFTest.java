package com.mex.drive.model;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.mex.drive.model.entity.Request;

public class EMFTest {

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
  public void shouldGetTheSameEntityManagerFactoryInstance() {
    EntityManagerFactory em = EMF.get();
    EntityManagerFactory em2 = EMF.get();

    // They should be the same
    assertEquals(em, em2);
  }

  @Test
  public void shouldPersistBasicEntity() {
    EntityManager em = EMF.get().createEntityManager();
    // Create a new base entity
    Request unit = new Request();
    unit.setProtocolNumber("12387771L");
    unit.setTitle("Test Process Unit");
    // Persist the basic entity
    em.persist(unit);

    // Check if the entity was saved into the datastore
    Request merge = em.merge(unit);
    Request foundEntity = em.find(Request.class, merge.getKey());

    assertEquals(unit.getTitle(), foundEntity.getTitle());

  }
}