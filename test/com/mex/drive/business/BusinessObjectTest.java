package com.mex.drive.business;

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

public class BusinessObjectTest {

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
  public void shouldInsertEntityWithChildren() throws Exception {
    BusinessObject bo = new BusinessObject() {
    };

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
    bo.insert(entity);
    assertNotNull(entity.getKey());

    // Check if the children was inserted
    assertNotNull(entity.getHistory());
    assertNotNull(entity.getHistory().get(0));
    assertNotNull(entity.getHistory().get(0).getKey());
  }

  @Test
  public void shouldDeleteEntity() throws Exception {
    BusinessObject bo = new BusinessObject() {
    };

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
    bo.insert(entity);

    // look for the object to remove
    Request found = bo.get(Request.class, entity.getKey());
    assertNotNull(found);
    assertEquals(found.getKey(), entity.getKey());

    // Remove the object
    bo.delete(found);

    // look for the object to remove
    Request deleted = bo.get(Request.class, entity.getKey());
    assertNull(deleted);

  }

  @Test
  public void shouldGetEntity() throws Exception {
    BusinessObject bo = new BusinessObject() {
    };

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
    bo.insert(entity);

    // look for the object to remove
    Request found = bo.get(Request.class, entity.getKey());
    assertNotNull(found);
    assertEquals(found.getKey(), entity.getKey());

    // look for the object to remove
    History children = bo.get(History.class, history.getKey());
    assertNotNull(children);
    assertEquals(children.getText(), history.getText());

  }

  @Test
  public void shouldUpdateEntity() throws Exception {
    BusinessObject bo = new BusinessObject() {
    };

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
    bo.insert(entity);

    // Create a new entity to replace the other one
    Request newUnit = new Request();
    newUnit.setKey(entity.getKey()); // They must have the same ID
    newUnit.setTitle("Text changed");

    bo.update(newUnit);

    // Get the new entity
    Request found = bo.get(Request.class, newUnit.getKey());
    assertNotNull(found);
    assertEquals("Text changed", found.getTitle());
    assertNotNull(found.getHistory());
    assertNotEquals("Any text", found.getHistory().get(0).getText());

  }
}