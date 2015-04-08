package com.mex.drive.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.mex.drive.model.entity.History;
import com.mex.drive.model.entity.Request;

public class DAOTest {

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
  public void shouldInsertEntityIntoDatastoreWithChildren() throws Exception {
    DAO dao = DAO.getInstance(Request.class);

    Request entity = new Request();
    entity.setProtocolNumber("1234567");
    entity.setTitle("Any subject");

    List<History> list = new ArrayList<History>();
    History history = new History();
    history.setDate(new Date());
    history.setText("Any text");
    list.add(history);

    entity.setHistory(list);

    assertNull(entity.getKey());
    dao.insert(entity);
    assertNotNull(entity.getKey());

    // Check if the children was inserted
    assertNotNull(entity.getHistory());
    assertNotNull(entity.getHistory().get(0));
    assertNotNull(entity.getHistory().get(0).getKey());
  }

  @Test
  public void shouldDeleteEntityFromDatastore() throws Exception {
    DAO dao = DAO.getInstance(Request.class);

    Request entity = new Request();
    entity.setProtocolNumber("1234567");
    entity.setTitle("Any subject");

    List<History> list = new ArrayList<History>();
    History history = new History();
    history.setDate(new Date());
    history.setText("Any text");
    list.add(history);

    entity.setHistory(list);

    // Insert a fake entity
    dao.insert(entity);

    // look for the object to remove
    Request found = dao.get(Request.class, entity.getKey());
    assertNotNull(found);
    assertEquals(found.getKey(), entity.getKey());

    // Remove the object
    dao.delete(found);

    // look for the object to remove
    Request deleted = dao.get(Request.class, entity.getKey());
    assertNull(deleted);

  }

  @Test
  public void shouldGetEntityFromDatastore() throws Exception {
    DAO dao = DAO.getInstance(Request.class);

    Request entity = new Request();
    entity.setProtocolNumber("1234567");
    entity.setTitle("Any subject");

    List<History> list = new ArrayList<History>();
    History history = new History();
    history.setDate(new Date());
    history.setText("Any text");
    list.add(history);

    entity.setHistory(list);

    assertNull(entity.getKey());
    assertNull(history.getKey());

    // Insert a fake entity
    dao.insert(entity);

    // look for the object to remove
    Request found = dao.get(Request.class, entity.getKey());
    assertNotNull(found);
    assertEquals(found.getKey(), entity.getKey());

    // look for the object to remove
    History children = dao.get(History.class, history.getKey());
    assertNotNull(children);
    assertEquals(children.getText(), history.getText());

  }

  @Test
  public void shouldUpdateEntityFromDatastore() throws Exception {
    DAO dao = DAO.getInstance(Request.class);

    Request entity = new Request();
    entity.setProtocolNumber("1234567");
    entity.setTitle("Any subject");

    List<History> list = new ArrayList<History>();
    History history = new History();
    history.setDate(new Date());
    history.setText("Any text");
    list.add(history);

    entity.setHistory(list);

    assertNull(entity.getKey());
    assertNull(history.getKey());

    // Insert a fake entity
    dao.insert(entity);

    // Create a new entity to replace the other one
    Request newUnit = new Request();
    newUnit.setKey(entity.getKey()); // They must have the same ID
    newUnit.setTitle("Text changed");

    dao.update(newUnit);

    // Get the new entity
    Request found = dao.get(Request.class, newUnit.getKey());
    assertNotNull(found);
    assertEquals("Text changed", found.getTitle());
    assertNotNull(found.getHistory());
    assertNotEquals("Any text", found.getHistory().get(0).getText());

  }
}